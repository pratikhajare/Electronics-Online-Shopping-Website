package net.ph.shoppingbackend.dao;

import java.util.List;

import net.ph.shoppingbackend.dto.Category;

public interface CategoryDAO {

	List<Category> list();
	Category getCategory(int id);
	boolean add(Category category);
	boolean update(Category category);
	boolean delete(Category category);
	
}
