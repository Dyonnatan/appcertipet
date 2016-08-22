package com.pet.certipet.model;

public enum Presenca {

	PRESENTE("Presente"), AUSENTE("Ausente"), DESISTENTE("Desistente"), INDEFINIDO("Indefinido");

	String confirmacao;

	private Presenca(String confirmacao) {
		this.confirmacao = confirmacao;
	}

	@Override
	public String toString() {
		return confirmacao;
	}
}
