package com.sail.awsomebasupdates.model;


public class InvestigationPositivity extends Investigation
{
	
	private double currentUpdatePositiveReviews;
	
	private double currentUpdatePositiveReviewsPercentage;
	
	private double currentUpdateDailyPositiveReviews;
	
	private double previousPositiveReviews;

	private double previousPositiveReviewsPercentage;
	
	private double previousDailyPositiveReviews;
	
	private double positivityRatio;
	
	private double positivityPercentageRatio;

	public double getCurrentUpdatePositiveReviews()
	{
		return currentUpdatePositiveReviews;
	}

	public void setCurrentUpdatePositiveReviews(double currentUpdatePositiveReviews)
	{
		this.currentUpdatePositiveReviews = currentUpdatePositiveReviews;
	}

	public double getCurrentUpdatePositiveReviewsPercentage()
	{
		return currentUpdatePositiveReviewsPercentage;
	}

	public void setCurrentUpdatePositiveReviewsPercentage(double currentUpdatePositiveReviewsPercentage)
	{
		this.currentUpdatePositiveReviewsPercentage = currentUpdatePositiveReviewsPercentage;
	}

	public double getCurrentUpdateDailyPositiveReviews()
	{
		return currentUpdateDailyPositiveReviews;
	}


	public void setCurrentUpdateDailyPositiveReviews(double currentUpdateDailyPositiveReviews)
	{
		this.currentUpdateDailyPositiveReviews = currentUpdateDailyPositiveReviews;
	}


	public double getPreviousPositiveReviews()
	{
		return previousPositiveReviews;
	}


	public void setPreviousPositiveReviews(double previousPositiveReviews)
	{
		this.previousPositiveReviews = previousPositiveReviews;
	}


	public double getPreviousPositiveReviewsPercentage()
	{
		return previousPositiveReviewsPercentage;
	}


	public void setPreviousPositiveReviewsPercentage(double previousPositiveReviewsPercentage)
	{
		this.previousPositiveReviewsPercentage = previousPositiveReviewsPercentage;
	}


	public double getPreviousDailyPositiveReviews()
	{
		return previousDailyPositiveReviews;
	}


	public void setPreviousDailyPositiveReviews(double previousDailyPositiveReviews)
	{
		this.previousDailyPositiveReviews = previousDailyPositiveReviews;
	}


	public double getPositivityRatio()
	{
		return positivityRatio;
	}


	public void setPositivityRatio(double positivityRatio)
	{
		this.positivityRatio = positivityRatio;
	}


	public double getPositivityPercentageRatio()
	{
		return positivityPercentageRatio;
	}


	public void setPositivityPercentageRatio(double positivityPercentageRatio)
	{
		this.positivityPercentageRatio = positivityPercentageRatio;
	}


	public void measurePositivityRatio()
	{
		
		double updateDailyPositiveReviews = currentUpdatePositiveReviews / updateLifeTime;
		double appDailyPositiveReviews = previousPositiveReviews / appLifeTime;
		double updatePositivityRatio = updateDailyPositiveReviews / appDailyPositiveReviews;
		positivityRatio = updatePositivityRatio;		
	}


	public void measurePositivityPercentageRatio()
	{
		positivityPercentageRatio = currentUpdatePositiveReviewsPercentage / previousPositiveReviewsPercentage;
		
	}
	
	
	public static final String HEADER = "AppID,UpdateID,CurrentUpdatePositiveReviews,CurrentUpdateTotalReviews,CurrentUpdatePositiveReviewsPercentage,CurrentUpdateDailyPositiveReviews,"
			+ "UpdateLifeTime,PreviousPositiveReviews,PreviousTotalReviews,PreviousPositiveReviewsPercentage,PreviousDailyPositiveReviews,AppLifeTime,PositivityRatio,PositivityPercentageRatio";


	@Override
	public String toString()
	{
		
		StringBuilder builder = new StringBuilder();
		builder.append(AppID);
		builder.append(",");
		builder.append(updateID);
		builder.append(",");
		builder.append(currentUpdatePositiveReviews);
		builder.append(",");
		builder.append(currentUpdateTotalReviews);
		builder.append(",");
		builder.append(currentUpdatePositiveReviewsPercentage);
		builder.append(",");
		builder.append(currentUpdateDailyPositiveReviews);
		builder.append(",");
		builder.append(updateLifeTime);
		builder.append(",");
		builder.append(previousPositiveReviews);
		builder.append(",");
		builder.append(previousTotalReviews);
		builder.append(",");
		builder.append(previousPositiveReviewsPercentage);
		builder.append(",");
		builder.append(previousDailyPositiveReviews);
		builder.append(",");
		builder.append(appLifeTime);
		builder.append(",");
		builder.append(positivityRatio);
		builder.append(",");
		builder.append(positivityPercentageRatio);
		
		return builder.toString();
	}
	
}
