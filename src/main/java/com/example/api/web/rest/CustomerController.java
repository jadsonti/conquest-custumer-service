package com.example.api.web.rest;

import java.util.List;

import com.example.api.response.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<Customer>> findAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<Customer> customerPage = service.findAll(pageable);

		PaginatedResponse<Customer> response = new PaginatedResponse<>();
		response.setContent(customerPage.getContent());
		response.setPage(customerPage.getNumber());
		response.setSize(customerPage.getSize());
		response.setTotalElements(customerPage.getTotalElements());
		response.setTotalPages(customerPage.getTotalPages());

		return ResponseEntity.ok(response);
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
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		return service.createCustomer(customer);
	}

	@PutMapping("update/{id}")
	public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customerDetails) {
		return service.updateCustomer(id, customerDetails);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
		service.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}


}
