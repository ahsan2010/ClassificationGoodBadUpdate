package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.HashSet;
import java.util.Set;

public class PermissionModel {

	Set<String> normalPermissionList = new HashSet<String>();
	Set<String> dangerousPermissionList = new HashSet<String>();
	Set<String> customPermissionList = new HashSet<String>();
	Set<String> signaturePermissionList = new HashSet<String>();
	Set<String> permissionNotUsedByThirdparty = new HashSet<String>();
	
	
	public Set<String> getPermissionNotUsedByThirdparty() {
		return permissionNotUsedByThirdparty;
	}
	public void setPermissionNotUsedByThirdparty(Set<String> permissionNotUsedByThirdparty) {
		this.permissionNotUsedByThirdparty = permissionNotUsedByThirdparty;
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
	public Set<String> getCustomPermissionList() {
		return customPermissionList;
	}
	public void setCustomPermissionList(Set<String> customPermissionList) {
		this.customPermissionList = customPermissionList;
	}
	public Set<String> getSignaturePermissionList() {
		return signaturePermissionList;
	}
	public void setSignaturePermissionList(Set<String> signaturePermissionList) {
		this.signaturePermissionList = signaturePermissionList;
	}
}
