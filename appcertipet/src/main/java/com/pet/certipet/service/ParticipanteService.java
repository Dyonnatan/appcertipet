package com.pet.certipet.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Nivel;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.Usuario;
import com.pet.certipet.repository.ParticipanteRepo;
import com.pet.certipet.repository.UsuarioRepo;

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
