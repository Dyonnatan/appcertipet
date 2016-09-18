package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.repository.TipoParticipanteRepo;;

@Service
public class TipoParticipanteService {

	@Autowired
	private TipoParticipanteRepo tpRepo;

	public List<TipoParticipante> buscarTodos() {
		return tpRepo.findAll();
	}
	
	public List<TipoParticipante> buscarTodosOrdenado() {
		return tpRepo.findAllByOrderByIdDesc();
	}
	
	public TipoParticipante salvar(TipoParticipante tp) {
		return tpRepo.save(tp);
	}

	public void excluir(Long id) {
		tpRepo.delete(id);
	}

}
