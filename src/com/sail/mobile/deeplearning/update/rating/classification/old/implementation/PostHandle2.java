package com.sail.mobile.deeplearning.update.rating.classification.old.implementation;

import com.csvreader.CsvReader;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;

//import MyNewException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.compress.archivers.dump.DumpArchiveEntry.PERMISSION;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

//import com.mysql.jdbc.PreparedStatement;

public class PostHandle2 extends DefaultHandler {

	private XMLReader xmlReader;

	Map<String,Integer> manifestInfo  = new HashMap<String,Integer>();
	
	public static String ROOT = "/home/ahsan/SampleApks/result_Previous_25_04_2018";

	public static String ANDROID_DANGEROUS_PERMISSION_LIST = ROOT + "/PermissionAnalysis/dangerous_permission_list.csv";
	public static String ANDROID_ALL_PERMISSION_LIST = ROOT + "/PermissionAnalysis/android_permission_list.csv";
	Set<String> androidDangeroudPermission = AdsInputDataLoader.readAndroidPermission(ANDROID_DANGEROUS_PERMISSION_LIST);// android.permissions.WRITE
	Set<String> androidPermissions = AdsInputDataLoader.readAndroidPermission(ANDROID_ALL_PERMISSION_LIST); // android.permissions.WRITE
	
	
	//ACTIVITY
	//RECEIVER
	//SERVICE
	//PERMISSION
	//DANGEROUS_PERMISSION
	//OTHER_PERMISSION
	
	
	/**
	 * Constructor for this class
	 * @param xmlReader
	 * @throws Exception
	 */
	public PostHandle2(XMLReader xmlReader) throws Exception {
		this.xmlReader = xmlReader;
		manifestInfo.put("ACTIVITY", 0);
		manifestInfo.put("RECEIVER", 0);
		manifestInfo.put("SERVICE", 0);
		manifestInfo.put("PERMISSION", 0);
		manifestInfo.put("DANGEROUS_PERMISSION", 0);
		manifestInfo.put("OTHER_PERMISSION", 0);
		
	}
	
	/**
	 * 
	 * @return
	 */
	
	public Map<String,Integer> getManifestFileInfo(){
		return manifestInfo;
	}
	/**
	 * Read the xml file with specific tags
	 * @param sName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	public void ReadPost(String sName, String qName, Attributes atts) throws SAXException {
		try {

			if (qName.equalsIgnoreCase("uses-permission")){
				String permission = atts.getValue("android:name");
				if(permission != null ){
					if(androidPermissions.contains(permission)){
						manifestInfo.put("PERMISSION", manifestInfo.get("PERMISSION") + 1);
					}
					if(androidDangeroudPermission.contains(permission)){
						manifestInfo.put("DANGEROUS_PERMISSION", manifestInfo.get("DANGEROUS_PERMISSION") + 1);
					}
					if(!androidPermissions.contains(permission)){
						manifestInfo.put("OTHER_PERMISSION", manifestInfo.get("OTHER_PERMISSION") + 1);
					}
				}
			}
			
			if (qName.equalsIgnoreCase("activity")) {
				manifestInfo.put("ACTIVITY", manifestInfo.get("ACTIVITY") + 1);
			}
			if (qName.equalsIgnoreCase("receiver")) {
				manifestInfo.put("RECEIVER", manifestInfo.get("RECEIVER") + 1);
			}

			if (qName.equalsIgnoreCase("service")) {
				manifestInfo.put("SERVICE", manifestInfo.get("SERVICE") + 1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * Method automatic call after finishing the reading of the xml file
	 */
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	/**
	 * Method automatically call for each of the start element of the xml file
	 */
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		//ReadPost(localName, qName, atts, false);
		ReadPost(localName, qName, atts);
	}

}