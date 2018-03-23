package com.sail.awsomebasupdates.model;


public class InvestigationNegativity extends Investigation
{
	
	private double currentUpdateNegativeReviews;
	
	private double currentUpdateNegativeReviewsPercentage;
	
	private double currentUpdateDailyNegativeReviews;
	
	private double previousNegativeReviews;

	private double previousNegativeReviewsPercentage;
	
	private double previousDailyNegativeReviews;
	
	private double negativityRatio;
	
	private double negativityPercentageRatio;


	public double getCurrentUpdateNegativeReviews()
	{
		return currentUpdateNegativeReviews;
	}


	public void setCurrentUpdateNegativeReviews(double currentUpdateNegativeReviews)
	{
		this.currentUpdateNegativeReviews = currentUpdateNegativeReviews;
	}


	public double getCurrentUpdateNegativeReviewsPercentage()
	{
		return currentUpdateNegativeReviewsPercentage;
	}


	public void setCurrentUpdateNegativeReviewsPercentage(double currentUpdateNegativeReviewsPercentage)
	{
		this.currentUpdateNegativeReviewsPercentage = currentUpdateNegativeReviewsPercentage;
	}


	public double getPreviousNegativeReviews()
	{
		return previousNegativeReviews;
	}


	public void setPreviousNegativeReviews(double previousNegativeReviews)
	{
		this.previousNegativeReviews = previousNegativeReviews;
	}


	public double getPreviousNegativeReviewsPercentage()
	{
		return previousNegativeReviewsPercentage;
	}


	public void setPreviousNegativeReviewsPercentage(double previousNegativeReviewsPercentage)
	{
		this.previousNegativeReviewsPercentage = previousNegativeReviewsPercentage;
	}


	public double getNegativityRatio()
	{
		return negativityRatio;
	}


	public void setNegativityRatio(double negativityRatio)
	{
		this.negativityRatio = negativityRatio;
	}


	public double getNegativityPercentageRatio()
	{
		return negativityPercentageRatio;
	}


	public void setNegativityPercentageRatio(double negativityPercentageRatio)
	{
		this.negativityPercentageRatio = negativityPercentageRatio;
	}

	public void measureNegativityRatio()
	{
		
		double updateDailyNegativeReviews = currentUpdateNegativeReviews / updateLifeTime;
		double appDailyNegativeReviews = previousNegativeReviews / appLifeTime;
		double updateNegativityRatio = updateDailyNegativeReviews / appDailyNegativeReviews;
		negativityRatio = updateNegativityRatio;		
	}


	public void measureNegativityPercentageRatio()
	{
		negativityPercentageRatio = currentUpdateNegativeReviewsPercentage / previousNegativeReviewsPercentage;
		
	}

	public double getCurrentUpdateDailyNegativeReviews()
	{
		return currentUpdateDailyNegativeReviews;
	}

	public void setCurrentUpdateDailyNegativeReviews(double currentUpdateDailyNegativeReviews)
	{
		this.currentUpdateDailyNegativeReviews = currentUpdateDailyNegativeReviews;
	}

	public double getPreviousDailyNegativeReviews()
	{
		return previousDailyNegativeReviews;
	}


	public void setPreviousDailyNegativeReviews(double previousDailyNegativeReviews)
	{
		this.previousDailyNegativeReviews = previousDailyNegativeReviews;
	}
	
	public static final String HEADER = "AppID,UpdateID,CurrentUpdateNegativeReviews,CurrentUpdateTotalReviews,CurrentUpdateNegativeReviewsPercentage,CurrentUpdateDailyNegativeReviews,"
			+ "UpdateLifeTime,PreviousNegativeReviews,PreviousTotalReviews,PreviousNegativeReviewsPercentage,PreviousDailyNegativeReviews,AppLifeTime,NegativityRatio,NegativityPercentageRatio";


	@Override
	public String toString()
	{
		
		StringBuilder builder = new StringBuilder();
		builder.append(AppID);
		builder.append(",");
		builder.append(updateID);
		builder.append(",");
		builder.append(currentUpdateNegativeReviews);
		builder.append(",");
		builder.append(currentUpdateTotalReviews);
		builder.append(",");
		builder.append(currentUpdateNegativeReviewsPercentage);
		builder.append(",");
		builder.append(currentUpdateDailyNegativeReviews);
		builder.append(",");
		builder.append(updateLifeTime);
		builder.append(",");
		builder.append(previousNegativeReviews);
		builder.append(",");
		builder.append(previousTotalReviews);
		builder.append(",");
		builder.append(previousNegativeReviewsPercentage);
		builder.append(",");
		builder.append(previousDailyNegativeReviews);
		builder.append(",");
		builder.append(appLifeTime);
		builder.append(",");
		builder.append(negativityRatio);
		builder.append(",");
		builder.append(negativityPercentageRatio);
		
		return builder.toString();
	}
	
}
