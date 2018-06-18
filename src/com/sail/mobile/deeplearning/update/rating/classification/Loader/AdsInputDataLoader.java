package com.sail.mobile.deeplearning.update.rating.classification.Loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.csvreader.CsvReader;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.analysis.adevolution.util.FileUtil;
import com.sail.mobile.analysis.adevolution.util.SortUtil;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class AdsInputDataLoader
{
	
	public static Set<String> loadManifestFileList(){
		Set<String> manifestFilesList = new HashSet<String>();
		try{
			ArrayList<String> list = FileUtil.listFiles(Constants.MANIFEST_FILE_LOCATION, "xml");
			for(String f : list){
				manifestFilesList.add(f.substring(f.lastIndexOf("/")+1));
			}
			
			System.out.println("Total manifest files ["+manifestFilesList.size()+"]");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return manifestFilesList;
	}
	public static Set<String> readAndroidPermission(String path){
		Set<String> permissionList = new HashSet<String>();
		try{
			CsvReader reader = new CsvReader(path);
			reader.readHeaders();
			while(reader.readRecord()){
				permissionList.add(reader.get("Permission"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return permissionList;
	}
	
	public static Map<String,AdInformation> readAdLibraryInformation() {
		Map<String,AdInformation> appUseAd = new HashMap<String,AdInformation>();
		try {
			CsvReader reader = new CsvReader(Constants.ADS_UPDATE_DATA_PATH);
			reader.readHeaders();
			while (reader.readRecord()) {
				String packageName = reader.get("App_Name");
				String versionCode = reader.get("Version_Code");
				String listOfAdds = reader.get("List_Of_Ads");
				String releaseDate = reader.get("Date");

				DateTime updateDateTime = DateUtil.formatterWithHyphen.parseDateTime(releaseDate);
				AdInformation adInfo = new AdInformation(packageName, versionCode, listOfAdds, releaseDate);
				String key = packageName + Constants.COMMA + versionCode;
				appUseAd.put(key, adInfo);
			}
			System.out.println("Ad library update data [" + appUseAd.size() + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appUseAd;
	}
	
	public static Set<String> loadInitialStudyAppsName(){
		Set<String> appNames = new HashSet<String>();
		try{
			CsvReader record = new CsvReader(Constants.APP_UPDATE_TABLE_PATH);
			record.readHeaders();
			while (record.readRecord()){
				//System.out.println(record.get("PACKAGE_NAME"));
				String packageName = record.get("PACKAGE_NAME");
				appNames.add(packageName);
			}
			System.out.println("Studied Total Apps ["+appNames.size()+"]");
			//Constants.TOTAL_NUMBER_OF_STUDIED_APPS = appNames.size();
		}catch(Exception e){
			e.printStackTrace();
		}
		return appNames;
	}
	
	
	public static HashMap<String,ArrayList<UpdateTable>> readUpdateData(String fileName){
		HashMap<String,ArrayList<UpdateTable>> appUpdateRecords = new HashMap<String,ArrayList<UpdateTable>>();
		
		try{
			int inputUpdatesCount = 0;
			CsvReader reader = new CsvReader(fileName);
			reader.readHeaders();
			while (reader.readRecord())
			{
				inputUpdatesCount++;
				UpdateTable update = new UpdateTable();
				update.setAPP_ID(reader.get("APP_ID").trim());
				update.setAPP_UPDATE_ID(reader.get("APP_UPDATE_ID").trim());
				update.setPACKAGE_NAME(reader.get("PACKAGE_NAME").trim());
				update.setVERSION_CODE(reader.get("VERSION_CODE").trim());
				update.setRELEASE_DATE(reader.get("RELEASE_DATE").trim());
				update.setAPK_SIZE(reader.get("APK_FILE_SIZE").trim());
				
				if(appUpdateRecords.containsKey(update.getPACKAGE_NAME())){
					appUpdateRecords.get(update.getPACKAGE_NAME()).add(update);
				}else{
					ArrayList<UpdateTable> updaetList = new ArrayList<UpdateTable>();
					updaetList.add(update);
					appUpdateRecords.put(update.getPACKAGE_NAME(), updaetList);
				}
				
			}
			System.out.println("Initially loaded ["+appUpdateRecords.size()+"] apps with [" + inputUpdatesCount + "] updates.");
			SortUtil.sortUpdatesByReleaseDate(appUpdateRecords);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return appUpdateRecords;
	}
	
	public static Map<String,Double> extractAppSize(String fileName){
		Map<String,Double> appUpdateSizeList = new HashMap<String, Double>();
		HashMap<String,ArrayList<UpdateTable>> appUpdates = readUpdateData(fileName);
		try{
			for(String appName : appUpdates.keySet()){
				for(int i = 0 ; i < appUpdates.get(appName).size() ; i ++){
					UpdateTable update = appUpdates.get(appName).get(i);
					String updateKey = appName + "-" + update.getVERSION_CODE();
					appUpdateSizeList.put(updateKey, Double.parseDouble(update.getAPK_SIZE()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return appUpdateSizeList;
	}
	
	
public static Map<String,AppTable> readAppData(){
		
		Map<String,AppTable> appTableRecords = new HashMap<String,AppTable>();
		
		try{
			
			CsvReader record = new CsvReader(Constants.APP_CSV_PATH);
			record.readHeaders();
			
			while (record.readRecord()){
				AppTable data = new AppTable();
				
				data.setAPP_ID(Integer.parseInt(record.get("APP_ID")));
				data.setPACKAGE_NAME(record.get("PACKAGE_NAME"));
				data.setAPP_TITLE(record.get("APP_TITLE"));
				data.setIS_APP_TITLE_HAS_NON_PRINTABLE_CHAR(record.get("IS_APP_TITLE_HAS_NON_PRINTABLE_CHAR"));
				data.setAPP_DESCRIPTION(record.get("APP_DESCRIPTION"));
				data.setIS_APP_DESCRIPTION_HAS_NON_PRINTABLE_CHAR(record.get("IS_APP_DESCRIPTION_HAS_NON_PRINTABLE_CHAR"));
				data.setAPP_CATEGORY(record.get("APP_CATEGORY"));
				data.setAPP_SUB_CATEGORY(record.get("APP_SUB_CATEGORY"));
				data.setORGANIZATION_ID(record.get("ORGANIZATION_ID"));
				data.setCONTENT_RATING(record.get("CONTENT_RATING"));
				data.setCONTENT_RATING_TEXT(record.get("CONTENT_RATING_TEXT"));
				data.setIS_TOP_DEVELOPER(record.get("IS_TOP_DEVELOPER"));
				data.setIS_WEARABLE_APP(record.get("IS_WEARABLE_APP"));
				data.setAPP_TYPE(record.get("APP_TYPE"));
				data.setAVAILABILITY_RESTRICTION(record.get("SETAVAILABILITY_RESTRICTION"));
				data.setMAIN_PAGE_URL(record.get("MAIN_PAGE_URL"));
				data.setDETAILS_URL(record.get("DETAILS_URL"));
				data.setREVIEWS_URL(record.get("REVIEWS_URL"));
				data.setPURCHASE_DETAILS_URL(record.get("PURCHASE_DETAILS_URL"));
				data.setDEVELOPER_EMAIL(record.get("DEVELOPER_EMAIL"));
				data.setDEVELOPER_WEBSITE(record.get("DEVELOPER_WEBSITE"));
				data.setIS_2013_TOP_10K_APPS(record.get("IS_2013_TOP_10K_APPS"));
				data.setIS_2015_TOP_500_APPS(record.get("IS_2015_TOP_500_APPS"));
				data.setIS_FDROID_APP(record.get("IS_FDROID_APP"));
				data.setIS_2016_TOP_2500_APPS(record.get("IS_2016_TOP_2500_APPS"));
				data.setIS_2016_FAMILY_APPS(record.get("IS_2016_FAMILY_APPS"));
				data.setIS_2016_TOP_2500_NON_FREE_APPS(record.get("IS_2016_TOP_2500_NON_FREE_APPS"));
				data.setIS_2016_FAMILY_NON_FREE_APPS(record.get("IS_2016_FAMILY_NON_FREE_APPS"));
				
				appTableRecords.put(data.getPACKAGE_NAME(),data);
				
			}
			System.out.println("Total Number of Available Apps in Study ["+appTableRecords.size()+"]");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return appTableRecords;
		
	}

	public static Set<String> readErrorJarList(String path){
		Set<String> errorJars = new HashSet<String>();
		
		try{
			
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			while((line = br.readLine()) != null){
				String jarName = line.substring(line.lastIndexOf("/")+1,line.lastIndexOf(".")).trim();
				errorJars.add(jarName);
			}
			return errorJars;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return errorJars;
	}
	
	
	public static void main(String arg[])
	{
		AdsInputDataLoader adsInputDataLoader = new AdsInputDataLoader();
		//adsInputDataLoader.loadData();
		//adsInputDataLoader.readErrorJarList(Constants.ERROR_JAR_LIST);
		adsInputDataLoader.loadInitialStudyAppsName();
		
	}

	
	
	
}
