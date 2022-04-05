package controller;

import java.util.Random;

import model.Product;
import model.User;
import view.LoginPage;
import view.MainFormAdmin;
import view.MainFormUser;
import view.RegisterPage;

public class UserController {

	private User currUser;
	public static UserController controller = null;
	public User user;
	private Random rand = new Random();
	private String errorMsg = "";
	MainFormUser currentMainForm;
	
	private UserController() {
		user = new User();
		currUser = getUserByID("US001");
	}
	
	public static UserController getInstance() {
		if(controller == null) {
			controller = new UserController();
		}
		return controller;
	}
	
	public MainFormUser getMainForm() {
		return currentMainForm;
	}
	
	public User getUserByID(String userID){
		user.setUserID(userID);
		return user.getUserByID();
	}
	
	public User getUserByUsernamePassword(String username, String password){
		user.setUserName(username);
		user.setUserPassword(password);
		return user.getUserByUsernamePassword();
	}
	
	public User getCurrUser(){
		return currUser;
	}
	
	public String generateID() {
		while (true) {			
			String id = "US" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			if(getUserByID(id) == null) {
				return id;
			}
		}
	}
	
	public String getErrorMessage() {
		return errorMsg;
	}
	
	public boolean auth(String username, String password) {
		if (username.isEmpty()) {
			errorMsg = "Username Field must be filled";
			return false;			
		}else if(password.isEmpty()){
		     errorMsg = "Password Field must be filled";
		     return false;
	    }else if (getUserByUsernamePassword(username, password) == null) {
	    	errorMsg = "Inputted Username and Password is invalid";
	    	return false;			
		}else {
			currUser = getUserByUsernamePassword(username, password);
			return true;
		}
	}
	
	public void viewMainForm() {
		if(UserController.getInstance().getCurrUser().getUserRole().equals("Admin")) {
			new MainFormAdmin();
		}else if(UserController.getInstance().getCurrUser().getUserRole().equals("User")) {
			currentMainForm = new MainFormUser();
		}
	}
	
	public void viewLoginPage() {
		new LoginPage();
	}
	
	public void viewRegisterPage() {
		new RegisterPage();
	}
	
	public boolean insert(String userName , String userEmail , String userPassword , String userGender , String userAddress, String userRole) {
		if(userName.length() < 5 || userName.length() > 20) {
			errorMsg = "Username length must between 5 and 20 characters";
			return false;
		}else if(userEmail.isEmpty()) {
			errorMsg = "Email Field must be fill!";
			return false;
		// validasi tambahan supaya format emailnya benar
		}else if(!userEmail.contains("@")|| !userEmail.contains(".")) {
			errorMsg = "Email input must contains '@' and '.' ";
			return false;
		}else if(userEmail.contains("@.") || userEmail.contains(".@")) {
			errorMsg = "Email character '@' must not be next to'.'";
			return false;
		}else if(userEmail.startsWith("@") || userEmail.startsWith(".")) {
			errorMsg = "Email input must not starts with '@' or '.'";
			return false;
		}else if(countChar(userEmail, '@') > 1 || countChar(userEmail, '.') > 1) {
			System.out.println();
			errorMsg = "Email must not contains more than 1 '@' or '.'";
			return false;
		}else if(!userEmail.endsWith(".com")) {
			errorMsg = "Email input must end with '.com'";
			return false;
		}else if(!isAlphaNum(userPassword)) {
			errorMsg = "Password must alphanumeric";
			return false;
		}else if(userGender.equals("")) {
			errorMsg = "One gender must be selected";
			return false;
		}else if(!userAddress.endsWith("Street")) {
			errorMsg = "Address must ends with 'Street'";
			return false;
		}else if(userName.isEmpty()) {
			errorMsg = "Username field must fill!";
			return false;
		}else if(userPassword.isEmpty()) {
			errorMsg = "Password Field must be fill!";
			return false;
		}else if(userAddress.isEmpty()) {
			errorMsg = "Address Field must be fill!";
			return false;
		}else if(!userAddress.endsWith("Street")) {
			errorMsg = "Address must ends with 'Street'";
			return false;
		}else {
			user = new User(generateID(), userName, userEmail, userPassword, userGender, userAddress, "User");
			boolean inserted = user.insert();
			
			if(inserted == false) {
				errorMsg = "Insert failed!";
			}
			return inserted;
		}
	}
	
	private int countChar(String email, char chars) {
		int count = 0;
		for (int i = 0; i < email.length() ; i++) {
			char c = email.charAt(i);
			if(c == chars) count++;
		}
		return count;
	}
	
    private boolean isAlphaNum(String name) {
        boolean alpha = false, num = false;
        for (int i = 0 ; i < name.length() ; i++) {
            char c = name.charAt(i);
            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                alpha = true;
            }else if(c >= '0' && c <= '9') {
                num = true;
            }
        }
        return alpha && num;
    }

}
