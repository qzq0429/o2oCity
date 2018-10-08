package com.qzq.haha.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
   //与接口配置信息中的Token要一致
	private static String token = "o2oCity";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token, timestamp, nonce};
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		MessageDigest mDigest = null;
		String tmpStr = null;
		
		try {
			mDigest=MessageDigest.getInstance("SHA-1");
			byte[] digest = mDigest.digest(content.toString().getBytes());
		    tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = null;
		return tmpStr!=null ? tmpStr.equals(signature.toUpperCase()):false;
	}

	private static String byteToStr(byte[] byteArray) {
		// TODO Auto-generated method stub
		String strDigest = "";
		for(int i = 0;i<byteArray.length;i++){
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		// TODO Auto-generated method stub
		char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};		
		char[] tempArr = new char[2];
		
		tempArr[0] = Digit[(mByte >>> 4)& 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		
		String s = new String(tempArr);
		return s;
	}
	
}
