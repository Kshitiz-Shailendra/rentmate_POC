package com.sap.bkc.models;

public class GeneralInfo {
	
	
	int postedProperties;
	int totalProperties;
	WalletModel wallet;
	
	public int getPostedProperties() {
		return postedProperties;
	}
	public void setPostedProperties(int postedProperties) {
		this.postedProperties = postedProperties;
	}
	public int getTotalProperties() {
		return totalProperties;
	}
	public void setTotalProperties(int totalProperties) {
		this.totalProperties = totalProperties;
	}
	public WalletModel getWallet() {
		return wallet;
	}
	public void setWallet(WalletModel wallet) {
		this.wallet = wallet;
	}
}
