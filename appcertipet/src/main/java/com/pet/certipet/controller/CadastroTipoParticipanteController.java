package com.pet.certipet.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.service.AutenticacaoCertificadoService;
import com.pet.certipet.service.CertificadoPdf;
import com.pet.certipet.service.CertificadoService;
import com.pet.certipet.service.TipoParticipanteService;

@Controller
@RequestMapping(value = "/dashboard/tipoparticipante")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CadastroTipoParticipanteController {

	private final String CADASTRO_TIPO_PARTICIPANTE = "CadastroTipoParticipante";

	@Autowired
	private TipoParticipanteService tpService;

	@Autowired
	private CertificadoService certificadoService;

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView goCadastro(TipoParticipante t) {
		ModelAndView mv = new ModelAndView(CADASTRO_TIPO_PARTICIPANTE);
		mv.addObject("tipPart", t);
		mv.addObject("certif", new Certificado());
		return mv;
	}

	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public ResponseEntity<byte[]> emissaoPDF(@RequestParam("certif") Certificado certificado,
			 @RequestParam("texto") String texto) {

		Evento e = new Evento();
		e.setNome("Nome do Evento");
		e.setDataRealizacao(new Date());
		Participante p = new Participante();
		p.setCpf("123.456.789-01");
		p.setNome("Fulano");
		p.setSobrenome("de Sousa Júnior");
		p.setMatricula("201401122");
		TipoParticipante tp = new TipoParticipante();
		tp.setCargaHoraria("20 horas");
		tp.setValor("20 reais");
		System.out.println(texto);
		tp.setTextoCertificado(texto);
		Participacao part = new Participacao();
		part.setEvento(e);
		part.setParticipante(p);
		part.setTipoParticipante(tp);

		String urlHash = "http://www.certipet.ufg.com/autentica/7cf5f1c5962e75133eee71e21954bfe1";

		java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();

		try {
			CertificadoPdf cpdf = new CertificadoPdf();
			b = cpdf.gerar("Certificado", certificado, cpdf.formatar(urlHash,
					part.getParticipante(), part.getEvento(), part.getTipoParticipante()));
		} catch (IOException ev) {
			ev.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(b.toByteArray(), headers, HttpStatus.OK);
		return response;

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
}
