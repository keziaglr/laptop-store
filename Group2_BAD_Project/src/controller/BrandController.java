package controller;

import java.util.Random;
import java.util.Vector;

import model.Brand;
import view.ManageBrand;

public class BrandController {

	public static BrandController controller = null;
	public Brand brand;
	private String errorMsg = "";
	private Random rand = new Random();
	
	private BrandController() {
		brand = new Brand();
	}
	
	public static BrandController getInstance() {
		if(controller == null) {
			controller = new BrandController();
		}
		return controller;
	}

	public Vector<Brand> getAll(){
		return brand.getAll();
	}
	
	public Brand getBrandByName(String brandName){
		brand.setBrandName(brandName);
		return brand.getBrandByName();
	}
	
	public Brand getBrandByID(String brandID){
		brand.setBrandID(brandID);
		return brand.getBrandByID();
	}
	
	public Vector<String> getAllBrandName(){
		Vector<String> brands = new Vector<>();
		for (Brand brand : getAll()) {
			brands.add(brand.getBrandName());
		}
		return brands;
	}

	public String getErrorMessage() {
		return errorMsg;
	}
	
	public ManageBrand showManageBrandView() {
		return new ManageBrand();
	}
	
	public String generateID() {
		while (true) {			
			String id = "BD" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			if(getBrandByID(id) == null) {
				return id;
			}
		}
	}
	
	public boolean insert(String brandID, String brandName) {
		if(brandID.isEmpty() && brandName.isEmpty()) {
			errorMsg = "You must select the data on the table";
			return false;
		}else if(brandName.isEmpty()) {
			errorMsg = "Brand Name must be fill";
			return false;
		}else {	
			brand = new Brand(brandID, brandName);
			boolean inserted = brand.insert();
			
			if(inserted == false) {
				errorMsg = "Insert failed!";
			}
			return inserted;
		}
	}
	
	public boolean update(String brandID, String brandName) {
		if(brandID.isEmpty() && brandName.isEmpty()) {
			errorMsg = "You must select the data on the table";
			return false;
		}else if(brandName.isEmpty()) {
			errorMsg = "Brand Name must be fill";
			return false;
		}else {
			brand = new Brand(brandID, brandName);
			boolean updated = brand.update();
			
			if(updated == false) {
				errorMsg = "Update failed!";
			}
			return updated;
		}
	}
	
	public boolean delete(String brandID) {
		if(brandID.isEmpty()) {
			errorMsg = "You must select the data on the table";
			return false;
		}else {
			brand = new Brand();
			brand.setBrandID(brandID);
			boolean deleted = brand.delete();
			
			if(deleted == false) {
				errorMsg = "Update failed!";
			}
			return deleted;
		}
	}
}
