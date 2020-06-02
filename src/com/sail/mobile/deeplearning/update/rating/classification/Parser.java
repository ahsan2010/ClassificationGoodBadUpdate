package com.sail.mobile.deeplearning.update.rating.classification;
//import Posts;

import java.util.ArrayList;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.derby.tools.sysinfo;
import org.xml.sax.XMLReader;

public class Parser {

	PostHandle handle = null;
	boolean findError = false;
	
	
	
	public Parser() {

	}
	
	public void ParseXml(String path) throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		try {
			handle = new PostHandle(xr);
			xr.setContentHandler(handle);
			xr.parse(path);
			
		} catch (Exception e) {
			this.findError = true;
			e.printStackTrace();
		}
		
	}
	
	public Set<String> getActivityList() {
		return handle.getActivityList();
	}

	public Set<String> getReceiverList() {
		return handle.getReceiverList();
	}

	public Set<String> getServiceList() {
		return handle.getServiceList();
	}

	public Set<String> getIntentList() {
		return handle.getIntentList();
	}
	public Set<String> getPermissionList(){
		return handle.getPermissionList();
	}
	public  String launcherActivity(){
		return handle.getLauncherActivity();
	}
	
	public PostHandle getHandle(){
		return handle;
	}
	
	public String getAppPackageName(){
		return handle.getAppPackageName();
	}
	
	public void summaryResult(){
		System.out.println("Permission ["+handle.getPermissionList().size()+"] " + handle.getPermissionList().toString());
		System.out.println("Activity ["+handle.getActivityList().size()+"] " + handle.getActivityList().toString());
		System.out.println("Service ["+handle.getServiceList().size()+"] " + handle.getServiceList().toString());
		System.out.println("Receiver ["+handle.getReceiverList().size() +"] "+ handle.getReceiverList().toString());
		System.out.println("Intent ["+handle.getIntentList().size()+"] " + handle.getIntentList().toString());
	}
	
	public static void main(String[] args) throws Exception {

		String path = "/home/ahsan/Documents/SAILLabResearch/Ahsan_Research_Project/Ads_Client_Study/Ads_Type_Analysis/November_26_2018/Investigation_Apps/2_3_Ad_Library/com.cmcm.live-37101390-2017_09_19/AndroidManifest.xml";
		String path2 = "/home/ahsan/SampleApks/AndroidManifest/AndroidManifest-air.au.com.metro.DumbWaysToDie-3000000-2014_08_18.xml";
		Parser p = new Parser();
		p.ParseXml(path);
		p.summaryResult();
	}

}
