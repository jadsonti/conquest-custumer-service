package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findAllByOrderByNameAsc();
	List<Customer> findByEmailContainingIgnoreCase(String email);
	Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Customer> findByGenderIgnoreCase(String gender, Pageable pageable);


}
