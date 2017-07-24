package com.sap.bkc.utils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3JObject {
	
	private static Web3j instance;
	
	private Web3JObject() {}
	
	public static synchronized Web3j getInstance() {
		
		if(instance == null) {
			instance = Web3j.build(new HttpService(AppConstants.SERVICE_URL));
		}		
		
		return instance;
	}

}
