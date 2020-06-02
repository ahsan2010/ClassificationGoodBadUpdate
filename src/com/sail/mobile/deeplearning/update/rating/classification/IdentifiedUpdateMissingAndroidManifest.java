
package com.sail.mobile.deeplearning.update.rating.classification;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.csvreader.CsvWriter;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class IdentifiedUpdateMissingAndroidManifest {
	public String MANIFEST_FILE_PATH = "/safwatscratch/shassan/AndroidManifest/";
	public final String UPDATE_PATH = Constants.ROOT + "/Data/Final_Studied_App_Updates_2016_2019.csv";
	
	public void checkManifestWithAppUpdate() throws Exception{
	
		int manifestFileExist = 0 ;
		int totalUpdates = 0 ;
		
		CsvWriter writer = new CsvWriter(Constants.ROOT + "/Data/UpdatesMissingManifest.csv");
		writer.write("APP_ID");
		writer.write("APP_UPDATE_ID");
		writer.write("PACKAGE_NAME");
		writer.write("VERSION_CODE");
		writer.write("RELEASE_DATE");
		writer.write("APK_FILE_SIZE");
		writer.endRecord();
		//AndroidManifest-ah.creativecodeapps.tiempo-13-2016_10_17.xml
		HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader.readUpdateData(UPDATE_PATH);
		for (String appName : appUpdates.keySet()) {
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			for (int i = 0; i < updates.size(); i++) {
				totalUpdates ++;
				UpdateTable update = updates.get(i);
				String key = "AndroidManifest" + "-" + appName + "-" + update.getVERSION_CODE() + "-"
						+ update.getRELEASE_DATE().replace("-", "_") + ".xml";
				
				Path p = Paths.get(MANIFEST_FILE_PATH + key);
				if(Files.exists(p)){
					manifestFileExist ++;
				}else{
					writer.write(update.getAPP_ID());
					writer.write(update.getAPP_UPDATE_ID());
					writer.write(update.getPACKAGE_NAME());
					writer.write(update.getVERSION_CODE());
					writer.write(update.getRELEASE_DATE());
					writer.write(update.getAPK_SIZE());
					writer.endRecord();
				}
				
				System.out.println("Total Updates ["+totalUpdates+"] AndroidManifest exist ["+manifestFileExist+"]");
			}
		}
		writer.close();
		System.out.println("[Final] Total Updates ["+totalUpdates+"] AndroidManifest exist ["+manifestFileExist+"]");
	}
	
	public static void main(String[] args) throws Exception {
		IdentifiedUpdateMissingAndroidManifest ob = new IdentifiedUpdateMissingAndroidManifest();
		ob.checkManifestWithAppUpdate();
		System.out.println("Program finishes successfully");
	}
}
