package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();
	List<Customer> findByNameContainingIgnoreCase(String name);
	List<Customer> findByEmailContainingIgnoreCase(String email);
	List<Customer> findByGenderIgnoreCase(String gender);
}
