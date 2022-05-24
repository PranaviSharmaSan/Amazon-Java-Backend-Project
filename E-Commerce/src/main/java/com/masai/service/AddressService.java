package com.masai.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.beans.Address;
import com.masai.beans.Customer;
import com.masai.beans.Product;
import com.masai.beans.Seller;
import com.masai.beans.User;
import com.masai.exception.AddressAlreadyExistException;
import com.masai.exception.AddressNotFoundException;
import com.masai.exception.ProductNotFoundException;
import com.masai.exception.SellerAlreadyExistException;
import com.masai.exception.SellerNotFoundException;
import com.masai.exception.UserNotFoundException;
import com.masai.repository.AddressCrudRepo;

@Service
public class AddressService implements AddressServiceInterface {
	
	@Autowired
	private AddressCrudRepo addressCrudRepo;
	
	
	//Need to disscuss about finding in database//
	@Override
	public Address addAddress(Address address) {
		// TODO Auto-generated method stub
		//Optional<Address> checkAddress = addressCrudRepo.findById(address.getAddressId());
		Address savedAddress = null;
		
//		if (!checkAddress.isPresent()) {
			savedAddress = addressCrudRepo.save(address);
//		} else {
//			throw new AddressAlreadyExistException("Address already availlable");
//		}
		return savedAddress;
	}
	
	@Override
	public String removeAddressByAddressId(Integer addressId) {
		// TODO Auto-generated method stub
		Optional<Address> checkAddress = addressCrudRepo.findById(addressId);
		String message = "Not deleted";
		
		if (checkAddress.isPresent()) {
			addressCrudRepo.deleteById(addressId);
			message = "Address deleted";
//			message = "Deleted address \nUser name : " + checkAddress.get().getUser().getUserName() + "\nId : " + checkAddress.get().getUser().getUserId();
		} else {
			throw new AddressNotFoundException("Address not availlable");
		}

		return message;
	}
	
	@Override
	public String removeAllAddressOfUser(User user) {
		// TODO Auto-generated method stub
		List<Address> checkAddress = addressCrudRepo.findByUser(user);
		String message = "Not deleted";
		
		if (checkAddress.size() > 0) {
			for (Address address : checkAddress) {
				addressCrudRepo.deleteById(address.getAddressId());
				message = "Deleted address \nUser name : " + address.getUser().getUserName() + "\nId : " + address.getUser().getUserId();
			}
			
		} else {
			throw new AddressNotFoundException("Address not availlable");
		}

		return message;
	}
	
	@Override
	public Address updateAddress(Address address) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Address showAddressById(Integer addressId) {
		
		Optional<Address> checkAddress = addressCrudRepo.findById(addressId);
		
		if (checkAddress.isPresent()) {
			return checkAddress.get();
		} else {
			throw new AddressNotFoundException("Address not availlable");
		}

	}
	
	@Override
	public Set<User> listAllUserByCity(String city) {
		// TODO Auto-generated method stub
		List<Address> listAddress = addressCrudRepo.findByCity(city);
		Set<User> user = new HashSet();
		
		if (listAddress.size() > 0) {
			for (Address address : listAddress) {
				user.add(address.getUser());
			}
		} else {
			throw new UserNotFoundException("No user Found in " + city);
		}
		return user;
	}
	
	@Override
	public Set<User> listAllUserByState(String state) {
		// TODO Auto-generated method stub
		List<Address> listAddress = addressCrudRepo.findByState(state);
		Set<User> user = new HashSet();
		
		if (listAddress.size() > 0) {
			for (Address address : listAddress) {
				user.add(address.getUser());
			}
		} else {
			throw new AddressNotFoundException("No user Found in " + state);
		}
		return user;
	}
	
	public Address persistCustomer(Customer customer, Address address) {
		address.setUser(customer);
		Address savedAddress = addressCrudRepo.save(address);
		
		return savedAddress;
	}
	
	@Override
	public String deleteAddress(Integer addressId) {
		// TODO Auto-generated method stub
		Optional<Address> findAddress = addressCrudRepo.findById(addressId);
		findAddress.get().setUser(null);
		
		if (findAddress.isPresent()) {
			addressCrudRepo.deleteById(addressId);
			return "Address with ID : " + addressId + " deleted.";
		} else {
			throw new AddressNotFoundException("No Address Found");
		}
		
	}

}
