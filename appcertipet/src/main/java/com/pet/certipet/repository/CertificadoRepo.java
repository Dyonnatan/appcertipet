package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Participacao;

public interface CertificadoRepo extends JpaRepository<Certificado, Long> {

	// Certificado findByEvento(Long idEvento);

	// Certificado findOneByEvento(Evento Evento);
	@Query(value = "select c from Participacao p, Certificado c where p.evento.id = ?1 and p.evento.certificado.id = c.id")
	 Certificado findEvento(Long idEvento);

//	@Query("select c.id, c.nome, c.data from Certificado c ")
//	List<CertificadoDTO> findAllSimples();
	
	@Query("select new com.pet.certipet.model.Certificado(c.id, c.nome, c.data) from Certificado c ")
	List<Certificado> findAllSimple();
	
	@Override
	 List<Certificado> findAll();
	
}
