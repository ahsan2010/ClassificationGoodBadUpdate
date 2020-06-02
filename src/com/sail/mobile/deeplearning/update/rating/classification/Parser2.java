package com.sail.mobile.deeplearning.update.rating.classification;


import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;


public class Parser2 {

	public Parser2() {

	}

	public Map<String,Integer> ParseXml(String path) throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		try {
			PostHandle2 postHandle = new PostHandle2(xr);
			xr.setContentHandler(postHandle);
			xr.parse(path);
			
			return postHandle.getManifestFileInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	
	public static void main(String[] args) throws Exception {
	
		String path = "/home/ahsan/SampleApks/AndroidManifest/AndroidManifest-air.nn.mobile.app.main-2019006-2017_07_31.xml";
		String path2 = "/home/ahsan/SampleApks/AndroidManifest/AndroidManifest-air.nn.mobile.app.main-2019026-2017_08_14.xml";
		Parser2 p = new Parser2();
		Map<String,Integer> info1 = p.ParseXml(path);
		Map<String,Integer> info2 = p.ParseXml(path2);
		
		
		for(String key : info1.keySet()){
			System.out.println(key +" " + info1.get(key));
		}

	}

}