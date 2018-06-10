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
	
	

	public static DateTime EPERIMENT_START_TIME = DateUtil.formatter.parseDateTime("2016_04_20");
	public static DateTime EPERIMENT_END_TIME = DateUtil.formatter.parseDateTime("2017_09_20");

	public static String ROOT = "/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/";

	public static final String VARIFIED_AD_PACKAGE_LIST = ROOT+"/scripts/verified_ads.csv";
	public static String csvSdkVersionInfoPath = ROOT +"/scripts/SDK_Versions_Report.csv";
	//all_app_update_2016_Apps.csv
	public static final String APP_UPDATE_TABLE_PATH_RATING = ROOT + "/scripts/all_app_update_2016_Apps.csv";
	public static final String APP_UPDATE_TABLE_PATH = ROOT + "/scripts/studied_app_updates_final_2016.csv";
	public static final String ADS_UPDATE_DATA_PATH = ROOT + "/scripts/Update_Verified__Group_Ads_Usage_Info_Overtime.csv";
	public static String APP_CSV_PATH = ROOT + "/scripts/app_top_2016.csv";
	public static final String CLOSED_APP_List = ROOT +"/scripts/closed_app.csv";
	public static final String MANIFEST_FILE_LOCATION = "/home/ahsan/SampleApks/result/AndroidManifest/";
	public static final String ANDROID_DANGEROUS_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/dangerous_permission_list.csv";
	public static final String ANDROID_ALL_PERMISSION_LIST = "/home/ahsan/SampleApks/result/PermissionAnalysis/android_permission_list.csv";
	public static final String APPS_LIBRARY_SIZE_PER_UPDATES = ROOT + "/scripts/app_ads_library_size.csv";
	public static final String APP_SIZE_INFO = ROOT + "/scripts/size_thread.csv";
	
	
}
