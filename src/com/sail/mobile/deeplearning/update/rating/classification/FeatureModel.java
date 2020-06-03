package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.Arrays;
import java.util.List;

public class FeatureModel {
	
	public String appName;
	public String versionCode;
	public String releaseDate;
	public double apkSize; // MegaBytes
	public int numAdLibraries;
	public int numPermissions;
	public int numDangerousPermissions;
	public int numNormalPermissions;
	public int numCustomPermission;
	public int minSdkVersion;
	public int targetSdkVersion;
	public int numActivity;
	public int numService;
	public int numReceiver;
	public int numIntent;
	
	public double ratioMedianOfAllPreviousReleaseTime;
	public double updateReleaseTime;
	
	public double releaseNoteLengthWord;
	public double releaseNoteModified;
	
	public double changeApkSize;
	public double changeNumAdLib;
	public double changeNumPermission;
	public double changeNumNormalPermission;
	public double changeNumDangerousPermission;
	public double changeNumCustomPermission;
	
	public double changeMinSdkVersion;
	public double changeTargetSdkVersion;
	public double changeNumActivity;
	public double changeNumService;
	public double changeNumReceiver;
	public double changeNumIntent;
	
	public double  increaseDecreaseApkSize;
	public double  increaseDecreaseNumAdLib;
	public double  increaseDecreaseNumPermission;
	public double  increaseDecreaseNumNormalPermission;
	public double  increaseDecreaseNumDangerousPermission;
	public double  increaseDecreaseNumCustomPermission;
	
	public double  increaseDecreaseMinSdkVersion;
	public double  inceaseDecreaseTargetSdkVersion;
	public double  increaseDecreaseNumActivity;
	public double  increaseDecreaseNumService;
	public double  increaseDecreaseNumIntent;
	public double  increaseDecreaseNumReceiver;
	
