package com.sail.awsomebasupdates.model;

public class SdkVersionModel {

	public String packageName;
	public String versionCode;
	public String releaseDate;
	
	public String minSdkVersion;
	public String maxSdkVersion;
	public String targetSdkVersion;
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
	public String getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public String getMaxSdkVersion() {
		return maxSdkVersion;
	}
	public void setMaxSdkVersion(String maxSdkVersion) {
		this.maxSdkVersion = maxSdkVersion;
	}
	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}
	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}
	
}
