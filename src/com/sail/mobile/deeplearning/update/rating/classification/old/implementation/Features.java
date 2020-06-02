package com.sail.mobile.deeplearning.update.rating.classification.old.implementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Features {

	public String appName;
	public int updateIndex;
	public String versionCode;

	public List<String> feature_name = Arrays.asList(new String[] { "TargetSDK", "MinimumSDK", "DiffTargetMinSDK",
			"AppUpdateSize", "AdSize", "NumberOfAds", "NumberOfPermission", "NumberOfDangerousPermission",
			"NumberOfOtherPermission", "NumberOfActivity", "NumberOfReceiver", "NumberOfService", "TargetSDKChange",
			"MiniumSDKChange", "AdsChange", "ActivityChange", "ReceiverChange", "ServiceChange", "PermissionChange",
			"DangerousPermissionChange", "OtherPermissionChange", "AppSizeChange", "AdsSizeChange", "Target" });
	Map<String, Double> featureValue = new HashMap<String, Double>();

	public void setFeature(Map<String, Double> featureValue) {
		this.featureValue = featureValue;
	}

	public Map<String, Double> getFeatureValue() {
		return this.featureValue;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getUpdateIndex() {
		return updateIndex;
	}

	public void setUpdateIndex(int updateIndex) {
		this.updateIndex = updateIndex;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public List<String> getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(List<String> feature_name) {
		this.feature_name = feature_name;
	}

	public void setFeatureValue(Map<String, Double> featureValue) {
		this.featureValue = featureValue;
	}
}
