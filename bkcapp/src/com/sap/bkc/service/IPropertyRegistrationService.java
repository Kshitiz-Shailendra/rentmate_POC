package com.sap.bkc.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sap.bkc.models.MailDetails;
import com.sap.bkc.models.PropertyModel;
import com.sap.bkc.models.RentalAgreementModel;
import com.sap.bkc.models.WalletModel;
import com.sap.bkc.utils.AppConstants;

public interface IPropertyRegistrationService {
	
	public TransactionReceipt insertRecord(PropertyModel propModel);
	public List<PropertyModel> getProperties(AppConstants.Rented state);
	public PropertyModel getPropertyDetails(String PropertyRegNo);
	public List<PropertyModel> getOwnerProperties(String ownerName);
	public TransactionReceipt createAgreement(RentalAgreementModel rentModel);
	public void requestForRent(MailDetails model);
	public WalletModel getWalletInfo() throws InterruptedException, ExecutionException;
}
