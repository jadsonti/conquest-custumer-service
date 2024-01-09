package com.example.api.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.api.domain.Address;
import com.example.api.response.PaginatedResponse;
import com.example.api.response.ViaCepResponse;
import com.example.api.service.AddressService;
import com.example.api.service.ViaCepService;
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

	private CustomerService customerService;
	private final AddressService addressService;
	private final ViaCepService viaCepService;


	@Autowired
	public CustomerController(CustomerService service, AddressService addressService, ViaCepService viaCepService) {
		this.customerService = service;
		this.addressService = addressService;
		this.viaCepService = viaCepService;
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<Customer>> findAll(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<Customer> customerPage = customerService.findAll(pageable);

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
		return customerService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@GetMapping("/search/name/{name}")
	public ResponseEntity<PaginatedResponse<Customer>> findByName(@PathVariable String name,
																  @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<Customer> customerPage = customerService.findByName(name, pageable);

		PaginatedResponse<Customer> response = new PaginatedResponse<>();
		response.setContent(customerPage.getContent());
		response.setPage(customerPage.getNumber());
		response.setSize(customerPage.getSize());
		response.setTotalElements(customerPage.getTotalElements());
		response.setTotalPages(customerPage.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/email/{email}")
	public List<Customer> findByEmail(@PathVariable String email) {
		return customerService.findByEmail(email);
	}

	@GetMapping("/search/gender/{gender}")
	public ResponseEntity<PaginatedResponse<Customer>> findByGender(@PathVariable String gender,
																	@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

		Page<Customer> customerPage = customerService.findByGender(gender, pageable);

		PaginatedResponse<Customer> response = new PaginatedResponse<>();
		response.setContent(customerPage.getContent());
		response.setPage(customerPage.getNumber());
		response.setSize(customerPage.getSize());
		response.setTotalElements(customerPage.getTotalElements());
		response.setTotalPages(customerPage.getTotalPages());

		return ResponseEntity.ok(response);
	}
	@PostMapping
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		return customerService.createCustomer(customer);
	}

	@PutMapping("update/{id}")
	public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customerDetails) {
		return customerService.updateCustomer(id, customerDetails);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/{customerId}/addresses")
	public ResponseEntity<?> addAddressesToCustomer(@PathVariable Long customerId, @RequestBody List<Address> addresses) {
		Optional<Customer> customerOptional = customerService.findById(customerId);

		if (!customerOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
		}

		Customer customer = customerOptional.get();
		List<Address> savedAddresses = new ArrayList<>();

		for (Address address : addresses) {
			if (address.getCep() != null) {
				ViaCepResponse viaCepResponse = viaCepService.getAddressByCep(address.getCep());
				if (viaCepResponse != null) {
					if (address.getStreet() == null) {
						address.setStreet(viaCepResponse.getLogradouro());
					}
					if (address.getCity() == null) {
						address.setCity(viaCepResponse.getLocalidade());
					}
					if (address.getState() == null) {
						address.setState(viaCepResponse.getUf());
					}
				}
			}

			address.setCustomer(customer);
			savedAddresses.add(addressService.saveAddress(address));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(savedAddresses);
	}



}
