package com.sail.awsomebasupdates.model;

public class UpdateRatingInfo
{
	public static final String HEADER ="AppID,UpdateID,Aggregated_Rating,App_Rating_Before_Deployment,Rate_Diff,OneStar,TwoStar,ThreeStar,FourStar,FiveStar,Total";
	
	protected long AppID;
	
	protected long updateID;
	
	protected double oneStar;

	protected double twoStar;
	
	protected double threeStar;
	
	protected double fourStar;
	
	protected double fiveStar;
	
	protected double aggregatedRating;
	
	protected double appRatingBeforeDeployment;

	protected int isAlife = 0;

	public int getIsAlife()
	{
		return isAlife;
	}

	public void setIsAlife(int isAlife)
	{
		this.isAlife = isAlife;
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

	public double getAggregatedRating()
	{
		return aggregatedRating;
	}

	public void setAggregatedRating(double aggregatedRating)
	{
		this.aggregatedRating = aggregatedRating;
	}

	public double getAppRatingBeforeDeployment()
	{
		return appRatingBeforeDeployment;
	}

	public void setAppRatingBeforeDeployment(double appRatingBeforeDeployment)
	{
		this.appRatingBeforeDeployment = appRatingBeforeDeployment;
	}

//	public static final String HEADER ="AppID,UpdateID,Aggregated_Rating,App_Rating_Before_Deployment,OneStar,TwoStar,ThreeStar,FourStar,FiveStar";
	public String printInfo()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(AppID);
		builder.append(",");
		builder.append(updateID);
		builder.append(",");
		builder.append(aggregatedRating);
		builder.append(",");
		builder.append(appRatingBeforeDeployment);
		builder.append(",");
		builder.append(aggregatedRating - appRatingBeforeDeployment);
		builder.append(",");
		builder.append(oneStar);
		builder.append(",");
		builder.append(twoStar);
		builder.append(",");
		builder.append(threeStar);
		builder.append(",");
		builder.append(fourStar);
		builder.append(",");
		builder.append(fiveStar);
		builder.append(",");
		builder.append(oneStar + twoStar + threeStar + fourStar + fiveStar);
		return builder.toString();
	}

	public double getNumberOfReviews(int rating)
	{
		if(rating==1)
		{
			return (oneStar);
		}
		else if(rating==2)
		{
			return (twoStar);
		}
		else if(rating==3)
		{
			return (threeStar);
		}
		else if(rating==4)
		{
			return (fourStar);
		}
		else if(rating==5)
		{
			return (fiveStar);
		}
		else
		{
			throw new RuntimeException("Invalid value");
		}
	}
	public double calculateProbability(int rating)
	{
		double totalUpdates = oneStar + twoStar + threeStar + fourStar + fiveStar;
		if(rating==1)
		{
			return (oneStar/totalUpdates);
		}
		else if(rating==2)
		{
			return (twoStar/totalUpdates);
		}
		else if(rating==3)
		{
			return (threeStar/totalUpdates);
		}
		else if(rating==4)
		{
			return (fourStar/totalUpdates);
		}
		else if(rating==5)
		{
			return (fiveStar/totalUpdates);
		}
		else
		{
			throw new RuntimeException("Invalid value");
		}
	}

	public double getTotalReviews()
	{
		return oneStar + twoStar + threeStar + fourStar + fiveStar;
	}

	public double[] convertRating2Distribution()
	{
		double totalUpdates = oneStar + twoStar + threeStar + fourStar + fiveStar;

		double [] Distribution = new double [(int) totalUpdates];
		for(int index = 0; index < oneStar; index ++)
		{
			Distribution [index] = 1;
		}
		
		for(int index = (int) oneStar; index < (oneStar+twoStar); index ++)
		{
			Distribution [index] = 2;
		}
		
		for(int index = (int) (oneStar+twoStar); index < (oneStar+twoStar+threeStar); index ++)
		{
			Distribution [index] = 3;
		}
		
		for(int index = (int) (oneStar+twoStar+threeStar); index < (oneStar+twoStar+threeStar+fourStar); index ++)
		{
			Distribution [index] = 4;
		}
		
		for(int index = (int) (oneStar+twoStar+threeStar+fourStar); index < totalUpdates; index ++)
		{
			Distribution [index] = 5;
		}
		
		return Distribution;
	}

	public static void main(String[] args)
	{
		UpdateRatingInfo u = new UpdateRatingInfo();
		u.setOneStar(2);
		u.setTwoStar(3);
		u.setThreeStar(4);
		u.setFourStar(5);
		u.setFiveStar(1);
		u.convertRating2Distribution();
	}

}
