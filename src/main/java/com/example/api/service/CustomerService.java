package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.customerRepository = repository;
	}

	public AddressRepository addressRepository;

	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public Page<Customer> findByName(String name, Pageable pageable) {
		return customerRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	public List<Customer> findByEmail(String email) {
		return customerRepository.findByEmailContainingIgnoreCase(email);
	}

	public Page<Customer> findByGender(String gender, Pageable pageable) {
		return customerRepository.findByGenderIgnoreCase(gender, pageable);
	}

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(Long id, Customer customerDetails) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
		customer.setName(customerDetails.getName());
		customer.setEmail(customerDetails.getEmail());
		customer.setGender(customerDetails.getGender());
		return customerRepository.save(customer);
	}

	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
		customerRepository.delete(customer);
	}

	public Page<Customer> findByCity(String city, Pageable pageable) {
		return customerRepository.findByCity(city, pageable);
	}

	public Page<Customer> findByState(String state, Pageable pageable) {
		return customerRepository.findByStateInAddresses(state, pageable);
	}
}
