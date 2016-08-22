package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Instituicao;
import com.pet.certipet.repository.InstituicaoRepo;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepo instituicao;

	// public Participante buscar(String cpf) {
	// return participante.findByCpf(cpf);
	// }
	//
	// public Page<Participante> buscaPaginada(int page, int size) {
	// return participante.findAll(new PageRequest(page, size));
	// }

	public List<Instituicao> buscarTodos() {
		return instituicao.findAll();
	}

}
