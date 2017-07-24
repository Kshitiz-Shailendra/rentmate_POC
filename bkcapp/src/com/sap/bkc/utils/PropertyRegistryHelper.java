package com.sap.bkc.utils;

import org.web3j.protocol.Web3j;

import com.sap.bkc.dao.contractWrappers.PropertyRegistry;

public class PropertyRegistryHelper {
	
	private PropertyRegistryHelper() {	}
	
	public static PropertyRegistry getPropertyRegistry(Web3j web3j, String propertyRegistryAddr) {
		final PropertyRegistry propertyRegistry = PropertyRegistry.load(
				propertyRegistryAddr,
                web3j,
                CredentialsHelper.getDefaultCredentials(),
                AppConstants.GAS_PRICE,
                AppConstants.GAS_LIMIT);
        if (propertyRegistry == null) {
            throw new IllegalStateException("PropertyRegistry not found at " + propertyRegistryAddr);
        }
        return propertyRegistry;
	}

}
