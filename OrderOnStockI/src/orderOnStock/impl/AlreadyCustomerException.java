package orderOnStock.impl;


public class AlreadyCustomerException extends Exception {
	private final String email;
	
	public AlreadyCustomerException(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
}
