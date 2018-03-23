package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.analysis.differentiation.FiniteDifferencesDifferentiator;
import org.apache.derby.tools.sysinfo;
import org.joda.time.DateTime;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.analysis.adevolution.util.FileUtil;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.SDKInfoLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

/**
 * 
 * @author ahsan
 * 
 *
 */
public class FeatureExtractor {

	HashMap<String, ArrayList<UpdateTable>> appUpdates;
	Map<String, UpdateRatingInformation> updateRatingInfo;
	Map<String, AdInformation> appUseAd = new HashMap<String, AdInformation>();
	Map<String, SDKCsvInfo> sdkInfo = new HashMap<String, SDKCsvInfo>();
	Set<String> manifestFilesList = new HashSet<String>();

	public static String MANIFEST_FILE_LOCATION = "/home/ahsan/SampleApks/result/AndroidManifest/";
	public static String MISSING_MANIFEST_FILE_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/missing_manifest_files.csv";
	public static String ANDROID_DANGEROUS_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/dangerous_permission_list.csv";
	public static String ANDROID_ALL_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/android_permission_list.csv";

	Map<String, String> varifiedAdPackageMapGroup = FileUtil.readVerfiedAdList(Constants.VARIFIED_AD_PACKAGE_LIST);
	Map<String, Double> updateAdSize = new HashMap<String, Double>();
	Map<String, Double> updateSize = new HashMap<String, Double>();
	ArrayList<String> analyzedAppName = new ArrayList<String>();
	public final int GOOD_UPDATE = 0;
	public final int BAD_UPDATE = 1;

	public List<String> feature_name = Arrays.asList(new String[] {
			"TargetSDK", "MinimumSDK", "DiffTargetMinSDK","AppUpdateSize", "AdSize", 
			"NumberOfAds", "NumberOfPermission","NumberOfDangerousPermission","NumberOfOtherPermission", "NumberOfActivity", 
			"NumberOfReceiver", "NumberOfService", "TargetSDKChange","MiniumSDKChange", "AdsChange", 
			"ActivityChange", "ReceiverChange", "ServiceChange", "PermissionChange","DangerousPermissionChange", 
			"OtherPermissionChange", "AppSizeChange", "AdsSizeChange","Target" 
			 });

	
	public final int MAX_UPDATE = 12;
	
