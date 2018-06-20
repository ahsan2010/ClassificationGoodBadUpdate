package com.sail.awsomebasupdates.model;

public class UpdateData {
	public String versionCode;
	public String appName;
	public double negivityRatio;
	public double negRatio;
	
	
	public UpdateData(String appName, String versionCode, double negativityRatio, double negRatio){
		this.appName = appName;
		this.versionCode = versionCode;
		this.negivityRatio = negativityRatio;
		this.negRatio = negRatio;
	}
	
	public String getCommaKey(){
		return appName +"," + versionCode;
	}
	public String getHashKey(){
		return appName +"-" + versionCode;
	}
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public double getNegivityRatio() {
		return negivityRatio;
	}
	public void setNegivityRatio(double negivityRatio) {
		this.negivityRatio = negivityRatio;
	}
	public double getNegRatio() {
		return negRatio;
	}
	public void setNegRatio(double negRatio) {
		this.negRatio = negRatio;
	}
	
	
	
}
