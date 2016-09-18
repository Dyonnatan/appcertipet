package com.pet.certipet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Participacao;

public interface AutenticaCertificadoRepo extends JpaRepository<AutenticacaoCertificado, String> {

	AutenticacaoCertificado findByParticipacao(Participacao id);

}
