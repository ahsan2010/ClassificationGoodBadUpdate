package com.sail.awsomebasupdates.model;

public class UpdateInfo
{
	private long updateID;
	
	private int lifetime;

	public UpdateInfo(long updateID, int lifetime)
	{
		this.updateID = updateID;
		this.lifetime = lifetime;
	}
	
	public long getUpdateID()
	{
		return updateID;
	}

	public void setUpdateID(long updateID)
	{
		this.updateID = updateID;
	}

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof UpdateInfo)
		{
			UpdateInfo anotherInfo = (UpdateInfo) obj;
			if(anotherInfo.getUpdateID() == updateID)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Long.valueOf(updateID).hashCode();
	}

	@Override
	public String toString()
	{
		return "updateID=" + updateID + ",lifetime=" + lifetime;
	}
	
}
