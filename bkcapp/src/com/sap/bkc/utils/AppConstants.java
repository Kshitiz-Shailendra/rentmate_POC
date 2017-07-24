package com.sap.bkc.utils;

import java.math.BigInteger;

public interface AppConstants {
	
	public static final String SERVICE_URL = "http://localhost:8085";
	public static  enum Rented{
		
		NOTRENTED(0),PARTIALRENTED(1),RENTED(2),ALL(3);
		
		private final int state;
		
		private Rented(int state) {
			this.state = state;
		}
		
		public int getState() {
			return state;
		}
		
		public static Rented getRented(int val) {
			return values()[val];
		}
	}
	//contract addresses
	//0xd046c5edafdd758f4834a2ba6f74a0ff232ba807
	public static final String PROPERTYREGISTRY_CONTRACT_ADDRESS = "0xc489e886002da0a92bab74a522cd56195f4404bb";
	
	public static final BigInteger GAS_PRICE = new BigInteger("18000000000");
	public static final BigInteger GAS_LIMIT = new BigInteger("2900000"); 
	
	

}
