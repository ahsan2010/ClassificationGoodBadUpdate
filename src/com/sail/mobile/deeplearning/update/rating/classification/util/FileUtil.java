package com.sail.mobile.deeplearning.update.rating.classification.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.csvreader.CsvReader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AdInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppInfoAPK;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;


public class FileUtil
{

	public static boolean isFileEndsWithCertainExtention(File file, String extention)
	{
		return (file.isFile() && file.getName().endsWith("." + extention));
	}

	/**
	 * Lists all files that have a certain extention. The script traver depth one layer (all files in the current directroy) and not ythe sub-directories.
	 * 
	 * @param dirName
	 * @return
	 */
	public static ArrayList<String> listFiles(String dirName, String fileExtention)
	{

		ArrayList<String> filesList = new ArrayList<String>();
		try
		{
			File directory = new File(dirName);

			File[] fList = directory.listFiles();

			for (File file : fList)
			{
				
					if (isFileEndsWithCertainExtention(file, fileExtention))
					{
						filesList.add(file.getAbsolutePath());
					}

				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return filesList;

	}

	
	
	public static ArrayList<String> listFilesII(String dirName, String fileExtention,ArrayList<String> filesList)
	{

		
		try
		{
			File directory = new File(dirName);

			File[] fList = directory.listFiles();

			for (File file : fList)
			{
				if(file.isFile()){
					if (isFileEndsWithCertainExtention(file, fileExtention))
					{
						filesList.add(file.getAbsolutePath());
					}

				}
				else if (file.isDirectory()) {
					//System.out.println(file.getAbsolutePath());
					listFilesII(file.getAbsolutePath(),fileExtention,filesList);
		        }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return filesList;

	}
	/**
	 * Input is folder location and generates a map with key is the "Package name , Version code" and value is the corresponding file name
	 * @param dirName
	 * @param fileExtention
	 * @return
	 */
	public static HashMap<String, String> loadFilesMap(String dirName, String fileExtention)
	{
		System.out.println("Start reading list of *." + fileExtention + " files in the folder [" + dirName + "]");
		long startTime = System.currentTimeMillis();
		ArrayList<String> filesList = listFiles(dirName, fileExtention);
		HashMap<String, String> filesMap = new HashMap<String, String>();
		for(String fileName : filesList)
		{
			AppInfoAPK appInfoAPK = getAppPackageInfo(fileName);
			String key = appInfoAPK.getAppName() + Constants.COMMA +  appInfoAPK.getVersionCode();
			filesMap.put(key, fileName);
			System.out.println(key);
		}
		
		System.out.println("Finished reading [" + filesList.size() + "] files in [" + TextUtil.getReadableFloatNumber((System.currentTimeMillis() - startTime)/(60*1000)) + "] minutes");
		return filesMap;

	}

	
	
	public static String getVersionCode(String st)
	{
		String code = "";

		String temp = st.substring(st.indexOf("-") + 1);
		temp = temp.substring(0, temp.indexOf("-"));
		// System.out.println("Hello: " + temp);

		return code;
	}

	
	/**
	 * Removes the inner class name for example "com.google.MathUtil$1" will be "com.google.MathUtil"
	 * 
	 * @param fullClassIdentifier
	 * @return
	 */
	public static String removeInnerClassName(String fullClassIdentifier)
	{
		if (fullClassIdentifier.contains("$"))
		{
			String modifiedText;
			modifiedText = fullClassIdentifier.substring(0, fullClassIdentifier.indexOf("$"));
			return modifiedText;
		}
		return fullClassIdentifier;
	}
	
	public static boolean hasInnerClass(String fullClassIdentifier){
		if (fullClassIdentifier.contains("$"))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Generate update key for the studied updates
	 * @param updates
	 * @return
	 */
	public  static Set<String> generateUpdateKey(HashMap<String,ArrayList<UpdateTable>> updates){
		Set<String> updateKeySet = new HashSet<String>();
		for(String updateKey : updates.keySet()){
			for(UpdateTable update: updates.get(updateKey)){
				String key = update.getPACKAGE_NAME()+Constants.COMMA+update.getVERSION_CODE();
				updateKeySet.add(key);
			}
		}
		return updateKeySet;
	}

	
	public static ArrayList<String> generateJarFileLocation(HashMap<String, String> jarFilesMap){
		ArrayList<String> jarLoacationList = new ArrayList<String>();
		jarLoacationList.addAll(jarFilesMap.keySet());
		return jarLoacationList;
	}
	
	/**
	 * This method extracts appName,ReleaseDate,VersionCode From the apk file Name.
	 * 
	 * @param filePath
	 * @return
	 */
	public static AppInfoAPK getAppPackageInfo(String filePath)
	{

		String info = filePath.substring(filePath.lastIndexOf("/") + 1);
		String appName = info.substring(0, info.indexOf("-")).trim();
		info = info.substring(info.indexOf("-") + 1);
		String versionCode = info.substring(0, info.indexOf("-")).trim();
		info = info.substring(info.indexOf("-") + 1);
		String dateString = info.substring(0, info.indexOf("-")).trim();

		AppInfoAPK appInfoAPK = new AppInfoAPK(appName, versionCode, dateString);
		return appInfoAPK;

	}

	public static String getClassName(String fullClassIdentifier)
	{
		String className = fullClassIdentifier.substring(fullClassIdentifier.lastIndexOf(".") + 1).trim();
		return className;
	}

	/**
	 * Reads the data in a file as a simple list of strings
	 */
	public static ArrayList<String> readFileAsList(String fileLocation, boolean skipHeader) throws Exception
	{
		ArrayList<String> fileRecords = new ArrayList<String>();
		String currentRecord;
		BufferedReader appsFileReader = new BufferedReader(new FileReader(fileLocation));
		if(skipHeader)
		{
			appsFileReader.readLine();
		}
		while ((currentRecord = appsFileReader.readLine()) != null)
		{
			fileRecords.add(currentRecord);
		}
		appsFileReader.close();
		return fileRecords;
	}
	
	
	/**
	 * read a file containing the group information
	 * 
	 * @return a map where key is the package name and value is the group name of that particular ad package
	 */
	public static Map<String, String> readVerfiedAdList(String path)
	{
		Map<String, String> adPackageMapGroup = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			while ((line = br.readLine()) != null)
			{
				String name[] = line.split(",");
						adPackageMapGroup.put(name[0].trim(),name[1].trim());
						
						System.out.println(name[0] + " " + name[1]);
				
			}
			System.out.println("Total Verified Ads: " + adPackageMapGroup.size());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return adPackageMapGroup;
	}

	public static boolean checkErrorJar(String fileName, Set<String>errorJarList){
		
		String jarName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.lastIndexOf("-"));
		if(errorJarList.contains(jarName)) 
			return true;
		
		return false;
	}
	
	public static Set<String> readAllAvailableJarNames(String path){
		Set<String> jarFiles = new HashSet<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			while((line = br.readLine()) != null){
				String w[] = line.split("-");
				jarFiles.add(w[0].trim()+Constants.COMMA+w[1].trim());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Total Available Jars: " + jarFiles.size());
		return jarFiles;
	}
	
	
	
	
	public static void main ( String arg[]){
		FileUtil futil = new FileUtil();
		//Set<String> errorJarList = new HashSet<String>();
		//errorJarList.add("com.roamingsquirrel.android.calculator-226-2016_06_29");
		//errorJarList.add("com.trustgo.mobile.security-471-2016_10_26");
		//System.out.println(futil.checkErrorJar("/home/com.roamingsquirrel.android.calculator-226-2016_06_29-dex2jar.jar", errorJarList));
		//readAllAvailableJarNames("/home/ahsan/Documents/SAILLabResearch/Ad Library Analysis/Results/listOfjars.txt");
		listFiles("/home/ahsan/SampleApks/com","class");
	}

	public static String readRecordCell(String line, int index)
	{
		return line.split(Constants.COMMA)[index];
	}

	public static Integer readIntRecordCell(String line, int index)
	{
		return Integer.valueOf(readRecordCell(line, index));
	}
	
	
	public static Map<String, DateTime> mapClosedAppWithDate() {
		Map<String, DateTime> closedAppMap = new HashMap<String, DateTime>();
		try {
			CsvReader reader = new CsvReader(Constants.CLOSED_APP_List);
			reader.readHeaders();
			while (reader.readRecord()) {
				String packageName = reader.get("PACKAGE_NAME");
				String lastUpdateString = reader.get("LASTS_UPDATE_DATE");
				DateTime lastUpdateDate = DateUtil.formatterWithHyphen.parseDateTime(lastUpdateString);
				closedAppMap.put(packageName, lastUpdateDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return closedAppMap;
	}
	
	public static Map<String,String> readAppUseAdLibraryPerUpdate(String path){
		
		Map<String,String> appUseAds = new HashMap<String,String>();
		
		try{
			CsvReader reader = new CsvReader(path);
			reader.readHeaders();
			while (reader.readRecord())
			{
				String packageName = reader.get("App_Name").trim();
				String versionCode = reader.get("Version_Code").trim();
				String listOfAdds = reader.get("List_Of_Ads").trim();
				String date = reader.get("Date").trim();
				
				String key = packageName+Constants.COMMA+versionCode;
				appUseAds.put(key, listOfAdds);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return appUseAds;
		
	}

	
	public static Map<String, ArrayList<AdInformation>> readAdLibraryInformation(){
		Map<String, ArrayList<AdInformation>> appUseAd = new HashMap<String, ArrayList<AdInformation>>();
		try{
			
			Set<String> uniqueAdLibraries = new HashSet<String>();
			CsvReader reader = new CsvReader(Constants.ADS_UPDATE_DATA_PATH);
			reader.readHeaders();
			
			while (reader.readRecord()) {
				String packageName = reader.get("App_Name");
				String versionCode = reader.get("Version_Code");
				String listOfAdds = reader.get("List_Of_Ads");
				String releaseDate = reader.get("Date");

				DateTime updateDateTime = DateUtil.formatterWithHyphen.parseDateTime(releaseDate);

				AdInformation adInfo = new AdInformation(packageName, versionCode, listOfAdds, releaseDate);

				if (!appUseAd.containsKey(packageName)) {
					ArrayList<AdInformation> adList = new ArrayList<AdInformation>();
					appUseAd.put(packageName, adList);
				}
				appUseAd.get(packageName).add(adInfo);

			}
			SortUtil.sortAdLibraryInformatoinByDate(appUseAd);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return appUseAd;
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
				data.setAVAILABILITY_RESTRICTION(record.get("setAVAILABILITY_RESTRICTION"));
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return appTableRecords;
		
	}

}
