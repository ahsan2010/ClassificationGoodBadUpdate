package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.derby.impl.sql.compile.HalfOuterJoinNode;

import com.csvreader.CsvWriter;
import com.sail.awsomebasupdates.model.AppAnalyticsModel;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;
import com.sail.mobile.deeplearning.update.rating.classification.util.FileUtil;

/**
 * 
 * @author ahsan
 * 
 *
 */
public class FeatureExtractorSBSEModified {

	HashMap<String, ArrayList<UpdateTable>> appUpdates;
	Map<String, UpdateRatingInformation> updateRatingInfo;
	Map<String, AdInformation> appUseAd = new HashMap<String, AdInformation>();
	Map<String, SDKCsvInfo> sdkInfo = new HashMap<String, SDKCsvInfo>();
	Set<String> manifestFilesList = new HashSet<String>();
	Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList;
	
	public static String APP_ANALYTICS_FILE_PATH = Constants.APP_ANALYTICS_FILE_PATH;
	
	
	public static String ANDROID_DANGEROUS_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/dangerous_permission_list.csv";
	public static String ANDROID_NORMAL_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/android_permission_list.csv";

	Map<String, String> varifiedAdPackageMapGroup = FileUtil.readVerfiedAdList(Constants.VARIFIED_AD_PACKAGE_LIST);
	
	double threshold_value = 0.1;
	
	public final int GOOD_UPDATE = 1;
	public final int BAD_UPDATE = 3;
	public final int NEUTRAL_UPDATE = 2;
	
	public List<String> feature_name_2 = Arrays.asList(new String[] {
		"App_name", "Version_code", "Release_date",	
		"Apk_size", "Number_of_ad_libraries", "Number_of_permissions", "Number_of_dangerous_permissions",
		"Number_of_normal_permission", "Min_sdk_version", "Target_Sdk_Version", "Number_of_activity",
		"Number_of_service", "Number_of_receiver", "Number_of_intent", 
		"Ratio_median_release_time","Diff_previous_release_time_day", 
		"Release_note_length_word", "Release_note_changed_word",
		"Change_Apk_size", "Change_Number_of_ad_libraries",
		"Change_number_of_permission", "Change_number_of_normal_permission", "Change_min_sdk_version", "Change_target_sdk_version",
		"Change_number_of_activity", "Change_number_of_service", "Change_number_of_recevier", "Change_number_of_intent",
		"Increase/decrease_Apk_size", "Increase/decrease_Number_of_ad_libraries",
		"Increase/decrease_number_of_permission", "Increase/decrease_number_of_normal_permission", 
		"Increase/decrease_min_sdk_version", "Increase/decrease_target_sdk_version",
		"Increase/decrease_number_of_activity", "Increase/decrease_number_of_service", 
		"Increase/decrease_number_of_recevier", "Increase/decrease_number_of_intent",
		"Previous_median_aggregated_rating", "Previous_median_negitivity_ratio",
		"Target_label"
		
	});
	
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
	
	public int calculateTargetUpdateLabel(double negativityRatio , DescriptiveStatistics stats){
		double oneStanDev = stats.getStandardDeviation() - stats.getMean();	
		
		if(negativityRatio > 1 + oneStanDev){
			return BAD_UPDATE;
		}else if (negativityRatio < 1 - oneStanDev){
			return GOOD_UPDATE;
		}
		return NEUTRAL_UPDATE;
	}
	
	
	public void init() {
		
		appUpdatesAnalyticList = FileUtil.readAppAnalyticsInfo(APP_ANALYTICS_FILE_PATH);
		FileUtil.addedAppUpdateDailyRating(appUpdatesAnalyticList);
		//RatingAnalyzer rat = new RatingAnalyzer(appUpdatesAnalyticList);
		//updateRatingInfo = rat.generateUpdateRating();
		writeRatingInformationData(appUpdatesAnalyticList);
	}
	
