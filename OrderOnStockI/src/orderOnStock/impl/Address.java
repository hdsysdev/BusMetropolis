package orderOnStock.impl;

public class Address {
	String address;
	String postCode;
	String city;
	String country;
	
	public Address(String addr, String postCode, String city, String country) {
		this.address = addr;
		this.postCode = postCode;
		this.city = city;
		this.country = country;
	}
}
