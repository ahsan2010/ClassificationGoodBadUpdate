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
import org.joda.time.Days;
import org.netlib.util.intW;

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
public class FeatureExtractor3 {

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
	
	double threshold_value = 0.1;
	
	public final int ZERO_PADDING = 0;
	public final int GOOD_UPDATE = 0;
	public final int BAD_UPDATE = 1;
	public final int NEUTRAL_UPDATE = 2;
	
	public final int THRESHOLD_REVEIEW = 30;

	public List<String> feature_name = Arrays.asList(new String[] {
			"DiffTargetMinSDK","AppUpdateSize", "AdSize", 
			"NumberOfAds", "NumberOfPermission","NumberOfDangerousPermission","NumberOfOtherPermission", "NumberOfActivity", 
			"NumberOfReceiver", "NumberOfService", 
			"TargetSDKChange_Increase","TargetSDKChange_Decrease","TargetSDKChange_Nochange",
			"MiniumSDKChange_Increase", "MiniumSDKChange_Decrease","MiniumSDKChange_Nochange",
			"AdsChange_Increase","AdsChange_Decrease","AdsChange_Nochange", 
			"ActivityChange_Increase","ActivityChange_Decrease","ActivityChange_Nochange", 
			"ReceiverChange_Increase","ReceiverChange_Decrease","ReceiverChange_Nochange", 
			"ServiceChange_Increase","ServiceChange_Decrease","ServiceChange_Nochange", 
			"PermissionChange_Increase","PermissionChange_Decrease","PermissionChange_Nochange",
			"DangerousPermissionChange_Increase","DangerousPermissionChange_Decrease","DangerousPermissionChange_Nochange", 
			"OtherPermissionChange_Increase","OtherPermissionChange_Decrease","OtherPermissionChange_Nochange", 
			"AppSizeChange_Increase","AppSizeChange_Decrease","AppSizeChange_Nochange", 
			"AdsSizeChange_Increase","AdsSizeChange_Decrease","AdsSizeChange_Nochange",
			"Duration_Release",
			"Prev_Rating","Prev_Neg_Rating",
			"TargetSDK_1","TargetSDK_2","TargetSDK_3","TargetSDK_4","TargetSDK_5","TargetSDK_6","TargetSDK_7","TargetSDK_8","TargetSDK_9","TargetSDK_10",
			"TargetSDK_11","TargetSDK_12","TargetSDK_13","TargetSDK_14","TargetSDK_15","TargetSDK_16","TargetSDK_17","TargetSDK_18","TargetSDK_19","TargetSDK_20",
			"TargetSDK_21","TargetSDK_22","TargetSDK_23","TargetSDK_24","TargetSDK_25","TargetSDK_26","TargetSDK_27",
			"MinimumSDK_1","MinimumSDK_2","MinimumSDK_3","MinimumSDK_4","MinimumSDK_5","MinimumSDK_6","MinimumSDK_7","MinimumSDK_8","MinimumSDK_9","MinimumSDK_10",
			"MinimumSDK_11","MinimumSDK_12","MinimumSDK_13","MinimumSDK_14","MinimumSDK_15","MinimumSDK_16","MinimumSDK_17","MinimumSDK_18","MinimumSDK_19","MinimumSDK_20",
			"MinimumSDK_21","MinimumSDK_22","MinimumSDK_23","MinimumSDK_24","MinimumSDK_25","MinimumSDK_26","MinimumSDK_27",
			"Target" 
			 });

	
	public final int MAX_UPDATE = 10;
	
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

	int getChange(int oldTarget, int presentTarget,Map<String, Double> features,int index) {
		features.put(feature_name.get(index), 0.0);
		features.put(feature_name.get(index + 1), 0.0);
		features.put(feature_name.get(index + 2), 0.0);
		
		if (presentTarget > oldTarget) {
			features.put(feature_name.get(index), 1.0);
			return 2;
		} else if (presentTarget < oldTarget) {
			features.put(feature_name.get(index + 1), 1.0);
			return 3;
		}
		features.put(feature_name.get(index + 2), 1.0);
		return 1;
	}

