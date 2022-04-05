package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;

public class User {
	private String userID;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userGender;
	private String userAddress;
	private String userRole;
	private Connect con = Connect.getConnection();

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public User(String userID, String userName, String userEmail, String userPassword, String userGender,
			String userAddress, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userAddress = userAddress;
		this.userRole = userRole;
	}
	
	private User map(ResultSet rs) {
		String userID;
		String userName;
		String userEmail;
		String userPassword;
		String userGender;
		String userAddress;
		String userRole;
		
		try {
			userID = rs.getString("UserID");
			userName = rs.getString("UserName");
			userEmail = rs.getString("UserEmail");
			userPassword = rs.getString("UserPassword");
			userGender = rs.getString("UserGender");
			userAddress = rs.getString("UserAddress");
			userRole = rs.getString("UserRole");
			return new User(userID, userName, userEmail, userPassword, userGender, userAddress, userRole);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Vector<User> getAll(){
		String query = "SELECT * FROM User";
		ResultSet rs = con.executeQuery(query);
		Vector<User> users = new Vector<>();
		try {
			while(rs.next()) {
				User user = map(rs);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insert() {
		String query = String.format("INSERT INTO User VALUES (?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setString(1, userID);
			ps.setString(2, userName);
			ps.setString(3, userEmail);
			ps.setString(4, userPassword);
			ps.setString(5, userGender);
			ps.setString(6, userAddress);
			ps.setString(7, userRole);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User getUserByID(){
		String query = "SELECT * FROM User WHERE UserID=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			User user = null;
			if(rs.first()) {
				user = map(rs);
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByUsernamePassword(){
		String query = "SELECT * FROM User WHERE UserName=? AND UserPassword=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, userPassword);
			ResultSet rs = ps.executeQuery();
			User user = null;
			if(rs.first()) {
				user = map(rs);
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
