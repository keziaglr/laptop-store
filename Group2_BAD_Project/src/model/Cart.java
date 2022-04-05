package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class Cart {
	private String userID;
	private String productID;
	private int qty;
	private Connect con = Connect.getConnection();

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public Cart(String userID, String productID, int qty) {
		super();
		this.userID = userID;
		this.productID = productID;
		this.qty = qty;
	}
	
	private Cart map(ResultSet rs) {
		String userID;
		String productID;
		int qty;
		
		try {
			userID = rs.getString("UserID");
			productID = rs.getString("ProductID");
			qty = rs.getInt("Qty");
			return new Cart(userID, productID, qty);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Cart> getAllByUserID(){
		String query = "SELECT * FROM Cart WHERE UserID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			Vector<Cart> carts = new Vector<>();
			while(rs.next()) {
				Cart cart = map(rs);
				carts.add(cart);
			}
			return carts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Cart getCartByID(){
		String query = "SELECT * FROM Cart WHERE UserID=? AND ProductID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userID);
			ps.setString(2, productID);
			ResultSet rs = ps.executeQuery();
			if(rs.first()) {
				return map(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateQty() {
		String query = String.format("UPDATE Cart SET Qty=? WHERE UserID=? AND ProductID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, qty);
			ps.setString(2, userID);
			ps.setString(3, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean insert() {
		String query = String.format("INSERT INTO Cart VALUES (?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, userID);
			ps.setString(2, productID);
			ps.setInt(3, qty);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM Cart WHERE UserID=? AND ProductID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, userID);
			ps.setString(2, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
