package com.pet.certipet.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.PersistenceException;

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

import com.pet.certipet.controller.semimodel.Participacoes;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Pagamento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.Presenca;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.service.EventoService;
import com.pet.certipet.service.ParticipacaoService;
import com.pet.certipet.service.ParticipanteService;

@Controller
@RequestMapping(value = { "/participante/evento" })
public class ListaEventosInscricao {

	private final String PESQUISA_EVENTO_VIEW = "PesquisaEventosDisponiveis";
	private final String INSCREVER_EVENTO_VIEW = "EventoInscricao";
	private final String INSCRITO_EVENTO_VIEW = "EventoInformacao";

	@Autowired
	private EventoService eventoService;
	@Autowired
	private ParticipacaoService participacaoService;
	@Autowired
	private ParticipanteService participanteService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listarEventos(Principal p) {

		List<Evento> todosEventos = eventoService.buscarNaoRealizados();

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

		if (evento == null || !evento.getNome().equalsIgnoreCase(nome)
		/* || evento.isEncerrarInscricao() */) {
			return listarEventos(principal);
		}

		String cpf = principal.getName();
		List<Participacao> p = participacaoService.buscar(cpf, id);
		if (p == null || p.isEmpty()) {			
			for (TipoParticipante part : evento.todasCategoriasExibir()) {
				Participacao partc = new Participacao();
				partc.setCategoriaParticipante(part);				
			}
			evento.getCategoriasParticipantes();
			ModelAndView mv = new ModelAndView(INSCREVER_EVENTO_VIEW);
			mv.addObject("oparticipacoes", new Participacoes(null, evento));
			// mv.addObject("parts", p);
			return mv;
		} else if (!verificaPagamento(p)) {
			List<TipoParticipante> cp = new LinkedList<TipoParticipante>();
			for (Participacao part : p) {
				cp.add(part.getCategoriaParticipante());
			}
			ModelAndView mv = new ModelAndView(INSCREVER_EVENTO_VIEW);
			mv.addObject("oparticipacoes", new Participacoes(cp, evento));
			// mv.addObject("parts", p);
			return mv;
		} else {
			return eventoEmAndamento(p, evento);
		}

	}

	private boolean verificaPagamento(List<Participacao> p) {
		for (Participacao participacao : p) {
			if (!participacao.getPagamento().equals(Pagamento.A)) {
				return true;
			}
		}
		return false;
	}

	private ModelAndView eventoEmAndamento(List<Participacao> participacoes, Evento evento) {

		if (participacoes == null) {
			participacoes = new LinkedList<>();
		}
		ModelAndView mv = new ModelAndView(INSCRITO_EVENTO_VIEW);
		mv.addObject("parts", participacoes);
		mv.addObject("evento", evento);

		return mv;
	}

	@RequestMapping(value = "/inscricao", method = RequestMethod.POST, params = "action=inscrever")
	public ModelAndView enviarPedido(@Validated @ModelAttribute(value = "oparticipacoes") Participacoes participacoes,
			Errors result, RedirectAttributes attributes, Principal principal) {
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação."+Arrays.asList(result.getAllErrors()));
			System.out.println(result);
			return mv;
		}

		String cpf = principal.getName();
		Participante particip = participanteService.buscar(cpf);

		if (particip == null) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação. Usuário não foi identificado.");
			return mv;
		}

		if (participacoes.getEvento().isEncerrarInscricao()) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação. Inscrições encerradas.");
			return mv;
		}

		List<TipoParticipante> cats = participacoes.getCategorias();
		List<Participacao> parts = new LinkedList<>();
		if (cats == null) {
			parts.add(new Participacao());
			parts.get(0).setEvento(participacoes.getEvento());
			parts.get(0).setCategoriaParticipante(participacoes.getEvento().getCategoriasParticipantes().get(0));
			parts.get(0).setParticipante(particip);
			parts.get(0).setPresenca(Presenca.AUSENTE);
			parts.get(0).setPagamento(Pagamento.A);
		} else {
			for (int i = 0; i < cats.size(); i++) {
				parts.add(i, new Participacao());
				parts.get(i).setEvento(participacoes.getEvento());
				parts.get(i).setParticipante(particip);
				parts.get(i).setCategoriaParticipante(cats.get(i));
				parts.get(i).setPresenca(Presenca.AUSENTE);
				parts.get(i).setPagamento(Pagamento.A);
			}
		}

		

			try {
				if (participacaoService.salvar(parts)) {
					ModelAndView mv = new ModelAndView("ConfirmarInscricao");
					StringBuilder mensagem = new StringBuilder("Solicitação de inscrição enviada com sucesso no evento ");
					mensagem.append(participacoes.getEvento().getNome());
					mensagem.append(" como ");
					for (int i = 0; i < parts.size() - 1; i++) {
						mensagem.append(" ");
						mensagem.append(parts.get(i).getCategoriaParticipante().getNome());
						mensagem.append(",");
					}
					mensagem.deleteCharAt(mensagem.length() - 1);
					if (parts.size() != 1)
						mensagem.append(" e ");
					int ultimo = parts.size() - 1;
					mensagem.append(parts.get(ultimo).getCategoriaParticipante().getNome());
					mensagem.append('.');
					mv.addObject("mensagem", mensagem);
					return mv;
				}
			} catch (PersistenceException e) {
				ModelAndView mv = new ModelAndView("ConfirmarInscricao");
				mv.addObject("mensagem", "Não foi possível realizar sua solicitação.");
				return mv;
			} catch (Exception e) {
				ModelAndView mv = new ModelAndView("ConfirmarInscricao");
				mv.addObject("mensagem", "Ocorreu um erro inesperado. Contate os organizadores do evento.");
				return mv;
			}

		
		return listarEventos(principal);
	}

	@RequestMapping(value = "/inscricao", method = RequestMethod.POST, params = "action=desinscrever")
	public ModelAndView cancelarPedido(@Validated @ModelAttribute(value = "oparticipacoes") Participacoes participacoes,
			Errors result, RedirectAttributes attributes, Principal principal) {

		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação.");
			return mv;
		}

		String cpf = principal.getName();
		Participante particip = participanteService.buscar(cpf);

		if (particip == null) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação. Usuário não foi identificado.");
			return mv;
		}

		if (participacoes == null || participacoes.getEvento() == null) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação. O evento não foi encontrado.");
			return mv;
		}

		try {
			participacaoService.remover(particip, participacoes.getEvento());
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Inscrição cancelada com sucesso");
			return mv;
		} catch (Exception e) {
			ModelAndView mv = new ModelAndView("ConfirmarInscricao");
			mv.addObject("mensagem", "Não foi possível realizar sua solicitação. Erro interno durante o cancelamento.");
			return mv;
		}

	}
}
