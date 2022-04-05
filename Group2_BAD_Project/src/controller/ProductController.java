package controller;
import java.util.Random;
import java.util.Vector;

import model.Cart;
import model.Product;
import view.ManageProduct;

public class ProductController {

	public static ProductController controller = null;
	public Product product;
	private String errorMsg = "";
	private Random rand = new Random();
	
	private ProductController() {
		product = new Product();
	}
	
	public static ProductController getInstance() {
		if(controller == null) {
			controller = new ProductController();
		}
		return controller;
	}

	public Vector<Product> getAll(){
		return product.getAll();
	}
	
	public Product getProductByID(String productID){
		product.setProductID(productID);
		return product.getProductByID();
	}
	
	public String generateID() {
		while (true) {			
			String id = "PD" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			if(getProductByID(id) == null) {
				return id;
			}
		}
	}
	
	public String getErrorMessage() {
		return errorMsg;
	}
	
	public ManageProduct showManageProductView() {
		return new ManageProduct();
	}
	
	public boolean insert(String productID, String brandName , String productName , String productPrice , int productStock , int productRating) {
		if(productRating < 1 || productRating > 10) {
			errorMsg = "Product Rating must be 1 to 10";
			return false;
		}else if(productName.isEmpty()) {
			errorMsg = "Product Name must be fill";
			return false;
		}else if(productPrice.isEmpty()) {
			errorMsg = "Product Price must be fill";
			return false;
		}else if(productStock == 0) {
			errorMsg = "Product Stock must be fill";
			return false;
		}else if(productRating == 0) {
			errorMsg = "Product Rating must be fill";
			return false;
		}else if(brandName.equals("")) {
			errorMsg = "Brand Name must be fill";
			return false;
		}else {
			int priceInt = 0;
			try {
				priceInt = Integer.parseInt(productPrice);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				errorMsg = "Product Price must be numeric!";
				return false;
			}
			
			String brandID = BrandController.getInstance().getBrandByName(brandName).getBrandID();
			product = new Product(productID, brandID, productName, priceInt, productStock, productRating);
			boolean inserted = product.insert();
			
			if(inserted == false) {
				errorMsg = "Insert failed!";
			}
			return inserted;
		}
	}
	
	public boolean update(String productID, String brandName , String productName , String productPrice , int productStock , int productRating) {
		if(productID.isEmpty() && productName.isEmpty() && productPrice.isEmpty() && productStock == 0 && productRating == 0 && brandName.equals("")) {
			errorMsg = "You must select the data on the table";
			return false;
		}else if(productName.isEmpty()) {
			errorMsg = "Product Name must be fill";
			return false;
		}else if(productPrice.isEmpty()) {
			errorMsg = "Product Price must be fill";
			return false;
		}else if(productStock == 0) {
			errorMsg = "Product Stock must be fill";
			return false;
		}else if(productRating == 0) {
			errorMsg = "Product Rating must be fill";
			return false;
		// validasi tambahan kayak insert
		}else if(productRating < 1 || productRating > 10) {
			errorMsg = "Product Rating must be 1 to 10";
			return false;
		}else if(brandName.equals("")) {
			errorMsg = "Brand Name must be fill";
			return false;
		}else {
			int priceInt = 0;
			try {
				priceInt = Integer.parseInt(productPrice);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				errorMsg = "Product Price must be numeric!";
				return false;
			}
			
			String brandID = BrandController.getInstance().getBrandByName(brandName).getBrandID();
			product = new Product(productID, brandID, productName, priceInt, productStock, productRating);
			boolean updated = product.update();
			
			if(updated == false) {
				errorMsg = "Update failed!";
			}
			return updated;
		}
	}
	
	public boolean delete(String productID) {
		if(productID.isEmpty()) {
			errorMsg = "You must select the data on the table";
			return false;
		}else {
			product = new Product();
			product.setProductID(productID);
			boolean deleted = product.delete();
			
			if(deleted == false) {
				errorMsg = "Delete failed!";
			}
			return deleted;
		}
	}
	
	public boolean decreaseStock(String productID, int qty) {
		Product prod = getProductByID(productID);
		int stock = prod.getProductStock();
		if(stock < qty) {
			errorMsg = "Quantity cannot be more than available stock";
			return false;
		}else {
			stock -= qty;
			product = new Product();
			product.setProductID(productID);
			product.setProductStock(stock);
			boolean updated = product.updateStock();
			
			if(updated == false) {
				errorMsg = "Update failed!";
			}
			return updated;
		}
		
	}
	
}
