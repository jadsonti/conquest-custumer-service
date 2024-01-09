package com.example.api.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
	private String name;

	@Column(nullable = false)
	@NotEmpty
	@Email(message = "O email deve ser válido")
	private String email;

	@Column(nullable = false)
	@NotEmpty(message = "O gênero não deve estar vazio")
	@Pattern(regexp = "M|F|Outro", message = "Gênero deve ser 'M', 'F' ou 'Outro'")
	private String gender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
