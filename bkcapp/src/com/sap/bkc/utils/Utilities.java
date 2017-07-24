package com.sap.bkc.utils;

import java.math.BigInteger;
import java.time.Instant;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;

public class Utilities {

	public static Utf8String getUTF8String(String str) {
		return new Utf8String(str);
	}
	public static Utf8String getUTF8String(long num) {
		return new Utf8String(Long.toString(num));
	}
	public static Uint256 getUint256(long num) {
		return new Uint256(num);
	}
	
	public static Uint256 getUint256(String num) {
		return new Uint256(new BigInteger(num));
	}
	public static Uint8 getUint8(String num) {
		return new Uint8(new BigInteger(num));
	}
	public static Uint8 getUint8(long num) {
		return new Uint8(new BigInteger(Long.toString(num)));
	}
	public static String getStringfromAddress(Address addr) {
		return addr.toString();
	}
	
	public static String getStringTimestampfromEpochSecond(long epochSecond) {
		return Instant.ofEpochSecond(epochSecond).toString();
	}
	
	
}
