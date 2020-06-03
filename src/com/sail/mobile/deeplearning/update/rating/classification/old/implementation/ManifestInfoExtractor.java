
package com.sail.mobile.deeplearning.update.rating.classification.old.implementation;

import java.util.ArrayList;
import java.util.HashMap;

import com.csvreader.CsvWriter;
import com.sail.mobile.deeplearning.update.rating.classification.Parser;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;
import com.sail.mobile.deeplearning.update.rating.classification.util.TextUtil;

public class ManifestInfoExtractor {
	public String MANIFEST_FILE_PATH = "/safwatscratch/shassan/AndroidManifest/";
	public final String UPDATE_PATH = Constants.ROOT + "/Data/Final_Studied_App_Updates_2016_2019.csv";
	public final String OUTPUT = Constants.ROOT + "/Data/ManifestExtractedInfo.csv";

	public void analyzeManifestFiles() throws Exception {
		CsvWriter writer = new CsvWriter(OUTPUT);
		writer.write("App_Name");
		writer.write("Versison_Code");
		writer.write("Release_Date");
		writer.write("Launcher_Activity");
		writer.write("Permission");
		writer.write("Activity");
		writer.write("Service");
		writer.write("Intent");
		writer.write("Receiver");
		writer.endRecord();

		int totalAnalyzeUpdate = 0;
		int totalUpdates = 0 ;

		HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader.readUpdateData(UPDATE_PATH);
		Parser p = new Parser();
		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			for (int i = 0; i < updates.size(); i++) {
				totalUpdates ++ ;
				UpdateTable update = updates.get(i);
				// AndroidManifest-ah.creativecodeapps.tiempo-13-2016_10_17.xml
				String key = "AndroidManifest" + "-" + appName + "-" + update.getVERSION_CODE() + "-"
						+ update.getRELEASE_DATE().replace("-", "_");
				try {
					p.ParseXml(MANIFEST_FILE_PATH + key + ".xml");
					String launcherActivity = p.launcherActivity();
					writer.write(appName);
					writer.write(update.getVERSION_CODE());
					writer.write(update.getRELEASE_DATE());
					writer.write(launcherActivity);
					writer.write(TextUtil.setToString(p.getPermissionList()));
					writer.write(TextUtil.setToString(p.getActivityList()));
					writer.write(TextUtil.setToString(p.getServiceList()));
					writer.write(TextUtil.setToString(p.getIntentList()));
					writer.write(TextUtil.setToString(p.getReceiverList()));
					writer.endRecord();
					++totalAnalyzeUpdate;
					
				} catch (Exception e) {

				}
				System.out.println("Analyzing updates [" + totalAnalyzeUpdate + "] Total updates [" + totalUpdates + "]");

			}
		}
		System.out.println("Total update analyze for finding Launcher Activity [" + totalAnalyzeUpdate + "]");
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		ManifestInfoExtractor ob = new ManifestInfoExtractor();
		ob.analyzeManifestFiles();
		System.out.println("Program finishes successfully");
	}
}
