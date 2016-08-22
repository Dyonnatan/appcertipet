package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.Curso;

public interface CursoRepo extends JpaRepository<Curso, Long> {

	List<Curso> findAll();
}
