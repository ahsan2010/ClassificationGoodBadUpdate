package com.sail.awsomebasupdates.model;

import java.util.Date;

import com.sail.mobile.analyzer.googleplay.commmon.Constants;
//import com.queens.sail.googleplaycrawler.common.Constants;
import com.sail.mobile.analyzer.googleplay.commmon.DateUtil;


public class UpdatePositiveSignalInfo extends UpdateRatingInfo
{
	public static final String HEADER = UpdateRatingInfo.HEADER + ",Lifetime,Start_Date,End_Date,Update_Total_Positive_Reviews,Update_Daily_Positive_Reviews,"
			+ "APP_Lifetime,App_Total_Positive_Reviews,App_Daily_Positive_Reviews,Update_Positivity,Deployed_APK,Previously_Deployed_APK,IsAlive";
	
	public static final double FIVE_STAR_WEIGHT = 1;
	public static final double FOUR_STARS_WEIGHT = 1;

	private int lifeTime;
	
	private long totalPositiveSignal;
	
	private double dailyPositiveSignal;

	private AppInfo appInfo;
	
	private String deployedApk;
	
	private String previousDeployedApk;

	private double updatePositivity;
	
	private Date startDate;
	
	private Date endDate;
	
	
	
	public double getUpdatePositivity()
	{
		return updatePositivity;
	}

	public void setUpdatePositivity(double updatePositivity)
	{
		this.updatePositivity = updatePositivity;
	}

	public void setTotalPositiveSignal(long totalPositiveSignal)
	{
		this.totalPositiveSignal = totalPositiveSignal;
	}

	public long getTotalPositiveSignal()
	{
		return totalPositiveSignal;
	}

	public double getDailyPositiveSignal()
	{
		return dailyPositiveSignal;
	}

	public void setDailyPositiveSignal(double dailyPositiveSignal)
	{
		this.dailyPositiveSignal = dailyPositiveSignal;
	}

	
	public String getDeployedApk()
	{
		return deployedApk;
	}

	public void setDeployedApk(String deployedApk)
	{
		this.deployedApk = deployedApk;
	}

	public String getPreviousDeployedApk()
	{
		return previousDeployedApk;
	}

	public void setPreviousDeployedApk(String previousDeployedApk)
	{
		this.previousDeployedApk = previousDeployedApk;
	}

	public AppInfo getAppInfo()
	{
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo)
	{
		this.appInfo = appInfo;
	}

	public int getLifeTime()
	{
		return lifeTime;
	}

	public void setLifeTime(int lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

//	public static final String HEADER = UpdateRatingInfo.HEADER + ",Lifetime,Start_Date,End_Date,Update_Total_Positive_Reviews,Update_Daily_Positive_Reviews,"
//			+ "APP_Lifetime,App_Total_Positive_Reviews,App_Daily_Positive_Reviews,Update_Positivity,Deployed_APK,Previously_Deployed_APK";

	public String printInfo()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(super.printInfo());
		builder.append(",");
		builder.append(lifeTime);
		builder.append(",");
		builder.append(DateUtil.convertDate2String(startDate, Constants.FULL_TIME_DATE_FORMAT));
		builder.append(",");
		builder.append(DateUtil.convertDate2String(endDate, Constants.FULL_TIME_DATE_FORMAT));
		builder.append(",");
		builder.append(totalPositiveSignal);
		builder.append(",");
		builder.append(dailyPositiveSignal);
		builder.append(",");
		builder.append(appInfo.getLifeTime());
		builder.append(",");
		builder.append(appInfo.getTotalPositiveSignal());
		builder.append(",");
		builder.append(appInfo.getDailyPositiveSignal());
		builder.append(",");
		builder.append(updatePositivity);
		builder.append(",");
		builder.append(deployedApk);
		builder.append(",");
		builder.append(previousDeployedApk);
		builder.append(",");
		builder.append(isAlife);
		
		return builder.toString();
		
	}

	public static void main(String[] args)
	{
		UpdatePositiveSignalInfo u = new UpdatePositiveSignalInfo();
		u.setOneStar(2);
		u.setTwoStar(3);
		u.setThreeStar(4);
		u.setFourStar(5);
		u.setFiveStar(1);
		u.convertRating2Distribution();
	}

}
