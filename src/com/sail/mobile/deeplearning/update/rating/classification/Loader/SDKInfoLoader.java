package com.sail.mobile.deeplearning.update.rating.classification.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.csvreader.CsvReader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.util.MyTimeUtil;
import com.sail.mobile.deeplearning.update.rating.classification.util.SDKUtil;

public class SDKInfoLoader {
	
	public Map<String,SDKCsvInfo> sdkInfo = new HashMap<String,SDKCsvInfo>();
	public Map<String, ArrayList<SDKCsvInfo>> sdkVersionInfos = new HashMap<String, ArrayList<SDKCsvInfo>>();
	public Map<String, Integer> perdayNumRelease = new HashMap<String, Integer>();
	public Map<String, AppTable> appTableRecords = new HashMap<String, AppTable>();
	public Set<Integer> sdkApiList = new HashSet<Integer>();
	public Set<Integer> minimumSdkApiList = new HashSet<Integer>();
	public Map<Integer, CountClass> appUsesSdks = new HashMap<Integer, CountClass>();
	public Map<Integer, CountClass> appUsesMinimumSdks = new HashMap<Integer, CountClass>();
	public Map<Integer, CountClass> appUsesMaximumSdks = new HashMap<Integer, CountClass>();
	
	public SDKInfoLoader(){
		MyCsvReader myReader = new MyCsvReader();
		appTableRecords = myReader.readAppData();
	}
	
	public void readCSV() {
		Set<String> tempTime = new HashSet<String>();

		int totalUpdate = 0;
		try {
			System.out.println("Start Reading SDK Information");
			CsvReader reader = new CsvReader(Constants.csvSdkVersionInfoPath);
			reader.readHeaders();
			while (reader.readRecord()) {
				try {
					String releaseName = reader.get("Release_Name");
					String packageName = reader.get("Application").trim();
					String dateString = reader.get("Date").trim().replace(".", "-");
					String versionName = reader.get("Version_Name").trim();
					String versionCode = reader.get("Version_Code").trim();
					String minSDK = reader.get("Min_SDK_Version");
					String target = reader.get("Target_SDK_Version");
					String maximum = reader.get("Max_SDK_Version");
		
					int minimumSDKVersion = Integer.parseInt(minSDK);
					int targetSDKVersion = Integer.parseInt(target);
					int maximumSDKVersion = -1;

					
					//System.out.println(packageName);
					
					if ((maximum.trim().length() > 0)) {
						maximumSDKVersion = Integer.parseInt(maximum);
					}

					if (!perdayNumRelease.containsKey(dateString)) {
						perdayNumRelease.put(dateString, 1);
					} else {
						perdayNumRelease.put(dateString, perdayNumRelease.get(dateString) + 1);
					}

					if (!appTableRecords.containsKey(packageName)) {
						continue;
					}

					if (!sdkVersionInfos.containsKey(packageName)) {
						ArrayList<SDKCsvInfo> temp = new ArrayList<SDKCsvInfo>();
						temp.add(new SDKCsvInfo(releaseName, packageName, dateString, versionCode, versionName,
								minimumSDKVersion, targetSDKVersion, 0));
						sdkVersionInfos.put(packageName, temp);
					} else {
						sdkVersionInfos.get(packageName).add(new SDKCsvInfo(releaseName, packageName, dateString,
								versionCode, versionName, minimumSDKVersion, targetSDKVersion, 0));
					}

					SDKUtil.storingInformation(targetSDKVersion, minimumSDKVersion, maximumSDKVersion,appUsesSdks,sdkApiList,
							appUsesMinimumSdks,appUsesMaximumSdks,minimumSdkApiList);

					totalUpdate++;
				
					tempTime.add(dateString.trim());
				} catch (Exception e) {
					System.err.println("One problem in Parsing");
				}

			}

			System.out.println("Total Unique APKs: " + sdkVersionInfos.size() + " Total Update: " + totalUpdate);

			// Sort the SDK information in Ascending Order So that we can
			// analyze as an overtime update
			for (Map.Entry<String, ArrayList<SDKCsvInfo>> m : sdkVersionInfos.entrySet()) {
				Collections.sort(m.getValue(), new Comparator<SDKCsvInfo>() {

					@Override
					public int compare(SDKCsvInfo o1, SDKCsvInfo o2) {
						// TODO Auto-generated method stub

						DateTime d1 = MyTimeUtil.formatter.parseDateTime(o1.dateString.replace(".", "-"));
						DateTime d2 = MyTimeUtil.formatter.parseDateTime(o2.dateString.replace(".", "-"));

						if (d1.isAfter(d2)) {
							return 1;
						} else if (d1.isBefore(d2)) {
							return -1;
						}
						return 0;
					}
				});
			}
			
			for(String appName : sdkVersionInfos.keySet()){
				ArrayList<SDKCsvInfo> updates = sdkVersionInfos.get(appName);
				for(int i = 0 ; i < updates.size() ; i ++ ){
					String key = appName + Constants.COMMA + updates.get(i).versionCode;
					sdkInfo.put(key, updates.get(i));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String,SDKCsvInfo> getSDKInfo(){
		return sdkInfo;
	}
	public static void main(String[] args) {
		SDKInfoLoader ob = new SDKInfoLoader();
		ob.readCSV();
	}
	
}
