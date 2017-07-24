package com.sap.bkc.utils;

import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;

import com.sap.bkc.dao.contractWrappers.RentalAgreement;



public class RentalAgreementHelper {

	private RentalAgreementHelper() {	}
	
	public static RentalAgreement getRentalAgreement(Web3j web3j,  Address rentAgreementAddr) {
		return getRentalAgreement(web3j, rentAgreementAddr.toString());
	}
	
	public static RentalAgreement getRentalAgreement(Web3j web3j,  String rentAgreementAddr) {
		
		final RentalAgreement rentalAgreement = RentalAgreement.load(rentAgreementAddr, web3j,
												CredentialsHelper.getDefaultCredentials(),
												AppConstants.GAS_PRICE,
												AppConstants.GAS_LIMIT);
				
		return rentalAgreement;
	}
	
}
