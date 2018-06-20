package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sail.awsomebasupdates.model.UpdateData;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class GenerateListForNegativityRatioUpdate {

	
	Map<String, UpdateRatingInformation> updateRatingInfo;
	ArrayList<UpdateData> updateDataList = new ArrayList<UpdateData>();
	HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
	Map<String,String> lastUpdateVersionCode = new HashMap<String,String>();
	
	Map<String,UpdateData> badUpdateList = new HashMap<String,UpdateData>();
	Map<String,UpdateData> goodUpdateList = new HashMap<String,UpdateData>();
	
	public List<String> feature_name = Arrays.asList(new String[] { 
			"DiffTargetMinSDK", "AppUpdateSize", "AdSize",
			"NumberOfAds", "NumberOfPermission", "NumberOfDangerousPermission",
			"NumberOfActivity", "NumberOfReceiver", "NumberOfService", 
			"TargetSDKChange", 
			"MiniumSDKChange", 
			"AdsChange", 
			"ActivityChange",
			"ReceiverChange", 
			"ServiceChange", 
			"PermissionChange", 
			"DangerousPermissionChange",
			"AppSizeChange", 
			"AdsSizeChange", 
			"Duration_Release", "Prev_Neg_Rating_Ratio",
			"Target",
			"Neg_ratio","Neg_Rating_Ratio" });
	
	public void readDataFile() throws Exception {
		CsvReader reader = new CsvReader("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data_June/update_feature/updated_100_0.2.csv");
		reader.readHeaders();
		while (reader.readRecord()) {
			String appName = reader.get("AppName");
			String versionCode = reader.get("VersionCode");
			double negativityRatio = Double.parseDouble(reader.get("Neg_ratio"));
			double negRatio = Double.parseDouble(reader.get("Neg_Rating_Ratio"));

			updateDataList.add(new UpdateData(appName, versionCode, negativityRatio, negRatio));
		}

		Collections.sort(updateDataList, new Comparator<UpdateData>() {

			@Override
			public int compare(UpdateData o1, UpdateData o2) {
				if (Double.compare(o1.getNegRatio(), o2.getNegRatio()) < 0) {
					return 1;
				}
				if (Double.compare(o1.getNegRatio(), o2.getNegRatio()) > 0) {
					return -1;
				}
				return 0;
			}
		});

		System.out.println("Successfull load [" +updateDataList.size()+"]" );
	}

	
	public boolean isIncluded(String appName, String versionCode){
		String lastVersion = lastUpdateVersionCode.get(appName);
		String startVersion = "";
		String endVersion = "";
		if(Double.compare(Double.parseDouble(versionCode), Double.parseDouble(lastVersion)) > 0){
			startVersion = lastVersion;
			endVersion = versionCode;
		}else{
			startVersion = versionCode;
			endVersion = lastVersion;
		}
		
		boolean flagUpdateBetweenStartEndPosition = false;
		int findUpdates = 0;
		for(int i = 0 ; i < appUpdates.get(appName).size() ; i ++ ){
			UpdateTable update = appUpdates.get(appName).get(i);
			if(update.getVERSION_CODE().compareTo(startVersion) == 0){
				flagUpdateBetweenStartEndPosition = true;
				continue;
			}
			if(update.getVERSION_CODE().compareTo(endVersion) == 0){
				flagUpdateBetweenStartEndPosition = false;
				break;
			}
			
			String key = update.getPACKAGE_NAME() +"," + update.getVERSION_CODE();
			
			if(flagUpdateBetweenStartEndPosition){
				if(updateRatingInfo.containsKey(key)){
					if(updateRatingInfo.get(key).getTotalStar() < 30){
						continue;
					}
					findUpdates ++;
				}
			}
			
		}
		System.out.println("Find updates ["+findUpdates+"]");
		if(findUpdates > 0){
			return true;
		}
		
		return false;
	}
	
	public void generateNegativityRatioList() {
		for(int i = 0 ; i < 600 ; i ++ ){
			UpdateData data = updateDataList.get(i);
			String updateKey = data.getAppName()+"-" + data.getVersionCode();
			if(!lastUpdateVersionCode.containsKey(data.getAppName())){
				badUpdateList.put(updateKey,data);
				lastUpdateVersionCode.put(data.getAppName(),data.getVersionCode());
			}else{
				if(isIncluded(data.getAppName(),data.getVersionCode())){
					badUpdateList.put(updateKey,data);
				}
				lastUpdateVersionCode.put(data.getAppName(),data.getVersionCode());
			}
		}
		
		for(int i = updateDataList.size() - 1 ; i > updateDataList.size() - 507 ; i -- ){
			UpdateData data = updateDataList.get(i);
			String updateKey = data.getAppName()+"-" + data.getVersionCode();
			goodUpdateList.put(updateKey,data);
		}
		
		System.out.println("Total bad updates ["+ badUpdateList.size() +"]");
		System.out.println("Total good updates ["+ goodUpdateList.size() +"]");
	}
	
	public void writeNewUpdateResult() throws Exception{
		
		CsvReader reader = new CsvReader("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data_June/update_feature/updated_100_0.2.csv");
		reader.readHeaders();
		CsvWriter writer = new CsvWriter("/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/ROOT/scripts/Data_June/update_feature/update_analysis_data_neg_ratio.csv");
		writer.write("AppName");
		writer.write("VersionCode");
		for (int j = 0; j < feature_name.size(); j++) {
			writer.write(feature_name.get(j));
		}
		writer.endRecord();

		while (reader.readRecord()) {
			String appName = reader.get("AppName");
			String versionCode =reader.get("VersionCode");
			
			String key = appName +"-" + versionCode;
			
			if(!goodUpdateList.containsKey(key) && !badUpdateList.containsKey(key)){
				continue;
			}
			
			for(int i = 0 ; i < reader.getColumnCount() - 3 ; i ++ ){
				writer.write(reader.get(i));
			}
			
			if(goodUpdateList.containsKey(key)){
				writer.write("1");
				writer.write(reader.get("Neg_ratio"));
				writer.write(reader.get("Neg_Rating_Ratio"));
			}
			if(badUpdateList.containsKey(key)){
				writer.write("2");
				writer.write(reader.get("Neg_ratio"));
				writer.write(reader.get("Neg_Rating_Ratio"));
			}
			writer.endRecord();
		}
		writer.close();
	}
	
	public void init(){
		RatingAnalyzer rat = new RatingAnalyzer();
		updateRatingInfo = rat.generateUpdateRating();
	}

	public static void main(String[] args) throws Exception{
		GenerateListForNegativityRatioUpdate ob = new GenerateListForNegativityRatioUpdate();
		ob.init();
		ob.readDataFile();
		ob.generateNegativityRatioList();
		ob.writeNewUpdateResult();
		System.out.println("Program finishes successfully");
	}
}
