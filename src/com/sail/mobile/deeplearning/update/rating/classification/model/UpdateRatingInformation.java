package com.sail.mobile.deeplearning.update.rating.classification.model;

import org.joda.time.DateTime;

public class UpdateRatingInformation {

	protected double totalStar;
	
	protected double oneStar;

	protected double twoStar;
	
	protected double threeStar;
	
	protected double fourStar;
	
	protected double fiveStar;
	
	protected double aggregatedRating;
	
	protected String packageName;
	
	protected String versionCode;
	
	protected DateTime startDate;
	
	protected DateTime endDate;

	public int targetSDK;
	
	public int minimumSDK;
	
	public String AppId;
	
	protected int updateLifeTimeInDays;
	
	
	
	
	public double getTotalStar() {
		return totalStar;
	}

	public void setTotalStar(double totalStar) {
		this.totalStar = totalStar;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public int getUpdateLifeTimeInDays() {
		return updateLifeTimeInDays;
	}

	public void setUpdateLifeTimeInDays(int updateLifeTimeInDays) {
		this.updateLifeTimeInDays = updateLifeTimeInDays;
	}

	public int getTargetSDK() {
		return targetSDK;
	}

	public void setTargetSDK(int targetSDK) {
		this.targetSDK = targetSDK;
	}

	public int getMinimumSDK() {
		return minimumSDK;
	}

	public void setMinimumSDK(int minimumSDK) {
		this.minimumSDK = minimumSDK;
	}

	public double getOneStar() {
		return oneStar;
	}

	public void setOneStar(double oneStar) {
		this.oneStar = oneStar;
	}

	public double getTwoStar() {
		return twoStar;
	}

	public void setTwoStar(double twoStar) {
		this.twoStar = twoStar;
	}

	public double getThreeStar() {
		return threeStar;
	}

	public void setThreeStar(double threeStar) {
		this.threeStar = threeStar;
	}

	public double getFourStar() {
		return fourStar;
	}

	public void setFourStar(double fourStar) {
		this.fourStar = fourStar;
	}

	public double getFiveStar() {
		return fiveStar;
	}

	public void setFiveStar(double fiveStar) {
		this.fiveStar = fiveStar;
	}

	public double getAggregatedRating() {
		return aggregatedRating;
	}

	public void setAggregatedRating(double aggregatedRating) {
		this.aggregatedRating = aggregatedRating;
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

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
}
