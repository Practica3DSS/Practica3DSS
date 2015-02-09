package com.recypapp.recypapp.Comunications.data;

public class UserLoggin {
	private String email;
	private String password;
	
	UserLoggin(){
		
	}

	public UserLoggin(String email, String password) {
		this.email = email;
		this.password = password;
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
}
