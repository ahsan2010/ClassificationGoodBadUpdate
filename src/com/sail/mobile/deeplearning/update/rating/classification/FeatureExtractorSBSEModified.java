package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.Days;

import com.csvreader.CsvWriter;
import com.sail.awsomebasupdates.model.AppAnalyticsModel;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;
import com.sail.mobile.deeplearning.update.rating.classification.util.FileUtil;
import com.sail.mobile.deeplearning.update.rating.classification.util.Preprocessing;

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
	public final String APP_UPDATE_FEATURE_FILE = Constants.ROOT + "/result/app_update_features.csv";
	public static String ANDROID_PERMISSION_FILE = Constants.ROOT + "/Data/Permission_List.csv";
	
	Map<String, String> varifiedAdPackageMapGroup = FileUtil.readVerfiedAdList(Constants.VARIFIED_AD_PACKAGE_LIST);
	
	double threshold_value = 0.1;
	
	public final int GOOD_UPDATE = 1;
	public final int BAD_UPDATE = 3;
	public final int NEUTRAL_UPDATE = 2;
	
	public List<String> featureNameList = Arrays.asList(new String[] {
		"App_name", 
		"Version_code", 
		"Release_date",	
		
		"Apk_size", 
		"Number_of_ad_libraries",
		"Min_sdk_version", 
		"Target_Sdk_Version",
		"Number_of_permissions", 
		"Number_of_dangerous_permissions",
		"Number_of_normal_permission", 
		"Number_of_custom_permission", 
		"Number_of_activity",
		"Number_of_service", 
		"Number_of_receiver", 
		"Number_of_intent", 
		
		"Ratio_median_release_time",
		"Diff_previous_release_time_day", 
		"Release_note_length_word", 
		"Release_note_changed_word",
		
		"Change_Apk_size", 
		"Change_Number_of_ad_libraries",
		"Change_number_of_permission", 
		"Change_number_of_dangeroud_permission",
		"Change_number_of_normal_permission", 
		"change_number_of_custom_permission",
		"Change_min_sdk_version", 
		"Change_target_sdk_version",
		"Change_number_of_activity", 
		"Change_number_of_service", 
		"Change_number_of_recevier", 
		"Change_number_of_intent",
		
		"Increase/decrease_Apk_size", 
		"Increase/decrease_Number_of_ad_libraries",
		"Increase/decrease_number_of_permission", 
		"Increase/decrease_number_of_dangeroud_permission",
		"Increase/decrease_number_of_normal_permission",
		"Increase/decrease_number_of_custom_permission",
		"Increase/decrease_min_sdk_version", 
		"Increase/decrease_target_sdk_version",
		"Increase/decrease_number_of_activity", 
		"Increase/decrease_number_of_service", 
		"Increase/decrease_number_of_recevier", 
		"Increase/decrease_number_of_intent",
		
		"Previous_median_aggregated_rating", 
		"Previous_median_negitive_rating_ratio",
		"Previous_update_aggreated_rating",
		"Previous_update_negative_rating_ratio",
		
		"Target_label"
		
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
		//writeRatingInformationData(appUpdatesAnalyticList);
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
	
	public PermissionModel getUsedPermission(Set<String> usedPermissionList, Map<String,Set<String>> androidPermissionList){
		PermissionModel ob = new PermissionModel();
		for(String permission : usedPermissionList){
			String permissionName = permission;
			if(permission.contains("android.permission.")){
				permissionName = permission.substring("android.permission.".length());	
			}
			
			if(androidPermissionList.get(Constants.PERMISSION_DANGEROUS).contains(permissionName)){
				ob.getDangerousPermissionList().add(permissionName);
			}else if (androidPermissionList.get(Constants.PERMISSION_NORMAL).contains(permissionName)){
				ob.getNormalPermissionList().add(permissionName);
			}else if (androidPermissionList.get(Constants.PERMISSION_SIGNATURE).contains(permissionName)){
				ob.getSignaturePermissionList().add(permissionName);
			}else if (androidPermissionList.get(Constants.PERMISSION_NO_LABEL).contains(permissionName)){
				ob.getPermissionNotUsedByThirdparty().add(permissionName);
			}else{
				ob.getCustomPermissionList().add(permissionName);
			}
		}
		return ob;
	}
	
	public String getCleanReleaseNote(String text){
		String cleanText = Preprocessing.htmlRemove(text);
		return cleanText;
	}
	public int getWordCount(String text){
		String[] words = text.split("\\s+");
		return words.length;
	}
	
	public double getPercentModifiedUpdates (String updatedCleanText, String prevUpdateCleanReleaseNote){
		double perModified = 0;
		String addedText = "";
		if(updatedCleanText.contains("[ADDED]")){
			if(updatedCleanText.contains("[REMOVED]")){
				addedText = updatedCleanText.substring(updatedCleanText.indexOf("[ADDED]") + "[ADDED]".length(), updatedCleanText.indexOf("[REMOVED]"));
			}else{
				addedText = updatedCleanText.substring(updatedCleanText.indexOf("[ADDED]") + "[ADDED]".length());
			}
			addedText = addedText.replace("=[", "").replace("]", "");
		}
		
		/*System.out.println("RN: " + prevUpdateCleanReleaseNote);
		System.out.println("UpdatedText: " + updatedCleanText);
		System.out.println("Added: " + addedText);
		System.out.println("------");*/
		
		double updatedWordCount = getWordCount(addedText);
		double releaseNoteWordCount = getWordCount(prevUpdateCleanReleaseNote);
		
		perModified = 100.0 * ((double)(releaseNoteWordCount - updatedWordCount)/(Math.max(releaseNoteWordCount, 1)));
		
		return perModified;
	}
	
	public void extractFeaturesFromUpdates() throws Exception{
		init();
		Map<String,Set<String>> androidPermissionList = FileUtil.readAndroidPermissionList(ANDROID_PERMISSION_FILE);
		DescriptiveStatistics medianApproachNegitivityRatioStat = getDescriptiveStatsNegativityRatioMedianApproach(appUpdatesAnalyticList);
		
		CsvWriter writer = new CsvWriter(APP_UPDATE_FEATURE_FILE);
		for(String featureLevel : featureNameList){
			writer.write(featureLevel);
		}
		writer.endRecord();
		
		for(String appName : appUpdatesAnalyticList.keySet()){
			ArrayList<AppAnalyticsModel> updates = appUpdatesAnalyticList.get(appName);
			
			for(int i = 1 ; i < updates.size() - 1 ; i ++){
				
				if(i > 10){
					break;
				}
				
				AppAnalyticsModel prevUpdate = updates.get(i - 1);
				AppAnalyticsModel update = updates.get(i);
				
				// Get the used permission type
				PermissionModel permissionForUpdate = getUsedPermission(update.getPermissionList(), androidPermissionList);
				PermissionModel permissionForPreviousUpdate = getUsedPermission(update.getPermissionList(), androidPermissionList);
				
				FeatureModel fm = new FeatureModel();
				
				fm.setAppName(update.getPackageName());
				fm.setVersionCode(update.getVersionCode());
				fm.setReleaseDate(update.getReleaseDate());
				fm.setApkSize(update.getApkSizeByte()/1000000);
				fm.setNumAdLibraries(update.getAdLibraryList().size());
				fm.setNumPermissions(update.getPermissionList().size());
				fm.setNumDangerousPermissions(permissionForUpdate.getDangerousPermissionList().size());
				fm.setNumNormalPermissions(permissionForUpdate.getNormalPermissionList().size());
				fm.setNumCustomPermission(permissionForUpdate.getCustomPermissionList().size());
				fm.setMinSdkVersion(update.getMinSdkVersion());
				fm.setTargetSdkVersion(update.getTargetSdkVersion());
				fm.setNumActivity(update.getActivityList().size());
				fm.setNumService(update.getServiceList().size());
				fm.setNumReceiver(update.getReceiverList().size());
				fm.setNumIntent(update.getIntentList().size());
				
				DescriptiveStatistics prevAggregatedRatingStat 		= getPrevStatAggratedRating(updates, i);
				DescriptiveStatistics prevNegativeRatingRatioStat 	= getPrevStatNegativeRatingRatio(updates, i);
				DescriptiveStatistics prevReleaseTime 				= getReleaseTimeStats(updates,i);
				DifferenceModel df 									= getDiffUpdateChange(prevUpdate, update, permissionForUpdate, permissionForPreviousUpdate);
				
				String prevCleanReleaseNote = getCleanReleaseNote(prevUpdate.getReleaseNote());
				String updateCleanReleaseNote = getCleanReleaseNote(update.getReleaseNote());
				String modifiedCleanReleaeNote = getCleanReleaseNote(update.getReleaseNoteDifference());
				
				
				
				double percentModifiedReleaseNote = getPercentModifiedUpdates(modifiedCleanReleaeNote, prevCleanReleaseNote);
				
				
				int releaseTime = Days.daysBetween(update.getJodaReleaseDate(), prevUpdate.getJodaReleaseDate()).getDays();
				
				fm.setRatioMedianOfAllPreviousReleaseTime(prevReleaseTime.getPercentile(50));
				fm.setUpdateReleaseTime(releaseTime);
				
				fm.setReleaseNoteLengthWord(getWordCount(updateCleanReleaseNote));
				fm.setReleaseNoteModified(percentModifiedReleaseNote);
				
				fm.setIncreaseDecreaseApkSize(df.getIncreaseDecreaseApkSize());
				fm.setIncreaseDecreaseNumAdLib(df.getIncreaseDecreaseNumAdLib());
				fm.setIncreaseDecreaseMinSdkVersion(df.getIncreaseDecreaseMinSdkVersion());
				fm.setInceaseDecreaseTargetSdkVersion(df.getIncreaseDecreaseTargetSdkVersion());
				fm.setIncreaseDecreaseNumPermission(df.getIncreaseDecreaseNumPermission());
				fm.setIncreaseDecreaseNumDangerousPermission(df.getIncreaseDecreaseNumDangerousPermission());
				fm.setIncreaseDecreaseNumNormalPermission(df.getIncreaseDecreaseNumNormalPermission());
				fm.setIncreaseDecreaseNumCustomPermission(df.getIncreaseDecreaseNumCustomPermission());
				fm.setIncreaseDecreaseNumActivity(df.getIncreaseDecreaseNumActivity());
				fm.setIncreaseDecreaseNumService(df.getIncreaseDecreaseNumService());
				fm.setIncreaseDecreaseNumReceiver(df.getIncreaseDecreaseNumReceiver());
				fm.setIncreaseDecreaseNumIntent(df.getIncreaseDecreaseNumIntent());
				
				fm.setChangeApkSize(df.getChangeApkSize());
				fm.setChangeNumAdLib(df.getChangeNumAdLib());
				fm.setChangeMinSdkVersion(df.getChangeMinSdkVersion());
				fm.setChangeTargetSdkVersion(df.getChangeTargetSdkVersion());
				fm.setChangeNumPermission(df.getChangeNumPermission());
				fm.setChangeNumDangerousPermission(df.getChangeNumDangerousPermission());
				fm.setChangeNumNormalPermission(df.getChangeNumNormalPermission());
				fm.setChangeNumCustomPermission(df.getChangeNumCustomPermission());
				fm.setChangeNumActivity(df.getChangeNumActivity());
				fm.setChangeNumService(df.getChangeNumService());
				fm.setChangeNumReceiver(df.getChangeNumReceiver());
				fm.setChangeNumIntent(df.getChangeNumIntent());
				
				fm.setPreviousAllUpdatesMedianAggregatedRating(prevAggregatedRatingStat.getPercentile(50));
				fm.setPreviousAllUpdatsMedianNegativeRatingRatio(prevNegativeRatingRatioStat.getPercentile(50));
				fm.setPreviousUpdateAggregatedRating(prevUpdate.getAggregatedRating());
				fm.setPreviousUpdateNegativeRatingRatio(prevUpdate.getRatioNegativeRatings());
				
				fm.setTargetValue(calculateTargetUpdateLabel(update.getNegativityRatioWithMedian(), medianApproachNegitivityRatioStat));
				
				writeFeaturesToFile(writer,fm);
			}
		}
	}

	
	public void writeFeaturesToFile(CsvWriter writer, FeatureModel fm) throws Exception{
		
		writer.write(fm.getAppName());
		writer.write(fm.getVersionCode());
		writer.write(fm.getReleaseDate());
		
		writer.write(String.format("%.5f", fm.getApkSize()));
		writer.write(String.format("%d", fm.getNumAdLibraries()));
		writer.write(String.format("%d", fm.getMinSdkVersion()));
		writer.write(String.format("%d", fm.getTargetSdkVersion()));
		writer.write(String.format("%d", fm.getNumPermissions()));
		writer.write(String.format("%d", fm.getNumDangerousPermissions()));
		writer.write(String.format("%d", fm.getNumNormalPermissions()));
		writer.write(String.format("%d", fm.getNumCustomPermission()));
		writer.write(String.format("%d", fm.getNumActivity()));
		writer.write(String.format("%d", fm.getNumService()));
		writer.write(String.format("%d", fm.getNumReceiver()));
		writer.write(String.format("%d", fm.getNumIntent()));
		
		writer.write(String.format("%.4f", fm.getRatioMedianOfAllPreviousReleaseTime()));
		writer.write(String.format("%.4f", fm.getUpdateReleaseTime()));
		writer.write(String.format("%.0f", fm.getReleaseNoteLengthWord()));
		writer.write(String.format("%.4f", fm.getReleaseNoteModified()));
		
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseApkSize()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumAdLib()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseMinSdkVersion()));
		writer.write(String.format("%.4f", fm.getInceaseDecreaseTargetSdkVersion()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumPermission()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumDangerousPermission()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumNormalPermission()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumCustomPermission()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumActivity()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumService()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumReceiver()));
		writer.write(String.format("%.4f", fm.getIncreaseDecreaseNumIntent()));
		
		writer.write(String.format("%.4f", fm.getChangeApkSize()));
		writer.write(String.format("%.0", fm.getChangeNumAdLib()));
		writer.write(String.format("%.0", fm.getChangeMinSdkVersion()));
		writer.write(String.format("%.0", fm.getChangeTargetSdkVersion()));
		writer.write(String.format("%.0", fm.getChangeNumPermission()));
		writer.write(String.format("%.0", fm.getChangeNumDangerousPermission()));
		writer.write(String.format("%.0", fm.getChangeNumNormalPermission()));
		writer.write(String.format("%.0", fm.getChangeNumCustomPermission()));
		writer.write(String.format("%.0", fm.getChangeNumActivity()));
		writer.write(String.format("%.0", fm.getChangeNumService()));
		writer.write(String.format("%.0", fm.getChangeNumReceiver()));
		writer.write(String.format("%.0", fm.getChangeNumIntent()));
		
		writer.write(String.format("%.4f", fm.getPreviousAllUpdatesMedianAggregatedRating()));
		writer.write(String.format("%.4f", fm.getPreviousAllUpdatsMedianNegativeRatingRatio()));
		writer.write(String.format("%.4f", fm.getPreviousUpdateAggregatedRating()));
		writer.write(String.format("%.4f", fm.getPreviousUpdateNegativeRatingRatio()));
		
		// Class level
		writer.write(Integer.toString(fm.getTargetValue()));
		
		writer.endRecord();
		
	}
	
	
	
	public boolean isChange(int oldValue, int newValue){
		return (oldValue != newValue);
	}
	
	public boolean isChange(Double oldValue, Double newValue){
		return (Double.compare(oldValue, newValue) != 0);
	}
	
	
	public boolean isChange(Set<String> oldValues, Set<String> newValues){
		if(oldValues.size() != newValues.size()){
			return true;
		}
		
		for(String oldValue : oldValues){
			if(!newValues.contains(oldValue)){
				return true;
			}
		}
		
		return false;
	}
	
	public double countChange(int newValue, int oldValue){
		return (newValue - oldValue);
	}
	
	public double countChange(double newValue, double oldValue){
		return (newValue - oldValue);
	}
	
	public double countChange(Set<String> newValues, Set<String> oldValues){
		double totalChange = 0;
		
		if(oldValues.size() != newValues.size()){
			return 0;
		}
		
		for(String oldValue : oldValues){
			if(!newValues.contains(oldValue)){
				totalChange ++;
			}
		}
		return totalChange;
	}
	
	
	public DescriptiveStatistics getReleaseTimeStats(ArrayList<AppAnalyticsModel> updates, int index){
		DescriptiveStatistics stat = new DescriptiveStatistics();
		for (int i = index ; i >= 1 ; i -- ){
			AppAnalyticsModel update = updates.get(i);
			AppAnalyticsModel prevUpdate = updates.get(i - 1);
			
			int days = Days.daysBetween(update.getJodaReleaseDate(), prevUpdate.getJodaReleaseDate()).getDays();
			stat.addValue(days);
		}
		
		return stat;
	}
	
	public DescriptiveStatistics getPrevStatAggratedRating(ArrayList<AppAnalyticsModel> updates, int index){
		DescriptiveStatistics stat = new DescriptiveStatistics();
		for(int i = index - 1 ; i >= 0 ; i --){
			AppAnalyticsModel update = updates.get(i);
			stat.addValue(update.getAggregatedRating());
		}
		return stat;
	}
	
	public DescriptiveStatistics getPrevStatNegativeRatingRatio(ArrayList<AppAnalyticsModel> updates, int index){
		DescriptiveStatistics stat = new DescriptiveStatistics();
		for(int i = index - 1 ; i >= 0 ; i --){
			AppAnalyticsModel update = updates.get(i);
			stat.addValue(update.getRatioNegativeRatings());
		}
		return stat;
	}
	
	public DifferenceModel getDiffUpdateChange(AppAnalyticsModel prevUpdate, AppAnalyticsModel update,
			PermissionModel permissionForUpdate, PermissionModel permissionForPreviousUpdate) throws Exception{
		
		// sdk change
		double targetSdkChange = 100.0 * (countChange(update.getTargetSdkVersion(),
				prevUpdate.getTargetSdkVersion())/(double)prevUpdate.getTargetSdkVersion());
		double minSdkChange = 100.0 * (countChange(update.getMinSdkVersion(), 
				prevUpdate.getMinSdkVersion())/(double)prevUpdate.getMinSdkVersion());
		
		// permission
		double permissionChange = 100.0 * (countChange(update.getPermissionList(),
				prevUpdate.getPermissionList())/(double)prevUpdate.getPermissionList().size());
		
		double dangPermChange = 100.0 * (countChange(permissionForUpdate.getDangerousPermissionList(), 
				permissionForPreviousUpdate.getDangerousPermissionList()) /
				(double)Math.max(permissionForPreviousUpdate.getDangerousPermissionList().size(),1));
		
		double normPermChange = 100.0 * (countChange(permissionForUpdate.getNormalPermissionList(), 
				permissionForPreviousUpdate.getNormalPermissionList())) /
				(double)Math.max(permissionForPreviousUpdate.getNormalPermissionList().size(),1);
		
		double customPermChange = 100.0 * (countChange(permissionForUpdate.getCustomPermissionList() , 
				permissionForPreviousUpdate.getCustomPermissionList())) /
				(double)Math.max(permissionForPreviousUpdate.getCustomPermissionList().size(),1);
		
		// apk change
		double apkSizeChange = 100.0 *(countChange(update.getApkSizeByte(), prevUpdate.getApkSizeByte()) /
				(double)(prevUpdate.getApkSizeByte()));
		
		// ad lib change
		double adLibChange = 100.0 * (countChange(update.getAdLibraryList(), prevUpdate.getAdLibraryList()) /
				(double) (Math.max(prevUpdate.getAdLibraryList().size(),1)));
		
		// manifest change
		double activityChange = 100.0 * (countChange(update.getActivityList(), prevUpdate.getActivityList()) /
				(double) (Math.max(prevUpdate.getActivityList().size(),1)));
		
		double serviceChange = 100.0 * (countChange(update.getServiceList(), prevUpdate.getServiceList()) /
				(double) (Math.max(prevUpdate.getServiceList().size(),1)));
		
		double receiverChange = 100.0 * (countChange(update.getReceiverList(),prevUpdate.getReceiverList()) /
				(double) (Math.max(prevUpdate.getReceiverList().size(),1)));
		
		double intentChange = 100.0 * (countChange(update.getIntentList(), prevUpdate.getIntentList()) /
				(double) (Math.max(prevUpdate.getIntentList().size(),1)));
		
		DifferenceModel df = new DifferenceModel();
		
		
		// Apk Size
		df.setIncreaseDecreaseApkSize(apkSizeChange);
		df.setIncreaseDecreaseNumAdLib(adLibChange);
		
		df.setIncreaseDecreaseNumPermission(permissionChange);
		df.setIncreaseDecreaseNumDangerousPermission(dangPermChange);
		df.setIncreaseDecreaseNumNormalPermission(normPermChange);
		df.setIncreaseDecreaseNumCustomPermission(customPermChange);
		df.setIncreaseDecreaseMinSdkVersion(minSdkChange);
		df.setIncreaseDecreaseTargetSdkVersion(targetSdkChange);
		df.setIncreaseDecreaseNumActivity(activityChange);
		df.setIncreaseDecreaseNumService(serviceChange);
		df.setIncreaseDecreaseNumIntent(intentChange);
		df.setIncreaseDecreaseNumReceiver(receiverChange);
		
		df.setChangeApkSize(countChange(prevUpdate.getApkSizeByte(),update.getApkSizeByte()));
		df.setChangeNumAdLib(countChange(prevUpdate.getAdLibraryList(),update.getAdLibraryList()));
		df.setChangeNumPermission(countChange(prevUpdate.getPermissionList(),update.getPermissionList()));
		df.setChangeNumDangerousPermission(countChange(permissionForPreviousUpdate.getDangerousPermissionList(),
				permissionForUpdate.getDangerousPermissionList()));
		df.setChangeNumNormalPermission(countChange(permissionForPreviousUpdate.getNormalPermissionList(),permissionForUpdate.getNormalPermissionList()));
		df.setChangeNumCustomPermission(countChange(permissionForPreviousUpdate.getCustomPermissionList(),
				permissionForUpdate.getCustomPermissionList()));
		df.setChangeMinSdkVersion(countChange(prevUpdate.getMinSdkVersion(), update.getMinSdkVersion()));
		df.setChangeTargetSdkVersion(countChange(prevUpdate.getTargetSdkVersion(), update.getTargetSdkVersion()));
		df.setChangeNumActivity(countChange(prevUpdate.getActivityList(),update.getActivityList()));
		df.setChangeNumService(countChange(prevUpdate.getServiceList(),update.getServiceList()));
		df.setChangeNumIntent(countChange(prevUpdate.getIntentList(),update.getIntentList()));
		df.setChangeNumReceiver(countChange(prevUpdate.getReceiverList(),update.getReceiverList()));
		
		/*df.setChangeApkSize(isChange(prevUpdate.getApkSizeByte(),update.getApkSizeByte()));
		df.setChangeNumAdLib(isChange(prevUpdate.getAdLibraryList().size(),update.getAdLibraryList().size()));
		df.setChangeNumPermission(isChange(prevUpdate.getPermissionList(),update.getPermissionList()));
		df.setChangeNumDangerousPermission(isChange(permissionForPreviousUpdate.getDangerousPermissionList(),
				permissionForUpdate.getDangerousPermissionList()));
		df.setChangeNumNormalPermission(isChange(permissionForPreviousUpdate.getNormalPermissionList(),permissionForUpdate.getNormalPermissionList()));
		df.setChangeNumCustomPermission(isChange(permissionForPreviousUpdate.getCustomPermissionList(),
				permissionForUpdate.getCustomPermissionList()));
		df.setChangeMinSdkVersion(isChange(prevUpdate.getMinSdkVersion(), update.getMinSdkVersion()));
		df.setChangeTargetSdkVersion(isChange(prevUpdate.getTargetSdkVersion(), update.getTargetSdkVersion()));
		df.setChangeNumActivity(isChange(prevUpdate.getActivityList(),update.getActivityList()));
		df.setChangeNumService(isChange(prevUpdate.getServiceList(),update.getServiceList()));
		df.setChangeNumIntent(isChange(prevUpdate.getIntentList(),update.getIntentList()));
		df.setChangeNumReceiver(isChange(prevUpdate.getReceiverList(),update.getReceiverList()));*/
		
		
		return df;
		
	}
	
	
	public static void main(String[] args) throws Exception {
		FeatureExtractorSBSEModified ob = new FeatureExtractorSBSEModified();
		ob.extractFeaturesFromUpdates();
		System.out.println("Program finishes successfully");
	}
}
