package net.ph.onlineshopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.ph.onlineshopping.util.FileUploadUtility;
import net.ph.onlineshopping.validator.ProductValidator;
import net.ph.shoppingbackend.dao.CategoryDAO;
import net.ph.shoppingbackend.dao.ProductDAO;
import net.ph.shoppingbackend.dto.Category;
import net.ph.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public ModelAndView manageProducts(@RequestParam(name="operation",required=false) String operation)
	{
		 
	ModelAndView mv=new ModelAndView("page");
	mv.addObject("title","Manage Products");
	
	mv.addObject("userClickManageProducts",true);
	
	Product nProduct=new Product();
	nProduct.setSupplierId(1);
	nProduct.setActive(true);
	
	mv.addObject("product",nProduct);
	
	if(operation!=null)
	{
		if(operation.equals("product")) {
			mv.addObject("message","Product Submitted Succesfully!");
		}
		else if(operation.equals("category"))
		{
			mv.addObject("message","Category Added Succesfully!");
		}
	}
	
	return mv;
	}
	
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public String handlerProductSubmission(@Valid @ModelAttribute("product") Product mProduct,BindingResult results,Model model,
			HttpServletRequest request)
	{
		
		if(mProduct.getId()==0)
		{
		new ProductValidator().validate(mProduct,results);
		}
		else {
			if(!mProduct.getFile().getOriginalFilename().equals(""))
			{
				new ProductValidator().validate(mProduct,results);
			}
		}
		
		if(results.hasErrors())
		{
			model.addAttribute("title","Manage Products");
			 model.addAttribute("userClickManageProducts",true);
		 model.addAttribute("message","Validation failed for product submission!!");
			 
			 return "page";
		}
		
		logger.info(mProduct.toString());
		
		if(mProduct.getId()==0) {
		productDAO.add(mProduct);	
		}
		else {
			productDAO.update(mProduct);	
		}
		
		if(!mProduct.getFile().getOriginalFilename().equals(""))
		{
			FileUploadUtility.uploadFile(request,mProduct.getFile(),mProduct.getCode());
		}
		
		return "redirect:/manage/products?operation=product";
	}
	
	@RequestMapping(value="/product/{id}/activation",method=RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable int id)
	{
		Product product=productDAO.getProduct(id);
		boolean isActive= product.isActive();		
		product.setActive(!isActive);
		
		productDAO.update(product);
	
		return (isActive)?"You have successfully deactivated the product with id "+product.getId():
			"You have successfully activaed the product with id "+product.getId();
	
	}
	
	@RequestMapping(value="/{id}/product",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView showEditProduct(@PathVariable int id)
	{
		
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","Manage Products");
		
		mv.addObject("userClickManageProducts",true);
		
		Product nProduct=productDAO.getProduct(id);
		
		mv.addObject("product",nProduct);
		
		return mv;
	
	}
	
	@RequestMapping(value="/category",method=RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category)
	{
		category.setActive(true);
		categoryDAO.add(category);
		
		return "redirect:/manage/products?operation=category";
	
	}
	
	@ModelAttribute("categories")
	public List<Category> getCategories(){
		return categoryDAO.list();
	}
	
	@ModelAttribute("category")
	public Category getCategory(){
		return new Category();
	}
}
