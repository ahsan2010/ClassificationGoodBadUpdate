package com.sail.awsomebasupdates.model;

import java.util.Date;

import com.sail.mobile.analyzer.googleplay.commmon.Constants;
import com.sail.mobile.analyzer.googleplay.commmon.DateUtil;

public class UpdateNegativeSignalInfo extends UpdateRatingInfo {
	public static final String HEADER = UpdateRatingInfo.HEADER
			+ ",Lifetime,Start_Date,End_Date,Update_Total_Negative_Reviews,Update_Daily_Negative_Reviews,"
			+ "APP_Lifetime,App_Total_Negative_Reviews,App_Daily_Negative_Reviews,Update_Negativity,Deployed_APK,Previously_Deployed_APK,IsAlive";

	public static final double ONE_STAR_WEIGHT = 1;
	public static final double TWO_STARS_WEIGHT = 1;

	private int lifeTime;

	private long totalNegativeSignal;

	private double dailyNegativeSignal;

	private AppInfo appInfo;

	private double updateNegativity;

	private double updateNegativityPercentage;

	private String deployedApk;

	private String previousDeployedApk;

	private Date startDate;

	private Date endDate;

	public String getDeployedApk() {
		return deployedApk;
	}

	public void setDeployedApk(String deployedApk) {
		this.deployedApk = deployedApk;
	}

	public String getPreviousDeployedApk() {
		return previousDeployedApk;
	}

	public void setPreviousDeployedApk(String previousDeployedApk) {
		this.previousDeployedApk = previousDeployedApk;
	}

	public double getUpdateNegativity() {
		return updateNegativity;
	}

	public void setUpdateNegativity(double updateNegativity) {
		this.updateNegativity = updateNegativity;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public long getTotalNegativeSignal() {
		return totalNegativeSignal;
	}

	public void setTotalNegativeSignal(long totalNegativeSignal) {
		this.totalNegativeSignal = totalNegativeSignal;
	}

	public double getDailyNegativeSignal() {
		return dailyNegativeSignal;
	}

	public void setDailyNegativeSignal(double dailyNegativeSignal) {
		this.dailyNegativeSignal = dailyNegativeSignal;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getUpdateNegativityPercentage() {
		return updateNegativityPercentage;
	}

	public void setUpdateNegativityPercentage(double updateNegativityPercentage) {
		this.updateNegativityPercentage = updateNegativityPercentage;
	}

	public String printInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.printInfo());
		builder.append(",");
		builder.append(lifeTime);
		builder.append(",");
		builder.append(DateUtil.convertDate2String(startDate, Constants.FULL_TIME_DATE_FORMAT));
		builder.append(",");
		builder.append(DateUtil.convertDate2String(endDate, Constants.FULL_TIME_DATE_FORMAT));
		builder.append(",");
		builder.append(totalNegativeSignal);
		builder.append(",");
		builder.append(dailyNegativeSignal);
		builder.append(",");
		builder.append(appInfo.getLifeTime());
		builder.append(",");
		builder.append(appInfo.getTotalNegativeSignal());
		builder.append(",");
		builder.append(appInfo.getDailyNegativeSignal());
		builder.append(",");
		builder.append(updateNegativity);
		builder.append(",");
		builder.append(deployedApk);
		builder.append(",");
		builder.append(previousDeployedApk);
		builder.append(",");
		builder.append(isAlife);

		return builder.toString();

	}

	public static void main(String[] args) {
		UpdateNegativeSignalInfo u = new UpdateNegativeSignalInfo();
		u.setOneStar(2);
		u.setTwoStar(3);
		u.setThreeStar(4);
		u.setFourStar(5);
		u.setFiveStar(1);
		u.convertRating2Distribution();
	}

}
