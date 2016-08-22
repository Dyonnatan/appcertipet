package com.pet.certipet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {

	private Long id;
	private String nome;
	// private Instituicao intituicao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// @ManyToOne(optional=true)
	// public Instituicao getIntituicao() {
	// return intituicao;
	// }
	// public void setIntituicao(Instituicao intituicao) {
	// this.intituicao = intituicao;
	// }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "";
	}
}
