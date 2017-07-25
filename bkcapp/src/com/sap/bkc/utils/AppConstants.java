package com.sap.bkc.utils;

import java.math.BigInteger;

public interface AppConstants {
	
	public static final String SERVICE_URL = "http://localhost:8080";
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
	public static final String PROPERTYREGISTRY_CONTRACT_ADDRESS = "0x5c094d4931903565960f1fe4c8b9775c11f26dbb";
	
	public static final BigInteger GAS_PRICE = new BigInteger("18000000000");
	public static final BigInteger GAS_LIMIT = new BigInteger("2900000"); 
	
	public static final String SERVERHOST = "mail.smtp.host";
	public static final String SERVERID = "smtp.gmail.com";
	public static final String FACTORYPORT = "mail.smtp.socketFactory.port";
	public static final String PORTNO = "465";
	public static final String FACTORYCLASS = "mail.smtp.socketFactory.class";
	public static final String SSLSOCKET = "javax.net.ssl.SSLSocketFactory";
	public static final String AUTH = "mail.smtp.auth";
	public static final String PORT = "mail.smtp.port";
	
	public static final String MAILUSERNAME = "tco2funuser@gmail.com";
	public static final String MAILPASSWORD = "I<3SAPtco2";
	

}
