package com.pet.certipet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Curso;
import com.pet.certipet.repository.CursoRepo;

@Service
public class CursoService {

	@Autowired
	private CursoRepo curso;

	public List<Curso> buscarTodos() {
		return curso.findAll();
	}

}
