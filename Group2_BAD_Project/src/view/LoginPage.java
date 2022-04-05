package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;

public class LoginPage extends JFrame{
	JPanel centerPanel, southPanel, northPanel;
	JLabel titleLbl, usernameLbl, passLbl, space;
	JTextField usernameField;
	JPasswordField passField;
	JButton SubmitButton, RegisterButton;
	
	public void init() {
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(2,2));
		southPanel = new JPanel(new GridLayout(1,3));
		
		titleLbl = new JLabel("Login");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 30)); 	
		titleLbl.setForeground(Color.BLACK);
		
		usernameLbl = new JLabel("Username");
		passLbl = new JLabel("Password");
		space = new JLabel("");
		
		RegisterButton = new JButton("Register");	
		RegisterButton.setForeground(Color.BLACK);
		RegisterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				UserController.getInstance().viewRegisterPage();
			}

		});
		
		add(RegisterButton);
		
		usernameField = new JTextField();
		passField = new JPasswordField();
		
		SubmitButton = new JButton("Submit");
		SubmitButton.setForeground(Color.BLACK);
		SubmitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String username = usernameField.getText();
				String password = passField.getText();
				if(!UserController.getInstance().auth(username, password)){
					JOptionPane.showMessageDialog(null, UserController.getInstance().getErrorMessage());
				} else {
					dispose();
					UserController.getInstance().viewMainForm();
				}
			}
		});
		
		northPanel.add(titleLbl);
		
		centerPanel.add(usernameLbl);
		centerPanel.add(usernameField);
		centerPanel.add(passLbl);
		centerPanel.add(passField);
		
		southPanel.add(RegisterButton);
		southPanel.add(space);
		southPanel.add(SubmitButton);
		
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public LoginPage() {
		// TODO Auto-generated constructor stub
		init();
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
