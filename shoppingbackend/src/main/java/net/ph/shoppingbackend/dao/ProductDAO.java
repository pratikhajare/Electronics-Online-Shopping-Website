package net.ph.shoppingbackend.dao;

import java.util.List;

import net.ph.shoppingbackend.dto.Product;

public interface ProductDAO {

	List<Product> list();
	Product getProduct(int id);
	boolean add(Product product);
	boolean update(Product product);
	boolean delete(Product product);
	
	List<Product> listAllActiveProducts();
	List<Product> listAllActiveProductsByCategory(int category_id);
	List<Product> listActiveProductsByCount(int count);
	List<Product> getProductsByParam(String string, int i);
}
