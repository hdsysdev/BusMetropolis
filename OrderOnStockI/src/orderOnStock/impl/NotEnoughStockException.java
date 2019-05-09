package orderOnStock.impl;

public class NotEnoughStockException extends Exception {
	int stock, qty;
	
	public NotEnoughStockException(int sock, int qty) {
		this.stock = stock;
		this.qty = qty;
	}
}
