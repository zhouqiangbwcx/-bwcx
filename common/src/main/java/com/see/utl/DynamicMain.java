package com.see.utl;

import java.lang.reflect.Method;

public class DynamicMain {

	// public static void main(String[] args) throws Exception {
	//
	// while (true) {
	// DynamicClassLoader loader = new DynamicClassLoader();
	// Class<?> clazz = loader.loadClass("D:", "User");
	// Method method = clazz.getMethod("sayHello", String.class);
	// System.out.println(method.invoke(clazz.newInstance(), "Ken"));
	// // 每隔3秒钟重新加载
	// Thread.sleep(3000);
	// }
	// }

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		String path = "E:\\User.class";
		ManageClassLoader mc = new ManageClassLoader();
	
			Class clazz = mc.loadClass(path);
			Object o = clazz.newInstance();
			Method method = clazz.getMethod("sayHello", String.class);
			method.invoke(o, "adfasfs");
			System.out.println(clazz.getClassLoader());
	
	}

}
