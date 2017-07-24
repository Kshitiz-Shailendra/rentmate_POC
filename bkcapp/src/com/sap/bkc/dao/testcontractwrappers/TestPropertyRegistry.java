package com.sap.bkc.dao.testcontractwrappers;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.sap.bkc.dao.contractWrappers.Property;
import com.sap.bkc.dao.contractWrappers.PropertyRegistry;
import com.sap.bkc.utils.AppConstants.Rented;
import com.sap.bkc.utils.Utilities;


public class TestPropertyRegistry {

	private static final BigInteger GAS_PRICE = new BigInteger("18000000000");
	private static final BigInteger GAS_LIMIT = new BigInteger("2900000");
	private static final String CONTRACT_ADDRESS = "0xed14cf67a9f6632efbb046a5acad8281d86f7574";
	
	private static Utf8String getUTF8String(String str) {
		return new Utf8String(str);
	}
	private static Uint256 getUint256(long num) {
		return new Uint256(num);
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, CipherException, 
													NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		/*insert record
		 * Utf8String _rent, Utf8String _securityDeposit, Utf8String _PropertyRegNo, 
		 * Utf8String _PropertyAddr, Utf8String _ownerName, Utf8String _houseSize, Utf8String _aboutProperty
		 */
		/*System.out.println("started....");
		Web3j web3 = Web3j.build(new HttpService("http://localhost:8085"));
		Credentials credentials;
		System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
		credentials = WalletUtils.loadCredentials("Tocsalp1989#", "/home/I331654/testnet/chaindata/keystore/UTC--2017-06-08T17-02-12.219450655Z--a0a383b16e8e2dbeb0c714788779294339380360");
		//PropertyRegistry registry = PropertyRegistry.deploy(web3, credentials, GAS_PRICE, GAS_LIMIT, BigInteger.ONE).get();
		PropertyRegistry registry = PropertyRegistry.load(CONTRACT_ADDRESS, web3, credentials, GAS_PRICE, GAS_LIMIT);
		//System.out.println(registry.getContractAddress());
		registry.insertRecord(getUTF8String("2000"),getUTF8String("300"), getUTF8String("129"), getUTF8String("alld"), 
				getUTF8String("ksh"), getUTF8String("big"), getUTF8String("dsfsdfdsaff"));
		registry.insertRecord(getUTF8String("2000"),getUTF8String("300"), getUTF8String("127"), getUTF8String("alld"), 
				getUTF8String("ksh"), getUTF8String("big"), getUTF8String("dsfsdfdsaff"));
		registry.insertRecord(getUTF8String("2000"),getUTF8String("300"), getUTF8String("128"), getUTF8String("alld"), 
				getUTF8String("ksh"), getUTF8String("big"), getUTF8String("dsfsdfdsaff"));
		
		Address addr = registry.getPropertyAddr(getUTF8String("129")).get();
		Utf8String houses = registry.getHouses(Utilities.getUint8(Rented.NOTRENTED.getState())).get();
		System.out.println("houses  : "+houses);
		System.out.println("addr of house  : "+addr);
		
		Property prop = Property.load(Utilities.getStringfromAddress(addr), web3, credentials, GAS_PRICE, GAS_LIMIT);
		System.out.println(prop.getPropertydetails().get());*/
		//System.out.println(prop.getOwnerInfo().get());
		
		String str =  String.format(
                "%s%stestnet%schaindata%skeystore", System.getProperty("user.home"), File.separator, File.separator,File.separator);
		
		System.out.println(WalletUtils.getTestnetKeyDirectory());
		
		System.out.println(WalletUtils.getDefaultKeyDirectory());
		System.out.println(str);
		
		//String walletloc = WalletUtils.generateFullNewWalletFile("kshitiztwo", new File("/home/I331654/testnet/chaindata/keystore"));
		//Incrementer inc = Incrementer.load(CONTRACT_ADDRESS, web3, credentials, GAS_PRICE, GAS_LIMIT);
		
		File dir = new File("/home/I331654/testnet/chaindata/keystore");
		File[] files = dir.listFiles((d, name) -> name.endsWith("Kshitiz"));
		System.out.println(files[0].getPath());
		
	}
}
