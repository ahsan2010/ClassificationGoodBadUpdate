package com.sail.mobile.deeplearning.update.rating.classification.feature.extraction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvWriter;
import com.sail.mobile.deeplearning.update.rating.classification.RatingAnalyzer;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;
import com.sail.mobile.deeplearning.update.rating.classification.util.FileUtil;
import com.sail.mobile.deeplearning.update.rating.classification.util.TextUtil;

public class DiffExecution {

	public int start;
	public int end;
	HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader
			.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
	Map<String, UpdateRatingInformation> updateRatingInfo;
	public final int THRESHOLD_REVEIEW = 30;
	public String DECOMPRESS_PATH = "/datadrive/Data/Decompiled_Apks/";
	//public String DECOMPRESS_PATH = "/home/ahsan/Documents/SAILLabResearch/DeepLaerningProject/Ui_Feature_Generation/";
	public static boolean diffBetWeenTwofiles(String file1, String file2) {
		boolean flag = false;
		String executableStatement = "diff -s --brief " + file1 + " " + file2;
		
		try {
			Process p = Runtime.getRuntime().exec(executableStatement);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line;
			line = stdError.readLine();
			// read any errors from the attempted command
			
			//System.out.println(executableStatement);
			if (!TextUtil.isBlankOrNull(line)) {
				throw new Exception("Shell Script running exception");
			}
			ArrayList<String> standardOutputText = new ArrayList<String>();
			// read the output from the command
			line = stdInput.readLine();

			if (line.contains("differ")) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean checkValidUpdate(String appName, UpdateTable presentUpdate) {
		String updateKey = appName + Constants.COMMA + presentUpdate.getVERSION_CODE();
		if (!updateRatingInfo.containsKey(updateKey)) {
			return false;
		}
		if (updateRatingInfo.get(updateKey).getTotalStar() < THRESHOLD_REVEIEW) {
			return false;
		}
		return true;
	}

	public int countChange(Set<String> commonFile, String oldPath, String presentPath, String dir) {
		int totalChange = 0;
		for (String cFile : commonFile) {
			try {
				String oldFilePath = oldPath + dir + cFile;
				String presentFilePath = presentPath + dir + cFile;
				//System.out.println("OLD: " + oldFilePath);
				//System.out.println("PRE: " + presentFilePath);
				
				boolean flag = diffBetWeenTwofiles(oldFilePath, presentFilePath);
				if (flag == true) {
					totalChange++;
				}
			} catch (Exception e) {

			}
		}
		return totalChange;
	}

	public void analyzeUpdate() throws Exception {

		RatingAnalyzer rat = new RatingAnalyzer();
		updateRatingInfo = rat.generateUpdateRating();

		CsvWriter writer = new CsvWriter(
				Constants.ROOT+"/result/"+"output.csv");
		writer.write("Package_Name");
		writer.write("Prev_Version_Code");
		writer.write("Pres_Version_Code");
		writer.write("Layout_Added");
		writer.write("Layout_Deleted");
		writer.write("Layout_Common");
		writer.write("Layout_Changed");
		writer.write("Color_Added");
		writer.write("Color_Deleted");
		writer.write("Color_Common");
		writer.write("Color_Changed");
		writer.endRecord();

		ArrayList<String> investigatedApps = new ArrayList<String>();
		investigatedApps.addAll(appUpdates.keySet());
		int finishApp = 0;
		for (int j = this.start ; j < this.end ; j ++ ) {
			String appName = investigatedApps.get(j);
			try {
				ArrayList<UpdateTable> updates = appUpdates.get(appName);
				UpdateTable oldUpdate = updates.get(0);
				for (int i = 1; i < updates.size(); i++) {
					UpdateTable presentUpdate = updates.get(i);
					if (!(checkValidUpdate(appName, presentUpdate) && checkValidUpdate(appName, oldUpdate))) {
						oldUpdate = presentUpdate;
						continue;
					}

					try {
						String oldPath = DECOMPRESS_PATH + appName + "-" + oldUpdate.getVERSION_CODE() + "-"
								+ oldUpdate.getRELEASE_DATE().replace("-", "_");
						String presentPath = DECOMPRESS_PATH + appName + "-" + presentUpdate.getVERSION_CODE() + "-"
								+ presentUpdate.getRELEASE_DATE().replace("-", "_");

					//	System.out.println("DirName: " + oldPath);

						// Layout
						Set<String> oldLayoutFiles = FileUtil.listFilesIII(oldPath + "/res/layout/", "xml", "/res/layout/", appName, oldUpdate.getVERSION_CODE());
						Set<String> presentLayoutFiles = FileUtil.listFilesIII(presentPath + "/res/layout/", "xml",
								"/res/layout/", appName, presentUpdate.getVERSION_CODE());
						Set<String> commonLayoutFile = new HashSet<String>(oldLayoutFiles);
						commonLayoutFile.retainAll(presentLayoutFiles);
						Set<String> addedLayoutFile = new HashSet<String>(); addedLayoutFile.addAll(presentLayoutFiles);
						addedLayoutFile.removeAll(oldLayoutFiles);
						Set<String> deletedLayoutFile = new HashSet<String>();deletedLayoutFile.addAll(oldLayoutFiles);
						deletedLayoutFile.removeAll(presentLayoutFiles);
						
						int layoutChange = countChange(commonLayoutFile, oldPath, presentPath, "/res/layout/");

						// Color
						Set<String> oldColorFiles = FileUtil.listFilesIII(oldPath + "/res/color/", "xml",  "/res/color/", appName, oldUpdate.getVERSION_CODE());
						Set<String> presentColorFiles = FileUtil.listFilesIII(presentPath + "/res/color/", "xml",
								 "/res/color/", appName, presentUpdate.getVERSION_CODE());
						Set<String> commonColorFile = new HashSet<String>(oldColorFiles);
						commonColorFile.retainAll(presentColorFiles);
						Set<String> addedColorFile = new HashSet<String>(); addedColorFile.addAll(presentColorFiles);
						addedColorFile.removeAll(oldColorFiles);
						Set<String> deletedColorFile = new HashSet<String>(); deletedColorFile.addAll(oldColorFiles);
						deletedColorFile.removeAll(presentColorFiles);

						int colorChange = countChange(commonColorFile, oldPath, presentPath, "/res/color/");

						writer.write(appName);
						writer.write(oldUpdate.getVERSION_CODE());
						writer.write(presentUpdate.getVERSION_CODE());
						writer.write(Integer.toString(addedLayoutFile.size()));
						writer.write(Integer.toString(deletedLayoutFile.size()));
						writer.write(Integer.toString(commonLayoutFile.size()));
						writer.write(Integer.toString(layoutChange));						
						writer.write(Integer.toString(addedColorFile.size()));
						writer.write(Integer.toString(deletedColorFile.size()));
						writer.write(Integer.toString(commonColorFile.size()));
						writer.write(Integer.toString(colorChange));
						writer.endRecord();
						
					} catch (Exception e) {
						System.err.println("Problem in App update running [P: " + appName + "] ["+presentUpdate.getVERSION_CODE()+"]");
					}

					oldUpdate = presentUpdate;

				}
			} catch (Exception e) {
				System.err.println("Problem in App update running [P: " + appName + "]");
			}
			finishApp ++;
			System.out.println("Finish App ["+finishApp+"] ["+appName+"]");
		}
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		//boolean f = diffBetWeenTwofiles("/home/ahsan/Documents/a.txt", "/home/ahsan/Documents/b.txt");
		//System.out.println("Program finishes successfully " + f);
		DiffExecution ob = new DiffExecution();
		ob.start = Integer.parseInt(args[0]);
		ob.end = Integer.parseInt(args[1]);
		ob.analyzeUpdate();
		System.out.println("Program finishes successfully");
	}
}
