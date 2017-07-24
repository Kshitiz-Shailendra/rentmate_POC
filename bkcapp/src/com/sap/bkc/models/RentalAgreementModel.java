package com.sap.bkc.models;

import org.web3j.abi.datatypes.Address;

public class RentalAgreementModel {
		
		private String propertyRegNo;
		private int rent;
		private int createdTimestamp;
		private Address tenant;
		private Address landlord;
		
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
		@Override
		public String toString() {
			return "RentalAgreementModel [propertyRegNo=" + propertyRegNo + ", rent=" + rent + ", createdTimestamp="
					+ createdTimestamp + ", tenant=" + tenant + ", landlord=" + landlord + "]";
		}
		
		
		
}
