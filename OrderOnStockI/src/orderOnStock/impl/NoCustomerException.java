package orderOnStock.impl;

public class NoCustomerException extends Exception {
	String email;
	
	public NoCustomerException(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
}