	public void readAnalyzedAppName (){
		try{
			
			CsvReader reader = new CsvReader("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/updates_per_app.csv");
			reader.readHeaders();
			while(reader.readRecord()){
				analyzedAppName.add(reader.get("APP_NAME"));
				System.out.println(reader.get("APP_NAME"));
			}
			
		}catch(Exception e){
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

				int totalCol = reader.getColumnCount();
				Double appSize = Math.ceil(Double.parseDouble(reader.get(2)));
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

	public int getFirstUpdateIndex(ArrayList<UpdateTable> updates) {

		for (int i = 0; i < updates.size(); i++) {
			UpdateTable update = updates.get(i);
			DateTime releaseTime = DateUtil.formatterWithHyphen.parseDateTime(update.getRELEASE_DATE());
			if (releaseTime.isAfter(Constants.EPERIMENT_START_TIME.minusDays(1))) {
				// get the previous update. our analysis start 2016-04-20. we
				// only considering the last immediate update of this time
				if (i > 0) {
					return (i - 1);
				} else if (i == 0) {
					return i;
				}

			}
		}
		return -1;
	}

	int getChange(int oldTarget, int presentTarget) {
		if (presentTarget > oldTarget) {
			return 2;
		} else if (presentTarget < oldTarget) {
			return 3;
		}
		return 1;
	}

	int getChange(double oldTarget, double presentTarget) {
		if (presentTarget > oldTarget) {
			return 2;
		} else if (presentTarget < oldTarget) {
			return 3;
		}
		return 1;
	}

	public Map<String, Double> generateFeatures(String appName, UpdateTable presentUpdate, UpdateTable oldUpdate,
			Parser p) {

		Map<String, Double> features = new HashMap<String, Double>();

		try {
			String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();
			String beforeKey = appName + Constants.COMMA + oldUpdate.getVERSION_CODE();

			String previousManifestKey = MANIFEST_FILE_LOCATION + "AndroidManifest-" + appName + "-"
					+ oldUpdate.getVERSION_CODE() + "-" + oldUpdate.getRELEASE_DATE().replace("-", "_") + ".xml";
			String presentManifestKey = MANIFEST_FILE_LOCATION + "AndroidManifest-" + appName + "-"
					+ presentUpdate.getVERSION_CODE() + "-" + presentUpdate.getRELEASE_DATE().replace("-", "_")
					+ ".xml";

			Map<String, Integer> manifestInfoOld = p.ParseXml(previousManifestKey);
			Map<String, Integer> manifestInfoPresent = p.ParseXml(presentManifestKey);

			double beforeRating = updateRatingInfo.get(beforeKey).getAggregatedRating();
			double updateRating = updateRatingInfo.get(updateKey).getAggregatedRating();

			features.put(feature_name.get(0), (double) sdkInfo.get(updateKey).targetSDK);
			features.put(feature_name.get(1), (double) sdkInfo.get(updateKey).minimumSDK);
			features.put(feature_name.get(2),
					(double) Math.abs(sdkInfo.get(updateKey).targetSDK) - sdkInfo.get(updateKey).minimumSDK);
			features.put(feature_name.get(3), (double) updateSize.get(updateKey));
			features.put(feature_name.get(4), (double) updateAdSize.get(updateKey));
			if (appUseAd.containsKey(updateKey)) {
				features.put(feature_name.get(5), (double) appUseAd.get(updateKey).getAdsNames().size());
			} else {
				features.put(feature_name.get(5), (double) 0);
			}

			features.put(feature_name.get(6), (double) manifestInfoPresent.get("PERMISSION"));
			features.put(feature_name.get(7), (double) manifestInfoPresent.get("DANGEROUS_PERMISSION"));
			features.put(feature_name.get(8), (double) manifestInfoPresent.get("OTHER_PERMISSION"));
			features.put(feature_name.get(9), (double) manifestInfoPresent.get("ACTIVITY"));
			features.put(feature_name.get(10), (double) manifestInfoPresent.get("RECEIVER"));
			features.put(feature_name.get(11), (double) manifestInfoPresent.get("SERVICE"));

			features.put(feature_name.get(12),
					(double) getChange(sdkInfo.get(beforeKey).targetSDK, sdkInfo.get(updateKey).targetSDK));
			features.put(feature_name.get(13),
					(double) getChange(sdkInfo.get(beforeKey).minimumSDK, sdkInfo.get(updateKey).minimumSDK));

			int presentAd = 0;
			int oldAd = 0;
			if (appUseAd.containsKey(updateKey)) {
				presentAd = appUseAd.get(updateKey).getAdsNames().size();
			}
			if (appUseAd.containsKey(beforeKey)) {
				oldAd = appUseAd.get(beforeKey).getAdsNames().size();
			}

			features.put(feature_name.get(14), (double) getChange(oldAd, presentAd));
			features.put(feature_name.get(15),
					(double) getChange(manifestInfoPresent.get("ACTIVITY"), manifestInfoOld.get("ACTIVITY")));
			features.put(feature_name.get(16),
					(double) getChange(manifestInfoPresent.get("RECEIVER"), manifestInfoOld.get("RECEIVER")));
			features.put(feature_name.get(17),
					(double) getChange(manifestInfoPresent.get("SERVICE"), manifestInfoOld.get("SERVICE")));
			features.put(feature_name.get(18),
					(double) getChange(manifestInfoPresent.get("PERMISSION"), manifestInfoOld.get("PERMISSION")));
			features.put(feature_name.get(19), (double) getChange(manifestInfoPresent.get("DANGEROUS_PERMISSION"),
					manifestInfoOld.get("DANGEROUS_PERMISSION")));
			features.put(feature_name.get(20), (double) getChange(manifestInfoPresent.get("OTHER_PERMISSION"),
					manifestInfoOld.get("OTHER_PERMISSION")));

			features.put(feature_name.get(21),
					(double) getChange(updateSize.get(beforeKey), updateSize.get(updateKey)));
			features.put(feature_name.get(22),
					(double) getChange(updateAdSize.get(beforeKey), updateAdSize.get(updateKey)));

			if (updateRating > beforeRating) {
				features.put(feature_name.get(23), (double) GOOD_UPDATE);
			} else {
				features.put(feature_name.get(23), (double) BAD_UPDATE);
			}

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
		double beforeRating = updateRatingInfo.get(beforeKey).getAggregatedRating();
		double updateRating = updateRatingInfo.get(updateKey).getAggregatedRating();

		String previousManifestKey = MANIFEST_FILE_LOCATION + "AndroidManifest-" + appName + "-"
				+ oldUpdate.getVERSION_CODE() + "-"
				+ oldUpdate.getRELEASE_DATE().replace("-", "_") + ".xml";
		String presentManifestKey = MANIFEST_FILE_LOCATION + "AndroidManifest-" + appName + "-"
				+ presentUpdate.getVERSION_CODE() + "-" + presentUpdate.getRELEASE_DATE().replace("-", "_") + ".xml";

		if (!sdkInfo.containsKey(beforeKey) || !sdkInfo.containsKey(updateKey)) {
			return false;
		}

		if (!manifestFilesList.contains(previousManifestKey) || !manifestFilesList.contains(presentManifestKey)) {
			return false;
		}
		
		return true;
	}

	public void FeatureExtractor() throws Exception {
		
		
		int missing_feature_generation_update = 0;
		Parser p = new Parser();

		CsvWriter writer = new CsvWriter(
				"/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/updates_Data.csv");
		writer.write("AppName");
		writer.write("VersionCode");
		
		
		CsvWriter trianingWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/training_nn.csv");
		CsvWriter testingWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/testing_nn.csv");
		
		trianingWriter.write("AppName");
		trianingWriter.write("VersionCode");
		testingWriter.write("AppName");
		testingWriter.write("VersionCode");
		
		
		
		for (int j = 0; j < feature_name.size(); j++) {
			writer.write(feature_name.get(j));
			trianingWriter.write(feature_name.get(j));
			testingWriter.write(feature_name.get(j));
		}
		writer.endRecord();
		testingWriter.endRecord();
		trianingWriter.endRecord();
		
		
		Set<String> androidDangeroudPermission = AdsInputDataLoader
				.readAndroidPermission(ANDROID_DANGEROUS_PERMISSION_LIST);// android.permissions.WRITE
		Set<String> androidPermissions = AdsInputDataLoader.readAndroidPermission(ANDROID_ALL_PERMISSION_LIST); // android.permissions.WRITE
		Set<String> manifestFileList = AdsInputDataLoader.loadManifestFileList();
		// "AndroidManifest-"+appName+"-"+ versionCode +"-" +
		// releaseDaet.replace("-", "_")+".xml";

		int missingUpdate = 0;
		int total_analyzed_updates = 0;
		int missingUpdateManifest = 0;
		int missingSDKInfo = 0;
		
		
		for (String appName : analyzedAppName) {
		
			ArrayList<Features> appFeature = new ArrayList<Features>();
			
			int total_update = 0;
			
			/*if (!appName.equals("com.pipcamera.activity")) {
				continue;
			}
			*/
			
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			
			
			System.out.println("App Name ["+appName+"]");
			
			int firstUpdate = getFirstUpdateIndex(updates);
			int totalUpdate = 0;
			if (firstUpdate < 0) {
				continue;
			}

			int validRatingUpdate = 0;
			
			for (int i = firstUpdate + 1; i < updates.size(); i++) {
				totalUpdate++;
				UpdateTable presentUpdate = updates.get(i);
				UpdateTable oldUpdate = updates.get(i - 1);

				if(checkValidUpdate(appName, presentUpdate, oldUpdate)){
					continue;
				}

				//This is valid update
				Map<String,Double> featureValue = generateFeatures(appName, presentUpdate, oldUpdate, p);
				if( featureValue == null ) {
					missing_feature_generation_update ++;
					continue;
				}
				Features feature = new Features();
				feature.setAppName(appName);
				feature.setVersionCode(presentUpdate.getVERSION_CODE());
				feature.setUpdateIndex(i);
				feature.setFeatureValue(featureValue);
				
				appFeature.add(feature);
				
				writeFeatureData(writer, featureValue, appName, presentUpdate.getVERSION_CODE());
				
				
				
				validRatingUpdate++;
				total_analyzed_updates ++;
				++ total_update;
			}
			writeFeatureData(trianingWriter,testingWriter,appFeature);
			addZeroPadding(writer, total_update, appName);
		}

		writer.close();
		trianingWriter.close();
		testingWriter.close();

		System.out.println("------------------------------------------------------");
		
		System.out.println("Problem feature genreation ["+missing_feature_generation_update+"] updates");
		System.out.println("Total analyzed updates [" + total_analyzed_updates + "]");
	}

	public void addZeroPadding(CsvWriter writer, int analyzedUpdate,String appName){
		try{
			
			for(int i = 0 ; i < (MAX_UPDATE - analyzedUpdate) ; i ++ ){
				writer.write(appName);
				writer.write("?");
				for (int j = 0; j < feature_name.size(); j++) {
					writer.write("0");
				}
				writer.endRecord();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void writeFeatureData(CsvWriter writer, Map<String, Double> features, String appName, String versinoCopde) {
		try {
			writer.write(appName);
			writer.write(versinoCopde);
			for (int j = 0; j < feature_name.size(); j++) {
				writer.write(Double.toString(features.get(feature_name.get(j))));
			}
			writer.endRecord();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFeatureData(CsvWriter trainingWriter,CsvWriter testingWriter, ArrayList<Features> appFeatures) {
		try {
			
			// Writing training information
			for(int i = 0 ; i < appFeatures.size() - 1 ; i ++ ){
				trainingWriter.write(appFeatures.get(i).getAppName());
				trainingWriter.write(appFeatures.get(i).getVersionCode());
				for (int j = 0; j < feature_name.size(); j++) {
					trainingWriter.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
				}
				trainingWriter.endRecord();
			}
			
			// Writing the last as testing features for each of the analyzed update
			testingWriter.write(appFeatures.get(appFeatures.size() - 1).getAppName());
			testingWriter.write(appFeatures.get(appFeatures.size() - 1).getVersionCode());
			for (int j = 0; j < feature_name.size(); j++) {
				testingWriter.write(Double.toString(appFeatures.get(appFeatures.size() - 1).getFeatureValue().get(feature_name.get(j))));
			}
			testingWriter.endRecord();			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		readAnalyzedAppName();
		SDKInfoLoader sdkInfoLoader = new SDKInfoLoader();
		sdkInfoLoader.readCSV();
		sdkInfo = sdkInfoLoader.getSDKInfo();
		manifestFilesList = AdsInputDataLoader.loadManifestFileList();
		RatingAnalyzer rat = new RatingAnalyzer();
		updateRatingInfo = rat.generateUpdateRating();
		appUpdates = AdsInputDataLoader.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
		appUseAd = AdsInputDataLoader.readAdLibraryInformation();
		
		
	}

	public static void main(String[] args) throws Exception {
		FeatureExtractor ob = new FeatureExtractor();
		ob.loadSizeInformation();
		ob.init();
		ob.FeatureExtractor();
		System.out.println("Program finishes successfully");
	}

}
