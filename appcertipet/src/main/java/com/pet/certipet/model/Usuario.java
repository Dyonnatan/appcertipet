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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "email='"/* + email.replaceFirst("@.*", "@***") */ + ", passwordHash='" + senha + ", role="
				+ nivel + '}';
	}
}
