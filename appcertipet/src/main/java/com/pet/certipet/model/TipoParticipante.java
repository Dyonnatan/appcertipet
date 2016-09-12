package com.pet.certipet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "tipos_participante")
public class TipoParticipante {

	private Long id;
	private String nome;
	private String cargaHoraria;
	private String valor;
	private String textoCertificado;
	private boolean exibir;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome é obrigatório")
	@Size(max = 45, message = "O nome não pode conter mais de 45 caracteres")
	@Column(length = 45, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Size(max = 45, message = "A carga horaria não pode conter mais de 45 caracteres")
	@Column(length = 45)
	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	@Size(max = 45, message = "O valor não pode conter mais de 45 caracteres")
	@Column(length = 45)
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Size(max = 500, message = "O texto do certificado não pode conter mais de 500 caracteres")
	@Column(length = 500)
	public String getTextoCertificado() {
		return textoCertificado;
	}
	
	public void setTextoCertificado(String textoCertificado) {
		this.textoCertificado = textoCertificado;
	}
	
	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TipoParticipante other = (TipoParticipante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoParticipante " + nome + ", " + cargaHoraria;
	}

}
