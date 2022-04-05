package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import controller.UserController;

public class MainFormUser extends JFrame{
	 JMenuItem buy;
	 JMenuBar bar;
	 JMenu transaction;
	 JMenu logout;
	 JMenuItem viewTransaction;
	 ViewTransaction vt;
	 BuyProductForm bpf;
	 CartForm cf;

	public MainFormUser() {
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
		vt = new ViewTransaction();
		bpf = new BuyProductForm();
		cf = new CartForm();
		
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
		
		buy = new JMenuItem("Buy Product");
		buy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(cf);
				remove(vt);
				bpf.loadData();
				add(bpf);
			}
			
		});
		viewTransaction = new JMenuItem("View Transaction");
		viewTransaction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(cf);
				remove(bpf);
				vt.loadData();
				add(vt);
			}
			
		});
		
		bar.add(transaction);
		bar.add(logout);
		
		transaction.add(buy);
		transaction.add(viewTransaction);
	}
	
	public void changeCart() {
		remove(bpf);
		remove(vt);
		cf.loadData();
		add(cf);
	}

}
