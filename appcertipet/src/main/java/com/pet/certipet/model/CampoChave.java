package com.pet.certipet.model;

public enum CampoChave {

	NOME("@nome@"),
	EVENTO("@evento@"),
	DATA_EVENTO("@data@"),
	DATA_EVENTO_INGLES("@date@"),
	CARGA_HORARIA("@ch@"),
	DATA_ATUAL("@gerar_data@"),
	MATRICULA("@matricula@"),
	CPF("@cpf@"),
	AUTENTICA("@autent@"),
	PARAGRAFO("@parag@"),
	FONTE("@fonte@"),
	WIDTH("@width@");
	
	private String chave;
	
	public String getChave() {
		return chave;
	}
	
	private CampoChave(String chave) {
		this.chave = chave;
	}
	
	
}
