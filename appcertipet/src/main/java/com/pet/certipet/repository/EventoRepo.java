package com.pet.certipet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pet.certipet.model.Evento;

public interface EventoRepo extends JpaRepository<Evento, Long> {

	List<Evento> findAll();

	@Query("select e from Evento e where e.nome like %?1% and e.cargaHoraria like %?2% and e.valor like %?3%")
	List<Evento> findInEventoIgnoreCaseContaining(String nome, String ch, String valor);
	
	@Query("select e from Evento e where e.dataRealizacao >= CURRENT_DATE ORDER BY e.descricaoSimplificada")
	List<Evento> findEventosDisponiveis();
}
