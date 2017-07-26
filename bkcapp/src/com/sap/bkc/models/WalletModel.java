package com.sap.bkc.models;

public class WalletModel {
	
	private String WalletAddress;
	private String WalletBalance;
	
	public WalletModel() {
		super();
	}

	public WalletModel(String walletAddress, String walletBalance) {
		super();
		WalletAddress = walletAddress;
		WalletBalance = walletBalance;
	}

	public String getWalletAddress() {
		return WalletAddress;
	}
	public void setWalletAddress(String walletAddress) {
		WalletAddress = walletAddress;
	}
	public String getWalletBalance() {
		return WalletBalance;
	}
	public void setWalletBalance(String walletBalance) {
		WalletBalance = walletBalance;
	}
	
	@Override
	public String toString() {
		return "WalletModel [WalletAddress=" + WalletAddress + ", WalletBalance=" + WalletBalance + "]";
	}
	
}
