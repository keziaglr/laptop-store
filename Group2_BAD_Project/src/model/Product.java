package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class Product {
	private String productID;
	private String brandID;
	private String productName;
	private int productPrice;
	private int productStock;
	private int productRating;
	private Connect con = Connect.getConnection();

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String productID, String brandID, String productName, int productPrice, int productStock,
			int productRating) {
		super();
		this.productID = productID;
		this.brandID = brandID;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
		this.productRating = productRating;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getBrandID() {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public int getProductRating() {
		return productRating;
	}

	public void setProductRating(int productRating) {
		this.productRating = productRating;
	}
	
	private Product map(ResultSet rs) {
		String productID;
		String brandID;
		String productName;
		int productPrice;
		int productStock;
		int productRating;
		
		try {
			productID = rs.getString("ProductID");
			brandID = rs.getString("BrandID");
			productName = rs.getString("ProductName");
			productPrice = rs.getInt("ProductPrice");
			productStock = rs.getInt("ProductStock");
			productRating = rs.getInt("ProductRating");
			return new Product(productID, brandID, productName, productPrice, productStock, productRating);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Product> getAll(){
		String query = "SELECT * FROM Product";
		ResultSet rs = con.executeQuery(query);
		Vector<Product> products = new Vector<>();
		try {
			while(rs.next()) {
				Product product = map(rs);
				products.add(product);
			}
			return products;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert() {
		String query = String.format("INSERT INTO Product VALUES (?, ?, ?, ?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, productID);
			ps.setString(2, brandID);
			ps.setString(3, productName);
			ps.setInt(4, productPrice);
			ps.setInt(5, productStock);
			ps.setInt(6, productRating);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update() {
		String query = String.format("UPDATE Product SET BrandID=? , ProductName=? , ProductPrice=? , ProductStock=? , ProductRating=? WHERE ProductID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, brandID);
			ps.setString(2, productName);
			ps.setInt(3, productPrice);
			ps.setInt(4, productStock);
			ps.setInt(5, productRating);
			ps.setString(6, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM Product WHERE ProductID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateStock() {
		String query = String.format("UPDATE Product SET ProductStock=? WHERE ProductID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, productStock);
			ps.setString(2, productID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
	public Product getProductByID(){
		String query = "SELECT * FROM Product WHERE ProductID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, productID);
			ResultSet rs = ps.executeQuery();
			Product product = null;
			if(rs.first()) {
				product = map(rs);
			}
			return product;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
