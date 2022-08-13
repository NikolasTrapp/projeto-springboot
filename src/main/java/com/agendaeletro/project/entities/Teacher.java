package com.agendaeletro.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.agendaeletro.project.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String role;

	@JsonIgnore
	@OneToMany(mappedBy = "teacher")
	private List<Scheduling> schedulings = new ArrayList<>();

	// construtor padrão
	public Teacher() {
	}

	// Sobrecarga de construtor usando todos os atributos
	public Teacher(Long id, String name, String email, String password, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		setRole(role);
	}

	@Override
	public String toString() {
		return String.format("Teacher: id=%s | name=%s | email=%s | password=%s | role=%s", id, name, email, password,
				role);
	}

	// Getters e Seters (para pegar ou atribuir atributos)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return Role.valueOf(role);
	}

	public void setRole(Role role) {
		if (role != null) {
			this.role = role.getRole();
		}
	}

	public void addScheduling(Scheduling scheduling) {
		this.schedulings.add(scheduling);
	}

	public List<Scheduling> getSchedulings() {
		return schedulings;
	}

	// Método hashcode para comparar objtos usando seu codigo Hash (mais rapido
	// porem menos preciso)
	@Override
	public int hashCode() {
		return Objects.hash(email, name, password);
	}

	// Método equals para comparar objetos (mais lento porém mais preciso)
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

}
