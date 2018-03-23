package com.sail.mobile.deeplearning.update.rating.classification.model;

public class SDKCsvInfo {
	public String releaseName;
	public String applicationName;
	public String dateString;
	public String versionName;
	public String versionCode;
	public int minimumSDK;
	public int maximumSDK;
	public int targetSDK;
	
	public SDKCsvInfo(String relaseName,String applicationName, String dateString,String versionCode, String versionName,
		int minimumSDK, int targetSDK, int maximumSDK){
		this.releaseName=relaseName;
		this.applicationName = applicationName;
		this.dateString = dateString;
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.minimumSDK = minimumSDK;
		this.targetSDK = targetSDK;
		this.maximumSDK = maximumSDK;
		
	}
}
