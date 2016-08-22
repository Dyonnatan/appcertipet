package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Participacao;
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
		System.out.println(i);
		return (i!=null);
	}
	
	public boolean salvar(Participacao p) {
		return participacao.save(p) != null;
	}

}
