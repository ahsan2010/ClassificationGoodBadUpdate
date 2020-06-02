package com.analysis.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sail.awsomebasupdates.model.AdsUsageModel;
import com.sail.awsomebasupdates.model.AppMetaData;
import com.sail.awsomebasupdates.model.AppUpdateModel;
import com.sail.awsomebasupdates.model.ManifestModel;
import com.sail.awsomebasupdates.model.SdkVersionModel;

public class MergeAllUpdateResult {

	public String updatePath = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/Final_Studied_App_Updates_2016_2019.csv";
	public String manifestPath = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/ManifestExtractedInfo.csv";
	public String sdkVersionPath = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/Final_SDK_Version_Data.csv";
	public String adsPath = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/Updates_With_Ad_Library.csv";
	public String updaetMetaData = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/App_Update_Meta_Data_2016_2020.csv";
	public String outputFullAppAnalyticsData = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT/Data/App_Analytics_Full_Info.csv";
	//Aggregated_Rating	
	// NUMBER_OF_DOWNLOADS	NUMBER_OF_ONE_STAR	NUMBER_OF_TWO_STARS	NUMBER_OF_THREE_STARS	
	// NUMBER_OF_FOUR_STARS	NUMBER_OF_FIVE_STARS	RELEASE_NOTE	RELEASE_NOTE_DIFFERENCE
	
	List<String> appUpdateExtractedInfoColumn = Arrays.asList(
			"APP_ID",
			"APP_UPDATE_ID",
			"Package_Name",
			"Version_Code",
			"Release_Date",
			"Aggregated_Rating",
			"Number_Of_Downloads",
			"Number_Of_One_Star",
			"Number_Of_Two_Star",
			"Number_Of_Three_Star",
			"Number_Of_Four_Star",
			"Number_Of_Five_Star",
			"Apk_Size",
			"Min_SDK_Version",
			"Target_SDK_Version",
			"Max_SDK_Version",
			"Launcher_Activity",
			"Permission_List",
			"Activity_List",
			"Service_List",
			"Intent_List",
			"Total_Ad_Library",
			"List_Of_Ad_Library",
			"List_Of_Ad_Library_Imported",
			"List_Ad_Library_Class_Count",
			 "Releae_Note",
			"Release_Note_Difference");
	
	
	public Map<String, AppUpdateModel> readUpdateInfo() throws Exception {
		// APP_ID APP_UPDATE_ID PACKAGE_NAME VERSION_CODE RELEASE_DATE
		// APK_FILE_SIZE
		Map<String, AppUpdateModel> updateInfoList = new HashMap<String, AppUpdateModel>();
		CsvReader reader = new CsvReader(updatePath);
		reader.readHeaders();
		while (reader.readRecord()) {
			String appId = reader.get("APP_ID");
			String appUpdateId = reader.get("APP_UPDATE_ID");
			String packageName = reader.get("PACKAGE_NAME");
			String versionCode = reader.get("VERSION_CODE");
			String releaseDate = reader.get("RELEASE_DATE");
			String apkFileSize = reader.get("APK_FILE_SIZE");
			String key = packageName + "-" + versionCode;

			AppUpdateModel ob = new AppUpdateModel();
			ob.setAppId(appId);
			ob.setAppUpdaetId(appUpdateId);
			ob.setPackageName(packageName);
			ob.setVersionCode(versionCode);
			ob.setReleaseDate(releaseDate);
			ob.setApkSize(apkFileSize);
			updateInfoList.put(key, ob);
		}
		reader.close();
		System.out.println("Finish reading AppUpdate Info");
		return updateInfoList;
	}

	public Map<String, ManifestModel> readManifestInfo() throws Exception {
		// App_Name Versison_Code Release_Date Launcher_Activity Permission
		// Activity Service Intent
		Map<String, ManifestModel> updateInfoList = new HashMap<String, ManifestModel>();
		CsvReader reader = new CsvReader(manifestPath);
		reader.readHeaders();
		while (reader.readRecord()) {
			String packageName = reader.get("App_Name");
			String versionCode = reader.get("Versison_Code");
			String releaseDate = reader.get("Release_Date");
			String launcherActivity = reader.get("Launcher_Activity");
			String permissionList = reader.get("Permission");
			String activityList = reader.get("Activity");
			String serviceList = reader.get("Service");
			String intentList = reader.get("Intent");

			String key = packageName + "-" + versionCode;

			ManifestModel ob = new ManifestModel();
			ob.setPackageName(packageName);
			ob.setVersionCode(versionCode);
			ob.setReleaseDate(releaseDate);
			ob.setLauncherActivity(launcherActivity);
			ob.setActivityList(activityList);
			ob.setPermissionList(permissionList);
			ob.setServiceList(serviceList);
			ob.setIntentList(intentList);
			updateInfoList.put(key, ob);

		}
		reader.close();
		System.out.println("Finish reading ManifestFile Info");
		return updateInfoList;
	}

