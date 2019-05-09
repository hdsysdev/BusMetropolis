package orderOnStock.impl;

public class NoOrderException extends Exception {
	String oid;
	
	public NoOrderException(String oid) {
		this.oid = oid;
	}
	
	public String getOId() {
		return oid;
	}
}
