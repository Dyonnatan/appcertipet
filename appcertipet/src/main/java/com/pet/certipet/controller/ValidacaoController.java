package com.pet.certipet.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.service.AutenticacaoCertificadoService;
import com.pet.certipet.service.CertificadoPdf;
import com.pet.certipet.service.CertificadoService;
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
		mv.addObject("participacao", autent);
		return mv;
	}

}
