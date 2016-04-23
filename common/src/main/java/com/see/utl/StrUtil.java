package com.see.utl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StrUtil {
	public String inputStreamString(FileInputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while (null != (line = in.readLine())) {
				buffer.append(line);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return buffer.toString();
	}

	private String key = "";

	private String decrypt(String inputStr) {
		String outputStr = "";
		try {

			byte[] inputData = inputStr.getBytes();
			inputData = DESCoder.encrypt(inputData, key);

			System.err.println("加密后:\t" + DESCoder.encryptBASE64(inputData));

			byte[] outputData = DESCoder.decrypt(inputData, key);
			outputStr = new String(outputData);

			System.err.println("解密后:\t" + outputStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return outputStr;
	}
}
