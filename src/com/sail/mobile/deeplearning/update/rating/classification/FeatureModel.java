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
	public int minSdkVersion;
	public int targetSdkVersion;
	public int numActivity;
	public int numService;
	public int numReceiver;
	public int numIntent;
	public double releaseNoteLengthWord;
	public double Release_note_changed_word;
	public boolean changeApkSize;
	public boolean changeNumAdLib;
	public boolean changeNumPermission;
	public boolean changeNumNormalPermission;
	public boolean changeNumDangerousPermission;
	public boolean changeMinSdkVersion;
	public boolean changeTargetSdkVersion;
	public boolean changeNumActivity;
	public boolean changeNumService;
	public boolean changeNumReceiver;
	public boolean changeNumIntent;
	public double  increaseDecreaseApkSize;
	public double increaseDecreaseNumAdLib;
	public double increaseDecreaseNumPermission;
	public double increaseDecreaseNumNormalPermission;
	public double increaseDecreaseNumDangerousPermission;
	public double increaseDecreaseMinSdkVersion;
	public double inceaseDecreaseTargetSdkVersion;
	public double increaseDecreaseNumActivity;
	public double increaseDecreaseNumService;
	public double increaseDecreaseNumIntent;
	public double increaseDecreaseNumReceiver;
	public double previousMedianAggregatedRating;
	public double previousMedianNegativityRatio;
	public double negativityRatio;
	public int targetValue;
	
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
	public double getRelease_note_changed_word() {
		return Release_note_changed_word;
	}
	public void setRelease_note_changed_word(double release_note_changed_word) {
		Release_note_changed_word = release_note_changed_word;
	}
	public boolean isChangeApkSize() {
		return changeApkSize;
	}
	public void setChangeApkSize(boolean changeApkSize) {
		this.changeApkSize = changeApkSize;
	}
	public boolean isChangeNumAdLib() {
		return changeNumAdLib;
	}
	public void setChangeNumAdLib(boolean changeNumAdLib) {
		this.changeNumAdLib = changeNumAdLib;
	}
	public boolean isChangeNumPermission() {
		return changeNumPermission;
	}
	public void setChangeNumPermission(boolean changeNumPermission) {
		this.changeNumPermission = changeNumPermission;
	}
	public boolean isChangeNumNormalPermission() {
		return changeNumNormalPermission;
	}
	public void setChangeNumNormalPermission(boolean changeNumNormalPermission) {
		this.changeNumNormalPermission = changeNumNormalPermission;
	}
	public boolean isChangeNumDangerousPermission() {
		return changeNumDangerousPermission;
	}
	public void setChangeNumDangerousPermission(boolean changeNumDangerousPermission) {
		this.changeNumDangerousPermission = changeNumDangerousPermission;
	}
	public boolean isChangeMinSdkVersion() {
		return changeMinSdkVersion;
	}
	public void setChangeMinSdkVersion(boolean changeMinSdkVersion) {
		this.changeMinSdkVersion = changeMinSdkVersion;
	}
	public boolean isChangeTargetSdkVersion() {
		return changeTargetSdkVersion;
	}
	public void setChangeTargetSdkVersion(boolean changeTargetSdkVersion) {
		this.changeTargetSdkVersion = changeTargetSdkVersion;
	}
	public boolean isChangeNumActivity() {
		return changeNumActivity;
	}
	public void setChangeNumActivity(boolean changeNumActivity) {
		this.changeNumActivity = changeNumActivity;
	}
	public boolean isChangeNumService() {
		return changeNumService;
	}
	public void setChangeNumService(boolean changeNumService) {
		this.changeNumService = changeNumService;
	}
	public boolean isChangeNumReceiver() {
		return changeNumReceiver;
	}
	public void setChangeNumReceiver(boolean changeNumReceiver) {
		this.changeNumReceiver = changeNumReceiver;
	}
	public boolean isChangeNumIntent() {
		return changeNumIntent;
	}
	public void setChangeNumIntent(boolean changeNumIntent) {
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
	public double getPreviousMedianAggregatedRating() {
		return previousMedianAggregatedRating;
	}
	public void setPreviousMedianAggregatedRating(double previousMedianAggregatedRating) {
		this.previousMedianAggregatedRating = previousMedianAggregatedRating;
	}
	public double getPreviousMedianNegativityRatio() {
		return previousMedianNegativityRatio;
	}
	public void setPreviousMedianNegativityRatio(double previousMedianNegativityRatio) {
		this.previousMedianNegativityRatio = previousMedianNegativityRatio;
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
	
}
