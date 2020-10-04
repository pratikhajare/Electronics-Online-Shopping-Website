package net.ph.shoppingbackend.dao;

import java.util.List;

import net.ph.shoppingbackend.dto.Address;
import net.ph.shoppingbackend.dto.Cart;
import net.ph.shoppingbackend.dto.User;

public interface UserDAO {
	
		//add an user
		boolean addUser(User user);
	//add an address
		boolean addAddress(Address address);	
		//add an cart
		boolean updateCart(Cart cart);
		//get user by email
		User getByEmail(String email);
		
		// adding and updating a new address
		Address getAddress(int addressId);
		boolean updateAddress(Address address);
		Address getBillingAddress(User user);
		List<Address> listShippingAddresses(User user);
		
		
}
