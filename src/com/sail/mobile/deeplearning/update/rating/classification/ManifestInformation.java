package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.HashSet;
import java.util.Set;

public class ManifestInformation {
	
	public Set<String> activityList = new HashSet<String>();
	public Set<String> receiverList = new HashSet<String>();
	public Set<String> serviceList = new HashSet<String>();
	public Set<String> normalPermissionList = new HashSet<String>();
	public Set<String> dangerousPermissionList = new HashSet<String>();
	public Set<String> userPermissionList = new HashSet<String>();
	public Set<String> getActivityList() {
		return activityList;
	}
	public void setActivityList(Set<String> activityList) {
		this.activityList = activityList;
	}
	public Set<String> getReceiverList() {
		return receiverList;
	}
	public void setReceiverList(Set<String> receiverList) {
		this.receiverList = receiverList;
	}
	public Set<String> getServiceList() {
		return serviceList;
	}
	public void setServiceList(Set<String> serviceList) {
		this.serviceList = serviceList;
	}
	public Set<String> getNormalPermissionList() {
		return normalPermissionList;
	}
	public void setNormalPermissionList(Set<String> normalPermissionList) {
		this.normalPermissionList = normalPermissionList;
	}
	public Set<String> getDangerousPermissionList() {
		return dangerousPermissionList;
	}
	public void setDangerousPermissionList(Set<String> dangerousPermissionList) {
		this.dangerousPermissionList = dangerousPermissionList;
	}
	public Set<String> getUserPermissionList() {
		return userPermissionList;
	}
	public void setUserPermissionList(Set<String> userPermissionList) {
		this.userPermissionList = userPermissionList;
	}
	
	
	
}
