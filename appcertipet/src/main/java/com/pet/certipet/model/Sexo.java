package com.pet.certipet.model;

public enum Sexo {

	M("Masculino"), F("Feminino");

	String descricao;

	private Sexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
