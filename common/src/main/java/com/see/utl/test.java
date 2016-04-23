package com.see.utl;



public class test {

	public static void main(String[] args) throws Exception {
		String key = "umF1itDagHw=";
		// 读取
		String pathName = "D:\\User.class";
		String pathNamea = "E:\\User.class";

		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		// String inputStr = read(pathName);
		// System.err.println("原来文:\t" + inputStr);

		byte[] inputData = IOUtil.getBytes(pathName);
		inputData = DESCoder.encrypt(inputData, key);
		System.err.println("加密后:\t" + new String(inputData));

		byte[] outputData = DESCoder.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);
		// 写入
		IOUtil.write(pathNamea, DESCoder.encryptBASE64(inputData));
		System.out.println("+++++++++++++++++++++++++++++++");
		// 文件解密开始
		byte[] inputStrFile = DESCoder.decryptBASE64(new String (IOUtil.getBytes(pathNamea)));

		byte[] outputDataFile = DESCoder.decrypt(inputStrFile, key);
		String outputStrFile = new String(outputDataFile);

		System.err.println("解密后:\t" + outputStrFile);
	}

}
