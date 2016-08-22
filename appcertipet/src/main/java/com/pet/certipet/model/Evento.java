package com.pet.certipet.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.httpclient.util.DateParseException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "eventos")
public class Evento {

	private Long id;
	private String nome;
	private TipoEvento tipo;
	private Date dataRealizacao;
	// horaRealizacao
	private String cargaHoraria;
	private String descricaoSimplificada;
	private String descricao;
	private String valor;
	private boolean encerrarInscricao;
	private Certificado certificado;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome é obrigatório")
	@Size(max = 65, message = "O nome não pode conter mais de 65 caracteres")
	@Column(length = 65, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Enumerated(EnumType.STRING)
	// @Column(length=40)
	@NotNull(message = "O tipo é obrigatório")
	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "Data de realização é obrigatória")
	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		try {
			this.dataRealizacao = dataRealizacao;
		} catch (Exception e) {

		}
	}

	@Size(max = 2500, message = "A descrição não pode conter mais de 2500 caracteres")
	@Column(length = 2501)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Size(max = 254, message = "A descrição simplificada não pode conter mais de 255 caracteres")
	public String getDescricaoSimplificada() {
		return descricaoSimplificada;
	}

	public void setDescricaoSimplificada(String descricaoSimplificada) {
		this.descricaoSimplificada = descricaoSimplificada;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isEncerrarInscricao() {
		return encerrarInscricao;
	}

	public void setEncerrarInscricao(boolean encerrarInscricao) {
		this.encerrarInscricao = encerrarInscricao;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
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
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private Certificado certView;
	
	@Transient
	public Certificado getCertView() {
		return certView;
	}
	
	public void setCertView(Certificado certView) {
		this.certView = certView;
	}
}
