package controller;

import java.util.Random;
import java.util.Vector;

import model.Cart;

public class CartController {
	public static CartController controller = null;
	public Cart cart;
	private String errorMsg = "";
	String userID = UserController.getInstance().getCurrUser().getUserID();
	
	private CartController() {
		cart = new Cart();
	}
	
	public static CartController getInstance() {
		if(controller == null) {
			controller = new CartController();
		}
		return controller;
	}
	
	public Vector<Cart> getAllCartByUserID(){
		cart.setUserID(userID);
		return cart.getAllByUserID();
	}
	
	public Cart getCartByID(String productID){
		cart.setUserID(userID);
		cart.setProductID(productID);
		return cart.getCartByID();
	}
	
	public String getErrorMessage() {
		return errorMsg;
	}
	
	public boolean delete(String productID) {
		cart.setUserID(userID);
		cart.setProductID(productID);
		return cart.delete();
	}
	
	public int getTotalPrice() {
		int total = 0;
		Vector<Cart> carts = getAllCartByUserID();
		for (Cart cart : carts) {
			int productPrice = ProductController.getInstance().getProductByID(cart.getProductID()).getProductPrice();
			int qty = cart.getQty();
			total += productPrice * qty;
		}
		return total;
	}
	
	public boolean insertCart(String productID, int qty) {
		if(qty < 1) {
			errorMsg = "Quantity minimum is 1";
			return false;
		}else if(productID.equals("-")) {			
			errorMsg = "You must select the data on the table";
			return false;
		}else if(!ProductController.getInstance().decreaseStock(productID, qty)) {	
			errorMsg = ProductController.getInstance().getErrorMessage();
			return false;
		}else{
			cart = getCartByID(productID);
			if(cart == null) {
				cart = new Cart(userID, productID, qty);
				boolean inserted = cart.insert();
				
				if(inserted == false) {
					errorMsg = "Insert failed!";
				}
				return inserted;
			// kalau add to cart product yg sama bakal update quantitynya
			}else {
				int qty2 = cart.getQty();
				qty2 += qty;
				cart = new Cart(userID, productID, qty2);
				boolean updated = cart.updateQty();
				if(updated == false) {
					errorMsg = "Update failed!";
				}
				return updated;
			}
		}
	}
	
	public boolean checkout(String date) {
		Vector<Cart> carts = getAllCartByUserID();
		String transactionID = TransactionController.getInstance().generateID();
		if(TransactionController.getInstance().insertHeader(transactionID, userID, date)) {		
			for (Cart cart : carts) {
				if(!TransactionController.getInstance().insertDetail(transactionID, cart.getProductID(), cart.getQty())) {
					return false;
				}else {
					if(!delete(cart.getProductID())) {
						return false;
					}
				}
			}
		}
		return false;
	}

}
