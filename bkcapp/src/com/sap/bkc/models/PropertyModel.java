package com.sap.bkc.models;

import org.web3j.abi.datatypes.Address;

import com.sap.bkc.utils.AppConstants;
import com.sap.bkc.utils.Utilities;
/*
 * Lot of things needs to be changed in this 
 */
public class PropertyModel {
	
	//private User user;
	//private PropAddress propAddress;
	private String username;
	private Address userAddress;
	private String address;
	private String PropertyRegNo;
	private long postedTimeStamp;
	private String aboutProperty;
	private int rent;
	private int securityDeposit;
	private String houseSize;

	/*public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	/*public PropAddress getPropAddress() {
		return propAddress;
	}

	public void setPropAddress(PropAddress propAddress) {
		this.propAddress = propAddress;
	}*/
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserAddressAsString() {
		return userAddress.toString();
	}
	

	public Address getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getAddress() {
		return address;
	}
	
	

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPropertyRegNo() {
		return PropertyRegNo;
	}

	

	public void setPropertyRegNo(String propertyRegNo) {
		PropertyRegNo = propertyRegNo;
	}

	public String getPostedTimeStampFormatted() {
		return Utilities.getStringTimestampfromEpochSecond(postedTimeStamp);
	}
	
	public long getPostedTimeStamp() {
		return postedTimeStamp;
	}
	
	public void setPostedTimeStamp(long postedTimeStamp) {
		this.postedTimeStamp = postedTimeStamp;
	}

	public String getAboutProperty() {
		return aboutProperty;
	}

	public void setAboutProperty(String aboutProperty) {
		this.aboutProperty = aboutProperty;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(int securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(String houseSize) {
		this.houseSize = houseSize;
	}

	@Override
	public String toString() {
		return "PropertyModel [username=" + username + ", address=" + address + ", PropertyRegNo=" + PropertyRegNo
				+ ", postedTimeStamp=" + postedTimeStamp + ", aboutProperty=" + aboutProperty + ", rent=" + rent
				+ ", securityDeposit=" + securityDeposit + ", houseSize=" + houseSize + "]";
	}
	
	
	
}
