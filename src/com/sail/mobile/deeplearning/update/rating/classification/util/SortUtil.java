package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.sail.awsomebasupdates.model.AppAnalyticsModel;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class SortUtil {

	/**
	 * Sort all the updates in ascending order of the release date
	 * 
	 * @param appUpdates
	 */
	public static void sortUpdatesByReleaseDate(HashMap<String, ArrayList<UpdateTable>> appUpdates) {

		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);

			Collections.sort(updates, new Comparator<UpdateTable>() {

				@Override
				public int compare(UpdateTable o1, UpdateTable o2) {
					// TODO Auto-generated method stub

					DateTime d1 = DateUtil.formatterWithHyphen.parseDateTime(o1.getRELEASE_DATE());
					DateTime d2 = DateUtil.formatterWithHyphen.parseDateTime(o2.getRELEASE_DATE());

					if (d1.isAfter(d2)) {
						return 1;
					} else if (d1.isBefore(d2)) {
						return -1;
					}
					return 0;
				}
			});
		}
	}

	
	public static void sortAppAnalyticsUpdateByReleaseDate(Map<String, ArrayList<AppAnalyticsModel>> appUpdateAnalytics) {
		for (String appName : appUpdateAnalytics.keySet()) {
			ArrayList<AppAnalyticsModel> updatesAnalytics = appUpdateAnalytics.get(appName);
			Collections.sort(updatesAnalytics, new Comparator<AppAnalyticsModel>() {
				@Override
				public int compare(AppAnalyticsModel o1, AppAnalyticsModel o2) {
					if (o1.getJodaReleaseDate().isAfter(o2.getJodaReleaseDate())) {
						return 1;
					} else if (o1.getJodaReleaseDate().isBefore(o2.getJodaReleaseDate())) {
						return -1;
					}
					return 0;
				}
			});
		}
	}
	
	public static void sortDateTime(ArrayList<DateTime> dateList) {
		Collections.sort(dateList, new Comparator<DateTime>() {

			@Override
			public int compare(DateTime o1, DateTime o2) {
				// TODO Auto-generated method stub

				DateTime d1 = o1;
				DateTime d2 = o2;

				if (d1.isAfter(d2)) {
					return 1;
				} else if (d1.isBefore(d2)) {
					return -1;
				}
				return 0;
			}
		});
	}

	/**
	 * Sort the ad library used informaiton by the release dates
	 * @param appUseAds
	 */
	public static void sortAdLibraryInformatoinByDate(Map<String, ArrayList<AdInformation>> appUseAds) {
		for (String appName : appUseAds.keySet()) {
			ArrayList<AdInformation> usedAds = appUseAds.get(appName);
			Collections.sort(usedAds, new Comparator<AdInformation>() {

				@Override
				public int compare(AdInformation o1, AdInformation o2) {
					// TODO Auto-generated method stub
					DateTime d1 = DateUtil.formatterWithHyphen.parseDateTime(o1.getReleaseDate());
					DateTime d2 = DateUtil.formatterWithHyphen.parseDateTime(o2.getReleaseDate());
					if (d1.isAfter(d2)) {
						return 1;
					} else if (d1.isBefore(d2)) {
						return -1;
					}
					return 0;
				}
			});
		}
	}

}
