package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.util.Map;
import java.util.Set;

import com.sail.mobile.deeplearning.update.rating.classification.Loader.CountClass;

public class SDKUtil {
	
	
	
	public static void storingInformation(int targetSDKVersion, int minimumSDKVersion, int maximumSDKVersion,
			Map<Integer, CountClass> appUsesSdks,Set<Integer> sdkApiList,Map<Integer, CountClass> appUsesMinimumSdks,
			Map<Integer, CountClass> appUsesMaximumSdks,Set<Integer> minimumSdkApiList) {
		
		
		sdkApiList.add(targetSDKVersion);
		minimumSdkApiList.add(minimumSDKVersion);

		if (!appUsesSdks.containsKey(targetSDKVersion)) {
			// System.out.println(appUsesSdks.size());
			CountClass cc = new CountClass();
			MyTimeUtil.initTimeMap(cc.noOfApps);
			appUsesSdks.put(targetSDKVersion, cc);

		}

		if (!appUsesMinimumSdks.containsKey(minimumSDKVersion)) {
			CountClass cc = new CountClass();
			MyTimeUtil.initTimeMap(cc.noOfApps);
			appUsesMinimumSdks.put(minimumSDKVersion, cc);

		}

		if (maximumSDKVersion > 0) {
			if (!appUsesMaximumSdks.containsKey(maximumSDKVersion)) {
				CountClass cc = new CountClass();
				MyTimeUtil.initTimeMap(cc.noOfApps);
				appUsesMaximumSdks.put(maximumSDKVersion, cc);

			}
		}
	}
}
