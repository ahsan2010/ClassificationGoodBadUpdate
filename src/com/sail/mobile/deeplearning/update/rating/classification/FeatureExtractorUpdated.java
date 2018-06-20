package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.analysis.adevolution.util.FileUtil;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.SDKInfoLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

/**
 * 
 * @author ahsan
 * 
 *
 */
public class FeatureExtractorUpdated {

	HashMap<String, ArrayList<UpdateTable>> appUpdates;
	Map<String, UpdateRatingInformation> updateRatingInfo;
	Map<String, AdInformation> appUseAd = new HashMap<String, AdInformation>();
	Map<String, SDKCsvInfo> sdkInfo = new HashMap<String, SDKCsvInfo>();
	Set<String> manifestFilesList = new HashSet<String>();

	public static String MANIFEST_FILE_LOCATION = "/home/ahsan/SampleApks/result/AndroidManifest/";
	public static String MISSING_MANIFEST_FILE_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/missing_manifest_files.csv";
	public static String ANDROID_DANGEROUS_PERMISSION_LIST = "/home/ahsan/SampleApks/result/Permissions/dangerous_permission_list.csv";
	public static String ANDROID_ALL_PERMISSION_LIST = "/home/ahsan/SampleApks/result/Permissions/android_permission_list.csv";

	Map<String, String> varifiedAdPackageMapGroup = FileUtil.readVerfiedAdList(Constants.VARIFIED_AD_PACKAGE_LIST);
	Map<String, Double> updateAdSize = new HashMap<String, Double>();
	Map<String, Double> updateSize = new HashMap<String, Double>();
	ArrayList<String> analyzedAppName = new ArrayList<String>();

	double threshold_value = 0.1;

	public final int ZERO_PADDING = 0;
	public final int GOOD_UPDATE = 1;
	public final int BAD_UPDATE = 2;
	public final int NEUTRAL_UPDATE = 3;

	public final int THRESHOLD_REVEIEW = 30;
	public double THRESHOLD = 0.2;
	Map<String, ManifestInformation> manifestList = new HashMap<String, ManifestInformation>();
	Map<String, Double> appUpdateSizeList = AdsInputDataLoader.extractAppSize(Constants.APP_UPDATE_TABLE_PATH);

	public Set<String> convertStringToSet(String info) {
		Set<String> infoList = new HashSet<String>();
		for (String w : info.split("-")) {
			w = w.trim();
			infoList.add(w);
		}
		return infoList;
	}

	public void addManifestInfo() throws Exception {
		CsvReader reader = new CsvReader("/home/ahsan/SampleApks/result/ManifestExtraction/manifest_info.csv");
		reader.readHeaders();

		while (reader.readRecord()) {
			String appName = reader.get("App_Name");
			String versionCode = reader.get("Verson_Code");
			String activityList = reader.get("Activity_List");
			String receiverList = reader.get("Receiver_List");
			String serviceList = reader.get("Service_List");

			String key = appName + Constants.COMMA + versionCode;

			ManifestInformation m = new ManifestInformation();
			m.setActivityList(convertStringToSet(activityList));
			m.setReceiverList(convertStringToSet(receiverList));
			m.setServiceList(convertStringToSet(serviceList));

			manifestList.put(key, m);
		}
		System.out.println("Total Manifest File Information [" + manifestList.size() + "]");
		CsvReader reader2 = new CsvReader("/home/ahsan/SampleApks/result/Permissions/permission_list.csv");
		reader2.readHeaders();
		while (reader2.readRecord()) {
			String appName = reader2.get("App_Name");
			String versionCode = reader2.get("Version_Code");
			String normalPermission = reader2.get("Normal_Permission_List");
			String dangerousPermission = reader2.get("Dangerous_Permission_List");
			String userPermission = reader2.get("User_Permission_List");

			String key = appName + Constants.COMMA + versionCode;

			manifestList.get(key).setNormalPermissionList(convertStringToSet(normalPermission));
			manifestList.get(key).setDangerousPermissionList(convertStringToSet(dangerousPermission));
			manifestList.get(key).setUserPermissionList(convertStringToSet(userPermission));
		}

		System.out.println("Total Manifest File Information [" + manifestList.size() + "]");
	}

	
	public List<String> feature_name = Arrays.asList(new String[] { 
			"DiffTargetMinSDK", "AppUpdateSize", "AdSize",
			"NumberOfAds", "NumberOfPermission", "NumberOfDangerousPermission",
			"NumberOfActivity", "NumberOfReceiver", "NumberOfService", 
			"TargetSDKChange", 
			"MiniumSDKChange", 
			"AdsChange", 
			"ActivityChange",
			"ReceiverChange", 
			"ServiceChange", 
			"PermissionChange", 
			"DangerousPermissionChange",
			"AppSizeChange", 
			"AdsSizeChange", 
			"Duration_Release", "Prev_Neg_Rating_Ratio",
			"Target",
			"Neg_ratio","Neg_Rating_Ratio" });
	
