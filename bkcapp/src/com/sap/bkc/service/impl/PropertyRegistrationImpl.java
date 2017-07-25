package com.sap.bkc.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
//import java.util.stream.Stream;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sap.bkc.dao.contractWrappers.Property;
import com.sap.bkc.dao.contractWrappers.PropertyRegistry;
import com.sap.bkc.dao.contractWrappers.RentalAgreement;
import com.sap.bkc.models.MailDetails;
import com.sap.bkc.models.PropertyModel;
import com.sap.bkc.models.RentalAgreementModel;
import com.sap.bkc.models.WalletModel;
import com.sap.bkc.service.IPropertyRegistrationService;
import com.sap.bkc.utils.AppConstants;
import com.sap.bkc.utils.AppConstants.Rented;
import com.sap.bkc.utils.CredentialsHelper;
import com.sap.bkc.utils.PropertyHelper;
import com.sap.bkc.utils.PropertyRegistryHelper;
import com.sap.bkc.utils.RentalAgreementHelper;
import com.sap.bkc.utils.SendMailHelper;
import com.sap.bkc.utils.Utilities;
import com.sap.bkc.utils.Web3JObject;

public class PropertyRegistrationImpl implements IPropertyRegistrationService {

	@Override
	public TransactionReceipt insertRecord(PropertyModel propModel) {
		return insertRecordUsingWrappers(propModel);

	}

	@Override
	public List<PropertyModel> getProperties(AppConstants.Rented state) {

		return getPropertiesUsingWrappers(state);
	}

	@Override
	public PropertyModel getPropertyDetails(String PropertyRegNo) {
		return getDetailsUsingWrappers(PropertyRegNo);
	}

	// possibility of using address as well will have to look into it
	@Override
	public List<PropertyModel> getOwnerProperties(String ownerName) {
		return getOwnerPropertiesUsingWrappers(ownerName);
	}

	@Override
	public TransactionReceipt createAgreement(RentalAgreementModel rentModel) {
		// TODO Auto-generated method stub
		return createAgreementUsingWrappers(rentModel);
	}
	@Override
	public void requestForRent(MailDetails model) {
		// TODO Auto-generated method stub
		requestForRentWrappers(model);
	}
	
	@Override
	public WalletModel getWalletInfo() throws InterruptedException, ExecutionException {
		return getWalletInfoWrappers();
	}


