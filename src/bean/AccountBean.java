package bean;

import java.io.Serializable;


public class AccountBean implements Serializable {
	private String familyName;
	private String firstName;
	private String postal;
	private String address;
	private String tel;
	private String email;
	private String password;

	public AccountBean (String familyName, String firstName, String postal, String address,
			String tel, String email, String password) {
		this.familyName = familyName;
		this.firstName = firstName;
		this.postal = postal;
		this.address = address;
		this.tel = tel;
		this.email = email;

		this.password = password;
		
	}
	
	public AccountBean (String familyName, String firstName, String postal, String address,
			String tel, String password) {
		this.familyName = familyName;
		this.firstName = firstName;
		this.postal = postal;
		this.address = address;
		this.tel = tel;
		this.password = password;
		
	}
	
	public AccountBean () {
		
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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