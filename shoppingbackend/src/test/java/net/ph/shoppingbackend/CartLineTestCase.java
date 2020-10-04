package net.ph.shoppingbackend;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ph.shoppingbackend.dao.CartLineDAO;
import net.ph.shoppingbackend.dao.ProductDAO;
import net.ph.shoppingbackend.dao.UserDAO;
import net.ph.shoppingbackend.dto.Cart;
import net.ph.shoppingbackend.dto.CartLine;
import net.ph.shoppingbackend.dto.Product;
import net.ph.shoppingbackend.dto.User;


public class CartLineTestCase {

	private static AnnotationConfigApplicationContext context;
	
	private static CartLineDAO cartLineDAO;
	private static UserDAO userDAO;
	private static ProductDAO productDAO;
	
	private CartLine cartLine = null;
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.ph.shoppingbackend");
		context.refresh();	
		productDAO=(ProductDAO)context.getBean("productDAO");
		userDAO = (UserDAO)context.getBean("userDAO");
		cartLineDAO = (CartLineDAO) context.getBean("cartLineDAO");
	}
	
	
	@Test
	public void testAddCartLine() {
		
		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("pmh@gmail.com");		
		Cart cart = user.getCart();
		
		// fetch the product 
		Product product = productDAO.getProduct(1);
		
		// Create a new CartLine
		cartLine = new CartLine();
		cartLine.setCartId(cart.getId());
		cartLine.setProduct(product);
		cartLine.setProductCount(1);
		cartLine.setBuyingPrice(product.getUnitPrice());
		cartLine.setAvailable(true);
		double oldTotal = cartLine.getTotal();		
		
		cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());
		
		cart.setCartLines(cart.getCartLines() + 1);
		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
		
		assertEquals("Failed to add the CartLine!",true, cartLineDAO.add(cartLine));
		assertEquals("Failed to update the cart!",true, userDAO.updateCart(cart));
		
	}
	
	
	
//	@Test
//	public void testUpdateCartLine() {
//
//		// fetch the user and then cart of that user
//		User user = userDAO.getByEmail("absr@gmail.com");		
//		Cart cart = user.getCart();
//				
//		cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), 2);
//		
//		cartLine.setProductCount(cartLine.getProductCount() + 1);
//				
//		double oldTotal = cartLine.getTotal();
//				
//		cartLine.setTotal(cartLine.getProduct().getUnitPrice() * cartLine.getProductCount());
//		
//		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));
//		
//		assertEquals("Failed to update the CartLine!",true, cartLineDAO.update(cartLine));	
//
//		
//	}
	
	
	
}