	public Map<String, SdkVersionModel> readSdkVersionInfo() throws Exception {
		//Package_Name	Version_Code	Release_Date	Min_SDK_Version	Target_SDK_Version	Max_SDK_Version
		Map<String, SdkVersionModel> updateInfoList = new HashMap<String, SdkVersionModel>();
		CsvReader reader = new CsvReader(sdkVersionPath);
		reader.readHeaders();
		while (reader.readRecord()) {
			String packageName = reader.get("Package_Name");
			String versionCode = reader.get("Version_Code");
			String releaseDate = reader.get("Release_Date");
			String minSdkVersion = reader.get("Min_SDK_Version");
			String targetSdkVersion = reader.get("Target_SDK_Version");
			String maxSdkVersion = reader.get("Max_SDK_Version");
			
			SdkVersionModel ob = new SdkVersionModel();
			ob.setPackageName(packageName);
			ob.setVersionCode(versionCode);
			ob.setReleaseDate(releaseDate);
			ob.setMinSdkVersion(minSdkVersion);
			ob.setTargetSdkVersion(targetSdkVersion);
			ob.setMaxSdkVersion(maxSdkVersion);
			
			String key = packageName + "-" + versionCode;
			updateInfoList.put(key, ob);
			
		}
		reader.close();
		System.out.println("Finish reading SdkInfo Info");
		return updateInfoList;
	}

	public Map<String, AdsUsageModel> readAdsInfo() throws Exception {
		// App_Name Version_Code Release_Date Total_Ads List_Of_Ads
		// Total_Ads_Import Ads_Class_Count

		Map<String, AdsUsageModel> updateInfoList = new HashMap<String, AdsUsageModel>();
		CsvReader reader = new CsvReader(adsPath);
		reader.readHeaders();
		while (reader.readRecord()) {
			String packageName = reader.get("App_Name");
			String versionCode = reader.get("Version_Code");
			String releaseDate = reader.get("Release_Date");
			String totalAds = reader.get("Total_Ads");
			String listOfAds = reader.get("List_Of_Ads");
			String adsImportList = reader.get("Total_Ads_Import");
			String adsClassCount = reader.get("Ads_Class_Count");

			AdsUsageModel ob = new AdsUsageModel();
			ob.setPackageName(packageName);
			ob.setVersionCode(versionCode);
			ob.setReleaseDate(releaseDate);
			ob.setTotalAds(totalAds);
			ob.setListOfAds(listOfAds);
			ob.setAdsImport(adsImportList);
			ob.setAdsClassCount(adsClassCount);

			String key = packageName + "-" + versionCode;
			updateInfoList.put(key, ob);

		}
		reader.close();
		System.out.println("Finish reading AdsInfo Info");
		return updateInfoList;
	}
	
	public Map<String, AppMetaData> readUpdateMetaData() throws Exception{
		
		//APP_ID	APP_UPDATE_ID	PACKAGE_NAME	VERSION_CODE	RELEASE_TIME	Aggregated_Rating	
		// NUMBER_OF_DOWNLOADS	NUMBER_OF_ONE_STAR	NUMBER_OF_TWO_STARS	NUMBER_OF_THREE_STARS	
		// NUMBER_OF_FOUR_STARS	NUMBER_OF_FIVE_STARS	RELEASE_NOTE	RELEASE_NOTE_DIFFERENCE

		
		Map<String, AppMetaData> updateInfoList = new HashMap<String, AppMetaData>();
		CsvReader reader = new CsvReader(updaetMetaData);
		reader.readHeaders();
		while (reader.readRecord()) {
			String packageName = reader.get("PACKAGE_NAME");
			String versionCode = reader.get("VERSION_CODE");
			String releaseDate = reader.get("RELEASE_TIME");
			String aggregatedRating = reader.get("Aggregated_Rating");
			String numberOfDownloads = reader.get("NUMBER_OF_DOWNLOADS");
			String numberOfOneStars = reader.get("NUMBER_OF_ONE_STAR");
			String numberOfTwoStars = reader.get("NUMBER_OF_TWO_STARS");
			String numberOfThreeStars = reader.get("NUMBER_OF_THREE_STARS");
			String numberOfFourStars = reader.get("NUMBER_OF_FOUR_STARS");
			String numberOfFiveStars = reader.get("NUMBER_OF_FIVE_STARS");
			String releaseNote = reader.get("RELEASE_NOTE");
			String releaseNoteDifference = reader.get("RELEASE_NOTE_DIFFERENCE");
			
			numberOfDownloads = numberOfDownloads.replace(",", "");
			numberOfDownloads = numberOfDownloads.replace("+", "").trim();
			
			AppMetaData ob = new AppMetaData();
			ob.setPackageName(packageName);
			ob.setVersionCode(versionCode);
			ob.setReleaseDate(releaseDate);
			ob.setAggregatedRating(aggregatedRating);
			ob.setNumberOfDownloads(numberOfDownloads);
			ob.setNumberOfOneStars(numberOfOneStars);
			ob.setNumberOfTwoStars(numberOfTwoStars);
			ob.setNumberOfThreeStars(numberOfThreeStars);
			ob.setNumberOfFourStars(numberOfFourStars);
			ob.setNumberOfFiveStars(numberOfFiveStars);
			ob.setReleaseNotes(releaseNote);
			ob.setReleaseNoteDifference(releaseNoteDifference);
			String key = packageName + "-" + versionCode;
			updateInfoList.put(key,ob);
		}
		return updateInfoList;
	}

