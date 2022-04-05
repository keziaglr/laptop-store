package controller;

import java.util.Random;
import java.util.Vector;

import model.Brand;
import model.DetailTransaction;
import model.HeaderTransaction;
import view.ViewTransaction;

public class TransactionController {

	public static TransactionController controller = null;
	public HeaderTransaction headerTransaction;
	public DetailTransaction detailTransaction;
	private Random rand = new Random();
	
	private TransactionController() {
		headerTransaction = new HeaderTransaction();
		detailTransaction = new DetailTransaction();
	}
	
	public static TransactionController getInstance() {
		if(controller == null) {
			controller = new TransactionController();
		}
		return controller;
	}

	public Vector<HeaderTransaction> getAllHeader(){
		return headerTransaction.getAll();
	}
	
	public Vector<DetailTransaction> getAllDetail(){
		return detailTransaction.getAll();
	}
	
	public ViewTransaction viewTransaction() {
		return new ViewTransaction();
	}
	
	public Vector<HeaderTransaction> getAllHeaderByUserID(String userID){
		headerTransaction.setUserID(userID);
		return headerTransaction.getAllByUserID();
	}
	
	public Vector<DetailTransaction> getAllDetailByID(String transactionID){
		detailTransaction.setTransactionID(transactionID);
		return detailTransaction.getAllByID();
	}
	
	public boolean insertHeader(String transactionID, String userID, String transactionDate) {
		headerTransaction = new HeaderTransaction(transactionID, userID, transactionDate);
		return headerTransaction.insert();
	}
	
	public boolean insertDetail(String transactionID, String productID, int qty) {
		detailTransaction = new DetailTransaction(transactionID, productID, qty);
		return detailTransaction.insert();
	}
	
	public String generateID() {
		while (true) {			
			String id = "TR" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			return id;
		}
	}
}
