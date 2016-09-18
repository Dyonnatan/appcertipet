package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Presenca;
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
	
	public Participacao buscar(String cpf, Long idEvento) {	 
		return participacao.buscar(cpf, idEvento);
	}
	
	public Participacao buscar(Long id) {	 
		return participacao.findOne(id);
	}
	
	public List<Participacao> todasParticipacoesConfirmadas(Long idEvento) {	 
		return participacao.findarByEventoParticipantesValidos(idEvento);
	}
	
	public boolean salvar(Participacao p) {
		return participacao.save(p) != null;
	}

	public void remover(Participacao p) {
		participacao.delete(p);
	}
	
	public void remover(Long idPartipacao) {
		participacao.delete(idPartipacao);
	}

	public Participacao setPresenca(Participacao p) {
		
		return participacao.save(p);
	}

}
