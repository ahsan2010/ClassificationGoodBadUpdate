package com.sail.awsomebasupdates.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.DateTime;

public class AppAnalyticsModel {
	
	public String appId;
	public String appUpdateId;
	public String packageName;
	public String versionCode;
	public String releaseDate;
	public DateTime jodaReleaseDate;
	public double aggregatedRating;
	public int numberOfOneStars;
	public int numberOfTwoStars;
	public int numberOfThreeStars;
	public int numberOfFourStars;
	public int numberOfFiveStars;
	public double apkSizeByte;
	public String numberOfDownloads;
	public int minSdkVersion;
	public int targetSdkVersion;
	public Set<String> permissionList;
	public Set<String> activityList;
	public Set<String> serviceList;
	public Set<String> intentList;
	public Set<String> receiverList;
	public String launcherActivity;
	public Set<String> adLibraryList;
	public Set<String> adLibraryImportedList;
	public String adLibraryClassCount;
	public String releaseNote;
	public String releaseNoteDifference;
	
	public double updateOneStar;
	public double updateTwoStar;
	public double updateThreeStar;
	public double updateFourStar;
	public double updateFiveStar;
	public double updateAggreatedRating;
	
	public double prevUpdatesNegRatings;
	public double prevUpdatesTotalRatings;
	
	
	DescriptiveStatistics previousRatioNegativesStat = new DescriptiveStatistics();
	
	
	public double getPrevUpdatesNegRatings() {
		return prevUpdatesNegRatings;
	}
	public void setPrevUpdatesNegRatings(double prevUpdatesNegRatings) {
		this.prevUpdatesNegRatings = prevUpdatesNegRatings;
	}
	public double getPrevUpdatesTotalRatings() {
		return prevUpdatesTotalRatings;
	}
	public void setPrevUpdatesTotalRatings(double prevUpdatesTotalRatings) {
		this.prevUpdatesTotalRatings = prevUpdatesTotalRatings;
	}
	public DescriptiveStatistics getPreviousRatioNegativesStat() {
		return previousRatioNegativesStat;
	}
	public double getUpdateOneStar() {
		return updateOneStar;
	}
	public void setUpdateOneStar(double updateOneStar) {
		this.updateOneStar = updateOneStar;
	}
	public double getUpdateTwoStar() {
		return updateTwoStar;
	}
	public void setUpdateTwoStar(double updateTwoStar) {
		this.updateTwoStar = updateTwoStar;
	}
	public double getUpdateThreeStar() {
		return updateThreeStar;
	}
	public void setUpdateThreeStar(double updateThreeStar) {
		this.updateThreeStar = updateThreeStar;
	}
	public double getUpdateFourStar() {
		return updateFourStar;
	}
	public void setUpdateFourStar(double updateFourStar) {
		this.updateFourStar = updateFourStar;
	}
	public double getUpdateFiveStar() {
		return updateFiveStar;
	}
	public void setUpdateFiveStar(double updateFiveStar) {
		this.updateFiveStar = updateFiveStar;
	}
	public double getUpdateAggreatedRating() {
		return updateAggreatedRating;
	}
	public void setUpdateAggreatedRating(double updateAggreatedRating) {
		this.updateAggreatedRating = updateAggreatedRating;
	}
	public String getNumberOfDownloads() {
		return numberOfDownloads;
	}
	public void setNumberOfDownloads(String numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppUpdateId() {
		return appUpdateId;
	}
	public void setAppUpdateId(String appUpdateId) {
		this.appUpdateId = appUpdateId;
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
	public DateTime getJodaReleaseDate() {
		return jodaReleaseDate;
	}
	public void setJodaReleaseDate(DateTime jodaReleaseDate) {
		this.jodaReleaseDate = jodaReleaseDate;
	}
	public double getAggregatedRating() {
		return aggregatedRating;
	}
	public void setAggregatedRating(double aggregatedRating) {
		this.aggregatedRating = aggregatedRating;
	}
	public int getNumberOfOneStars() {
		return numberOfOneStars;
	}
	public void setNumberOfOneStars(int numberOfOneStars) {
		this.numberOfOneStars = numberOfOneStars;
	}
	public int getNumberOfTwoStars() {
		return numberOfTwoStars;
	}
	public void setNumberOfTwoStars(int numberOfTwoStars) {
		this.numberOfTwoStars = numberOfTwoStars;
	}
	public int getNumberOfThreeStars() {
		return numberOfThreeStars;
	}
	public void setNumberOfThreeStars(int numberOfThreeStars) {
		this.numberOfThreeStars = numberOfThreeStars;
	}
	public int getNumberOfFourStars() {
		return numberOfFourStars;
	}
	public void setNumberOfFourStars(int numberOfFourStars) {
		this.numberOfFourStars = numberOfFourStars;
	}
	public int getNumberOfFiveStars() {
		return numberOfFiveStars;
	}
	public void setNumberOfFiveStars(int numberOfFiveStars) {
		this.numberOfFiveStars = numberOfFiveStars;
	}
	public double getApkSizeByte() {
		return apkSizeByte;
	}
	public void setApkSizeByte(double apkSizeByte) {
		this.apkSizeByte = apkSizeByte;
	}
	public int getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(int minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public int getTargetSdkVersion() {
		return targetSdkVersion;
	}
	public void setTargetSdkVersion(int targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}
	public Set<String> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(Set<String> permissionList) {
		this.permissionList = permissionList;
	}
	public Set<String> getActivityList() {
		return activityList;
	}
	public void setActivityList(Set<String> activityList) {
		this.activityList = activityList;
	}
	public Set<String> getServiceList() {
		return serviceList;
	}
	public void setServiceList(Set<String> serviceList) {
		this.serviceList = serviceList;
	}
	public Set<String> getIntentList() {
		return intentList;
	}
	public void setIntentList(Set<String> intentList) {
		this.intentList = intentList;
	}
	public String getLauncherActivity() {
		return launcherActivity;
	}
	public void setLauncherActivity(String launcherActivity) {
		this.launcherActivity = launcherActivity;
	}
	public Set<String> getAdLibraryList() {
		return adLibraryList;
	}
	public void setAdLibraryList(Set<String> adLibraryList) {
		this.adLibraryList = adLibraryList;
	}
	public Set<String> getAdLibraryImportedList() {
		return adLibraryImportedList;
	}
	public void setAdLibraryImportedList(Set<String> adLibraryImportedList) {
		this.adLibraryImportedList = adLibraryImportedList;
	}
	public String getAdLibraryClassCount() {
		return adLibraryClassCount;
	}
	public void setAdLibraryClassCount(String adLibraryClassCount) {
		this.adLibraryClassCount = adLibraryClassCount;
	}
	public String getReleaseNote() {
		return releaseNote;
	}
	public void setReleaseNote(String releaseNote) {
		this.releaseNote = releaseNote;
	}
	public String getReleaseNoteDifference() {
		return releaseNoteDifference;
	}
	public void setReleaseNoteDifference(String releaseNoteDifference) {
		this.releaseNoteDifference = releaseNoteDifference;
	}
	
	public void printInfo(){
		System.out.println("PackageName: " + this.getPackageName());
		System.out.println("VersionCode: " + this.getVersionCode());
		System.out.println("ReleaseDaet: " + this.getJodaReleaseDate().toString());
		System.out.println("MinSDk: " + this.getMinSdkVersion());
		System.out.println("TargetSDK: " + this.getTargetSdkVersion());
		System.out.println("Permission: " + this.getPermissionList().size());
		System.out.println("Activity: " + this.getActivityList().size());
		System.out.println("Service: " + this.getServiceList().size());
		System.out.println("Intent: " + this.getIntentList().size());
		System.out.println("Number of downloads: " + this.getNumberOfDownloads());
		System.out.println("Aggregated rating: " + this.getAggregatedRating());
		System.out.println("One Star: " + this.getNumberOfOneStars());
		System.out.println("Two Star: " + this.getNumberOfTwoStars());
		System.out.println("Three Star: " + this.getNumberOfThreeStars());
		System.out.println("Four Star: " + this.getNumberOfFourStars());
		System.out.println("Five Star: " + this.getNumberOfFiveStars());
		System.out.println("Release note: " + this.getReleaseNote().length());
		System.out.println("-------------------------------------------------");
	}
	
	public double getRatioNegativeRatings(){
		return getNegativeStars()/Math.max(getTotalStars(),1);
	}
	
	public double getNegativeStars(){
		return this.getUpdateOneStar() + this.getUpdateTwoStar();
	}
	public double getTotalStars(){
		return 	this.getUpdateOneStar() + this.getUpdateTwoStar() + 
				this.getUpdateThreeStar() + this.getUpdateFourStar() + this.getUpdateFiveStar();
	}
	public double getPrevNegativeRatioSafwatTSE(){
		return (getPrevUpdatesNegRatings()/Math.max(getPrevUpdatesTotalRatings(),1));
	}
	public double getPrevNegativeRatioMedian(){
		return getPreviousRatioNegativesStat().getPercentile(50);
	}
	public double getNegativityRatioSafwatTSE(){
		if(getPrevNegativeRatioSafwatTSE() <= 0){
			return (getRatioNegativeRatings()/Math.max(getPrevNegativeRatioSafwatTSE(),1));
		}
		return (getRatioNegativeRatings()/getPrevNegativeRatioSafwatTSE());
	}
	public double getNegativityRatioWithMedian(){
		if(getPrevNegativeRatioMedian() <= 0){
			return  (getRatioNegativeRatings()/Math.max(getPrevNegativeRatioMedian(),1));
		}
		return (getRatioNegativeRatings()/getPrevNegativeRatioMedian());
	}
	public Set<String> getReceiverList() {
		return receiverList;
	}
	public void setReceiverList(Set<String> receiverList) {
		this.receiverList = receiverList;
	}
	public void setPreviousRatioNegativesStat(DescriptiveStatistics previousRatioNegativesStat) {
		this.previousRatioNegativesStat = previousRatioNegativesStat;
	}
}
