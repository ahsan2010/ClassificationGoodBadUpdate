package com.sail.mobile.analyzer.googleplay.commmon;


public class TextUtil
{

	public static String getColumn(String record, int index)
	{
		return record.split(",")[index];
	}
	
	public static long getLongColumn(String record, int index)
	{
		return Long.valueOf(getColumn(record, index));
	}
	
	public static double getDoubleColumn(String record, int index)
	{
		return Double.valueOf(getColumn(record, index));
	}
	
	public static int getIntColumn(String record, int index)
	{
		return Integer.valueOf(getColumn(record, index));
	}
	
	
}
