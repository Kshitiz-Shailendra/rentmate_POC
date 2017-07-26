package com.sap.bkc.models;

import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class RentalAgreementModel {
		
		private String propertyRegNo;
		private int rent;
		private int createdTimestamp;
		private Address tenant;
		private Address landlord;
		private String tenantEmail;
		private TransactionReceipt receipt;
		
		public String getPropertyRegNo() {
			return propertyRegNo;
		}
		public void setPropertyRegNo(String propertyRegNo) {
			this.propertyRegNo = propertyRegNo;
		}
		public int getRent() {
			return rent;
		}
		public void setRent(int rent) {
			this.rent = rent;
		}
		public int getCreatedTimestamp() {
			return createdTimestamp;
		}
		public void setCreatedTimestamp(int createdTimestamp) {
			this.createdTimestamp = createdTimestamp;
		}
		public Address getTenant() {
			return tenant;
		}
		public void setTenant(Address tenant) {
			this.tenant = tenant;
		}
		public Address getLandlord() {
			return landlord;
		}
		public void setLandlord(Address landlord) {
			this.landlord = landlord;
		}
		public TransactionReceipt getReceipt() {
			return receipt;
		}
		public void setReceipt(TransactionReceipt receipt) {
			this.receipt = receipt;
		}
		public String getTenantEmail() {
			return tenantEmail;
		}
		public void setTenantEmail(String tenantEmail) {
			this.tenantEmail = tenantEmail;
		}
		
		@Override
		public String toString() {
			return "RentalAgreementModel "
					+ "\nProperty Registration No : " + propertyRegNo + ""
					+ "\nRent : " + rent + ""
					+ "\nCreatedTimestamp : "+ createdTimestamp + ""
					+ "\nTenant wallet address : " + tenant + ""
					+ "\nlandlord wallet address : " + landlord + "\n";
		}
		
		public String toString(TransactionReceipt receipt) {
			return this.toString() + 
			"\n\nTransaction Hash : " + receipt.getTransactionHash()
			+ "\nTransaction Index : " + receipt.getTransactionIndex()
			+ "\nBlock Number : " + receipt.getBlockNumber()
			+ "\nBlock Hash :" + receipt.getBlockHash();
		}
		
}