	public double  previousAllUpdatesMedianAggregatedRating;
	public double  previousAllUpdatsMedianNegativeRatingRatio;
	public double  previousUpdateAggregatedRating;
	public double  previousUpdateNegativeRatingRatio;
	public double  negativityRatio;
	public int     targetValue;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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
	public double getApkSize() {
		return apkSize;
	}
	public void setApkSize(double apkSize) {
		this.apkSize = apkSize;
	}
	public int getNumAdLibraries() {
		return numAdLibraries;
	}
	public void setNumAdLibraries(int numAdLibraries) {
		this.numAdLibraries = numAdLibraries;
	}
	public int getNumPermissions() {
		return numPermissions;
	}
	public void setNumPermissions(int numPermissions) {
		this.numPermissions = numPermissions;
	}
	public int getNumDangerousPermissions() {
		return numDangerousPermissions;
	}
	public void setNumDangerousPermissions(int numDangerousPermissions) {
		this.numDangerousPermissions = numDangerousPermissions;
	}
	public int getNumNormalPermissions() {
		return numNormalPermissions;
	}
	public void setNumNormalPermissions(int numNormalPermissions) {
		this.numNormalPermissions = numNormalPermissions;
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
	public int getNumActivity() {
		return numActivity;
	}
	public void setNumActivity(int numActivity) {
		this.numActivity = numActivity;
	}
	public int getNumService() {
		return numService;
	}
	public void setNumService(int numService) {
		this.numService = numService;
	}
	public int getNumReceiver() {
		return numReceiver;
	}
	public void setNumReceiver(int numReceiver) {
		this.numReceiver = numReceiver;
	}
	public int getNumIntent() {
		return numIntent;
	}
	public void setNumIntent(int numIntent) {
		this.numIntent = numIntent;
	}
	public double getReleaseNoteLengthWord() {
		return releaseNoteLengthWord;
	}
	public void setReleaseNoteLengthWord(double releaseNoteLengthWord) {
		this.releaseNoteLengthWord = releaseNoteLengthWord;
	}
	public double getReleaseNoteModified() {
		return releaseNoteModified;
	}
	public void setReleaseNoteModified(double releaseNoteModified) {
		this.releaseNoteModified = releaseNoteModified;
	}
	
	public double getChangeApkSize() {
		return changeApkSize;
	}
	public void setChangeApkSize(double changeApkSize) {
		this.changeApkSize = changeApkSize;
	}
	public double getChangeNumAdLib() {
		return changeNumAdLib;
	}
	public void setChangeNumAdLib(double changeNumAdLib) {
		this.changeNumAdLib = changeNumAdLib;
	}
	public double getChangeNumPermission() {
		return changeNumPermission;
	}
	public void setChangeNumPermission(double changeNumPermission) {
		this.changeNumPermission = changeNumPermission;
	}
	public double getChangeNumNormalPermission() {
		return changeNumNormalPermission;
	}
	public void setChangeNumNormalPermission(double changeNumNormalPermission) {
		this.changeNumNormalPermission = changeNumNormalPermission;
	}
	public double getChangeNumDangerousPermission() {
		return changeNumDangerousPermission;
	}
	public void setChangeNumDangerousPermission(double changeNumDangerousPermission) {
		this.changeNumDangerousPermission = changeNumDangerousPermission;
	}
	public double getChangeNumCustomPermission() {
		return changeNumCustomPermission;
	}
	public void setChangeNumCustomPermission(double changeNumCustomPermission) {
		this.changeNumCustomPermission = changeNumCustomPermission;
	}
	public double getChangeMinSdkVersion() {
		return changeMinSdkVersion;
	}
	public void setChangeMinSdkVersion(double changeMinSdkVersion) {
		this.changeMinSdkVersion = changeMinSdkVersion;
	}
	public double getChangeTargetSdkVersion() {
		return changeTargetSdkVersion;
	}
	public void setChangeTargetSdkVersion(double changeTargetSdkVersion) {
		this.changeTargetSdkVersion = changeTargetSdkVersion;
	}
	public double getChangeNumActivity() {
		return changeNumActivity;
	}
	public void setChangeNumActivity(double changeNumActivity) {
		this.changeNumActivity = changeNumActivity;
	}
	public double getChangeNumService() {
		return changeNumService;
	}
	public void setChangeNumService(double changeNumService) {
		this.changeNumService = changeNumService;
	}
	public double getChangeNumReceiver() {
		return changeNumReceiver;
	}
	public void setChangeNumReceiver(double changeNumReceiver) {
		this.changeNumReceiver = changeNumReceiver;
	}
	public double getChangeNumIntent() {
		return changeNumIntent;
	}
	public void setChangeNumIntent(double changeNumIntent) {
		this.changeNumIntent = changeNumIntent;
	}
	public double getIncreaseDecreaseApkSize() {
		return increaseDecreaseApkSize;
	}
	public void setIncreaseDecreaseApkSize(double increaseDecreaseApkSize) {
		this.increaseDecreaseApkSize = increaseDecreaseApkSize;
	}
	public double getIncreaseDecreaseNumAdLib() {
		return increaseDecreaseNumAdLib;
	}
	public void setIncreaseDecreaseNumAdLib(double increaseDecreaseNumAdLib) {
		this.increaseDecreaseNumAdLib = increaseDecreaseNumAdLib;
	}
	public double getIncreaseDecreaseNumPermission() {
		return increaseDecreaseNumPermission;
	}
	public void setIncreaseDecreaseNumPermission(double increaseDecreaseNumPermission) {
		this.increaseDecreaseNumPermission = increaseDecreaseNumPermission;
	}
	public double getIncreaseDecreaseNumNormalPermission() {
		return increaseDecreaseNumNormalPermission;
	}
	public void setIncreaseDecreaseNumNormalPermission(double increaseDecreaseNumNormalPermission) {
		this.increaseDecreaseNumNormalPermission = increaseDecreaseNumNormalPermission;
	}
	public double getIncreaseDecreaseNumDangerousPermission() {
		return increaseDecreaseNumDangerousPermission;
	}
	public void setIncreaseDecreaseNumDangerousPermission(double increaseDecreaseNumDangerousPermission) {
		this.increaseDecreaseNumDangerousPermission = increaseDecreaseNumDangerousPermission;
	}
	public double getIncreaseDecreaseMinSdkVersion() {
		return increaseDecreaseMinSdkVersion;
	}
	public void setIncreaseDecreaseMinSdkVersion(double increaseDecreaseMinSdkVersion) {
		this.increaseDecreaseMinSdkVersion = increaseDecreaseMinSdkVersion;
	}
	public double getInceaseDecreaseTargetSdkVersion() {
		return inceaseDecreaseTargetSdkVersion;
	}
	public void setInceaseDecreaseTargetSdkVersion(double inceaseDecreaseTargetSdkVersion) {
		this.inceaseDecreaseTargetSdkVersion = inceaseDecreaseTargetSdkVersion;
	}
	public double getIncreaseDecreaseNumActivity() {
		return increaseDecreaseNumActivity;
	}
	public void setIncreaseDecreaseNumActivity(double increaseDecreaseNumActivity) {
		this.increaseDecreaseNumActivity = increaseDecreaseNumActivity;
	}
	public double getIncreaseDecreaseNumService() {
		return increaseDecreaseNumService;
	}
	public void setIncreaseDecreaseNumService(double increaseDecreaseNumService) {
		this.increaseDecreaseNumService = increaseDecreaseNumService;
	}
	public double getIncreaseDecreaseNumIntent() {
		return increaseDecreaseNumIntent;
	}
	public void setIncreaseDecreaseNumIntent(double increaseDecreaseNumIntent) {
		this.increaseDecreaseNumIntent = increaseDecreaseNumIntent;
	}
	public double getIncreaseDecreaseNumReceiver() {
		return increaseDecreaseNumReceiver;
	}
	public void setIncreaseDecreaseNumReceiver(double increaseDecreaseNumReceiver) {
		this.increaseDecreaseNumReceiver = increaseDecreaseNumReceiver;
	}
	
	public double getNegativityRatio() {
		return negativityRatio;
	}
	public void setNegativityRatio(double negativityRatio) {
		this.negativityRatio = negativityRatio;
	}
	public int getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(int targetValue) {
		this.targetValue = targetValue;
	}
	public int getNumCustomPermission() {
		return numCustomPermission;
	}
	public void setNumCustomPermission(int numCustomPermission) {
		this.numCustomPermission = numCustomPermission;
	}
	
	public double getIncreaseDecreaseNumCustomPermission() {
		return increaseDecreaseNumCustomPermission;
	}
	public void setIncreaseDecreaseNumCustomPermission(double increaseDecreaseNumCustomPermission) {
		this.increaseDecreaseNumCustomPermission = increaseDecreaseNumCustomPermission;
	}
	public double getPreviousAllUpdatesMedianAggregatedRating() {
		return previousAllUpdatesMedianAggregatedRating;
	}
	public void setPreviousAllUpdatesMedianAggregatedRating(double previousAllUpdatesMedianAggregatedRating) {
		this.previousAllUpdatesMedianAggregatedRating = previousAllUpdatesMedianAggregatedRating;
	}
	public double getPreviousAllUpdatsMedianNegativeRatingRatio() {
		return previousAllUpdatsMedianNegativeRatingRatio;
	}
	public void setPreviousAllUpdatsMedianNegativeRatingRatio(double previousAllUpdatsMedianNegativeRatingRatio) {
		this.previousAllUpdatsMedianNegativeRatingRatio = previousAllUpdatsMedianNegativeRatingRatio;
	}
	public double getPreviousUpdateAggregatedRating() {
		return previousUpdateAggregatedRating;
	}
	public void setPreviousUpdateAggregatedRating(double previousUpdateAggregatedRating) {
		this.previousUpdateAggregatedRating = previousUpdateAggregatedRating;
	}
	public double getPreviousUpdateNegativeRatingRatio() {
		return previousUpdateNegativeRatingRatio;
	}
	public void setPreviousUpdateNegativeRatingRatio(double previousUpdateNegativeRatingRatio) {
		this.previousUpdateNegativeRatingRatio = previousUpdateNegativeRatingRatio;
	}
	public double getRatioMedianOfAllPreviousReleaseTime() {
		return ratioMedianOfAllPreviousReleaseTime;
	}
	public void setRatioMedianOfAllPreviousReleaseTime(double ratioMedianOfAllPreviousReleaseTime) {
		this.ratioMedianOfAllPreviousReleaseTime = ratioMedianOfAllPreviousReleaseTime;
	}
	public double getUpdateReleaseTime() {
		return updateReleaseTime;
	}
	public void setUpdateReleaseTime(double updateReleaseTime) {
		this.updateReleaseTime = updateReleaseTime;
	}
	
}
