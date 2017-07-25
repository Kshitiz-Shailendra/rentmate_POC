package com.sap.bkc.utils;

import java.util.Properties;

public class PropertyMailObject {

	private static Properties instance;
	
	private PropertyMailObject() {}
	
	public static synchronized Properties getInstance() {
		
		if(instance == null) {
			
			instance = new Properties();
			instance.put(AppConstants.SERVERHOST, AppConstants.SERVERID);
			instance.put(AppConstants.FACTORYPORT, AppConstants.PORTNO);
			instance.put(AppConstants.FACTORYCLASS, AppConstants.SSLSOCKET);
			instance.put(AppConstants.AUTH, "true");
			instance.put(AppConstants.PORT, AppConstants.PORTNO);
		}		
		
		return instance;
	}
	
}
