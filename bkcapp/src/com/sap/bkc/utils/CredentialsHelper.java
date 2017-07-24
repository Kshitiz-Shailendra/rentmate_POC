package com.sap.bkc.utils;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class CredentialsHelper {

	private static final String WALLET_DIR = "";
	private static final String DEFAULT_USER = "";
	private static final String DEFAULT_PASS = "";
	
	private CredentialsHelper () {}
	
	public static Credentials getDefaultCredentials() {
		
		ServletRequestAttributes reqAttr = (ServletRequestAttributes)   RequestContextHolder.currentRequestAttributes();
		HttpSession session = reqAttr.getRequest().getSession();
		System.out.println("address : "+((Credentials)session.getAttribute("creds")).getAddress());
		return (Credentials)session.getAttribute("creds");
		//return getCredentials(DEFAULT_USER, DEFAULT_PASS);
	}
	
	public static String getCurrentUserAddress() {
		return getDefaultCredentials().getAddress();
	}
	/**
	 * Returns the credentials for accessing the blockchain node by accessing the
	 * user wallet.
	 * 
	 * @param user
	 *            the blockchain address of a user associated with a wallet
	 * @param pass
	 *            the wallet pass
	 * @return A new Credentials instance
	 */
	public static Credentials getCredentials(String user, String pass) {
		try {
			return WalletUtils.loadCredentials(pass, getWalletFile(user));
			//return WalletUtils.loadCredentials("Tocsalp1989#", "/home/I331654/testnet/chaindata/keystore/UTC--2017-06-08T17-02-12.219450655Z--a0a383b16e8e2dbeb0c714788779294339380360");
		} catch (Exception x) {
			throw new IllegalStateException("Error loading credentials from wallet " + WALLET_DIR, x);
		}
	}
	
	
	/*public static Credentials getCredentialsInstance(String user,String pass) {
		
		return null;
		
	}*/
	private static File getWalletFile(String user) {
		File dir = new File(getDefaultTestnetDirectory());
		File[] files = dir.listFiles((d, name) -> name.endsWith(user));
		return new File(files[0].getPath());
	}
	
	private static String getDefaultTestnetDirectory() {
		return String.format(
                "%s%stestnet%schaindata%skeystore", System.getProperty("user.home"), File.separator, File.separator,File.separator);
	}
	
	/*public static void main(String[] args) {
		Credentials creds = CredentialsHelper.getCredentials("Kshitiz", "kshitizone");
		System.out.println("wallet address : "+creds.getAddress());
	}
	*/
}
