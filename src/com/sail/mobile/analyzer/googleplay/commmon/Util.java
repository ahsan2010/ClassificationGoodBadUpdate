package com.sail.mobile.analyzer.googleplay.commmon;

import java.text.ParseException;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Util
{

	public static String removeDelimeter(String originalText, String delimiter)
	{
		return originalText.replaceAll(delimiter, " ");
	}

	public static void main(String[] args) throws ParseException
	{
		System.out.println(removeDelimeter("asd,rr", ","));
	
	}

	
	public static String getReleaseApkName(String applicationName, String releaseCode, String dateString)
	{
		String apkName = applicationName + "-" + releaseCode + "-" + dateString.replaceAll("\\.", "_") + ".apk";
		return apkName;
	}
	
	public static String printStatistics(String dataDescription, DescriptiveStatistics releaseStatistics)
	{
		return dataDescription + ":  Max [" + releaseStatistics.getMax() + "] Min [" + releaseStatistics.getMin() + "] and average value is [" + releaseStatistics.getMean() + "], Median [" + releaseStatistics.getPercentile(50) + "]";
	}
	
	public static String printStatistics(DescriptiveStatistics releaseStatistics)
	{
		return "  Max [" + releaseStatistics.getMax() + "] Min [" + releaseStatistics.getMin() + "] and average value is [" + releaseStatistics.getMean() + "], Median [" + releaseStatistics.getPercentile(50) + "]";
	}

	public static boolean isBlankOrNull(String releaseNotes)
	{
		return (releaseNotes==null) || (releaseNotes.trim().isEmpty());
	}
	
	public static void incrementCounter(HashMap<String, Integer> counterMap, String counterKey)
	{
		if (counterMap.get(counterKey) != null)
		{
			counterMap.put(counterKey, counterMap.get(counterKey) + 1);
		}
		else
		{
			counterMap.put(counterKey, 1);
		}
	}
}
