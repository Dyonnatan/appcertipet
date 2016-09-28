package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.repository.ParticipacaoRepo;

@Service
public class ParticipacaoService {

	@Autowired
	private ParticipacaoRepo participacao;

	public List<Participacao> filtrar(String cpf) {
		return participacao.findarByParticipante(cpf);
	}
	
	public boolean verificarInscrito(String cpf, Long idEvento) {
		Integer i = participacao.verificarInscrito(cpf, idEvento);
		return (i!=null);
	}
	
	public List<Participacao> buscar(String cpf, Long idEvento) {	 
		return participacao.buscar(cpf, idEvento);
	}
	
	public List<TipoParticipante> buscarCategoria(String cpf, Long idEvento) {	 
		return participacao.buscarCategoria(cpf, idEvento);
	}
	
	public Participacao buscar(Long id) {	 
		return participacao.findOne(id);
	}
	
	public List<Participacao> todasParticipacoesConfirmadas(Long idEvento) {	 
		return participacao.findarByEventoParticipantesValidos(idEvento);
	}
	
	public List<Participacao> todasParticipacoes(Long idEvento) {	
		Evento e = new Evento();
		e.setId(idEvento);
		return participacao.findByEvento(e);
	}
	
	public List<Participacao> todasParticipacoesDistintasPorParticipante(Long idEvento) {	
		Evento e = new Evento();
		e.setId(idEvento);
		return participacao.findDistinctParticipanteByEvento(e);
	}
	
	public boolean salvar(Participacao p) {
		return participacao.save(p) != null;
	}

	public boolean salvar(List<Participacao> p) {
		return participacao.save(p) != null;
	}
	public void remover(Participacao p) {
		participacao.delete(p);
	}
	
	public void remover(Participante p, Evento e) {
		participacao.deleteByParticipanteAndEvento(p, e);
	}
	
	public void remover(Long idPartipacao) {
		participacao.delete(idPartipacao);
	}

	public Participacao setagem(Participacao p) {
		
		return participacao.save(p);
	}

	public void setagem(List<Participacao> par) {
		
		participacao.save(par);
	}

	public List<Participacao> todasParticipacoesConfirmadasPorNome(Long idevento) {
		return participacao.findarByEventoOrderByNome(idevento);
	}


}
