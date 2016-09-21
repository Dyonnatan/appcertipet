package com.pet.certipet.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Evento;
import com.pet.certipet.repository.EventoRepo;
import com.pet.certipet.service.filter.EventoFilter;

@Service
public class EventoService {

	@Autowired
	private EventoRepo eventoR;
	// @Autowired
	// private UsuarioRepo usuarioRepo;

	@Autowired
	EntityManagerFactory emf;

	// public Participante buscar(String cpf) {
	// return participante.findByCpf(cpf);
	// }
	//
	// public Page<Participante> buscaPaginada(int page, int size) {
	// return participante.findAll(new PageRequest(page, size));
	// }

	public Evento salvar(Evento e) {
		return eventoR.save(e);
	}

	public List<Evento> buscarDisponiveis() {
		return eventoR.findEventosDisponiveis();
	}
	
	public List<Evento> buscarNaoRealizados() {
		return eventoR.findEventosNaoRealizados();
	}
	
	public List<Evento> buscarTodos() {
		return eventoR.findAll();
	}
	
	public Evento buscar(Long id) {
		return eventoR.findOne(id);
	}
	
	public Evento buscar(Long id, String nome) {
		return eventoR.findOne(id);
	}

	public void excluir(Long id) {
		eventoR.delete(id);
	}

	public List<Evento> filtrar(EventoFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		String ch = filtro.getCargaHoraria() == null ? "%" : filtro.getCargaHoraria();
		String valor = filtro.getValor() == null ? "%" : filtro.getValor();
		
		return eventoR.findInEventoIgnoreCaseContaining(nome, ch, valor);
	}

	
}
