package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import controller.UserController;

public class MainFormAdmin extends JFrame{
	JMenuItem buy, viewTransaction, brand, product;
	JMenuBar bar;
	JMenu transaction, logout, manage;
	ManageProduct mp;
	ManageBrand mb;
	ViewTransaction vt;
	 
	public MainFormAdmin() {
		// TODO Auto-generated constructor stub
		initialize();
		setJMenuBar(bar);
        setTitle("Main Form");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private void initialize() {
		bar = new JMenuBar();
		mp = new ManageProduct();
		mb = new ManageBrand();
		vt = new ViewTransaction();
		
		manage = new JMenu("Manage");
		transaction = new JMenu("Transaction");
		logout = new JMenu ("Logout");
		logout.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				dispose();
				UserController.getInstance().viewLoginPage();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		brand = new JMenuItem("Brand");
		brand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(mp);
				remove(vt);
				add(mb);
			}
			
		});
		product = new JMenuItem("Product");
		product.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(vt);
				remove(mb);
				add(mp);
			}
			
		});
		
		viewTransaction = new JMenuItem("View Transaction");
		viewTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(mb);
				remove(mp);
				add(vt);
			}
			
		});
		
		bar.add(manage);
		bar.add(transaction);
		bar.add(logout);
		
		manage.add(brand);
		manage.add(product);
		transaction.add(viewTransaction);		
	}



//	@Override
//	public void menuSelected(MenuEvent e) {
//		// TODO Auto-generated method stub
//		if(e == brand) {
//			System.out.println("Manage Brand");
//			ManageBrand brd = new ManageBrand();
//			this.add(brd);
//		}else if(e.getSource() == product) {
//			this.add(ProductController.getInstance().showManageProductView());
//		}else if(e.getSource() == viewTransaction) {
//			this.add(TransactionController.getInstance().viewTransaction());
//		}else if(e.getSource() == logout) {
//			dispose();
//			UserController.getInstance().viewLoginPage();
//		}
//	}
//
//	@Override
//	public void menuDeselected(MenuEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void menuCanceled(MenuEvent e) {
//		// TODO Auto-generated method stub
//		
//	}

}
