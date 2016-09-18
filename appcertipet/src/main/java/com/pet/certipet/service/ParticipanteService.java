package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Participante;
import com.pet.certipet.repository.ParticipanteRepo;

@Service
public class ParticipanteService {

	@Autowired
	private ParticipanteRepo participante;

	public List<Participante> buscarTodos() {
		return participante.findAll();
	}

	public Participante buscar(String cpf) {
		return participante.findByCpf(cpf);
	}
}
