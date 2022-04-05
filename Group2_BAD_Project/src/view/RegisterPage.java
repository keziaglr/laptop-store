package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.UserController;
import model.User;


public class RegisterPage extends JFrame {
	
	String errorMsg ="";
	Random rand = new Random();
	String gender = "";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel centerPanel, southPanel, northPanel, genderPanel;
	JLabel titleLbl, usernameLbl, emailLbl, passLbl, genderLbl, addressLbl, space, space2;
	JTextField usernameField, emailField;
	JTextArea addressField;
	JPasswordField passField;
    JRadioButton radio1;
    JRadioButton radio2;
    ButtonGroup genderGroup = new ButtonGroup();
	JButton regButton, logButton, restButton;
	
	public void init() {
		
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(6,1));
		southPanel = new JPanel(new GridLayout(1,3));
		genderPanel = new JPanel(new GridLayout(1, 2));
		
		titleLbl = new JLabel("JKLAPTOP");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);
		
		radio1 = new JRadioButton("Male");
		radio2 = new JRadioButton("Female");
		genderGroup.add(radio1);
		genderGroup.add(radio2);

		space = new JLabel("");
		space2 = new JLabel("");
		usernameLbl = new JLabel("Username");
		emailLbl = new JLabel("E-mail");
		passLbl = new JLabel("Password");
		genderLbl = new JLabel("Gender");
		addressLbl = new JLabel("Address");
	
		restButton = new JButton("Reset");	
		restButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				usernameField.setText("");
				emailField.setText("");
				passField.setText("");
				genderGroup.clearSelection();
				radio1.setSelected(false);
				radio2.setSelected(false);
				addressField.setText("");	
			}

		});
		
		add(restButton);
		
		logButton = new JButton("Login");
		logButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				UserController.getInstance().viewLoginPage();
				
			}

		});
		
		add(logButton);
		
		regButton = new JButton("Register"); 	
		regButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = usernameField.getText();
				String email = emailField.getText();
				String password = passField.getText();
				String address = addressField.getText();
				String gender = "";
				if(radio1.isSelected()) {
					gender = "Male";
				}else if(radio2.isSelected()) {
					gender = "Female";
				}
				
				
				if(!UserController.getInstance().insert(username, email, password, gender, address, gender)) {
					JOptionPane.showMessageDialog(null, UserController.getInstance().getErrorMessage());
				}else{
					JOptionPane.showMessageDialog(null, "Register Success!");
					dispose();
					UserController.getInstance().viewLoginPage();
				}
			}

		});

		usernameField = new JTextField();
		emailField = new JTextField();
		addressField = new JTextArea();
		passField = new JPasswordField();
		
		genderPanel.add(radio1);
		genderPanel.add(radio2);
		
		northPanel.add(titleLbl);
		
		centerPanel.add(usernameLbl);
		centerPanel.add(usernameField);
		centerPanel.add(emailLbl);
		centerPanel.add(emailField);
		centerPanel.add(passLbl);
		centerPanel.add(passField);
		centerPanel.add(genderLbl);
		centerPanel.add(genderPanel);
		centerPanel.add(addressLbl);
		centerPanel.add(addressField);
		southPanel.add(space);
		southPanel.add(restButton);
		southPanel.add(space);
		southPanel.add(space2);
		southPanel.add(logButton);
		southPanel.add(space);
		southPanel.add(regButton);

		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
}
	public RegisterPage() {
		// TODO Auto-generated constructor stub
		init();
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

