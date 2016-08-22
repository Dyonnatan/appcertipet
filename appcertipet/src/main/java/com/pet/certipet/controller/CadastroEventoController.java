package com.pet.certipet.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

import com.pet.certipet.service.CertificadoService;
import com.pet.certipet.service.EventoService;
import com.pet.certipet.service.filter.EventoFilter;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.TipoEvento;

@Controller
@RequestMapping(value = "/dashboard/evento")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CadastroEventoController {

	private static final String MANUTENCAO_EVENTO = "CadastroEvento";

	@Autowired
	private EventoService eventoService;
//	@Autowired
//	private CertificadoDTOService certificadoDTOService;
	@Autowired
	private CertificadoService certificadoService;

	
//	@Secured({ "asRole('ADMINISTRADOR')" })
	@RequestMapping(value = "/manutencao", method = RequestMethod.GET)
	public ModelAndView goCadastro(Evento e) {
		ModelAndView mv = new ModelAndView(MANUTENCAO_EVENTO);
		// mv.addObject("tipos", Arrays.asList(TipoEvento.values()));
		mv.addObject("evento", e);
		mv.addObject("certifSelec", new Certificado());
		//mv.addObject(attributeName, attributeValue)
		//System.out.println(a.getPrincipal());
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return mv;
	}


//    @PreAuthorize("hasRole('ADMINISTRADOR')")
	@RequestMapping(value = "/manutencao", method = RequestMethod.POST)
	public ModelAndView salvar(@Validated @ModelAttribute(value="evento") Evento p, Errors result ,
	 RedirectAttributes attributes // @Requestparam @pathvariable
	) {
		if (result.hasErrors()) {
			return goCadastro(p);
		}

		try {
			if (p.getCertView().getId() != null) {
				System.out.println("Cert "+p.getCertView().getId()+" "+p.getCertView().getArquivo());
				p.setCertificado(p.getCertView());
			} else {
				p.getCertificado().setData(new Date());
				certificadoService.salvar(p.getCertificado());
			}System.out.println(p.getId());
			eventoService.salvar(p);
		} catch (Exception e) {
			// result.addError(new ObjectError("message", e.getMessage()));
			return goCadastro(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.clear();
		mv.setViewName(MANUTENCAO_EVENTO);
		mv.addObject("mensagem", "Evento salvo com sucesso!");
		mv.addObject(new Evento());
		// mv.addObject("tipos", Arrays.asList(TipoEvento.values()));
		return mv;
	}

	
	@ModelAttribute("todosTipos")
	public List<TipoEvento> todosTipos() {
		return Arrays.asList(TipoEvento.values());
	}

	@ModelAttribute("todosCertificados")
	public List<Certificado> todosCertificados() {
		List<Certificado> lista = certificadoService.buscaTodosSimple();
		Certificado ce = new Certificado();
		ce.setId(null);
		ce.setNome("Fazer upload");
		lista.add(0, ce);
		return lista;

	}

	@ModelAttribute("eventos")
	public List<Evento> listarEventos() {
		List<Evento> lista = eventoService.buscarTodos();
		return lista;
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") EventoFilter filtro) {
		List<Evento> todosTitulos = eventoService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaEventos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView edicao(@PathVariable("codigo") Evento evento) {
		return goCadastro(evento);
	}
	
	@RequestMapping(value="/del", method = RequestMethod.POST)
	public String exclui(@RequestParam Long id, RedirectAttributes attributes) {
		eventoService.excluir(id);
		attributes.addFlashAttribute("mensagem", "Evento "+id+" excluído com sucesso!");
		return "redirect:/dashboard/evento";
	}
	
//	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
//	public @ResponseBody String receber(@PathVariable Long id) {
//		return eventoService.receber(id);
//	}
	

	// @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	// public @ResponseBody String receber(@PathVariable Long codigo) {
	// return "ola recebe";
	// }

}
