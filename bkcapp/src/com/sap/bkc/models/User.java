package com.sap.bkc.models;

import org.web3j.abi.datatypes.Address;

import com.sap.bkc.utils.Utilities;

public class User {
	
	private String userName;
	private Address userAddress;
	
	public User(String userName) {
		this(userName,null);
	}
	public User(String userName,Address userAddress) {
		this.userName = userName;
		this.userAddress = userAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Address getUserAddress() {
		return userAddress;
	}
	public String getUserAddressasString() {
		return Utilities.getStringfromAddress(userAddress);
	}
	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
	
	
}
