package com.pet.certipet.model;

public enum TipoEvento {

	EVENTO("Evento"), COLOQUIO("Colóquio"), VISITA_INSTITUCIONAL("Visita institucional"), CONDECORACAO(
			"Condecoração"), HOMENAGEM_PREMIACAO("Homenagens e premiações"), INAUGURACAO(
					"Inauguração"), TECNICO_CIENTIFICO("Evento Técnico-Científico"), CICLO_DE_PALESTRAS(
							"Ciclo de palestras"), CONFERENCIA("Conferência"), CONGRESSO("Congresso"), FORUM(
									"Fórum"), MESA_REDONDA("Mesa redonda"), PAINEL("Painel"), REUNIAO(
											"Reunião"), SEMANA("Semana"), SEMINARIO("Seminário"), SIMPOSIO(
													"Simpósio"), WORKSHOP("Workshop"), CONVENCAO("Convenção"), FEIRA(
															"Feira"), EXPOSICAO("Exposição"), MOSTRA(
																	"Mostra"), EVENTO_CULTURAL(
																			"EVENTO CULTURAL"), CONCURSO(
																					"Concurso"), TORNEIO("Torneio");

	String tipo;

	private TipoEvento(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
