package net.ph.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.ph.shoppingbackend.dao.CategoryDAO;
import net.ph.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Category> list() {
		
		String selectActiveCategory="From Category WHERE active = :active";
		Query query= sessionFactory.getCurrentSession().createQuery(selectActiveCategory);
		
		query.setParameter("active", true);
		return query.getResultList();
	}

	
	//fetching a single category
	@Override
	public Category getCategory(int id) {
		// TODO Auto-generated method stub
		
		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
		
	}

	@Override
	public boolean add(Category category) {
		
		try {
			//add the category to database table
			sessionFactory.getCurrentSession().persist(category);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public boolean update(Category category) {
		try {
			//update the category to database table
			sessionFactory.getCurrentSession().update(category);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public boolean delete(Category category) {
		
		try {
			category.setActive(false);
			
			//update the category to database table
			this.update(category);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
}
