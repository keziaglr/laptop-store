package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.BrandController;
import controller.ProductController;
import model.Brand;
import model.Product;

public class ManageProduct extends JInternalFrame implements ActionListener{

	JPanel mainPanel, titlePanel, northPanel, formPanel, labelPanel, fieldPanel, buttonPanel;
	JTable table;
	JLabel titleLbl, idLbl, nameLbl, priceLbl, ratingLbl, stockLbl, brandLbl;
	JTextField idField, nameField, priceField;
	JSpinner ratingSpinner, stockSpinner;
	JComboBox brandComboBox;
	JButton insertBtn, updateBtn, deleteBtn, submitBtn, cancelBtn;
	JScrollPane scrollPane;
	Vector<Object> tableContent;
	Vector<String> brandName;
	String action = "";
	Random rand = new Random();
	
	public ManageProduct() {
		// TODO Auto-generated constructor stub
		brandName = new Vector<>();
		brandName.add("");
		for (String string : BrandController.getInstance().getAllBrandName()) {
			brandName.add(string);
		}
		initialize();
		setTitle("Manage Product");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setVisible(true);
	}
	
	private void initialize() {

		mainPanel = new JPanel(new GridLayout(2, 1));
		formPanel = new JPanel(new GridLayout(1, 3));
		labelPanel = new JPanel(new GridLayout(6, 1));
		fieldPanel = new JPanel(new GridLayout(6, 1));
		buttonPanel = new JPanel(new GridLayout(5, 1));
		northPanel = new JPanel(new GridLayout(2, 1));
		titlePanel = new JPanel();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 32, 500, 190);

		table = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		titleLbl = new JLabel("Product List");
		titleLbl.setFont(new Font("Calibri", Font.BOLD, 20)); 	
		titlePanel.add(titleLbl, BorderLayout.CENTER);
		northPanel.add(titlePanel);
		northPanel.add(scrollPane);
		
		idLbl = new JLabel("ProductID");
		nameLbl = new JLabel("Product Name");
		priceLbl = new JLabel("Product Price");
		ratingLbl = new JLabel("Product Rating");
		stockLbl = new JLabel("Product Stock");
		brandLbl = new JLabel("Product Brand");
		
		labelPanel.add(idLbl);
		labelPanel.add(nameLbl);
		labelPanel.add(priceLbl);
		labelPanel.add(ratingLbl);
		labelPanel.add(stockLbl);
		labelPanel.add(brandLbl);
		
		idField = new JTextField();
		nameField = new JTextField();
		priceField = new JTextField();
		ratingSpinner = new JSpinner();
		stockSpinner = new JSpinner();
		brandComboBox = new JComboBox(brandName);

		fieldPanel.add(idField);
		fieldPanel.add(nameField);
		fieldPanel.add(priceField);
		fieldPanel.add(ratingSpinner);
		fieldPanel.add(stockSpinner);
		fieldPanel.add(brandComboBox);
		
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		submitBtn = new JButton("Submit");
		cancelBtn = new JButton("Cancel");

		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		submitBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		setInit();
		
		buttonPanel.add(insertBtn);
		buttonPanel.add(updateBtn);
		buttonPanel.add(deleteBtn);
		buttonPanel.add(submitBtn);
		buttonPanel.add(cancelBtn);
		
		formPanel.add(labelPanel);
		formPanel.add(fieldPanel);
		formPanel.add(buttonPanel);
		
		mainPanel.add(northPanel);
		mainPanel.add(formPanel);
		add(mainPanel);

		loadData();
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = 0;
				if(action.equals("Update") || action.equals("Delete")) {					
					row = table.getSelectedRow();
					idField.setText("" + table.getValueAt(row, 0));
					nameField.setText("" + table.getValueAt(row, 2));
					priceField.setText("" + table.getValueAt(row, 3));
					stockSpinner.setValue((int) table.getValueAt(row, 4));
					ratingSpinner.setValue((int) table.getValueAt(row, 5));
					brandComboBox.setSelectedIndex(getIndex("" + table.getValueAt(row, 1)));;
				}
				
			}
		});
	}
	
	private int getIndex(String brandName) {
		int idx = 1;
		for (Brand brand : BrandController.getInstance().getAll()) {
			if(brand.getBrandName().equals(brandName)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}
	
	public void loadData() {
		String header[] = { "ProductID" , "BrandName" , "ProductName" , "ProductPrice", "ProductStock", "ProductRating"};
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
		table.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == insertBtn) {
			setButton("Insert");
		}else if(e.getSource() == updateBtn) {
			setButton("Update");
		} else if(e.getSource() == deleteBtn) {
			setButton("Delete");
		} else if(e.getSource() == cancelBtn) {
			setInit();
		} else if (e.getSource() == submitBtn) {
			String productID = idField.getText();
			String productName = nameField.getText();
			String productPrice = priceField.getText();
			try {
				stockSpinner.commitEdit();
				ratingSpinner.commitEdit();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int productStock =  (int) stockSpinner.getValue();
			int productRating = (int) ratingSpinner.getValue();
			String productBrand = (String) brandComboBox.getSelectedItem();

			if(action.equals("Insert")) {
				boolean insert = ProductController.getInstance().insert(productID, productBrand, productName, productPrice, productStock, productRating);
				if(insert == false) {
					JOptionPane.showMessageDialog(null, ProductController.getInstance().getErrorMessage());
				}else {
					JOptionPane.showMessageDialog(null, "Insert Success");
					setInit();
				}
				loadData();
			}else if(action.equals("Update")) {
				boolean update = ProductController.getInstance().update(productID, productBrand, productName, productPrice, productStock, productRating);
				if(update == false) {
					JOptionPane.showMessageDialog(null, ProductController.getInstance().getErrorMessage());
				}else {
					JOptionPane.showMessageDialog(null, "Update Success");
					setInit();
				}
				loadData();
			}else if(action.equals("Delete")) {
				boolean delete = ProductController.getInstance().delete(productID);
				if(delete == false) {
					JOptionPane.showMessageDialog(null, ProductController.getInstance().getErrorMessage());
				}else {
					JOptionPane.showMessageDialog(null, "Delete Success");
					setInit();
				}
				loadData();
			}
		}
	}
	
	private void setInit() {
		idField.setEnabled(false);
		nameField.setEnabled(false);
		priceField.setEnabled(false);
		ratingSpinner.setEnabled(false);
		stockSpinner.setEnabled(false);
		brandComboBox.setEnabled(false);
		
		idField.setText("");
		nameField.setText("");
		priceField.setText("");
		ratingSpinner.setValue(0);
		stockSpinner.setValue(0);
		brandComboBox.setSelectedIndex(0);
		
		insertBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
		updateBtn.setEnabled(true);
		submitBtn.setEnabled(false);
		cancelBtn.setEnabled(false);
		
		action = "";
	}
	
	private void setButton(String str) {
		submitBtn.setEnabled(true);
		cancelBtn.setEnabled(true);
		insertBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		updateBtn.setEnabled(false);
		action = str;
		
		if(str.equals("Insert")) {			
			String productID = ProductController.getInstance().generateID();
			idField.setText(productID);
		}
		
		idField.setEnabled(false);
		nameField.setEnabled(true);
		priceField.setEnabled(true);
		ratingSpinner.setEnabled(true);
		stockSpinner.setEnabled(true);
		brandComboBox.setEnabled(true);
	}
}