	int getChange(double oldTarget, double presentTarget, Map<String, Double> features,int index) {
		features.put(feature_name.get(index), 0.0);
		features.put(feature_name.get(index + 1), 0.0);
		features.put(feature_name.get(index + 2), 0.0);
		
		if (presentTarget > oldTarget) {
			features.put(feature_name.get(index), 1.0);
			return 2;
		} else if (presentTarget < oldTarget) {
			features.put(feature_name.get(index + 1), 1.0);
			return 3;
		}
		features.put(feature_name.get(index + 2), 1.0);
		return 1;
	}

	public void setSDKVersion(Map<String, Double> features,int index, int sdkVersion){
		
		for(int i = 1 ; i <= 27 ; i ++ ){
			if(i == sdkVersion){
				features.put(feature_name.get(index), 1.0);
			}else{
				features.put(feature_name.get(index), 0.0);
			}
			index ++ ;
		}
	}
	
	
	public double getNegativityRatio(ArrayList<UpdateTable> updates, int index, String appName){
		double negativitiyRatio = 0.0;
		
		double negativeRatio = 0.0;
		double negativeReviewBefore = 0.0;
		double totalReviewBefore = 0.0;
		
		for(int i = index - 1; i >= 0 ; i --){
			String beforeKey = appName + Constants.COMMA + updates.get(i).getVERSION_CODE();
			totalReviewBefore += updateRatingInfo.get(beforeKey).getTotalStar();
			negativeReviewBefore += updateRatingInfo.get(beforeKey).getOneStar() + updateRatingInfo.get(beforeKey).getTwoStar();
		}
		
		negativeReviewBefore = negativeReviewBefore/totalReviewBefore;
		String key = appName + Constants.COMMA + updates.get(index).getVERSION_CODE();
		negativeRatio = (updateRatingInfo.get(key).getOneStar() + updateRatingInfo.get(key).getTwoStar())/(double)(updateRatingInfo.get(key).getTotalStar());
		
		
		negativitiyRatio = negativeRatio/negativeReviewBefore;
		
		
		
		return negativitiyRatio;
	}
	
	
	public Map<String, Double> generateFeatures(String appName, UpdateTable presentUpdate, UpdateTable oldUpdate,
			Parser p, int index, ArrayList<UpdateTable> updates) {

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
			
			
			if((updateRatingInfo.get(beforeKey).getTotalStar() < THRESHOLD_REVEIEW) || (updateRatingInfo.get(updateKey).getTotalStar() < THRESHOLD_REVEIEW)){
				return null;
			}

			double beforeRating = updateRatingInfo.get(beforeKey).getAggregatedRating();
			double updateRating = updateRatingInfo.get(updateKey).getAggregatedRating();

			//features.put(feature_name.get(0), (double) sdkInfo.get(updateKey).targetSDK);
			//features.put(feature_name.get(1), (double) sdkInfo.get(updateKey).minimumSDK);
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
			features.put(feature_name.get(6), (double) manifestInfoPresent.get("OTHER_PERMISSION"));
			features.put(feature_name.get(7), (double) manifestInfoPresent.get("ACTIVITY"));
			features.put(feature_name.get(8), (double) manifestInfoPresent.get("RECEIVER"));
			features.put(feature_name.get(9), (double) manifestInfoPresent.get("SERVICE"));

			getChange(sdkInfo.get(beforeKey).targetSDK, sdkInfo.get(updateKey).targetSDK,features, 10);
			getChange(sdkInfo.get(beforeKey).minimumSDK, sdkInfo.get(updateKey).minimumSDK, features, 13);

			int presentAd = 0;
			int oldAd = 0;
			if (appUseAd.containsKey(updateKey)) {
				presentAd = appUseAd.get(updateKey).getAdsNames().size();
			}
			if (appUseAd.containsKey(beforeKey)) {
				oldAd = appUseAd.get(beforeKey).getAdsNames().size();
			}

			getChange(oldAd, presentAd,features,16);
			getChange(manifestInfoPresent.get("ACTIVITY"), manifestInfoOld.get("ACTIVITY"), features, 19);
			getChange(manifestInfoPresent.get("RECEIVER"), manifestInfoOld.get("RECEIVER"),features,22);
			getChange(manifestInfoPresent.get("SERVICE"), manifestInfoOld.get("SERVICE"),features,25);
			getChange(manifestInfoPresent.get("PERMISSION"), manifestInfoOld.get("PERMISSION"),features,28);
			getChange(manifestInfoPresent.get("DANGEROUS_PERMISSION"),manifestInfoOld.get("DANGEROUS_PERMISSION"),features, 31);
			getChange(manifestInfoPresent.get("OTHER_PERMISSION"),manifestInfoOld.get("OTHER_PERMISSION"), features, 34);
			getChange(updateSize.get(beforeKey), updateSize.get(updateKey), features, 37);
			getChange(updateAdSize.get(beforeKey), updateAdSize.get(updateKey), features, 40);

			System.out.println(presentUpdate.getRELEASE_DATE() +" " + oldUpdate.getRELEASE_DATE());
			features.put(feature_name.get(43),(double)Days.daysBetween(DateUtil.formatterWithHyphen.parseDateTime(oldUpdate.getRELEASE_DATE()), DateUtil.formatterWithHyphen.parseDateTime(presentUpdate.getRELEASE_DATE())).getDays());
			//setSDKVersion(features,43,sdkInfo.get(updateKey).targetSDK);
			//setSDKVersion(features,70,sdkInfo.get(updateKey).minimumSDK);
			
			features.put(feature_name.get(44),beforeRating);
			features.put(feature_name.get(45), updateRatingInfo.get(beforeKey).getNegativeRatingRatio());
			setSDKVersion(features,46,sdkInfo.get(updateKey).targetSDK);
			setSDKVersion(features,73,sdkInfo.get(updateKey).targetSDK);
			
			
			
			double negativityRatio = getNegativityRatio(updates, index, appName);
			
			
			
			/*if(negativityRatio  > 1.0 + 0.15){
				features.put(feature_name.get(100), (double) GOOD_UPDATE);
			}else if (negativityRatio  < 1.0 - 0.15){
				features.put(feature_name.get(100), (double) BAD_UPDATE);
			}else{
				features.put(feature_name.get(100), (double) NEUTRAL_UPDATE);
			}*/
			
			if ((updateRating - beforeRating) >= threshold_value) {
				features.put(feature_name.get(100), (double) GOOD_UPDATE);
			} else if((updateRating - beforeRating)<= -threshold_value){
				features.put(feature_name.get(100), (double) BAD_UPDATE);
			}else{
				features.put(feature_name.get(100), (double) NEUTRAL_UPDATE);
			}
			
			
			/*if ((updateRating ) > beforeRating) {
				features.put(feature_name.get(46), (double) GOOD_UPDATE);
			} else {
				features.put(feature_name.get(46), (double) BAD_UPDATE);
			}*/

			
			writerDiff.write(appName);
			writerDiff.write(presentUpdate.getVERSION_CODE());
			writerDiff.write(Double.toString(updateRatingInfo.get(beforeKey).getAggregatedRating()));
			writerDiff.write(Double.toString(updateRatingInfo.get(beforeKey).getOneStar() + updateRatingInfo.get(beforeKey).getTwoStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(beforeKey).getFourStar() + updateRatingInfo.get(beforeKey).getFiveStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(beforeKey).getThreeStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(beforeKey).getTotalStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(updateKey).getAggregatedRating()));
			writerDiff.write(Double.toString(updateRatingInfo.get(updateKey).getOneStar() + updateRatingInfo.get(updateKey).getTwoStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(updateKey).getFourStar() + updateRatingInfo.get(updateKey).getFiveStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(updateKey).getThreeStar()));
			writerDiff.write(Double.toString(updateRatingInfo.get(updateKey).getTotalStar()));
			writerDiff.endRecord();
			
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

	CsvWriter writerDiff = new CsvWriter(
			"/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/diff_data_rating.csv");
	
	public void FeatureExtractor() throws Exception {
		
		
		int missing_feature_generation_update = 0;
		Parser p = new Parser();

		CsvWriter writer = new CsvWriter(
				"/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/updates_Data_two_category.csv");
		writer.write("AppName");
		writer.write("VersionCode");
		
		
		
		writerDiff.write("APP_Name");
		writerDiff.write("Version_Code");
		writerDiff.write("Previous_Rating");
		writerDiff.write("Previous_Negative_Rating");
		writerDiff.write("Previous_Positive_Rating");
		writerDiff.write("Previous_Neutral_Rating");
		writerDiff.write("Previous_Total_Rating");
		writerDiff.write("Present_Rating");
		writerDiff.write("Present_Negative_Rating");
		writerDiff.write("Present_Positive_Rating");
		writerDiff.write("Present_Neutral_Rating");
		writerDiff.write("Present_Total_Rating");
		writerDiff.endRecord();
		
		
		CsvWriter trianingWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/training_nn_neg_ratio_3_2_Seq_1.csv");
		CsvWriter testingWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/testing_nn_neg_ratio_3_2_Seq_1.csv");
		
		
		CsvWriter trianingSeqWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/training_seq_50_nn.csv");
		CsvWriter testingSewWriter = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data/testing_seq_50_nn.csv");
		
		
		trianingWriter.write("AppName");
		trianingWriter.write("VersionCode");
		testingWriter.write("AppName");
		testingWriter.write("VersionCode");
		
		
		trianingSeqWriter.write("AppName");
		trianingSeqWriter.write("VersionCode");
		testingSewWriter.write("AppName");
		testingSewWriter.write("VersionCode");
		
		
		for (int j = 0; j < feature_name.size(); j++) {
			writer.write(feature_name.get(j));
			trianingWriter.write(feature_name.get(j));
			testingWriter.write(feature_name.get(j));
			trianingSeqWriter.write(feature_name.get(j));
			testingSewWriter.write(feature_name.get(j));
		}
		
		writer.endRecord();
		testingWriter.endRecord();
		trianingWriter.endRecord();
		trianingSeqWriter.endRecord();
		testingSewWriter.endRecord();
		
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
				Map<String,Double> featureValue = generateFeatures(appName, presentUpdate, oldUpdate, p, i,updates);
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
				
				validRatingUpdate++;
				total_analyzed_updates ++;
				++ total_update;
			}
			writeFeatureDataII(trianingWriter,testingWriter,appFeature);
			writeFeatureDataIII(trianingSeqWriter,testingSewWriter,appFeature,50, appName);
			addZeroPadding(writer, total_update, appName);
		}

