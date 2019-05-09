package orderOnStock.impl;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	String name, email, pw;
	Address address;
	List<Order> orders;
	
	public Customer (String name, Address addr, String email, String pw) {
		this.name = name;
		this.address = addr;
		this.email = email;
		this.pw = pw;
		
		orders = new ArrayList<Order>();
	}
	
	public String getName() {
		return  name;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return pw;
	}
	
	public void addOrder(Order o) {
		orders.add(o);
	}
	
	public boolean hasOrder(Order o) {
		return orders.contains(o);
	}
	
	public void cancelOrder (Order o) {
		orders.remove(o);
	}
}
