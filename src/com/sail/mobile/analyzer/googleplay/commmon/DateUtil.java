package com.sail.mobile.analyzer.googleplay.commmon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil
{

	
	/**
	 * Calculate the difference between two dates in days as follows:  Result = secondDate - firstDate
	 * @throws ParseException 
	 */
	public static int calculateDatesDifference(String firstDate, String secondDate) throws ParseException
	{
		return calculateDatesDifference(firstDate, secondDate, Constants.RESULTS_DATE_FORMAT);
	}

	/**
	 * Returns number of days between new and old dates.
	 * 
	 * @param oldDate
	 * @param newDate
	 * @return  new date - old date
	 */
	public static int calculateDatesDifference(Date oldDate,  Date newDate)
	{
		long diff = newDate.getTime() - oldDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return (int) diffDays;
	}
	
	/**
	 * Calculate the difference between two dates in days as follows:  Result = secondDate - firstDate
	 * Dates should follow the format string represented in the dateFormat
	 * @throws ParseException 
	 */
	public static int calculateDatesDifference(String firstDate, String secondDate, String dateFormat) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date oldDate;
		Date newDate;
		oldDate = (Date) formatter.parse(firstDate);
		newDate = (Date) formatter.parse(secondDate);
		return calculateDatesDifference(oldDate, newDate);
	}

	/**
	 * Shift the date with the passed seconds.
	 * @param date   The original date that will be shifted.
	 * @param shiftingSeconds	The shifting duraion in seconds (this value can be either positive or negative value).
	 * @return
	 */
	public static Date shiftDate(Date date, int shiftingDays)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, shiftingDays);
		Date shiftedDate = calendar.getTime();
		return shiftedDate;
	}
	
	
	public static Date getDateValue(String dateText, String dateFormat) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.parse(dateText);
	}
	
	public static Date readFullDate(String record, int index) throws Exception
	{
		Date currentReviewDate = getDateValue(TextUtil.getColumn(record, index).replaceAll("\\\"", ""), Constants.FULL_TIME_DATE_FORMAT);
		return currentReviewDate;
	}
	
	public static Date readShortDate(String record, int index) throws Exception
	{
		Date currentReviewDate = getDateValue(TextUtil.getColumn(record, index).replaceAll("\\\"", ""), Constants.SHORT_DATE_FORMAT);
		return currentReviewDate;
	}
	
	/**
	 * This central metod used to convert the dates inot same string format so all strings can be easly handled.
	 * If the input date in <b>Null</b> then the returned value will be null. So the code is immune agains null values.
	 * @return
	 */
	public static String convertDate2String(Date date, String dateFormat)
	{
		if(date!=null)
		{
			DateFormat formatter = new SimpleDateFormat(dateFormat);
			String dateString = formatter.format(date);
			return dateString;
		}
		return null;
	}

}
