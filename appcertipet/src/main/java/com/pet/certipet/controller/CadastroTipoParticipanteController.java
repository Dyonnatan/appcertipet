package com.pet.certipet.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.service.CertificadoPdf;
import com.pet.certipet.service.CertificadoService;
import com.pet.certipet.service.TipoParticipanteService;

@Controller
@RequestMapping(value = "/dashboard/tipoparticipante")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CadastroTipoParticipanteController {

	private final String CADASTRO_TIPO_PARTICIPANTE = "CadastroTipoParticipante";
	private final String PESQUISA_TIPO_PARTICIPANTE = "PesquisaTipoParticipante";

	@Autowired
	private TipoParticipanteService tpService;

	@Autowired
	private CertificadoService certificadoService;

	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView goLista() {
		ModelAndView mv = new ModelAndView(PESQUISA_TIPO_PARTICIPANTE);
		List<TipoParticipante> tp = tpService.buscarTodos();
		mv.addObject("tipParts", tp);
		return mv;
	}
	
	@RequestMapping(value = "/manutencao", method = RequestMethod.GET)
	public ModelAndView goCadastrar(TipoParticipante t) {
		ModelAndView mv = new ModelAndView(CADASTRO_TIPO_PARTICIPANTE);
		mv.addObject("tipPart", t);
		mv.addObject("certif", new Certificado());
		return mv;
	}
	
	@RequestMapping(value = "/manutencao", method = RequestMethod.POST)
	public ModelAndView goCadastro(TipoParticipante t) {
		if(tpService.salvar(t)!=null){
			ModelAndView mv = new ModelAndView("redirect:/dashboard/tipoparticipante");
			return mv;
		}else{
		ModelAndView mv = new ModelAndView(CADASTRO_TIPO_PARTICIPANTE);
		mv.addObject("tipPart", t);
		mv.addObject("certif", new Certificado());
		return mv;
		}
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
//		System.out.println(texto);		
		tp.setTextoCertificado(texto);
		Participacao part = new Participacao();
		part.setEvento(e);
		part.setParticipante(p);
		part.setCategoriaParticipante(tp);

		String urlHash = "http://www.pet.emc.ufg.com/eventos/autentica/7cf5f1c5962e75133eee71e21954bfe1";

		java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();

		try {
			CertificadoPdf cpdf = new CertificadoPdf();
			b = cpdf.gerar("Certificado", certificado, cpdf.formatar(urlHash,
					part.getParticipante(), part.getEvento(), part.getCategoriaParticipante()));
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
		return lista;

	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView edicao(@PathVariable("codigo") TipoParticipante t) {
		return goCadastrar(t);
	}
	
	@RequestMapping(value="/del", method = RequestMethod.POST)
	public String exclui(@RequestParam Long id, RedirectAttributes attributes) {
		tpService.excluir(id);
		attributes.addFlashAttribute("mensage", "Tipo participante "+id+" excluído com sucesso!");
		return "redirect:/dashboard/tipoparticipante";
	}
}
