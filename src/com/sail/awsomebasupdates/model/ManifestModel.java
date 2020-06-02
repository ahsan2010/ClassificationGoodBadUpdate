package com.sail.awsomebasupdates.model;

public class ManifestModel {
	
	public String packageName;
	public String versionCode;
	public String releaseDate;
	public String launcherActivity;
	public String permissionList;
	public String activityList;
	public String serviceList;
	public String intentList;
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getLauncherActivity() {
		return launcherActivity;
	}
	public void setLauncherActivity(String launcherActivity) {
		this.launcherActivity = launcherActivity;
	}
	public String getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(String permissionList) {
		this.permissionList = permissionList;
	}
	public String getActivityList() {
		return activityList;
	}
	public void setActivityList(String activityList) {
		this.activityList = activityList;
	}
	public String getServiceList() {
		return serviceList;
	}
	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}
	public String getIntentList() {
		return intentList;
	}
	public void setIntentList(String intentList) {
		this.intentList = intentList;
	}
}
