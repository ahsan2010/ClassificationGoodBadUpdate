package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

//import com.mysql.jdbc.PreparedStatement;

public class PostHandle extends DefaultHandler {

	private XMLReader xmlReader;
	private Set<String> permissionList = new HashSet<String>();
	private Set<String> activityList = new HashSet<String>();
	private Set<String> receiverList = new HashSet<String>();
	private Set<String> serviceList = new HashSet<String>();
	private Set<String> intentList = new HashSet<String>();
	private String launcherActivity = "";
	private String packageName = "";
	String tempActivity = "";
	
	
	public Set<String> getPermissionList() {
		return permissionList;
	}
	public XMLReader getXmlReader() {
		return xmlReader;
	}	
	public Set<String> getActivityList() {
		return activityList;
	}
	public Set<String> getReceiverList() {
		return receiverList;
	}
	public Set<String> getServiceList() {
		return serviceList;
	}
	public Set<String> getIntentList() {
		return intentList;
	}
	public String getLauncherActivity() {
		return launcherActivity;
	}
	public String getAppPackageName(){
		return this.packageName;
	}

	/**
	 * Constructor for this class
	 * @param xmlReader
	 * @throws Exception
	 */
	public PostHandle(XMLReader xmlReader) throws Exception {
		this.xmlReader = xmlReader;
	}
	
	/**
	 * 
	 * @return
	 */
	
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
					//permission = permission.substring(permission.lastIndexOf(".")+1);
					permissionList.add(permission);
				}
			}			
			if(qName.equalsIgnoreCase("activity")){
				String activityName = atts.getValue("android:name");
				if(activityName != null){
					activityList.add(activityName);
					tempActivity = activityName;
				}
					
			}
			if(qName.equalsIgnoreCase("receiver")){
				String receiverName = atts.getValue("android:name");
				if(receiverName != null)
					receiverList.add(receiverName);
			}
			if(qName.equalsIgnoreCase("service")){
				String serviceName = atts.getValue("android:name");
				if(serviceName != null)
					serviceList.add(serviceName);
			}
			if(qName.equalsIgnoreCase("action")){
				String intentName = atts.getValue("android:name");
				if(intentName != null)
					intentList.add(intentName);
			}
			if(qName.equals("category")){
				String launcherName = atts.getValue("android:name");
				if(launcherName != null && launcherName.equals("android.intent.category.LAUNCHER")){
					launcherActivity = tempActivity;					
				}
			}
			if(qName.equals("manifest")){
				String packageName = atts.getValue("package");
				if (packageName != null){
					this.packageName = packageName;
				}
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

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		
		
	}
	
	

}
