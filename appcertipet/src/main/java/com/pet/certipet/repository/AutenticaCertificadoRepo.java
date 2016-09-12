package com.pet.certipet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.AutenticacaoCertificado;

public interface AutenticaCertificadoRepo extends JpaRepository<AutenticacaoCertificado, String> {

	AutenticacaoCertificado findByParticipacao(Long id);

}
