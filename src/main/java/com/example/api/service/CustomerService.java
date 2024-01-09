package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public List<Customer> findByName(String name) {
		return repository.findByNameContainingIgnoreCase(name);
	}

	public List<Customer> findByEmail(String email) {
		return repository.findByEmailContainingIgnoreCase(email);
	}

	public List<Customer> findByGender(String gender) {
		return repository.findByGenderIgnoreCase(gender);
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
