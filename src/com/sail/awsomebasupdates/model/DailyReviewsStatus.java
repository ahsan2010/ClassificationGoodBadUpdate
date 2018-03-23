package com.sail.awsomebasupdates.model;

import java.util.Date;

public class DailyReviewsStatus
{

	private Date date;
	
	private double oneStar;

	private double twoStar;
	
	private double threeStar;
	
	private double fourStar;
	
	private double fiveStar;
	
	@Override
	public DailyReviewsStatus clone() throws CloneNotSupportedException
	{
		DailyReviewsStatus clonedDailyReviewsStatus = new DailyReviewsStatus();
		clonedDailyReviewsStatus.setDate(date);
		clonedDailyReviewsStatus.setOneStar(oneStar);
		clonedDailyReviewsStatus.setTwoStar(twoStar);
		clonedDailyReviewsStatus.setThreeStar(threeStar);
		clonedDailyReviewsStatus.setFourStar(fourStar);
		clonedDailyReviewsStatus.setFiveStar(fiveStar);
		
		return clonedDailyReviewsStatus;
	}
	

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public double getOneStar()
	{
		return oneStar;
	}

	public void setOneStar(double oneStar)
	{
		this.oneStar = oneStar;
	}

	public double getTwoStar()
	{
		return twoStar;
	}

	public void setTwoStar(double twoStar)
	{
		this.twoStar = twoStar;
	}

	public double getThreeStar()
	{
		return threeStar;
	}

	public void setThreeStar(double threeStar)
	{
		this.threeStar = threeStar;
	}

	public double getFourStar()
	{
		return fourStar;
	}

	public void setFourStar(double fourStar)
	{
		this.fourStar = fourStar;
	}

	public double getFiveStar()
	{
		return fiveStar;
	}

	public void setFiveStar(double fiveStar)
	{
		this.fiveStar = fiveStar;
	}

	@Override
	public String toString()
	{
		return "date=" + date + " ,One-star=" + oneStar + " ,two-stars=" + twoStar + " ,three-stars=" + threeStar + " ,four-stars=" + fourStar + " ,five-stars="+ fiveStar;
	}
}
