package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;



public class DateUtil {
	public static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy_MM_dd");
	public static DateTimeFormatter formatterWithHyphen = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static DateFormat javaUtilDateTimeformatter = new SimpleDateFormat("yyyy_MM_dd");
	
	
	public static String generateYearMonth(String dateString){
		return dateString.substring(0,dateString.lastIndexOf("_"));
	}
	
	
	
	public static DateTime getEndDate(String durationType, DateTime studyStartDate,String metricName){
		DateTime endDate = null;
		if (durationType.equals(Constants.WEEK_DURATION)) {
			endDate = studyStartDate.plusWeeks(1);
			endDate = endDate.minusDays(1);
		} else if (durationType.equals(Constants.MONTH_DURATION)) {
			endDate = studyStartDate.plusMonths(1);
			endDate = endDate.minusDays(1);
		} else if (durationType.equals(Constants.DAY_DURATION)) {
			endDate = studyStartDate;
		}
		return endDate;
	}
	
	public static void main(String arg[])throws Exception{
		
		DateUtil ob = new DateUtil();
		//System.out.println(ob.formatter.parseDateTime("2016_06_15").plusWeeks(1).minusDays(1).toString());
		//System.out.println(ob.javaUtilDateTimeformatter.parse("2016_06_15"));
		
		
	}
	
	
	/**
	 * The first day of the week and all weeks from start date till end date.
	 * 1/1/2017, 8/1/2017 .... 1/5/2018
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static ArrayList<DateTime> getWeeksInBetween(DateTime startDate, DateTime endDate)
	{
		
		Weeks weeks = Weeks.weeksBetween(startDate, endDate);

		int numbOfWeek = weeks.getWeeks();
		DateTime tempIncrementDate = startDate;
		
		System.out.println("Number of Weeks ["+numbOfWeek+"]");
		
		ArrayList<DateTime> firstDaysOfWeek = new ArrayList<DateTime>();
		firstDaysOfWeek.add(tempIncrementDate);
		for(int i = 0 ; i < numbOfWeek ; i ++){			
			tempIncrementDate = tempIncrementDate.plusDays(7);
			firstDaysOfWeek.add(tempIncrementDate);
		}
		
	
		return firstDaysOfWeek;
	}
	
	public static ArrayList<DateTime> getMonthsInBetween(DateTime startDate, DateTime endDate){
		ArrayList<DateTime> daysBetween = new ArrayList<DateTime>();
		int durationMonths = Months.monthsBetween(startDate, endDate).getMonths();
		DateTime tempIncrementDate = startDate;
		for(int i = 0 ; i < durationMonths ; i ++ ){
			daysBetween.add(tempIncrementDate);
			tempIncrementDate = tempIncrementDate.plusDays(30);
			
		}
		
		return daysBetween;
	}
	
	public static ArrayList<DateTime> getDaysInBetween(DateTime startDate, DateTime endDate){
		ArrayList<DateTime> daysBetween = new ArrayList<DateTime>();
		int durationMonths = Days.daysBetween(startDate, endDate).getDays();
		DateTime tempIncrementDate = startDate;
		for(int i = 0 ; i < durationMonths ; i ++ ){
			daysBetween.add(tempIncrementDate);
			tempIncrementDate = tempIncrementDate.plusDays(1);
			
		}
		
		return daysBetween;
	}
	
	
}
