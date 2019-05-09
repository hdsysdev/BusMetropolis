package orderOnStock.impl;

public class NoProductException extends Exception {
	String pid;
	
	public NoProductException(String pid) {
		this.pid = pid;
	}
	
	public String getPId() {
		return pid;
	}
}
