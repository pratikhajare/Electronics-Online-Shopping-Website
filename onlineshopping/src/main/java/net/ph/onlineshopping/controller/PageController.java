package net.ph.onlineshopping.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.ph.onlineshopping.exception.ProductNotFoundException;
import net.ph.shoppingbackend.dao.CategoryDAO;
import net.ph.shoppingbackend.dao.ProductDAO;
import net.ph.shoppingbackend.dto.Category;
import net.ph.shoppingbackend.dto.Product;

@Controller
public class PageController {
	
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = {"/","/home","/index"})
	public ModelAndView index()
	{
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","Home");
		mv.addObject("categories",categoryDAO.list());
		
		logger.info("Inside PageController index method - INFO");
		logger.debug("Inside PageController index method - DEBUG");
		
		mv.addObject("userClickHome",true);
		return mv;
	}
	
	@RequestMapping(value ="/about")
	public ModelAndView about()
	{
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;
	}
	
	@RequestMapping(value ="/contact")
	public ModelAndView contact()
	{
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	/* Method to load all products*/	
	@RequestMapping(value ="/show/all/products")
	public ModelAndView showAllProducts()
	{
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","All Products");
		
		mv.addObject("categories",categoryDAO.list());
		
		mv.addObject("userClickAllProducts",true);
		return mv;
	}
	
	
	/* Method to load only category products*/	
	@RequestMapping(value ="/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id)
	{
		ModelAndView mv=new ModelAndView("page");
		
		Category category=null;
		
		category=categoryDAO.getCategory(id);
		
		mv.addObject("category",category);
		
		mv.addObject("title",category.getName());
		
		mv.addObject("categories",categoryDAO.list());
		
		mv.addObject("userClickCategoryProducts",true);
		return mv;
	}
	
	/* Viewing a single product*/	
	@RequestMapping(value ="/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException
	{
		ModelAndView mv=new ModelAndView("page");
		
		Product product= productDAO.getProduct(id);
		
		if(product==null) throw new ProductNotFoundException();
		
		product.setViews(product.getViews()+1);
		
		//update the view count
		productDAO.update(product);
		
		mv.addObject("title",product.getName());
		mv.addObject("product",product);
		
		mv.addObject("userClickShowProduct",true);
		
		return mv;
	}
	
	@RequestMapping(value ="/login")
	public ModelAndView login(@RequestParam(name="error",required=false)String error,
			@RequestParam(name="logout",required=false)String logout)
	{
		ModelAndView mv=new ModelAndView("login");
		
		if(error!=null) {
			mv.addObject("message","Invalid username and password");
		}
		
		if(logout!=null) {
			mv.addObject("logout","User has successfully logged out");
		}
		
		mv.addObject("title","Log in");
		return mv;
	}
	
	@RequestMapping(value ="/access-denied")
	public ModelAndView accessDenied()
	{
		ModelAndView mv=new ModelAndView("error");
		mv.addObject("title","Access Denied");
		mv.addObject("errorTitle","AHA! Caught You");
		mv.addObject("errorDescription","You are not authorised to access this page");
		return mv;
	}
	
	@RequestMapping(value="/perform-logout")
	public String logout(HttpServletRequest request,HttpServletResponse response)
	{
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication!=null)
		{
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value="/membership")
	public ModelAndView register() {
		ModelAndView mv= new ModelAndView("page");
		
		logger.info("Page Controller membership called!");
		
		return mv;
	}
}
