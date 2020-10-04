package net.ph.shoppingbackend;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ph.shoppingbackend.dao.CategoryDAO;
import net.ph.shoppingbackend.dto.Category;

public class CategoryTestCase {

	private static AnnotationConfigApplicationContext context;

	private static CategoryDAO categoryDAO;

	private static Category category;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.ph.shoppingbackend");
		context.refresh();
		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	}

	@Test
	public void testCRUDCategory() {
		
		category = new Category();

		category.setName("Television");
		category.setActive(true);
		category.setDescription("This is one description for Television");
		category.setImageUrl("CAT_1.png");

		assertEquals("Succesfully added a category inside the table!", true, categoryDAO.add(category));

		category = new Category();

		category.setName("Laptop");
		category.setActive(true);
		category.setDescription("This is one description for Laptop");
		category.setImageUrl("CAT_2.png");

		assertEquals("Succesfully added a category inside the table!", true, categoryDAO.add(category));
		
		category = new Category();

		category.setName("Mobile");
		category.setActive(true);
		category.setDescription("This is one description for Mobile");
		category.setImageUrl("CAT_3.png");

		assertEquals("Succesfully added a category inside the table!", true, categoryDAO.add(category));
		
		category = categoryDAO.getCategory(3);

		assertEquals("Succesfully delete a single category inside the table!", true, categoryDAO.delete(category));



		category = categoryDAO.getCategory(1);

		assertEquals("Succesfully fetched a single category inside the table!", "Television", category.getName());

		category = categoryDAO.getCategory(2);
		category.setName("Lap-Top");

		assertEquals("Succesfully fetched a single category inside the table!", true, categoryDAO.update(category));

		assertEquals("Succesfully fetched categories inside the table!", 2, categoryDAO.list().size());
	}
}
