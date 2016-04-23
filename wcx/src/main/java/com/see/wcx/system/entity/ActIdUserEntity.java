package com.see.wcx.system.entity;

public class ActIdUserEntity {
	
	//id
	private  String id;

/**
 * 姓
 */
	private  String firstName;

	/**
	 * 名
	 */  
	private  String lastName;
/**
 * 邮件
 */
	private  String email;

	  /**
	   * 密码
	   */
	private  String password; 

	  
	private  boolean isPictureSet;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isPictureSet() {
		return isPictureSet;
	}


	public void setPictureSet(boolean isPictureSet) {
		this.isPictureSet = isPictureSet;
	}
	
	
}
