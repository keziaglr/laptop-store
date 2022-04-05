package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class HeaderTransaction {
	private String transactionID;
	private String userID;
	private String transactionDate;
	private Connect con = Connect.getConnection();

	public HeaderTransaction() {
		// TODO Auto-generated constructor stub
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public HeaderTransaction(String transactionID, String userID, String transactionDate) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.transactionDate = transactionDate;
	}
	
	private HeaderTransaction map(ResultSet rs) {
		String transactionID;
		String userID;
		String transactionDate;
		
		try {
			transactionID = rs.getString("TransactionID");
			userID = rs.getString("UserID");
			transactionDate = rs.getString("TransactionDate");
			return new HeaderTransaction(transactionID, userID, transactionDate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<HeaderTransaction> getAll(){
		String query = "SELECT * FROM HeaderTransaction";
		ResultSet rs = con.executeQuery(query);
		Vector<HeaderTransaction> transactions = new Vector<>();
		try {
			while(rs.next()) {
				HeaderTransaction transaction = map(rs);
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<HeaderTransaction> getAllByUserID(){
		String query = "SELECT * FROM HeaderTransaction WHERE UserID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			Vector<HeaderTransaction> transactions = new Vector<>();
			while(rs.next()) {
				HeaderTransaction transaction = map(rs);
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
		String query = String.format("INSERT INTO HeaderTransaction VALUES (?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, transactionID);
			ps.setString(2, userID);
			ps.setString(3, transactionDate);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
