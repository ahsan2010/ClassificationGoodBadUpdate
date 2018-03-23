package com.sail.awsomebasupdates.model;

public class AppInfo
{

	private DailyReviewsStatus startRating;

	private DailyReviewsStatus endRating;

	
	private int lifeTime;
	
	private long totalNegativeSignal;

	private long totalReviews;

	private long totalPositiveSignal;

	private double dailyNegativeSignal;

	private double negativePercentage;
	
	private double dailyPositiveSignal;
	
	private double positivePercentage;
	
	boolean isCorruptedData = false;
	
	

	public long getTotalReviews()
	{
		return totalReviews;
	}

	public void setTotalReviews(long totalReviews)
	{
		this.totalReviews = totalReviews;
	}

	@Override
	public AppInfo clone() throws CloneNotSupportedException
	{
		AppInfo clonedAppInfo = new AppInfo();
		clonedAppInfo.setTotalReviews(totalReviews);
		clonedAppInfo.setNegativePercentage(negativePercentage);
		clonedAppInfo.setCorruptedData(isCorruptedData);
		clonedAppInfo.setDailyNegativeSignal(dailyNegativeSignal);
		clonedAppInfo.setDailyPositiveSignal(dailyPositiveSignal);
		clonedAppInfo.setEndRating(endRating.clone());
		clonedAppInfo.setLifeTime(lifeTime);
		clonedAppInfo.setStartRating(startRating.clone());
		clonedAppInfo.setTotalNegativeSignal(totalNegativeSignal);
		clonedAppInfo.setTotalPositiveSignal(totalPositiveSignal);
		return clonedAppInfo;
	}
	
	public long getTotalPositiveSignal()
	{
		return totalPositiveSignal;
	}

	public void setTotalPositiveSignal(long totalPositiveSignal)
	{
		this.totalPositiveSignal = totalPositiveSignal;
	}

	public double getDailyPositiveSignal()
	{
		return dailyPositiveSignal;
	}

	public void setDailyPositiveSignal(double dailyPositiveSignal)
	{
		this.dailyPositiveSignal = dailyPositiveSignal;
	}

	public boolean isCorruptedData()
	{
		return isCorruptedData;
	}

	public void setCorruptedData(boolean isCorruptedData)
	{
		this.isCorruptedData = isCorruptedData;
	}

	public int getLifeTime()
	{
		return lifeTime;
	}

	public void setLifeTime(int lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	public long getTotalNegativeSignal()
	{
		return totalNegativeSignal;
	}

	public void setTotalNegativeSignal(long totalNegativeSignal)
	{
		this.totalNegativeSignal = totalNegativeSignal;
	}

	public double getDailyNegativeSignal()
	{
		return dailyNegativeSignal;
	}

	public void setDailyNegativeSignal(double dailyNegativeSignal)
	{
		this.dailyNegativeSignal = dailyNegativeSignal;
	}

	public DailyReviewsStatus getStartRating()
	{
		return startRating;
	}

	public void setStartRating(DailyReviewsStatus startRating)
	{
		this.startRating = startRating;
	}

	public DailyReviewsStatus getEndRating()
	{
		return endRating;
	}

	public void setEndRating(DailyReviewsStatus endRating)
	{
		this.endRating = endRating;
	}
	
	
	public double getNegativePercentage()
	{
		return negativePercentage;
	}

	public void setNegativePercentage(double negativePercentage)
	{
		this.negativePercentage = negativePercentage;
	}
	
	public double getPositivePercentage()
	{
		return positivePercentage;
	}

	public void setPositivePercentage(double positivePercentage)
	{
		this.positivePercentage = positivePercentage;
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("lifeTime=" + lifeTime + ", totalNegativeSignal=" + totalNegativeSignal + ", dailyNegativeSignal=" + dailyNegativeSignal);
		result.append(", totalPositiveSignal=" + totalPositiveSignal + ", dailyPositiveSignal=" + dailyPositiveSignal);
		if(startRating!=null)
		{
			result.append(", Start date = " + startRating.getDate());
		}
		else
		{
			result.append(", Start date is null");
		}
		
		if(endRating!=null)
		{
			result.append(", End date = " + endRating.getDate());
		}
		else
		{
			result.append(", End date is null");
		}
		return result.toString();
	}

	
}
