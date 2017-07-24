package com.sap.bkc.utils;

import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;

import com.sap.bkc.dao.contractWrappers.Property;



public class PropertyHelper {
	
private PropertyHelper() {	}
	
	
	public static Property getProperty(Web3j web3j,Address propertyAddr) {
		return getProperty(web3j, Utilities.getStringfromAddress(propertyAddr));
	}
	public static Property getProperty(Web3j web3j, String propertyAddr) {
		final Property propertyRegistry = Property.load(
				propertyAddr,
                web3j,
                CredentialsHelper.getDefaultCredentials(),
                AppConstants.GAS_PRICE,
                AppConstants.GAS_LIMIT);
        if (propertyRegistry == null) {
            throw new IllegalStateException("Property not found at " + propertyAddr);
        }
        return propertyRegistry;
	}
	
	

}
