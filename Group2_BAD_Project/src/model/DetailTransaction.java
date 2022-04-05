package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class DetailTransaction {
	private String transactionID;
	private String productID;
	private int qty;
	private Connect con = Connect.getConnection();

	public DetailTransaction() {
		// TODO Auto-generated constructor stub
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public DetailTransaction(String transactionID, String productID, int qty) {
		super();
		this.transactionID = transactionID;
		this.productID = productID;
		this.qty = qty;
	}
	
	private DetailTransaction map(ResultSet rs) {
		String transactionID;
		String productID;
		int qty;
		
		try {
			transactionID = rs.getString("TransactionID");
			productID = rs.getString("ProductID");
			qty = rs.getInt("Qty");
			return new DetailTransaction(transactionID, productID, qty);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<DetailTransaction> getAll(){
		String query = "SELECT * FROM DetailTransaction";
		ResultSet rs = con.executeQuery(query);
		Vector<DetailTransaction> transactions = new Vector<>();
		try {
			while(rs.next()) {
				DetailTransaction transaction = map(rs);
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<DetailTransaction> getAllByID(){
		String query = "SELECT * FROM DetailTransaction WHERE TransactionID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, transactionID);
			ResultSet rs = ps.executeQuery();
			Vector<DetailTransaction> transactions = new Vector<>();
			while(rs.next()) {
				DetailTransaction transaction = map(rs);
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert() {
		String query = String.format("INSERT INTO DetailTransaction VALUES (?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, transactionID);
			ps.setString(2, productID);
			ps.setInt(3, qty);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
