package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.BrandController;
import controller.CartController;
import controller.ProductController;
import controller.UserController;
import model.Brand;
import model.Cart;
import model.Product;

public class CartForm extends JInternalFrame implements ActionListener {
	
	JPanel mainPanel, northPanel, centerPanel, southPanel, topPanel, bottomPanel, idPanel, idFieldPanel, usernamePanel, usernameFieldPanel, datePanel, dateFieldPanel, totalPricePanel, totalPriceFieldPanel;
	JLabel titleLabel, idLabel, idFieldLabel, usernameLabel, usernameFieldLabel, dateLabel, dateFieldLabel, totalPriceLabel, totalPriceFieldLabel, subtitleLabel;
	JTable productTable;
	JScrollPane tableScrollPane;
	JButton checkoutButton;
	Vector<Object> tableContent;
	

	public CartForm() {
		
		//Inisialisasi
		mainPanel = new JPanel(new BorderLayout());
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(2, 1));
		southPanel = new JPanel();
		topPanel = new JPanel(new GridLayout(2,4));
		bottomPanel = new JPanel(new BorderLayout());
		
		idPanel = new JPanel();
		idFieldPanel = new JPanel();
		usernamePanel = new JPanel();
		usernameFieldPanel = new JPanel();
		datePanel = new JPanel();
		dateFieldPanel = new JPanel();
		totalPricePanel = new JPanel();
		totalPriceFieldPanel = new JPanel();
		
		titleLabel = new JLabel("Cart");
		idLabel = new JLabel("User ID :");
		idFieldLabel = new JLabel("-");
		usernameLabel = new JLabel("Username :");
		usernameFieldLabel = new JLabel("-");
		dateLabel = new JLabel("Date  :");
		dateFieldLabel = new JLabel("-");
		totalPriceLabel = new JLabel("Total Price :");
		totalPriceFieldLabel = new JLabel("-");
		subtitleLabel= new JLabel("Detail");
		
		checkoutButton = new JButton("Check Out");
		
		//North Panel
		northPanel.add(titleLabel);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
		
		//Top Panel
		idPanel.add(idLabel);
		idFieldPanel.add(idFieldLabel);
		usernamePanel.add(usernameLabel);
		usernameFieldPanel.add(usernameFieldLabel);
		datePanel.add(dateLabel);
		dateFieldPanel.add(dateFieldLabel);
		totalPricePanel.add(totalPriceLabel);
		totalPriceFieldPanel.add(totalPriceFieldLabel);
		
		idPanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		idFieldPanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		usernamePanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		usernameFieldPanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		datePanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		dateFieldPanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		totalPricePanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		totalPriceFieldPanel.setBorder(new EmptyBorder(30, 30, 0, 0));
		
		topPanel.add(idPanel);
		topPanel.add(idFieldPanel);
		topPanel.add(usernamePanel);
		topPanel.add(usernameFieldPanel);
		topPanel.add(datePanel);
		topPanel.add(dateFieldPanel);
		topPanel.add(totalPricePanel);
		topPanel.add(totalPriceFieldPanel);
		
		idLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		idFieldLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		usernameLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		usernameFieldLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		dateLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		dateFieldLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		totalPriceLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		totalPriceFieldLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		
		//Bottom Panel
		productTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableScrollPane = new JScrollPane(productTable);
		tableScrollPane.setPreferredSize(new Dimension(750, 200));
		bottomPanel.add(subtitleLabel, BorderLayout.NORTH);
		subtitleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

		//Center Panel
		centerPanel.add(topPanel);
		centerPanel.add(bottomPanel);
		
		//South Panel
		southPanel.add(checkoutButton);
		checkoutButton.setFont(new Font("Calibri", Font.BOLD, 12));
		checkoutButton.setPreferredSize(new Dimension(600, 30));
		
		//Main Panel
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		//Add Listener
		checkoutButton.addActionListener(this);
		
		setInit();
		loadData();
		
		//Internal Frame
		add(mainPanel);
		setVisible(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setSize(600, 400);
		setTitle("Cart");
		
		//Frame
		setVisible(true);
		setTitle("Cart");
		setSize(1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void setInit() {
		idFieldLabel.setText(UserController.getInstance().getCurrUser().getUserID());
		usernameFieldLabel.setText(UserController.getInstance().getCurrUser().getUserName());
		dateFieldLabel.setText(""+java.time.LocalDate.now());
	}
	
	public void loadData() {
		String header[] = { "ProductID" , "ProductName" , "ProductPrice", "Qty"};
		DefaultTableModel dtm = new DefaultTableModel(header , 0);
		
		totalPriceFieldLabel.setText(""+CartController.getInstance().getTotalPrice());
		
		Vector<Cart> carts = CartController.getInstance().getAllCartByUserID();
		for (Cart cart : carts) {
			tableContent = new Vector<>();
			Product product = ProductController.getInstance().getProductByID(cart.getProductID());
			tableContent.add(product.getProductID());
			tableContent.add(product.getProductName());
			tableContent.add(product.getProductPrice());
			tableContent.add(cart.getQty());
			dtm.addRow(tableContent);
		}
		productTable.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == checkoutButton) {
			if(CartController.getInstance().checkout(dateFieldLabel.getText())) {
				JOptionPane.showMessageDialog(this, "Check Out Success", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}		
		loadData();
		
	}

}
