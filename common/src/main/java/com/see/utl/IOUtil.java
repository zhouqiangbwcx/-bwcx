package com.see.utl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtil {
	public static String read(String pathName) throws Exception {
		InputStreamReader read = new InputStreamReader(new FileInputStream(
				pathName), "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		StringBuffer inputBuffer = new StringBuffer();
		while ((lineTxt = bufferedReader.readLine()) != null) {
			inputBuffer.append(lineTxt);
		}
		read.close();

		return inputBuffer.toString();
	}

	public static void write(String pathName, String inputData)
			throws Exception {
		BufferedWriter br = new BufferedWriter(new FileWriter(pathName));
		br.write(inputData);
		br.flush(); // 刷新缓冲区的数据到文件
		br.close();

	}

	// 从本地读取文件
	public static byte[] getBytes(String filename) throws Exception {
		File file = new File(filename);
		long len = file.length();

		byte raw[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(raw);
		if (r != len) {
			throw new IOException("Can't read all, " + r + " != " + len);
		}
		fin.close();

		return raw;
	}
}
