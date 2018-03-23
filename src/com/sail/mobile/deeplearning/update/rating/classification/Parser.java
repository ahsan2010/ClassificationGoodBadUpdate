package com.sail.mobile.deeplearning.update.rating.classification;
//import Posts;

import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;


public class Parser {

	
	
	public Parser() {

	}

	public Map<String,Integer> ParseXml(String path) throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		try {
			PostHandle postHandle = new PostHandle(xr);
			xr.setContentHandler(postHandle);
			xr.parse(path);
			return postHandle.getManifestFileInfo();
			//System.out.println(postHandle.getPermissionList());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	
	public static void main(String[] args) throws Exception {

		
		String path = "/home/ahsan/SampleApks/AndroidManifest/AndroidManifest-air.nn.mobile.app.main-2019006-2017_07_31.xml";
		String path2 = "/home/ahsan/SampleApks/AndroidManifest/AndroidManifest-air.nn.mobile.app.main-2019026-2017_08_14.xml";
		Parser p = new Parser();
		Map<String,Integer> info1 = p.ParseXml(path);
		Map<String,Integer> info2 = p.ParseXml(path2);
		
		
		for(String key : info1.keySet()){
			System.out.println(key +" " + info1.get(key));
		}
		/*
		for(String key: info2.keySet()){
			
		}*/
		
		
		
	}

}
