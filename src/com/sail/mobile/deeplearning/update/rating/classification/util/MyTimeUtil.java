package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.util.ArrayList;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MyTimeUtil {
	public static ArrayList<String> timeIntervalAll = new ArrayList<String>();
	public static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static DateTimeFormatter formatterDot = DateTimeFormat.forPattern("yyyy.MM.dd");
	public static void initTimeMap(Map<String, Integer> m) {
		for (String s : timeIntervalAll) {
			m.put(s, 0);
		}
	}
}
