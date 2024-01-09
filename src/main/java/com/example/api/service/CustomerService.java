package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.domain.Address;
import com.example.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public AddressRepository addressRepository;

	public Page<Customer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Page<Customer> findByName(String name, Pageable pageable) {
		return repository.findByNameContainingIgnoreCase(name, pageable);
	}

	public List<Customer> findByEmail(String email) {
		return repository.findByEmailContainingIgnoreCase(email);
	}

	public Page<Customer> findByGender(String gender, Pageable pageable) {
		return repository.findByGenderIgnoreCase(gender, pageable);
	}

	public Customer createCustomer(Customer customer) {
		return repository.save(customer);
	}

	public Customer updateCustomer(Long id, Customer customerDetails) {
		Customer customer = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
		customer.setName(customerDetails.getName());
		customer.setEmail(customerDetails.getEmail());
		customer.setGender(customerDetails.getGender());
		return repository.save(customer);
	}

	public void deleteCustomer(Long id) {
		Customer customer = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
		repository.delete(customer);
	}
}
