package com.see.utl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * 动态加载class文件
 * 
 * @author Ken
 * @since 2013-02-17
 * 
 */

public class DynamicClassLoader extends ClassLoader {

	public Class<?> findClass(byte[] b) throws ClassNotFoundException {

		return defineClass(null, b, 0, b.length);
	}
}