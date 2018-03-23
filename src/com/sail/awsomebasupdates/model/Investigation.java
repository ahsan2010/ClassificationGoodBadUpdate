package com.sail.awsomebasupdates.model;


public class Investigation
{
	

	protected long AppID;
	
	protected long updateID;

	protected double currentUpdateTotalReviews;
	
	protected double updateLifeTime;

	protected double appLifeTime;
	
	protected double previousTotalReviews;


	public double getCurrentUpdateTotalReviews()
	{
		return currentUpdateTotalReviews;
	}


	public void setCurrentUpdateTotalReviews(double currentUpdateTotalReviews)
	{
		this.currentUpdateTotalReviews = currentUpdateTotalReviews;
	}


	public double getPreviousTotalReviews()
	{
		return previousTotalReviews;
	}


	public void setPreviousTotalReviews(double previousTotalReviews)
	{
		this.previousTotalReviews = previousTotalReviews;
	}


	public double getUpdateLifeTime()
	{
		return updateLifeTime;
	}


	public void setUpdateLifeTime(double updateLifeTime)
	{
		this.updateLifeTime = updateLifeTime;
	}


	public double getAppLifeTime()
	{
		return appLifeTime;
	}


	public void setAppLifeTime(double appLifeTime)
	{
		this.appLifeTime = appLifeTime;
	}


	public long getAppID()
	{
		return AppID;
	}


	public void setAppID(long appID)
	{
		AppID = appID;
	}


	public long getUpdateID()
	{
		return updateID;
	}


	public void setUpdateID(long updateID)
	{
		this.updateID = updateID;
	}
	
}
