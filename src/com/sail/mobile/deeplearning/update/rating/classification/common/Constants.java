package com.sail.mobile.deeplearning.update.rating.classification.common;

import org.joda.time.DateTime;

import com.sail.mobile.analysis.adevolution.util.DateUtil;

public class Constants {
	public static final String JAR_FILES = "jar";
	public static final String APK_FILES = "apk";
	public static final String COMMA = ",";
	public static final String HYPHEN = "-";
	public static final String UNDERSCORE = "_";
	public static final String WEEK_DURATION = "Week";
	public static final String MONTH_DURATION = "Month";
	public static final String DAY_DURATION = "Month";
	public static int TOTAL_NUMBER_OF_STUDIED_APPS = 2174;
	
	

	public static String PERMISSION_NORMAL = "Normal";
	public static String PERMISSION_DANGEROUD = "Dangerous";
	public static String PERMISSION_SIGNATURE = "Signature";
	public static String PERMISSION_CUSTOM = "Custom";
	
	public static DateTime EPERIMENT_START_TIME = DateUtil.formatter.parseDateTime("2016_04_20");
	public static DateTime EPERIMENT_END_TIME = DateUtil.formatter.parseDateTime("2019_04_20");

	//public static String ROOT = "/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT";
	///home/local/SAIL/ahsan/Documents/Update_Classification/
	//public static String ROOT = "/home/ahasan/Documents/ROOT";
	public static String ROOT = "/home/local/SAIL/ahsan/Documents/Pinky/Documents/Update_Classification_SBSE_May/ROOT";
	
	public static String APP_ANALYTICS_FILE_PATH = ROOT + "/Data/App_Analytics_Full_Info.csv";
	public static final String APP_NEGATIVITY_RATIO_ANALYSIS_FILE = ROOT + "/result/app_negativity_ratio.csv";
	public static final String VARIFIED_AD_PACKAGE_LIST = ROOT+"/Data/Verified_Ads_Updated.csv";
	public static String csvSdkVersionInfoPath = ROOT +"/scripts/SDK_Versions_Report.csv";
	//all_app_update_2016_Apps.csv
	public static final String APP_UPDATE_TABLE_PATH_RATING = ROOT + "/Data/Final_Studied_App_Updates_2016_2019.csv";
	public static final String APP_UPDATE_TABLE_PATH = ROOT + "/scripts/studied_app_updates_final_2016.csv";
	public static final String ADS_UPDATE_DATA_PATH = ROOT + "/scripts/Update_Verified__Group_Ads_Usage_Info_Overtime.csv";
	public static String APP_CSV_PATH = ROOT + "/scripts/app_top_2016.csv";
	public static final String CLOSED_APP_List = ROOT +"/scripts/closed_app.csv";
	public static final String MANIFEST_FILE_LOCATION = "/home/ahsan/SampleApks/result/AndroidManifest/";
	public static final String ANDROID_DANGEROUS_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/dangerous_permission_list.csv";
	public static final String ANDROID_ALL_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/android_permission_list.csv";
	public static final String APPS_LIBRARY_SIZE_PER_UPDATES = ROOT + "/scripts/app_ads_library_size.csv";
	public static final String APP_SIZE_INFO = ROOT + "/scripts/size_thread.csv";
	public static final String APP_SIZE_INFO_II = ROOT + "/scripts/App_Ad_Size.csv";
	
	
	//Safwat
	public static final long DEFAULT_LIFE_TIME = -9999999999L;
	public static final double DEFAULT_REPONSE_TIME = -9999999999L;
	public static final String COMMA_SEPARATOR = ",";
	public static final String TAB_SEPARATOR = "\t";
	public static final String SQL_DATE_FORMAT = "MMM d, yyyy";
	public static final String RESULTS_DATE_FORMAT = "yyyy.MM.dd";
	public static final String RESULT_OUTPUT_LOCATION = "/Users/shassan/Desktop/Safwat/PhD_Work/MSR/Project/Mobile/Results/";
	public static final String INPUT_LOCATION = "/Users/shassan/Desktop/Safwat/PhD_Work/MSR/Project/Mobile/Data/";
	public static final String REPOSATORY_INPUT_LOCATION = "/Users/shassan/Documents/PhDWorkspace/MSR_Project_Documentation/Results/";
	
	public static final String DATA_SNAPSHOT_DATE = "2014.11.18";
	public static final String PREPROCESSING_LOCATION = "/Users/shassan/Documents/PhDWorkspace/MSR_Project_Documentation/Results/Preprocessing/";
	
	public static final String MEDICAL_CATEGORY = "MEDICAL";
	public static final String DASH_SEPARATOR = "-";
	public static final String DUMMY_VERSION = "Dummy_Version_String";
	
	public static final String OR_CONDITION = "|";
	public static final String AND_CONDITION = "&";
	public static final String SPACE_SEPARATOR = " ";
	public static final String UNDERSCORE_SEPARATOR = "_";

	/** This format will result date in this shape "2010-05-30 22:15:52"	*/
	public static final String FULL_TIME_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
	
}
