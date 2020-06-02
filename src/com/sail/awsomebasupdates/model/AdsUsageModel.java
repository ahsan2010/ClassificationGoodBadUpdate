package com.sail.awsomebasupdates.model;

public class AdsUsageModel {

	public String packageName;
	public String versionCode;
	public String releaseDate;
	public String totalAds;
	public String listOfAds;
	public String adsImport;
	public String adsClassCount;
	
	
	public String getAdsImport() {
		return adsImport;
	}
	public void setAdsImport(String adsImport) {
		this.adsImport = adsImport;
	}
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
	public String getTotalAds() {
		return totalAds;
	}
	public void setTotalAds(String totalAds) {
		this.totalAds = totalAds;
	}
	public String getListOfAds() {
		return listOfAds;
	}
	public void setListOfAds(String listOfAds) {
		this.listOfAds = listOfAds;
	}
	public String getAdsClassCount() {
		return adsClassCount;
	}
	public void setAdsClassCount(String adsClassCount) {
		this.adsClassCount = adsClassCount;
	}
}
