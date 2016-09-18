package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.TipoParticipante;

public interface TipoParticipanteRepo extends JpaRepository<TipoParticipante, Long> {

	List<TipoParticipante> findAllByOrderByIdDesc();

}
