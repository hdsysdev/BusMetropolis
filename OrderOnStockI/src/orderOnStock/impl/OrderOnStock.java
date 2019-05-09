package orderOnStock.impl;
import java.util.HashMap;
import java.util.Map;

public class OrderOnStock {
	Map<String, Customer> customers;
	Map<String, Product> products;
	Map<String, Order> orders;
	
	public OrderOnStock () {
		customers = new HashMap<String, Customer>();
		products = new HashMap<String, Product>();
		orders = new HashMap<String, Order>();
	}
	
	public void registerCustomer(String name, Address addr, String email, String pw) throws AlreadyCustomerException {
		if (customers.containsKey(email))
			throw new AlreadyCustomerException(email);
		
		Customer c = new Customer(name, addr, email, pw);
		
		customers.put(email, c);
	}
	
	public Customer findCustomer (String email) {
		return customers.get(email);
	}
	
	public Product findProduct (String pid) {
		return products.get(pid);
	}
	
	public Order findOrder (String oid) {
		return orders.get(oid);
	}
	
	public String addProduct (String desc,  UnitKind unit, int stock, int price) {
		
		Product p = new Product (desc, unit, stock, price);
		
		products.put(p.getPID(), p);
		
		return p.getPID();
	}

	public String orderProduct(String email, String pid, int qty) 
			throws NoProductException, NoCustomerException {
		Customer c = findCustomer(email);
		Product p = findProduct (pid);
		
		if (c == null) 
			throw new NoCustomerException(email);
		
		if (p == null) 
			throw new NoProductException(pid);
		
		Order o = new Order(qty, p, c);
		
		c.addOrder(o);
		
		orders.put(o.getOid(), o);
		
		return o.getOid();
		
	}
	
	public void invoiceOrder(String oid) 
			throws NoOrderException, NotEnoughStockException, IllegalStateException {
		
		Order o = findOrder(oid);
		
		
		if (o == null) {
			throw new NoOrderException(oid);
		}
		
		o.invoice();
	}
	
	public void cancelOrder(String oid) 
			throws NoOrderException, IllegalStateException {
		
		Order o = findOrder(oid);
		
		if (o == null) {
			throw new NoOrderException(oid);
		}
		
		o.cancel();
		
		orders.remove(o.getOid());
	}
	
	public void updateStock(String pid, int qty) throws NoProductException {
		Product p = findProduct(pid);
		
		if (p == null) 
			throw new NoProductException(pid);
		
		p.updateStock(qty);
	}
}