	public void mergeAppUpdateResult() throws Exception {
		
		CsvWriter writer = new CsvWriter(outputFullAppAnalyticsData);
		for(String colName : appUpdateExtractedInfoColumn){
			writer.write(colName);
		}
		writer.endRecord();
		
		Map<String, AppUpdateModel> appUpdateInfoList = readUpdateInfo();
		Map<String, ManifestModel> manifestInfoList = readManifestInfo();
		Map<String, SdkVersionModel> sdkVersionInfoList = readSdkVersionInfo();
		Map<String, AdsUsageModel> adsInfoList = readAdsInfo();
		Map<String, AppMetaData> appsMetaInfoList = readUpdateMetaData();
		
		System.out.println("appUpdate = " + appUpdateInfoList.size());
		System.out.println("manifest = " + manifestInfoList.size());
		System.out.println("sdkVersion = " + sdkVersionInfoList.size());
		System.out.println("adsInfo = " + adsInfoList.size());
		System.out.println("App meta info = " + appsMetaInfoList.size());
		
	
		int totalGoodUpdate = 0 ;
		int missingUpdates = 0;
		
		for(String updateKey : appUpdateInfoList.keySet()){
			if(manifestInfoList.containsKey(updateKey) && 
					sdkVersionInfoList.containsKey(updateKey) &&
					adsInfoList.containsKey(updateKey) &&
					appsMetaInfoList.containsKey(updateKey)){
				++totalGoodUpdate;
				
				AppUpdateModel updateModel 		= appUpdateInfoList.get(updateKey);
				ManifestModel manifestModel 	= manifestInfoList.get(updateKey);
				SdkVersionModel sdkVersionModel = sdkVersionInfoList.get(updateKey);
				AdsUsageModel adsUsageModel 	= adsInfoList.get(updateKey);
				AppMetaData appsMetaModel 		= appsMetaInfoList.get(updateKey);
				
				
				writer.write(updateModel.getAppId());
				writer.write(updateModel.getAppUpdaetId());
				writer.write(updateModel.getPackageName());
				writer.write(updateModel.getVersionCode());
				writer.write(updateModel.getReleaseDate());
				writer.write(appsMetaModel.getAggregatedRating());
				writer.write(appsMetaModel.getNumberOfDownloads());
				writer.write(appsMetaModel.getNumberOfOneStars());
				writer.write(appsMetaModel.getNumberOfTwoStars());
				writer.write(appsMetaModel.getNumberOfThreeStars());
				writer.write(appsMetaModel.getNumberOfFourStars());
				writer.write(appsMetaModel.getNumberOfFiveStars());
				writer.write(updateModel.getApkSize());
				writer.write(sdkVersionModel.getMinSdkVersion());
				writer.write(sdkVersionModel.getTargetSdkVersion());
				writer.write(sdkVersionModel.getMaxSdkVersion());
				writer.write(manifestModel.getLauncherActivity());
				writer.write(manifestModel.getPermissionList());
				writer.write(manifestModel.getActivityList());
				writer.write(manifestModel.getServiceList());
				writer.write(manifestModel.getIntentList());
				writer.write(adsUsageModel.getTotalAds());
				writer.write(adsUsageModel.getListOfAds());
				writer.write(adsUsageModel.getAdsImport());
				writer.write(adsUsageModel.getAdsClassCount());
				writer.write(appsMetaModel.getReleaseNotes());
				writer.write(appsMetaModel.getReleaseNoteDifference());
				writer.endRecord();
			
				
			}else{
				++missingUpdates;
				/*System.out.println(updateKey +" M: manifest: " + manifestInfoList.containsKey(updateKey));
				System.out.println(updateKey +" M: sdkInfo: " + sdkVersionInfoList.containsKey(updateKey));
				System.out.println(updateKey +" M: adsInfo: " + adsInfoList.containsKey(updateKey));
				System.out.println(updateKey +" M: metaInfo: " + appsMetaInfoList.containsKey(updateKey));
				System.out.println("---------------------");*/
			}
		}
		writer.close();
		System.out.println("Finish writing data to the file :-) ");
		System.out.println("Total good update " + totalGoodUpdate);
		System.out.println("Missing updates " + missingUpdates);
		
		
	}

	public static void main(String[] args) throws Exception {
		MergeAllUpdateResult ob = new MergeAllUpdateResult();
		ob.mergeAppUpdateResult();
		System.out.println("Program finishes successfully");
	}
}
