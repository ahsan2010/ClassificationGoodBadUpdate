package com.sail.mobile.deeplearning.update.rating.classification.feature.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.derby.tools.sysinfo;

import com.csvreader.CsvWriter;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class CollectClassMethodFeature implements Runnable {

	HashMap<String, ArrayList<UpdateTable>> appUpdates ;
	ArrayList<String> investigatedApp;
	String JAR_LOC = "/archive/ahsan/Jars/";
	String outputLocation = "/home/local/SAIL/ahsan/Documents/Update_Classification/ROOT/result/Code_Churn_Output/";
	int startPosition;
	int endPosition;
	int THREAD_NO;
	int error_files = 0;
	
	public CollectClassMethodFeature(ArrayList<String> investigatedApp, HashMap<String, ArrayList<UpdateTable>> appUpdates, int start, int end, int threadNo) {
		this.appUpdates = appUpdates;
		this.startPosition = start;
		this.endPosition = end;
		this.THREAD_NO = threadNo;
		this.investigatedApp = investigatedApp;
	}
	
	private static Map<String, JavaClass> collectJavaClasses(String jarPath, JarFile jarFile)
			throws ClassFormatException, IOException {
		Map<String, JavaClass> javaClasses = new LinkedHashMap<String, JavaClass>();
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (!entry.getName().endsWith(".class")) {
				continue;
			}
			ClassParser parser = new ClassParser(jarPath, entry.getName());
			JavaClass javaClass = parser.parse();
			javaClasses.put(javaClass.getClassName(), javaClass);
		}
		return javaClasses;
	}

	public String collectCodeChurnMetrics(String jarFileLocation) throws Exception{
		JarFile jarFile = new JarFile(new File(jarFileLocation));
		Map<String, JavaClass> javaClassesMap;
		javaClassesMap = collectJavaClasses(jarFileLocation, jarFile);
		int totalClass = javaClassesMap.size();
		int totalMethod = 0;
		for (String cName : javaClassesMap.keySet()) {
			JavaClass investigationClass = javaClassesMap.get(cName);
			totalMethod += investigationClass.getMethods().length;
		}
		//System.out.println("Total Class ["+totalClass+"]");
		//System.out.println("Total methods ["+totalMethod+"]");
		return (totalClass + "-" + totalMethod);
	}
	
	public static void main(String[] args) {
		System.out.println("Program finishes successfully");
	}

	@Override
	public void run() {
		try{
			CsvWriter writer = new CsvWriter(outputLocation +"codeChurn_"+THREAD_NO+".csv");
			writer.write("Package_Name");
			writer.write("Version_Code");
			writer.write("Release_Date");
			writer.write("Number_Of_Classes");
			writer.write("Number_Of_Methods");
			writer.endRecord();
			int finishApp = 0;
			for(int i = startPosition ; i < endPosition ; i ++ ){
				ArrayList<UpdateTable> updates = appUpdates.get(investigatedApp.get(i));
				for(int j = 0 ; j < updates.size() ; j ++){
					String jarFileLocation="";
					try{
						UpdateTable update = updates.get(j);
						String key = update.getPACKAGE_NAME() + "-" + update.getVERSION_CODE() + "-" + update.getRELEASE_DATE().replace("-","_");
						jarFileLocation = JAR_LOC + key + "-dex2jar.jar";
						String data = collectCodeChurnMetrics(jarFileLocation);
						writer.write(update.getPACKAGE_NAME());
						writer.write(update.getVERSION_CODE());
						writer.write(update.getRELEASE_DATE());
						writer.write(data.split("-")[0]);
						writer.write(data.split("-")[1]);
						writer.endRecord();
					}catch(Exception e){
						System.err.println("File is missing ["+jarFileLocation+"]");
					}
				}
				finishApp ++;
				System.out.println("Finish Thread ["+THREAD_NO+"] ["+finishApp+"]");
			}
			writer.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
