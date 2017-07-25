package com.sap.bkc.models;

public class MailDetails {

	private String userName;
	private String userEmail;
	private String userWalletAddress;
	private String userMessage;
	private String userMobileNo;
	private String propertyRegNo;
	private String ownerEmail;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserWalletAddress() {
		return userWalletAddress;
	}
	public void setUserWalletAddress(String userWalletAddress) {
		this.userWalletAddress = userWalletAddress;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public String getUserMobileNo() {
		return userMobileNo;
	}
	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}
	public String getPropertyRegNo() {
		return propertyRegNo;
	}
	public void setPropertyRegNo(String propertyRegNo) {
		this.propertyRegNo = propertyRegNo;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	
	@Override
	public String toString() {
		return "MailDetails [userName=" + userName + ", userEmail=" + userEmail + ", userWalletAddress="
				+ userWalletAddress + ", userMessage=" + userMessage + ", userMobileNo=" + userMobileNo
				+ ", propertyRegNo=" + propertyRegNo + "]";
	}
	
	
	
}
