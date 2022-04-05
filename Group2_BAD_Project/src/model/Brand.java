package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class Brand {
	private String brandID;
	private String brandName;
	private Connect con = Connect.getConnection();

	public Brand() {
		// TODO Auto-generated constructor stub
	}

	public String getBrandID() {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Brand(String brandID, String brandName) {
		super();
		this.brandID = brandID;
		this.brandName = brandName;
	}

	private Brand map(ResultSet rs) {
		String brandID;
		String brandName;
		
		try {
			brandID = rs.getString("BrandID");
			brandName = rs.getString("BrandName");
			return new Brand(brandID, brandName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Brand> getAll(){
		String query = "SELECT * FROM Brand";
		ResultSet rs = con.executeQuery(query);
		Vector<Brand> brands = new Vector<>();
		try {
			while(rs.next()) {
				Brand brand = map(rs);
				brands.add(brand);
			}
			return brands;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Brand getBrandByID(){
		String query = "SELECT * FROM Brand WHERE BrandID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, brandID);
			ResultSet rs = ps.executeQuery();
			Brand brand = null;
			if(rs.first()) {
			    brand = map(rs);
			}
			return brand;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Brand getBrandByName(){
		String query = "SELECT * FROM Brand WHERE BrandName=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, brandName);
			ResultSet rs = ps.executeQuery();
			Brand brand = null;
			if(rs.first()) {
			    brand = map(rs);
			}
			return brand;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert() {
		String query = String.format("INSERT INTO Brand VALUES (?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, brandID);
			ps.setString(2, brandName);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update() {
		String query = String.format("UPDATE Brand SET BrandName=? WHERE BrandID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, brandName);
			ps.setString(2, brandID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM Brand WHERE BrandID=?");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, brandID);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
