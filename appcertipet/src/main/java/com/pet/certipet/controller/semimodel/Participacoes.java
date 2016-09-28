package com.pet.certipet.controller.semimodel;

import java.util.List;

import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.TipoParticipante;

public class Participacoes {

//	private List<Participacao> participacoes;
	private List<TipoParticipante> categorias;
	private Evento evento;
	
	public Participacoes() {
		// TODO Auto-generated constructor stub
	}
	
	public Participacoes(List<TipoParticipante> categorias, Evento evento) {
		super();
		this.categorias = categorias;
		this.evento = evento;
	}
	
//	public List<Participacao> getParticipacoes() {
//		return participacoes;
//	}
//	public void setParticipacoes(List<Participacao> participacoes) {
//		this.participacoes = participacoes;
//	}
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorias == null) ? 0 : categorias.hashCode());
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
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
		Participacoes other = (Participacoes) obj;
		if (categorias == null) {
			if (other.categorias != null)
				return false;
		} else if (!categorias.equals(other.categorias))
			return false;
		if (evento == null) {
			if (other.evento != null)
				return false;
		} else if (!evento.equals(other.evento))
			return false;
		return true;
	}

	public List<TipoParticipante> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<TipoParticipante> categorias) {
		this.categorias = categorias;
	}
	
	
}
