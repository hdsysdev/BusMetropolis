package orderOnStock.impl;

import java.time.LocalDate;
import java.util.Date;

public class Order {
	private static int last_id = 0;
	
	String oid;
	OrderStatus status;
	LocalDate orderDt, invoiceDt;
	int qty;
	Product product;
	Customer customer;
	
	public Order(int qty, Product p, Customer c) throws IllegalArgumentException{
		
		if (qty<0)
			throw new IllegalArgumentException("Negative quantity!");
		
		last_id++;
		oid = "O"+ last_id; 
		this.qty = qty;
		this.status = OrderStatus.pending;
		orderDt =  LocalDate.now();
		invoiceDt = null;
		this.product = p;
		this.customer = c;
	}
	
	public String getOid() {
		return oid;
	}
	
	public int getQty() {
		return qty;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public LocalDate getOrderDt() {
		return orderDt;
	}
	
	public LocalDate getInvoiceDt() {
		return invoiceDt;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void invoice() throws NotEnoughStockException, IllegalStateException {
		
		if (status != OrderStatus.pending)
			throw new IllegalStateException("State status of the order is not pending");
		product.deductStock(qty);
		
		this.status = OrderStatus.invoiced;
		this.invoiceDt = LocalDate.now();
	}
	
	public void cancel() throws IllegalStateException {
		
		if (status != OrderStatus.pending)
			throw new IllegalStateException("Order is not pending");
		
		product = null;
		customer.cancelOrder(this);
		customer = null;
	}
}
