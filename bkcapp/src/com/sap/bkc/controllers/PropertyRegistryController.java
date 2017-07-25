package com.sap.bkc.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.sap.bkc.models.MailDetails;
import com.sap.bkc.models.PropertyModel;
import com.sap.bkc.models.RentalAgreementModel;
import com.sap.bkc.models.WalletModel;
import com.sap.bkc.service.IPropertyRegistrationService;
import com.sap.bkc.utils.AppConstants;
import com.sap.bkc.utils.CredentialsHelper;
import com.sap.bkc.utils.SendMailHelper;

@RestController
@RequestMapping("property/")
//@SessionAttributes("creds")
public class PropertyRegistryController {
	
	@Autowired
	IPropertyRegistrationService propertyRegistry;
	
	@GetMapping("getHouses/{state}/")
	public List<PropertyModel> getHouses(@PathVariable("state") String state){
		System.out.println("coming here");
		return propertyRegistry.getProperties(AppConstants.Rented.getRented(Integer.parseInt(state)));
	}
	
	@GetMapping("gethouseDetails/{propertyRegNo}/")
	public PropertyModel getPropertyDetails(@PathVariable("propertyRegNo") String propertyRegNo) {
		return propertyRegistry.getPropertyDetails(propertyRegNo);
	}
	
	@GetMapping("getWalletDetails")
	public WalletModel getWalletInfo() throws InterruptedException, ExecutionException {
		return propertyRegistry.getWalletInfo();
	}
	
	@PostMapping("postProperty")
	public TransactionReceipt insertRecord(@RequestBody PropertyModel model){
		model.setUserAddress(new Address(CredentialsHelper.getCurrentUserAddress()));
		System.out.println(model);
		return propertyRegistry.insertRecord(model);
		//return null;
	}
	
	@PostMapping("createAgreement")
	public TransactionReceipt createAgreement(@RequestBody RentalAgreementModel model) {
		model.setLandlord(new Address(CredentialsHelper.getCurrentUserAddress()));
		return propertyRegistry.createAgreement(model);
	}
	@PostMapping("requestForRent")
	public String requestForRentingProperty(@RequestBody MailDetails model) {
		model.setUserWalletAddress(CredentialsHelper.getCurrentUserAddress());
		propertyRegistry.requestForRent(model);
		return "200 OK";
	}
	/*@RequestMapping("/static/index.htm") 
	public String getIndexPage() { 
		return "index.html"; 

	} */
}
