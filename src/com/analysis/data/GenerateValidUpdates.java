package com.analysis.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.deeplearning.update.rating.classification.RatingAnalyzer;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.SDKInfoLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.SDKCsvInfo;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class GenerateValidUpdates {

	Map<String, Double> updateAdSize = new HashMap<String, Double>();
	Map<String, Double> updateSize = new HashMap<String, Double>();
	
	Set<String> adInfo = new HashSet<String>();
	
	public void loadSizeInformation() {
		try {
			CsvReader reader = new CsvReader(Constants.APP_SIZE_INFO);
			reader.readHeaders();
			while (reader.readRecord()) {
				String appName = reader.get("App_Name");
				String versionCode = reader.get("Version_Code");

				int totalCol = reader.getColumnCount();
				Double appSize = Math.abs(Double.parseDouble(reader.get(2)));
				double adsSize = 0;
				for (int i = 3; i < totalCol; i++) {
					adsSize += Math.ceil(Double.parseDouble(reader.get(i)));
				}
				
				String key = appName + Constants.COMMA + versionCode;
				adInfo.add(key);
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getFirstUpdateIndex(ArrayList<UpdateTable> updates) {

		// Considering 30 days as a threshold value
		for (int i = 0; i < updates.size(); i++) {
			UpdateTable update = updates.get(i);
			DateTime releaseTime = DateUtil.formatterWithHyphen.parseDateTime(update.getRELEASE_DATE());
			if (releaseTime.isAfter(Constants.EPERIMENT_START_TIME.minusDays(1))) {
				// get the previous update. our analysis start 2016-04-20. we
				// only considering the last immediate update of this time
				if (i > 0) {
					return (i);
				} else if (i == 0) {
					return i;
				}
			}
		}
		return -1;
	}

	public void filterValidUpdate() throws Exception {
		
		Map<String, Double> updateSize = new HashMap<String, Double>();
		SDKInfoLoader sdkInfoLoader = new SDKInfoLoader();
		sdkInfoLoader.readCSV();
		loadSizeInformation();
		
		
		Map<String, SDKCsvInfo> sdkInfo = sdkInfoLoader.getSDKInfo();
		RatingAnalyzer rat = new RatingAnalyzer();
		Map<String, UpdateRatingInformation> updateRatingInfo = rat.generateUpdateRating();

		HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader
				.readUpdateData(Constants.APP_UPDATE_TABLE_PATH_RATING);
		CsvWriter writer = new CsvWriter(Constants.ROOT + "/scripts/app_update_2016_valid.csv");
		writer.write("APP_ID");
		writer.write("APP_UPDATE_ID");
		writer.write("PACKAGE_NAME");
		writer.write("VERSION_CODE");
		writer.write("RELEASE_DATE");
		writer.write("APK_FILE_SIZE");
		writer.endRecord();

		CsvWriter writerUpdateNumber = new CsvWriter(Constants.ROOT + "/scripts/updates_per_app.csv");
		writerUpdateNumber.write("APP_NAME");
		writerUpdateNumber.write("Updates");
		writerUpdateNumber.endRecord();

		int total_analyzed_updates = 0;
		int only_one_update = 0;
		int total_analyzed_app = 0;
		int missingUpdates = 0;
		
		Set<String> manifestFilesList = AdsInputDataLoader.loadManifestFileList();
		
		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			int firstUpdate = getFirstUpdateIndex(updates);

			if (firstUpdate < 0) {
				System.err.println("Problem in First Update Index.. check the code !!!");
				continue;
			}

			if (Math.abs(updates.size() - firstUpdate) <= 2) {
				only_one_update++;
				continue;
			}
			total_analyzed_app++;
			int total_update = 0;
			for (int i = firstUpdate; i < updates.size(); i++) {
				UpdateTable presentUpdate = updates.get(i);

				String key = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();

				if (!updateRatingInfo.containsKey(key)) {
					missingUpdates++;
					continue;
				}
				
				String presentKey = "AndroidManifest-" + appName + "-" + presentUpdate.getVERSION_CODE() + "-"
						+ presentUpdate.getRELEASE_DATE().replace("-", "_") + ".xml";

				if (!manifestFilesList.contains(presentKey)) {
					continue;
				}
				
				if(!sdkInfo.containsKey(key)){
					continue;
				}
				
				if(!adInfo.contains(key)){
					continue;
				}
				
				/*
				if(!updateSize.containsKey(key) || !updateAdSize.containsKey(key)){
					continue;
				}*/
				
				
				
				writer.write(presentUpdate.getAPP_ID());
				writer.write(presentUpdate.getAPP_UPDATE_ID());
				writer.write(presentUpdate.getPACKAGE_NAME());
				writer.write(presentUpdate.getVERSION_CODE());
				writer.write(presentUpdate.getRELEASE_DATE());
				writer.write(presentUpdate.getAPK_SIZE());
				writer.endRecord();
				total_analyzed_updates++;
				total_update ++;
			}
			if(total_update > 2){
				writerUpdateNumber.write(appName);
				writerUpdateNumber.write(Integer.toString(total_update));
				writerUpdateNumber.endRecord();
			}
			
		}
		System.out.println("Missing updates because of rating [" + missingUpdates + "]");
		System.out.println("Total_analyzed_app [" + total_analyzed_app + "]");
		System.out.println("App has only one update [" + only_one_update + "]");
		System.out.println("Total analyzed updates [" + total_analyzed_updates + "]");

		writer.close();
		writerUpdateNumber.close();
	}

	public void filterUpdatesNoRatingInfo() throws Exception {

		CsvWriter writer = new CsvWriter(Constants.ROOT + "/scripts/update_missing_rating.csv");
		writer.write("App_Name");
		writer.write("Version_Code");
		writer.write("Release_Date");
		writer.endRecord();

		RatingAnalyzer rat = new RatingAnalyzer();
		Map<String, UpdateRatingInformation> updateRatingInfo = rat.generateUpdateRating();

		HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader
				.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
		int total_analyzed_updates = 0;
		int only_one_update = 0;
		int total_analyzed_app = 0;
		int missingRatingInfo = 0;
		int missingUpdates = 0;

		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			int firstUpdate = getFirstUpdateIndex(updates);

			if (firstUpdate < 0) {
				continue;
			}

			if (Math.abs(updates.size() - firstUpdate) <= 1) {
				only_one_update++;
				continue;
			}
			System.out.println(updates.get(firstUpdate).getRELEASE_DATE());
			total_analyzed_app++;
			for (int i = firstUpdate; i < updates.size(); i++) {
				UpdateTable presentUpdate = updates.get(i);
				String key = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();
				if (!updateRatingInfo.containsKey(key)) {
					missingRatingInfo++;
					writer.write(appName);
					writer.write(presentUpdate.getVERSION_CODE());
					writer.write(presentUpdate.getRELEASE_DATE());
					writer.endRecord();
					continue;
				}

				total_analyzed_updates++;
			}
		}
		writer.close();
		System.out.println("Missing rating info [" + missingRatingInfo + "]");
		System.out.println("Analyzed updates [" + total_analyzed_updates + "]");
		System.out.println("Total Updates [" + (missingRatingInfo + total_analyzed_updates) + "]");
	}

	public void manifestFileAnalysis() throws Exception{

		CsvWriter writer = new CsvWriter(Constants.ROOT+"/scripts/missing_manifest_files.csv");
		writer.write("KEY");
		writer.endRecord();
		
		
		
		HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader
				.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
		Set<String> manifestFilesList = AdsInputDataLoader.loadManifestFileList();
		int missingUpdateManifest = 0;
		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			int firstUpdate = getFirstUpdateIndex(updates);
			int totalUpdate = 0;
			if (firstUpdate < 0) {
				continue;
			}

			int validRatingUpdate = 0;
			int updateIncrease = 0;
			int updateDecrease = 0;
			

			for (int i = firstUpdate; i < updates.size(); i++) {
				totalUpdate++;
				UpdateTable presentUpdate = updates.get(i);

				String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();

				String presentKey = "AndroidManifest-" + appName + "-" + presentUpdate.getVERSION_CODE() + "-"
						+ presentUpdate.getRELEASE_DATE().replace("-", "_") + ".xml";

				if (!manifestFilesList.contains(presentKey)) {
					missingUpdateManifest++;
					writer.write(appName+"-"+presentUpdate.getVERSION_CODE()+"-"+presentUpdate.getRELEASE_DATE().replace("-", "_"));
					writer.endRecord();
				}

			}

		}
		writer.close();
		
		System.out.println("Updates missing manifest file ["+missingUpdateManifest+"]");
	}

	public static void main(String[] args) throws Exception {
		GenerateValidUpdates ob = new GenerateValidUpdates();
		ob.filterValidUpdate();
		// ob.filterUpdatesNoRatingInfo();
		//ob.manifestFileAnalysis();
	}
}
