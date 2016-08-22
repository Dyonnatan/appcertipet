package com.pet.certipet.model;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "id", nullable = false, updatable = false)
	// private Long id;

	@Id
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "senha", nullable = true)
	private String senha;

	@Column(name = "nivel", nullable = false)
	@Enumerated(EnumType.STRING)
	private Nivel nivel;

	@Column(nullable = false)
	private Boolean enabled;

	// public Long getId() {
	// return id;
	// }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public String getNivelToString() {
		if (nivel != null)
			return nivel.toString();
		return Nivel.ROLE_PARTICIPANTE.toString();
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User{" + "email='"/* + email.replaceFirst("@.*", "@***") */ + ", passwordHash='" + senha + ", role="
				+ nivel + '}';
	}
}
