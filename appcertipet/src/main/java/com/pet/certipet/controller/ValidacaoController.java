package com.pet.certipet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.service.AutenticacaoCertificadoService;
import com.pet.certipet.service.ParticipacaoService;

@Controller
@RequestMapping("/autentica")
public class ValidacaoController {

	private final String VALIDA_CERTIFICADOS_VIEW = "Autenticacao";

	@Autowired
	private AutenticacaoCertificadoService autenticServ;

	@RequestMapping(value = "/{codigo}", method=RequestMethod.GET)
	public ModelAndView pesquisar(@PathVariable String codigo) {
		AutenticacaoCertificado autent = autenticServ.buscar(codigo);
		ModelAndView mv = new ModelAndView(VALIDA_CERTIFICADOS_VIEW);
		mv.addObject("p", autent);
		return mv;
	}

}