	public DescriptiveStatistics getDescriptiveStatsNegativityRatioSafawtTSE(Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(String appName : appUpdatesAnalyticList.keySet()){
			ArrayList<AppAnalyticsModel> updates = appUpdatesAnalyticList.get(appName);
			for(int i = 1 ; i < updates.size() - 1 ; i ++){
				stats.addValue(updates.get(i).getNegativityRatioSafwatTSE());
			}
		}
		return stats;
	}
	public DescriptiveStatistics getDescriptiveStatsNegativityRatioMedianApproach(Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(String appName : appUpdatesAnalyticList.keySet()){
			ArrayList<AppAnalyticsModel> updates = appUpdatesAnalyticList.get(appName);
			for(int i = 1 ; i < updates.size() - 1 ; i ++){
				stats.addValue(updates.get(i).getNegativityRatioWithMedian());
			}
		}
		return stats;
	}
	
	public void writeRatingInformationData(Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList) {
		
		DescriptiveStatistics safwatTSENegitivityRatioStat = getDescriptiveStatsNegativityRatioSafawtTSE(appUpdatesAnalyticList);
		DescriptiveStatistics medianApproachNegitivityRatioStat = getDescriptiveStatsNegativityRatioMedianApproach(appUpdatesAnalyticList);
		
		
		System.out.println("Safwat TSE Negitivity Ratio SD: " + safwatTSENegitivityRatioStat.getStandardDeviation() +" Median: " + safwatTSENegitivityRatioStat.getPercentile(50));
		System.out.println("Median Negativity Ratio SD: " + medianApproachNegitivityRatioStat.getStandardDeviation() + " Median: " + medianApproachNegitivityRatioStat.getPercentile(50) );
		
		CsvWriter writer = new CsvWriter(Constants.APP_NEGATIVITY_RATIO_ANALYSIS_FILE);
		
		try{
			writer.write("Package_Name");
			writer.write("Version_Code");
			writer.write("Release_Date");
			writer.write("Prev_Neg_Rating");
			writer.write("Total_Prev_Rating");
			writer.write("Prev_Ratio_Negative_Rating");
			writer.write("Prev_Median_Negative_Rating");
			writer.write("Update_Neg_Ratings");
			writer.write("Update_Total_Ratings");
			writer.write("Safwat_TSE_Negativity_Ratio");
			writer.write("Prev_Median_Negativity_Ratio");
			writer.write("SafwatTSE_Target");
			writer.write("Median_Approach_Target");
			writer.endRecord();
			
			for(String appName : appUpdatesAnalyticList.keySet()){
				ArrayList<AppAnalyticsModel> updates = appUpdatesAnalyticList.get(appName);
				for(int i = 1 ; i < updates.size() - 1 ; i ++){
					
					writer.write(appName);
					writer.write(updates.get(i).getVersionCode());
					writer.write(updates.get(i).getReleaseDate());
				
					
					writer.write(String.format("%.3f", updates.get(i).getPrevUpdatesNegRatings()));
					writer.write(String.format("%.3f", updates.get(i).getPrevUpdatesTotalRatings()));
					writer.write(String.format("%.3f", updates.get(i).getRatioNegativeRatings()));
					writer.write(String.format("%.3f", updates.get(i).getPreviousRatioNegativesStat().getPercentile(50)));
					writer.write(String.format("%.3f", updates.get(i).getNegativeStars()));
					writer.write(String.format("%.3f", updates.get(i).getTotalStars()));
					
					writer.write(String.format("%.3f", updates.get(i).getNegativityRatioSafwatTSE()));
					writer.write(String.format("%.3f", updates.get(i).getNegativityRatioWithMedian()));
					
					writer.write(Integer.toString(calculateTargetUpdateLabel(updates.get(i).getNegativityRatioSafwatTSE(), safwatTSENegitivityRatioStat)));
					writer.write(Integer.toString(calculateTargetUpdateLabel(updates.get(i).getNegativityRatioWithMedian(), medianApproachNegitivityRatioStat)));
					
					writer.endRecord();
				}
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	

	public static void main(String[] args) throws Exception {
		FeatureExtractorSBSEModified ob = new FeatureExtractorSBSEModified();
		ob.init();
		System.out.println("Program finishes successfully");
	}
}
