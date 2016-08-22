package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.Instituicao;

public interface InstituicaoRepo extends JpaRepository<Instituicao, Long> {

	List<Instituicao> findAll();
}
