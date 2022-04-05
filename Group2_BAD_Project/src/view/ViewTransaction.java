package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.TransactionController;
import controller.UserController;
import model.DetailTransaction;
import model.HeaderTransaction;

public class ViewTransaction extends JInternalFrame implements ActionListener {
	 JScrollPane scrollPane;
	 JLabel title, title2;
	 JPanel northPanel, southPanel, mainPanel, titlePanel, title2Panel;
	 JTable table, table2;
	 JScrollPane tableSP, tableSP2;
	 Vector<Object> header;
	 Vector<Object> tableContent;
	 String transID = "";
	 
	 public ViewTransaction() {
		northPanel = new JPanel(new GridLayout(2,1));
		southPanel = new JPanel(new GridLayout(2,1));
		mainPanel = new JPanel(new GridLayout(2,1));
		titlePanel = new JPanel();
		title2Panel = new JPanel();
			 
		title = new JLabel("Transaction List");
		title.setFont(new Font("Calibri", Font.BOLD, 20));
		title.setForeground(Color.BLACK);
		
		titlePanel.add(title, BorderLayout.CENTER);
		 
		 table = new JTable() {
				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;
		
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
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
					row = table.getSelectedRow();
					transID = "" + table.getValueAt(row, 0);
					loadDataDetail();
				}
			});
			
		 tableSP = new JScrollPane(table);
		 northPanel.add(titlePanel);
		 northPanel.add(tableSP);
		 loadData();
		
		 title2 = new JLabel("Transaction Detail");
		 title2.setFont(new Font("calibri", Font.BOLD, 20));
		 title2.setForeground(Color.BLACK);
		 title2Panel.add(title2, BorderLayout.CENTER);
		 
		 table2 = new JTable() {
				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;
		
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		 
		 
		 tableSP2 = new JScrollPane(table2);
		 
		 loadDataDetail();
		 
		 southPanel.add(title2Panel);
		 southPanel.add(tableSP2);
		 
		 mainPanel.add(northPanel, BorderLayout.NORTH);
		 mainPanel.add(southPanel, BorderLayout.SOUTH);

		 add(mainPanel);
	
		 setTitle("View Transaction");
	     setResizable(false);
	     setSize(800, 500);
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	     setVisible(true);
	 }
	 
	public void loadData() {
		String header[] = { "TransactionID" , "UserID" , "Transaction Date"};
		DefaultTableModel dtm = new DefaultTableModel(header , 0);
		Vector<HeaderTransaction> vHT = null;
		if(UserController.getInstance().getCurrUser() != null) {			
			if(UserController.getInstance().getCurrUser().getUserRole().equals("Admin")) {			
				vHT = TransactionController.getInstance().getAllHeader();
			}else {
				vHT = TransactionController.getInstance().getAllHeaderByUserID(UserController.getInstance().getCurrUser().getUserID());
			}
			for (HeaderTransaction ht : vHT) {
				tableContent = new Vector<>();
				tableContent.add(ht.getTransactionID());
				tableContent.add(ht.getUserID());
				tableContent.add(ht.getTransactionDate());
				dtm.addRow(tableContent);
			}
			table.setModel(dtm);
		}
	}
	  
	public void loadDataDetail() {
		String header[] = { "TransactionID" , "ProductID" , "Qty"};
		DefaultTableModel dtm2 = new DefaultTableModel(header , 0);
		Vector<DetailTransaction> vDT = null;
		if(UserController.getInstance().getCurrUser() != null) {			
			vDT = TransactionController.getInstance().getAllDetailByID(transID);
			for (DetailTransaction dt : vDT) {
				tableContent = new Vector<>();
				tableContent.add(dt.getTransactionID());
				tableContent.add(dt.getProductID());
				tableContent.add(dt.getQty());
				dtm2.addRow(tableContent);
			}
			table2.setModel(dtm2);
		}
	}
	 
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
	}

}
