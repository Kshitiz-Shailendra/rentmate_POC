package com.sap.bkc.service;

import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sap.bkc.dao.contractWrappers.RentalAgreement;
import com.sap.bkc.models.RentalAgreementModel;

public interface IRentAgreementService {
	
	public RentalAgreementModel getRentalAgreementDetails(Address landlordAddr,String propertyRegNo);
	public RentalAgreementModel getRentalAgreementDetails(Address tenantAddr);
	//public TransactionReceipt confirmAgreement(Address tenantAddr,boolean confirmation);
	public TransactionReceipt PayRent(Address tenantAddr);
	public TransactionReceipt terminateContract(Address landlordAddr,String propertyRegNo);
	
}