		writerDiff.close();
		writer.close();
		trianingWriter.close();
		testingWriter.close();
		trianingSeqWriter.close();
		testingSewWriter.close();

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
	
	
	public void writeFeatureDataII(CsvWriter trainingWriter,CsvWriter testingWriter, ArrayList<Features> appFeatures) {
		try {
			int index = appFeatures.size() - MAX_UPDATE;
			if(index <= 0) return;
			// Writing training information
			int size = 1;
			for(int i = index ; i < index + size; i ++ ){
				trainingWriter.write(appFeatures.get(i).getAppName());
				trainingWriter.write(appFeatures.get(i).getVersionCode());
				for (int j = 0; j < feature_name.size(); j++) {
					trainingWriter.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
				}
				trainingWriter.endRecord();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeFeatureDataIII(CsvWriter trainingWriter,CsvWriter testingWriter, ArrayList<Features> appFeatures, int maxSeq, String appName) {
		try {
			int index = appFeatures.size() - maxSeq;
			
			if(index > 0 ){
				for(int i = index ; i < appFeatures.size(); i ++ ){
					trainingWriter.write(appFeatures.get(i).getAppName());
					trainingWriter.write(appFeatures.get(i).getVersionCode());
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
					}
					trainingWriter.endRecord();
				}
				return;
			}
			
			if(index < 0 ){
				for(int i = 0 ; i < appFeatures.size(); i ++ ){
					trainingWriter.write(appFeatures.get(i).getAppName());
					trainingWriter.write(appFeatures.get(i).getVersionCode());
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter.write(Double.toString(appFeatures.get(i).getFeatureValue().get(feature_name.get(j))));
					}
					trainingWriter.endRecord();
				}
				
				// Add Zero Padding
				
				for(int i = 0 ; i < Math.abs(index) ; i ++ ){
					trainingWriter.write(appName);
					trainingWriter.write("?");
					for (int j = 0; j < feature_name.size(); j++) {
						trainingWriter.write("0");
					}
					trainingWriter.endRecord();
				}
			}
			
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
		FeatureExtractor3 ob = new FeatureExtractor3();
		ob.loadSizeInformation();
		ob.init();
		ob.FeatureExtractor();
		System.out.println("Program finishes successfully");
	}

}
