package com.pet.certipet.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Pagamento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Presenca;
import com.pet.certipet.service.EventoService;
import com.pet.certipet.service.ParticipacaoService;

@Controller
@RequestMapping(value = "/dashboard/controle")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class ControleParticipacaoController {

	private static final String SEL_EVENTO = "PesquisaEventosDisponiveisControle";
	private static final String MANUTENCAO_EVENTO = "ControleParticipacoes";
	private static final String VALIDA_INSCRICAO = "ControleValidaInscricao";

	@Autowired
	private EventoService eventoService;
	@Autowired
	private ParticipacaoService participacaoService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView selecionarEvento() {
		ModelAndView mv = new ModelAndView(SEL_EVENTO);
		// mv.addObject("tipos", Arrays.asList(TipoEvento.values()));
		List<Evento> eventos = eventoService.buscarDisponiveis();
		mv.addObject("eventos", eventos);
		return mv;
	}

	// @PreAuthorize("hasRole('ADMINISTRADOR')")
	@RequestMapping(value = "/manutencao", method = RequestMethod.POST)
	public ModelAndView salvar(@Validated @ModelAttribute(value = "evento") Evento p, Errors result,
			RedirectAttributes attributes // @Requestparam @pathvariable
	) {

		ModelAndView mv = new ModelAndView();
		mv.clear();
		mv.setViewName(MANUTENCAO_EVENTO);
		mv.addObject("mensagem", "Evento salvo com sucesso!");
		mv.addObject(new Evento());
		// mv.addObject("tipos", Arrays.asList(TipoEvento.values()));
		return mv;
	}

	@RequestMapping(value = "/{id}/validarinscricoes", method = RequestMethod.GET)
	public ModelAndView validarincricao(@PathVariable("id") Long idEvento) {

		List<Participacao> parts = participacaoService.todasParticipacoes(idEvento);

		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getPresenca() == null)
				parts.get(i).setPresenca(Presenca.INDEFINIDO);
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName(VALIDA_INSCRICAO);
		mv.addObject("participacoes", parts);
		mv.addObject("pagamentos", Pagamento.values());
		return mv;
	}

	// @ModelAttribute("eventos")
	// public List<Evento> listarEventos() {
	// List<Evento> lista = eventoService.buscarTodos();
	// return lista;
	// }

	// @RequestMapping
	// public ModelAndView pesquisar(@ModelAttribute("filtro") EventoFilter
	// filtro) {
	// List<Evento> todosTitulos = eventoService.filtrar(filtro);
	//
	// ModelAndView mv = new ModelAndView("PesquisaEventos");
	// mv.addObject("titulos", todosTitulos);
	// return mv;
	// }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView edicao(@PathVariable("id") Long idEvento) {
		List<Participacao> parts = participacaoService.todasParticipacoesConfirmadas(idEvento);
		System.out.println(Arrays.asList(parts));

		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getPresenca() == null)
				parts.get(i).setPresenca(Presenca.INDEFINIDO);
		}

		ModelAndView mv = new ModelAndView();
		mv.setViewName(MANUTENCAO_EVENTO);
		mv.addObject("participacoes", parts);
		mv.addObject("pagamentos", Pagamento.values());
		return mv;
	}

	@RequestMapping(value = "pagamento/{p}/{id}", method = RequestMethod.GET)
	public String edicao(@PathVariable("id") Participacao p, @PathVariable("p") Pagamento pag) {
		p.setPagamento(pag);
		participacaoService.setagem(p);
		return "redirect:/dashboard/controle/" + p.getEvento().getId()+"/validarinscricoes";
	}
	
	@RequestMapping(value = "pagamento/todas/{id}", method = RequestMethod.GET)
	public String pagartodas(@PathVariable("id") Long idEvento) {
		List<Participacao>par= participacaoService.todasParticipacoes(idEvento);
		for (Participacao p : par) {
			p.setPagamento(Pagamento.P);
		}
		participacaoService.setagem(par);
		return "redirect:/dashboard/controle/" + idEvento+"/validarinscricoes";
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String exclui(@RequestParam Long id, RedirectAttributes attributes) {
		eventoService.excluir(id);
		attributes.addFlashAttribute("mensagem", "Evento " + id + " excluído com sucesso!");
		return "redirect:/dashboard/evento";
	}

	@RequestMapping(value = "/{id}/{presente}/presenca", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable(value = "id") Participacao p,
			@PathVariable(value = "presente") Presenca presente) {
		p.setPresenca(presente);
		return participacaoService.setagem(p).getPresenca().toString();
	}

	@RequestMapping(value = "/{id}/{presente}/presenca", method = RequestMethod.GET)
	public String recebe(@PathVariable(value = "id") Participacao p,
			@PathVariable(value = "presente") Presenca presente) {
		p.setPresenca(presente);
		participacaoService.setagem(p).getPresenca().toString();
		return "redirect:/dashboard/controle/" + p.getEvento().getId();
	}

	// @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	// public @ResponseBody String receber(@PathVariable Long codigo) {
	// return "ola recebe";
	// }

}
