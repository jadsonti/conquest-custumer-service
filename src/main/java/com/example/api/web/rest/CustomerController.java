package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@GetMapping("/search/name/{name}")
	public List<Customer> findByName(@PathVariable String name) {
		return service.findByName(name);
	}

	@GetMapping("/search/email/{email}")
	public List<Customer> findByEmail(@PathVariable String email) {
		return service.findByEmail(email);
	}

	@GetMapping("/search/gender/{gender}")
	public List<Customer> findByGender(@PathVariable String gender) {
		return service.findByGender(gender);
	}

	@PostMapping
	public Customer createCustomer(@RequestBody Customer customer) {
		return service.createCustomer(customer);
	}

	@PutMapping("update/{id}")
	public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
		return service.updateCustomer(id, customerDetails);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		service.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}

}
