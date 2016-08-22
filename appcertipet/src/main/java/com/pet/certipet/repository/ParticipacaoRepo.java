package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pet.certipet.model.Participacao;

public interface ParticipacaoRepo extends JpaRepository<Participacao, Long> {

	@Query(value = "select p from Participacao p where p.presenca = 'PRESENTE' and p.participante.cpf = ?1")
	List<Participacao> findarByParticipante(String cpf);
	
	@Query(value = "select distinct 1 from Participacao p where p.evento.id = ?2 and p.participante.cpf = ?1")
	Integer verificarInscrito(String cpf, Long idEvento);

}
