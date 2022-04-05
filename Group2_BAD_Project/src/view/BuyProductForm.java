package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import controller.BrandController;
import controller.CartController;
import controller.ProductController;
import controller.UserController;
import model.Brand;
import model.Product;

public class BuyProductForm extends JInternalFrame implements MouseListener, ActionListener{

	JPanel mainPanel, northPanel, centerPanel, southPanel, tablePanel, labelPanel;
	JScrollPane tableScrollPane;
	JLabel titleLabel, idLabel, nameLabel, priceLabel, brandLabel, quantityLabel, ratingLabel, selectIdLabel, selectNameLabel, selectPriceLabel, selectBrandLabel, selectRatingLabel;
	JTable productTable;
	JSpinner quantitySpinner;
	JButton addButton;
	String string;
	Vector<Object> tableContent;
	
	public BuyProductForm() {
		
		//Inisialisasi
		mainPanel = new JPanel(new BorderLayout());
		northPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(2, 1));
		southPanel = new JPanel();
		tablePanel = new JPanel();
		labelPanel = new JPanel(new GridLayout(6, 2));
		
		titleLabel = new JLabel("Our Product");
		idLabel = new JLabel("Product ID");
		nameLabel = new JLabel("Product Name");
		priceLabel = new JLabel("Product Price");
		brandLabel = new JLabel("Product Brand");
		quantityLabel = new JLabel("Quantity");
		ratingLabel = new JLabel("Rating");
		
		selectIdLabel = new JLabel("-");
		selectNameLabel = new JLabel("-");
		selectPriceLabel = new JLabel("-");
		selectBrandLabel = new JLabel("-");
		selectRatingLabel = new JLabel("-");
		
		quantitySpinner = new JSpinner();		
		addButton = new JButton("Add to Cart");
		
		//North Panel
		northPanel.add(titleLabel);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
		
		//Table Panel
		productTable = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableScrollPane = new JScrollPane(productTable);
		tableScrollPane.setPreferredSize(new Dimension(750, 200));
		tablePanel.add(tableScrollPane);
		loadData();
		
		//Label Panel
		labelPanel.add(idLabel);
		labelPanel.add(selectIdLabel);
		labelPanel.add(nameLabel);
		labelPanel.add(selectNameLabel);
		labelPanel.add(priceLabel);
		labelPanel.add(selectPriceLabel);
		labelPanel.add(quantityLabel);
		labelPanel.add(quantitySpinner);
		labelPanel.add(ratingLabel);
		labelPanel.add(selectRatingLabel);
		
		//Center Panel
		centerPanel.add(tablePanel);
		centerPanel.add(labelPanel);
		
		//South Panel
		southPanel.add(addButton);
		addButton.setFont(new Font("Calibri", Font.BOLD, 12));
		addButton.setPreferredSize(new Dimension(100, 30));
		
		//Main Panel
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		//Add Listener
		productTable.addMouseListener(this);
		addButton.addActionListener(this);
		
		setInit();
		
		//Internal Frame
		add(mainPanel);
		setVisible(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setSize(800, 600);
		setTitle("Buy Product");
		
		//Frame
		setVisible(true);
		setTitle("Home Frame");
		setSize(1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private void setInit() {
		selectIdLabel.setText("-");
		selectNameLabel.setText("-");
		selectPriceLabel.setText("-");
		selectBrandLabel.setText("-");
		selectRatingLabel.setText("-");
		quantitySpinner.setValue(0);	
	}

	public void loadData() {
		String header[] = { "ProductID" , "BrandName" , "ProductName" , "ProductPrice", "ProductQuantity", "ProductRating"};
		DefaultTableModel dtm = new DefaultTableModel(header , 0);
		
		Vector<Product> products = ProductController.getInstance().getAll();
		for (Product product : products) {
			tableContent = new Vector<>();
			tableContent.add(product.getProductID());
			tableContent.add(BrandController.getInstance().getBrandByID(product.getBrandID()).getBrandName());
			tableContent.add(product.getProductName());
			tableContent.add(product.getProductPrice());
			tableContent.add(product.getProductStock());
			tableContent.add(product.getProductRating());
			dtm.addRow(tableContent);
		}
		productTable.setModel(dtm);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			try {
				quantitySpinner.commitEdit();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int qty =  (int) quantitySpinner.getValue();
			String productID = selectIdLabel.getText();
			
			boolean insert = CartController.getInstance().insertCart(productID, qty);
			if(insert == false) {
				JOptionPane.showMessageDialog(null, CartController.getInstance().getErrorMessage());
			}else {
				JOptionPane.showMessageDialog(null, "Insert Success");
				setInit();
				loadData();
				UserController.getInstance().getMainForm().changeCart();
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == productTable) {
			String id = productTable.getValueAt(productTable.getSelectedRow(), 0).toString();
			String brand = productTable.getValueAt(productTable.getSelectedRow(), 1).toString();
			String name = productTable.getValueAt(productTable.getSelectedRow(), 2).toString();
			String price = productTable.getValueAt(productTable.getSelectedRow(), 3).toString();
			String rating = productTable.getValueAt(productTable.getSelectedRow(), 5).toString();
			
			selectIdLabel.setText(id);
			selectNameLabel.setText(name);
			selectPriceLabel.setText(price);
			selectBrandLabel.setText(brand);
			selectRatingLabel.setText(rating);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
