package com.pet.certipet.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.service.EventoService;
import com.pet.certipet.service.ParticipacaoService;
import com.pet.certipet.service.ParticipanteService;

@Controller
@RequestMapping(value = { "/participante/evento" })
public class ListaEventosInscricao {

	private final String PESQUISA_EVENTO_VIEW = "PesquisaEventosDisponiveis";
	private final String INSCREVER_EVENTO_VIEW = "EventoInscricao";

	@Autowired
	private EventoService eventoService;
	@Autowired
	private ParticipacaoService participacaoService;
	@Autowired
	private ParticipanteService participanteService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listarEventosDisponiveis(Principal p) {

		List<Evento> todosEventos = eventoService.buscarDisponiveis();

		if (todosEventos.size() == 1) {
			return verEvento(todosEventos.get(0).getId(), todosEventos.get(0).getNome(), p);
		}
		ModelAndView mv = new ModelAndView(PESQUISA_EVENTO_VIEW);
		mv.addObject("eventos", todosEventos);
		return mv;
	}

	@RequestMapping(value = "/inscricao/{codigo}/{nome}", method = RequestMethod.GET)
	public ModelAndView verEvento(@PathVariable("codigo") long id, @PathVariable("nome") String nome,
			Principal principal) {

		Evento evento = eventoService.buscar(id, nome);

		if (evento == null || evento.isEncerrarInscricao()) {
			return listarEventosDisponiveis(principal);
		}
		String inscricao = "Inscrever-me";
		String cpf = principal.getName();
		Participacao p = participacaoService.buscar(cpf, id);
		if (p != null) {
			inscricao = "Desinscrever-me";
		} else {
			p = new Participacao();
		}
		p.setEvento(evento);
		ModelAndView mv = new ModelAndView(INSCREVER_EVENTO_VIEW);
		mv.addObject("part", p);
		mv.addObject("inscricao", inscricao);
		return mv;
	}

	@RequestMapping(value = "/inscricao", method = RequestMethod.POST)
	public ModelAndView enviarPedido(@Validated @ModelAttribute(value = "part") Participacao p, Errors result,
			RedirectAttributes attributes, Principal principal) {
		if (result.hasErrors()) {
			System.out.println(1);
			return listarEventosDisponiveis(principal);
		}

		if (p == null || p.getEvento() == null || p.getEvento().isEncerrarInscricao()) {
			System.out.println(2);
			return listarEventosDisponiveis(principal);
		}
		System.out.println(3);
		String cpf = principal.getName();

		Participante particip = participanteService.buscar(cpf);

		System.out.println(particip);
		if (particip == null) {
			return listarEventosDisponiveis(principal);
		}
		System.out.println(4);
		p.setParticipante(particip);

		if (participacaoService.salvar(p)) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Inscrito com sucesso");
			return mv;
		}

		return listarEventosDisponiveis(principal);
	}
}
