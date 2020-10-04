package net.ph.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.ph.shoppingbackend.dao.ProductDAO;
import net.ph.shoppingbackend.dto.Product;

@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public List<Product> list() {
		
		String selectProduct="From Product ";
		Query query= sessionFactory.getCurrentSession().createQuery(selectProduct);
		
	
		return query.getResultList();
		
	}

	@Override
	public Product getProduct(int id) {
		
		return sessionFactory.getCurrentSession().get(Product.class, Integer.valueOf(id));
		
	}

	@Override
	public boolean add(Product product) {
		try {
			//add the category to database table
			sessionFactory.getCurrentSession().persist(product);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public boolean update(Product product) {
		try {
			//update the category to database table
			sessionFactory.getCurrentSession().update(product);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public boolean delete(Product product) {
		try {
			product.setActive(false);
			
			//update the category to database table
			this.update(product);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public List<Product> listAllActiveProducts() {
		
		String selectActiveProduct="From Product WHERE active = :active";
		Query query= sessionFactory.getCurrentSession().createQuery(selectActiveProduct);
		
		query.setParameter("active", true);
		return query.getResultList();
	}

	@Override
	public List<Product> listAllActiveProductsByCategory(int category_Id) {
		String selectActiveProduct="From Product WHERE active = :active AND categoryId = :categoryID";
		Query query= sessionFactory.getCurrentSession().createQuery(selectActiveProduct);
		
		query.setParameter("active", true);
		query.setParameter("categoryID",category_Id);
		return query.getResultList();
	}

	@Override
	public List<Product> listActiveProductsByCount(int count) {
		String selectActiveProduct="From Product WHERE active = :active ORDER BY id";
		Query query= sessionFactory.getCurrentSession().createQuery(selectActiveProduct).setFirstResult(0).setMaxResults(count);
		
		query.setParameter("active", true);
		return query.getResultList();
	}
	
	@Override
	public List<Product> getProductsByParam(String param, int count) {
		
		String query = "FROM Product WHERE active = true ORDER BY " + param + " DESC";
		
		return sessionFactory
					.getCurrentSession()
					.createQuery(query,Product.class)
					.setFirstResult(0)
					.setMaxResults(count)
					.getResultList();
					
		
	}


}