	private TransactionReceipt insertRecordUsingWrappers(PropertyModel propModel) {

		PropertyRegistry propertyRegsitry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(),
				AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		TransactionReceipt receipt = null;
		String record = String.join(",", Integer.toString(propModel.getRent()), // 0
				propModel.getUsername(), // 1
				propModel.getUserAddress().toString(), // 2
				propModel.getPropertyRegNo(), // 3
				propModel.getHouseSize(), // 4
				propModel.getAboutProperty());// 5
		try {

			receipt = propertyRegsitry.insertRecord(Utilities.getUTF8String(propModel.getRent()),
					Utilities.getUTF8String(propModel.getUserEmail()),
					Utilities.getUTF8String(propModel.getPropertyRegNo()),
					Utilities.getUTF8String(propModel.getAddress()), Utilities.getUTF8String(propModel.getUsername()),
					Utilities.getUTF8String(propModel.getHouseSize()),
					Utilities.getUTF8String(propModel.getAboutProperty())).get();
			propertyRegsitry.insertRecordDetailsArray(Utilities.getUTF8String(propModel.getPropertyRegNo()),
					Utilities.getUTF8String(record));
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipt;

	}

	private List<PropertyModel> getPropertiesUsingWrappers(AppConstants.Rented state) {

		PropertyRegistry propertyRegsitry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(),
				AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		List<PropertyModel> propertiesList = new ArrayList<PropertyModel>();
		try {
			String[] allProps = propertyRegsitry.getHouses(Utilities.getUint8(state.getState())).get().toString()
					.split(";");

			for (String str : allProps) {
				if (!str.equals("")) {
					String[] strArr = str.split(",");
					PropertyModel model = new PropertyModel();
					model.setRent(Integer.parseInt(strArr[0]));
					model.setUsername(strArr[1]);
					model.setUserAddress(new Address(strArr[2]));
					model.setPropertyRegNo(strArr[3]);
					model.setHouseSize(strArr[4]);
					model.setAboutProperty(strArr[5]);
					propertiesList.add(model);
				}
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return propertiesList;
	}

	private List<PropertyModel> getOwnerPropertiesUsingWrappers(String ownerName) {

		List<PropertyModel> ownerProperties = getPropertiesUsingWrappers(Rented.NOTRENTED)
					.parallelStream()
					.filter(p -> p.getUsername().equals(ownerName))
					.collect(Collectors.toCollection(ArrayList::new));

		return ownerProperties;

	}

	private List<PropertyModel> getOwnerPropertiesUsingAddress(String ownerAddr) {

		List<PropertyModel> ownerProperties = getPropertiesUsingWrappers(Rented.NOTRENTED)
				.parallelStream()
				.filter(p -> p.getUserAddressAsString().equals(ownerAddr))
				.collect(Collectors.toCollection(ArrayList::new));

		return ownerProperties;

	}

	private PropertyModel getDetailsUsingWrappers(String PropertyRegNo) {
		PropertyRegistry propertyRegsitry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(),
				AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		PropertyModel model = new PropertyModel();
		try {
			Address addr = propertyRegsitry.getPropertyAddr(Utilities.getUTF8String(PropertyRegNo)).get();
			Property prop = PropertyHelper.getProperty(Web3JObject.getInstance(), addr);
			
			List<Type> details = prop.getPropertydetails().get();
			
			Address userAddress = (Address)details.get(1);
			
			String[] detailArr = (details.get(0).toString()).split(",");
			model.setRent(Integer.parseInt(detailArr[0]));
			model.setUserEmail(detailArr[1]);
			model.setPropertyRegNo(detailArr[2]);
			model.setAddress(detailArr[3]);
			model.setUsername(detailArr[4]);
			model.setHouseSize(detailArr[5]);
			model.setAboutProperty(detailArr[6]);
			model.setUserAddress(userAddress);
			model.setPostedTimeStamp(((Uint) details.get(2)).getValue().intValue());
			model.setIsCurrentUserOwner(userAddress.toString().equals(CredentialsHelper.getCurrentUserAddress()));
		
		} catch (InterruptedException | ExecutionException e) {

			e.printStackTrace();
		}

		return model;
	}
	//have to check how to store wallet address
	private TransactionReceipt createAgreementUsingWrappers(RentalAgreementModel rentModel) {
		
		PropertyRegistry  propertyRegsitry = PropertyRegistryHelper.getPropertyRegistry(Web3JObject.getInstance(), AppConstants.PROPERTYREGISTRY_CONTRACT_ADDRESS);
		TransactionReceipt receipt = null;
		try {
			
			receipt = propertyRegsitry.createAgreement(Utilities.getUTF8String(rentModel.getPropertyRegNo()), 
														Utilities.getUTF8String(rentModel.getRent()),
														(rentModel.getTenant())).get();
			
		} catch (InterruptedException | ExecutionException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return receipt;
		
	}
	
	private void requestForRentWrappers(MailDetails model) {
		
		StringBuilder body = new StringBuilder("Dear Owner,\nPlease approve my booking request\n");
		body.append("\nProperty RegNo: "+model.getPropertyRegNo());
		body.append("\nTenant Name: "+model.getUserName());
		body.append("\nTenant Email: "+model.getUserEmail());
		body.append("\nTenant Wallet Address: "+model.getUserWalletAddress());
		body.append("\nTenant Mobile No: "+model.getUserMobileNo());
		if(model.getUserMessage()!=null) body.append("\nTenant Message: "+model.getUserMessage());
		
		
		SendMailHelper.sendMail(model.getOwnerEmail(), body);
		//SendMailHelper.sendMail("saurav.patel@sap.com", body);
		
		
	}
	
	private WalletModel getWalletInfoWrappers() throws InterruptedException, ExecutionException {
		WalletModel walletObject = new WalletModel();
		
		walletObject.setWalletAddress(CredentialsHelper.getCurrentUserAddress());
		
		EthGetBalance ethGetBalance = Web3JObject.getInstance().ethGetBalance(walletObject.getWalletAddress(), DefaultBlockParameterName.LATEST)
				  .sendAsync()
				  .get();
		
		BigInteger wei = ethGetBalance.getBalance();
		
		String balance = wei.divide(new BigInteger("1000000000000000000")).toString();
		
		walletObject.setWalletBalance(balance);
		
		return walletObject;
	}

	
	

	/*
	 * public static void main(String[] args) { PropertyRegistrationImpl prop = new
	 * PropertyRegistrationImpl();
	 * System.out.println(prop.getOwnerProperties("ksh1")); }
	 */
}
