package com.pet.certipet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "certificados")
public class Certificado //extends CertificadoDTO
{

	private Long id;
	private String nome;
	private Date data;
	private String arquivo;

	public Certificado() {
	}
	
	public Certificado(Long id, String nome, Date data) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Size(max = 65, message = "O nome não pode conter mais de 65 caracteres")
	@Column(length = 65, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// @Type(type="org.hibernate.type.StringClobType")
	// @Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	// @ManyToOne(optional=false,fetch=FetchType.LAZY)
	// public Evento getEvento() {
	// return evento;
	// }
	//
	// public void setEvento(Evento evento) {
	// this.evento = evento;
	// }


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
		if (!(obj instanceof Certificado))
			return false;
		Certificado other = (Certificado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int hashC() {
		final int prime = 31;
		int result = 1;
		System.currentTimeMillis();
		result = prime * result + ((data == null) ? 0 : (int) data.getTime());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