	public List<String> feature_name_original = Arrays.asList(new String[] { 
			"DiffTargetMinSDK", "AppUpdateSize", "AdSize",
			"NumberOfAds", "NumberOfPermission", "NumberOfDangerousPermission",
			"NumberOfActivity", "NumberOfReceiver", "NumberOfService", 
			"TargetSDKChange", 
			"MiniumSDKChange", 
			"AdsChange", 
			"ActivityChange",
			"ReceiverChange", 
			"ServiceChange", 
			"PermissionChange", 
			"DangerousPermissionChange",
			"AppSizeChange", 
			"AdsSizeChange", 
			"Duration_Release", "Prev_Rating", "Prev_Neg_Rating",
			"Target",
			"Neg_ratio" });

	public final int MAX_UPDATE = 10;

	public void readAnalyzedAppName() {
		try {

			CsvReader reader = new CsvReader(
					"/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/updates_per_app.csv");
			reader.readHeaders();
			while (reader.readRecord()) {
				analyzedAppName.add(reader.get("APP_NAME"));
				System.out.println(reader.get("APP_NAME"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSizeInformation() {
		try {
			CsvReader reader = new CsvReader(Constants.APP_SIZE_INFO);
			reader.readHeaders();
			while (reader.readRecord()) {
				String appName = reader.get(0);
				String versionCode = reader.get(1);
				String updateKey = appName + "-" + versionCode;
				int totalCol = reader.getColumnCount();
				Double appSize = Double.parseDouble(reader.get("Total_Size"));
				double adsSize = 0;
				for (int i = 3; i < totalCol; i++) {
					adsSize += Math.ceil(Double.parseDouble(reader.get(i)));
				}
				
				updateSize.put(appName + Constants.COMMA + versionCode, appSize);
				updateAdSize.put(appName + Constants.COMMA + versionCode, adsSize);
				// System.out.println(appName +" " + versionCode +" " + appSize
				// +" " +adsSize);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int getChange(int oldTarget, int presentTarget, Map<String, Double> features, int index) {
		features.put(feature_name.get(index), 0.0);
		if (presentTarget > oldTarget) {
			features.put(feature_name.get(index), 1.0);
			return 1;
		} else if (presentTarget < oldTarget) {
			features.put(feature_name.get(index), -1.0);
			return -1;
		}
		features.put(feature_name.get(index), 0.0);
		return 0;
	}

	int getChange(double oldTarget, double presentTarget, Map<String, Double> features, int index) {
		features.put(feature_name.get(index), 0.0);

		if (presentTarget > oldTarget) {
			features.put(feature_name.get(index), 1.0);
			return 1;
		} else if (presentTarget < oldTarget) {
			features.put(feature_name.get(index + 1), -1.0);
			return -1;
		}
		features.put(feature_name.get(index + 2), 0.0);
		return 0;
	}

	public void setSDKVersion(Map<String, Double> features, int index, int sdkVersion) {

		for (int i = 1; i <= 27; i++) {
			if (i == sdkVersion) {
				features.put(feature_name.get(index), 1.0);
			} else {
				features.put(feature_name.get(index), 0.0);
			}
			index++;
		}
	}

	public double getNegativityRatio(ArrayList<UpdateTable> updates, int index, String appName) {
		double negativitiyRatio = 0.0;

		double negativeRatio = 0.0;
		double negativeReviewBefore = 0.0;
		double totalReviewBefore = 0.0;

		for (int i = index - 1; i >= 0; i--) {

			String beforeKey = appName + Constants.COMMA + updates.get(i).getVERSION_CODE();

			if (!updateRatingInfo.containsKey(beforeKey)) {
				continue;
			}
			totalReviewBefore += updateRatingInfo.get(beforeKey).getTotalStar();
			negativeReviewBefore += updateRatingInfo.get(beforeKey).getOneStar()
					+ updateRatingInfo.get(beforeKey).getTwoStar();
		}

		negativeReviewBefore = negativeReviewBefore / totalReviewBefore;
		String key = appName + Constants.COMMA + updates.get(index).getVERSION_CODE();
		negativeRatio = (updateRatingInfo.get(key).getOneStar() + updateRatingInfo.get(key).getTwoStar())
				/ (double) (updateRatingInfo.get(key).getTotalStar());

		negativitiyRatio = negativeRatio / negativeReviewBefore;

		return negativitiyRatio;
	}

	public Map<String, Integer> getManifestInfo(String key) {
		Map<String, Integer> result = new HashMap<String, Integer>();

		ManifestInformation m = manifestList.get(key);
		result.put("PERMISSION", m.getNormalPermissionList().size());
		result.put("DANGEROUS_PERMISSION", m.getDangerousPermissionList().size());
		result.put("OTHER_PERMISSION", m.userPermissionList.size());
		result.put("ACTIVITY", m.getActivityList().size());
		result.put("RECEIVER", m.getReceiverList().size());
		result.put("SERVICE", m.getServiceList().size());

		return result;
	}

	public Map<String, Double> generateFeatures(String appName, UpdateTable presentUpdate, UpdateTable oldUpdate,
			Parser p, int index, ArrayList<UpdateTable> updates) {

		Map<String, Double> features = new HashMap<String, Double>();

		try {
			String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();
			String beforeKey = appName + Constants.COMMA + oldUpdate.getVERSION_CODE();

			Map<String, Integer> manifestInfoOld = getManifestInfo(beforeKey);
			Map<String, Integer> manifestInfoPresent = getManifestInfo(updateKey);

			if ((updateRatingInfo.get(beforeKey).getTotalStar() < THRESHOLD_REVEIEW)
					|| (updateRatingInfo.get(updateKey).getTotalStar() < THRESHOLD_REVEIEW)) {
				return null;
			}

			double beforeRating = updateRatingInfo.get(beforeKey).getAggregatedRating();
			double updateRating = updateRatingInfo.get(updateKey).getAggregatedRating();

			features.put(feature_name.get(0),
					(double) Math.abs(sdkInfo.get(updateKey).targetSDK) - sdkInfo.get(updateKey).minimumSDK);
			features.put(feature_name.get(1), (double) updateSize.get(updateKey));
			features.put(feature_name.get(2), (double) updateAdSize.get(updateKey));
			if (appUseAd.containsKey(updateKey)) {
				features.put(feature_name.get(3), (double) appUseAd.get(updateKey).getAdsNames().size());
			} else {
				features.put(feature_name.get(3), (double) 0);
			}

			features.put(feature_name.get(4), (double) manifestInfoPresent.get("PERMISSION"));
			features.put(feature_name.get(5), (double) manifestInfoPresent.get("DANGEROUS_PERMISSION"));
			
			features.put(feature_name.get(6), (double) manifestInfoPresent.get("ACTIVITY"));
			features.put(feature_name.get(7), (double) manifestInfoPresent.get("RECEIVER"));
			features.put(feature_name.get(8), (double) manifestInfoPresent.get("SERVICE"));

			getChange(sdkInfo.get(beforeKey).targetSDK, sdkInfo.get(updateKey).targetSDK, features, 9);
			getChange(sdkInfo.get(beforeKey).minimumSDK, sdkInfo.get(updateKey).minimumSDK, features, 10);

			int presentAd = 0;
			int oldAd = 0;
			if (appUseAd.containsKey(updateKey)) {
				presentAd = appUseAd.get(updateKey).getAdsNames().size();
			}
			if (appUseAd.containsKey(beforeKey)) {
				oldAd = appUseAd.get(beforeKey).getAdsNames().size();
			}

			getChange(oldAd, presentAd, features, 11);
			getChange(manifestInfoPresent.get("ACTIVITY"), manifestInfoOld.get("ACTIVITY"), features, 12);
			getChange(manifestInfoPresent.get("RECEIVER"), manifestInfoOld.get("RECEIVER"), features, 13);
			getChange(manifestInfoPresent.get("SERVICE"), manifestInfoOld.get("SERVICE"), features, 14);
			getChange(manifestInfoPresent.get("PERMISSION"), manifestInfoOld.get("PERMISSION"), features, 15);
			getChange(manifestInfoPresent.get("DANGEROUS_PERMISSION"), manifestInfoOld.get("DANGEROUS_PERMISSION"),
					features, 16);

			getChange(updateSize.get(beforeKey), updateSize.get(updateKey), features, 17);
			getChange(updateAdSize.get(beforeKey), updateAdSize.get(updateKey), features, 18);

			features.put(feature_name.get(19),
					(double) Days
							.daysBetween(DateUtil.formatterWithHyphen.parseDateTime(oldUpdate.getRELEASE_DATE()),
									DateUtil.formatterWithHyphen.parseDateTime(presentUpdate.getRELEASE_DATE()))
							.getDays());
		
			//features.put(feature_name.get(20), beforeRating);
			features.put(feature_name.get(20), updateRatingInfo.get(beforeKey).getNegativeRatingRatio());
			
			/*setSDKVersion(features, 46, sdkInfo.get(updateKey).targetSDK);
			setSDKVersion(features, 73, sdkInfo.get(updateKey).targetSDK);*/

			double negativityRatio = getNegativityRatio(updates, index, appName);
			double afterNegativity = updateRatingInfo.get(updateKey).getNegativeRatingRatio();

			
			if (negativityRatio >= 1.0 + THRESHOLD) {
				features.put(feature_name.get(21), (double) BAD_UPDATE);
			} 
			else if (negativityRatio <= 1.0 - THRESHOLD) {
				features.put(feature_name.get(21), (double) GOOD_UPDATE);
			}
			else {
				features.put(feature_name.get(21), (double) NEUTRAL_UPDATE);
			}
			
			/*if(afterNegativity > 15){
				features.put(feature_name.get(22), (double) BAD_UPDATE);
			}else if (afterNegativity < 5){
				features.put(feature_name.get(22), (double) GOOD_UPDATE);
			}else{
				features.put(feature_name.get(22), (double) NEUTRAL_UPDATE);
			}*/
			
			features.put(feature_name.get(22), negativityRatio);
			features.put(feature_name.get(23), afterNegativity);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return features;

	}

	public boolean checkValidUpdate(String appName, UpdateTable presentUpdate, UpdateTable oldUpdate) {
		String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();
		String beforeKey = appName + Constants.COMMA + oldUpdate.getVERSION_CODE();

		if (!updateRatingInfo.containsKey(beforeKey) || !updateRatingInfo.containsKey(updateKey)) {
			return false;
		}

		if (!sdkInfo.containsKey(beforeKey) || !sdkInfo.containsKey(updateKey)) {
			return false;
		}

		if (!manifestList.containsKey(updateKey) || !manifestList.containsKey(beforeKey)) {
			return false;
		}

		if (!updateSize.containsKey(beforeKey) || !updateSize.containsKey(updateKey)) {
			return false;
		}

		if (!updateAdSize.containsKey(beforeKey) || !updateAdSize.containsKey(updateKey)) {
			return false;
		}

		return true;
	}

	public boolean checkValidUpdate(String appName, UpdateTable presentUpdate) {
		String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();

		String updateManifestKey = appName + "-" + presentUpdate.getVERSION_CODE();

		if (!updateRatingInfo.containsKey(updateKey)) {
			System.out.println("Missin: " + appName + " " + presentUpdate.getVERSION_CODE() + " "
					+ presentUpdate.getRELEASE_DATE());
			return false;
		}

		if (!sdkInfo.containsKey(updateKey)) {
			return false;
		}

		if (!manifestList.containsKey(updateKey)) {
			return false;
		}

		if (!updateSize.containsKey(updateKey)) {
			return false;
		}

		if (!updateAdSize.containsKey(updateKey)) {
			return false;
		}
		return true;
	}

	public int numberOfValidUpdate(String appName, ArrayList<UpdateTable> updates) {

		int validUpdates = 0;
		for (int i = 0; i < updates.size(); i++) {
			if (checkValidUpdate(appName, updates.get(i))) {
				validUpdates++;
			}
		}
		return validUpdates;
	}

	public void FeatureExtractor() throws Exception {

		int missing_feature_generation_update = 0;
		Parser p = new Parser();

		String path = "/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data_June/update_feature/";

		CsvWriter trianingSeqWriter = new CsvWriter(path + "updated_100_"+THRESHOLD+".csv");

		trianingSeqWriter.write("AppName");
		trianingSeqWriter.write("VersionCode");

		for (int j = 0; j < feature_name.size(); j++) {
			trianingSeqWriter.write(feature_name.get(j));
		}

		trianingSeqWriter.endRecord();

		Set<String> androidDangeroudPermission = AdsInputDataLoader
				.readAndroidPermission(ANDROID_DANGEROUS_PERMISSION_LIST);// android.permissions.WRITE
		Set<String> androidPermissions = AdsInputDataLoader.readAndroidPermission(ANDROID_ALL_PERMISSION_LIST); // android.permissions.WRITE
		Set<String> manifestFileList = AdsInputDataLoader.loadManifestFileList();
		
		int missingUpdate = 0;
		int total_analyzed_updates = 0;
		int missingUpdateManifest = 0;
		int missingSDKInfo = 0;

		for (String appName : analyzedAppName) {

			ArrayList<Features> appFeature = new ArrayList<Features>();
			ArrayList<UpdateTable> updates = appUpdates.get(appName);

			// System.out.println("App Name [" + appName + "]");

			int totalUpdate = 0;
			int validRatingUpdate = 0;

			UpdateTable oldUpdate = updates.get(0);
			for (int i = 1; i < updates.size(); i++) {
				totalUpdate++;
				UpdateTable presentUpdate = updates.get(i);

				if (!checkValidUpdate(appName, presentUpdate, oldUpdate)) {
					oldUpdate = presentUpdate;
					continue;
				}

				Map<String, Double> featureValue = generateFeatures(appName, presentUpdate, oldUpdate, p, i, updates);
				if (featureValue == null) {
					missing_feature_generation_update++;
					continue;
				}
				Features feature = new Features();
				feature.setAppName(appName);
				feature.setVersionCode(presentUpdate.getVERSION_CODE());
				feature.setUpdateIndex(i);
				feature.setFeatureValue(featureValue);

				appFeature.add(feature);

				validRatingUpdate++;
				total_analyzed_updates++;

				oldUpdate = presentUpdate;
			}
			writeFeatureDataIII(trianingSeqWriter, appFeature, 50, appName);

		}

		trianingSeqWriter.close();

		System.out.println("------------------------------------------------------");

		System.out.println("Problem feature genreation [" + missing_feature_generation_update + "] updates");
		System.out.println("Total analyzed updates [" + total_analyzed_updates + "]");
	}

	public void writeFeatureDataIII(CsvWriter trainingWriter, ArrayList<Features> appFeatures, int maxSeq,
			String appName) {
		try {
			int index = appFeatures.size() - maxSeq;

			if (index > 0) {
				for (int i = index; i < appFeatures.size(); i++) {
					trainingWriter.write(appFeatures.get(i).getAppName());
					trainingWriter.write(appFeatures.get(i).getVersionCode());
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter
								.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
					}
					trainingWriter.endRecord();
				}
				return;
			}

			if (index < 0) {
				for (int i = 0; i < appFeatures.size(); i++) {
					trainingWriter.write(appFeatures.get(i).getAppName());
					trainingWriter.write(appFeatures.get(i).getVersionCode());
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter
								.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
					}
					trainingWriter.endRecord();
				}

				/*// Add Zero Padding

				for (int i = 0; i < Math.abs(index); i++) {
					trainingWriter.write(appName);
					trainingWriter.write("?");
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter.write("0");
					}
					trainingWriter.endRecord();
				}*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() throws Exception {
		readAnalyzedAppName();
		SDKInfoLoader sdkInfoLoader = new SDKInfoLoader();
		sdkInfoLoader.readCSV();
		sdkInfo = sdkInfoLoader.getSDKInfo();
		RatingAnalyzer rat = new RatingAnalyzer();
		updateRatingInfo = rat.generateUpdateRating();
		appUpdates = AdsInputDataLoader.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
		appUseAd = AdsInputDataLoader.readAdLibraryInformation();
		addManifestInfo();

	}

	public void test() {
		int validUpdate = 0;
		int problemUpdate = 0;
		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			for (int i = 0; i < updates.size(); i++) {
				UpdateTable presentUpdate = updates.get(i);
				if (!checkValidUpdate(appName, presentUpdate)) {
					problemUpdate++;
					System.out.println(
							appName + " " + updates.get(i).getVERSION_CODE() + " " + updates.get(i).getRELEASE_DATE());
					continue;
				}
				validUpdate++;
			}
		}
		System.out.println("Valid update [" + validUpdate + "]");
		System.out.println("Problem in update [" + problemUpdate + "]");
	}

	public static void main(String[] args) throws Exception {
		FeatureExtractorUpdated ob = new FeatureExtractorUpdated();
		ob.loadSizeInformation();
		ob.init();
		// ob.test();
		ob.FeatureExtractor();
		// ob.addManifestInfo();
		System.out.println("Program finishes successfully");
	}

}
