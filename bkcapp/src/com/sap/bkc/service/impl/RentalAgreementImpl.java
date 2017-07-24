package com.sap.bkc.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.expression.spel.support.ReflectionHelper;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sap.bkc.dao.contractWrappers.PropertyRegistry;
import com.sap.bkc.dao.contractWrappers.RentalAgreement;
import com.sap.bkc.models.RentalAgreementModel;
import com.sap.bkc.service.IRentAgreementService;
import com.sap.bkc.utils.AppConstants;
import com.sap.bkc.utils.CredentialsHelper;
import com.sap.bkc.utils.PropertyHelper;
import com.sap.bkc.utils.PropertyRegistryHelper;
import com.sap.bkc.utils.RentalAgreementHelper;
import com.sap.bkc.utils.Utilities;
import com.sap.bkc.utils.Web3JObject;

public class RentalAgreementImpl implements IRentAgreementService {

	@Override
	public RentalAgreementModel getRentalAgreementDetails(Address landlordAddr, String propertyRegNo) {
		// TODO Auto-generated method stub
		return getRentalAgreementDetailsUsingWrappers(landlordAddr,propertyRegNo);
	}

	@Override
	public RentalAgreementModel getRentalAgreementDetails(Address tenantAddr) {
		// TODO Auto-generated method stub
		return getRentalAgreementDetailsUsingWrappers(tenantAddr);
	}

	/*@Override
	public TransactionReceipt confirmAgreement(Address tenantAddr, boolean confirmation) {
		
		return confirmAgreementUsingWrappers(tenantAddr, confirmation);
	}*/

	@Override
	public TransactionReceipt PayRent(Address tenantAddr) {
		
		return PayRentUsingWrappers(tenantAddr);
	}

	@Override
	public TransactionReceipt terminateContract(Address landlordAddr, String propertyRegNo) {
		
		return terminateContractUsingWrappers(landlordAddr, propertyRegNo);
	}
	
	
	private RentalAgreementModel getRentalAgreementDetailsUsingWrappers(Address landlordAddr, String propertyRegNo) {
		
		PropertyRegistry registry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		RentalAgreementModel rentAgreementModel = new RentalAgreementModel();
		try {
			Address rentAgreementAddr = registry.getRentAgreementAddr(Utilities.getUTF8String(propertyRegNo), 
																							landlordAddr).get();
			
			RentalAgreement rentAgreement = RentalAgreementHelper.getRentalAgreement(Web3JObject.getInstance(), rentAgreementAddr);
			List<Type> rentAgreementList = rentAgreement.getAgreementDetails().get();
			
			String rentAgstr[] = rentAgreementList.get(0).toString().split(",");
			rentAgreementModel.setRent(Integer.parseInt(rentAgstr[0]));
			rentAgreementModel.setPropertyRegNo(rentAgstr[1]);
			
			rentAgreementModel.setCreatedTimestamp(((Uint)rentAgreementList.get(1)).getValue().intValue());
			rentAgreementModel.setTenant((Address)rentAgreementList.get(2));
			rentAgreementModel.setLandlord((Address)rentAgreementList.get(3));
			
			
		
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return rentAgreementModel;
	}
	private RentalAgreementModel getRentalAgreementDetailsUsingWrappers(Address tenantAddr) {
		PropertyRegistry registry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		RentalAgreementModel rentAgreementModel = new RentalAgreementModel();
		try {
			Address rentAgreementAddr = registry.getRentAgreementAddrFromTenantAddr(tenantAddr).get();
			
			RentalAgreement rentAgreement = RentalAgreementHelper.getRentalAgreement(Web3JObject.getInstance(), rentAgreementAddr);
			List<Type> rentAgreementList = rentAgreement.getAgreementDetails().get();
			
			String rentAgstr[] = rentAgreementList.get(0).toString().split(",");
			rentAgreementModel.setRent(Integer.parseInt(rentAgstr[0]));
			rentAgreementModel.setPropertyRegNo(rentAgstr[1]);
			
			rentAgreementModel.setCreatedTimestamp(((Uint)rentAgreementList.get(1)).getValue().intValue());
			rentAgreementModel.setTenant((Address)rentAgreementList.get(2));
			rentAgreementModel.setLandlord((Address)rentAgreementList.get(3));
			
			
		
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return rentAgreementModel;
	}

	
	/*private TransactionReceipt confirmAgreementUsingWrappers(Address tenantAddr, boolean confirmation) {
		
		PropertyRegistry registry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		TransactionReceipt receipt = null;
		try {
			
			Address rentAgreementAddr = registry.getRentAgreementAddrFromTenantAddr(tenantAddr).get();
			RentalAgreement rentAgreement = RentalAgreementHelper.getRentalAgreement(Web3JObject.getInstance(), rentAgreementAddr);
			receipt = rentAgreement.confirmAgreement().get();
						
		
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return receipt;
	}*/

	
	private TransactionReceipt PayRentUsingWrappers(Address tenantAddr) {
		PropertyRegistry registry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		TransactionReceipt receipt = null;
		try {
			
			Address rentAgreementAddr = registry.getRentAgreementAddrFromTenantAddr(tenantAddr).get();
			RentalAgreement rentAgreement = RentalAgreementHelper.getRentalAgreement(Web3JObject.getInstance(), rentAgreementAddr);
			String rentValue = rentAgreement.rent().get().toString();
			receipt = rentAgreement.payRent(Utilities.getUint256(rentValue)).get();						
		
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return receipt;
		
	}

	
	
	private TransactionReceipt terminateContractUsingWrappers(Address landlordAddr, String propertyRegNo) {
		PropertyRegistry registry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		
		TransactionReceipt receipt = null;
		try {
			Address rentAgreementAddr = registry.getRentAgreementAddr(Utilities.getUTF8String(propertyRegNo), 
																							landlordAddr).get();
			
			RentalAgreement rentAgreement = RentalAgreementHelper.getRentalAgreement(Web3JObject.getInstance(), rentAgreementAddr);
			receipt = rentAgreement.terminateContract().get();			
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipt;
		
	}
	
	
	public static void main(String[] args) throws IOException, CipherException {
		Credentials creds = WalletUtils.loadCredentials("kshitizone", "/home/I331654/testnet/chaindata/keystore/UTC--2017-07-21T08-02-30.919722320Z--05786eb12726588a1d569e0b0ecda0a2852ea9c2");
		RentalAgreementImpl rent = new RentalAgreementImpl();
		
		System.out.println(rent.getRentalAgreementDetails(new Address(creds.getAddress())));
		System.out.println(rent.getRentalAgreementDetails(new Address(CredentialsHelper.getDefaultCredentials().getAddress()),"129"));
		//System.out.println(rent.confirmAgreement(new Address(creds.getAddress()), true));
	}
}
