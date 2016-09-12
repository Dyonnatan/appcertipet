package com.pet.certipet.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "respostas")
public class Resposta {

	private Long id;
	private String resposta;
	private Questionario pergunta;
	// private Instituicao intituicao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResposta() {
		return resposta;
	}
	
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	
	// @ManyToOne(optional=true)
	// public Instituicao getIntituicao() {
	// return intituicao;
	// }
	// public void setIntituicao(Instituicao intituicao) {
	// this.intituicao = intituicao;
	// }

	@ManyToOne(fetch = FetchType.LAZY)
	public Questionario getPergunta() {
		return pergunta;
	}

	public void setPergunta(Questionario pergunta) {
		this.pergunta = pergunta;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "";
	}
}
