package com.pet.certipet.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.Participante;

@Transactional
public interface ParticipanteRepo extends JpaRepository<Participante, Long> {

	Participante findByCpf(String cpf);
	// Participante saveAndFlush(Participante p);

}
