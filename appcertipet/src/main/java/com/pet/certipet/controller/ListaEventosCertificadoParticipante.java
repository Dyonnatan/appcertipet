package com.pet.certipet.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/participante/certificados")
public class ListaEventosCertificadoParticipante {

	private static final String PESQUISA_CERTIFICADOS__VIEW = "PesquisaCertificados";

	@Autowired
	private ParticipacaoService participacaoService;

	@Autowired
	private AutenticacaoCertificadoService autenticServ;

	@RequestMapping
	public ModelAndView pesquisar(HttpServletRequest request, Principal principal) {
		String cpf = principal.getName();
		List<Participacao> todasParticipacoes = participacaoService.filtrar(cpf);

		System.out.println(Arrays.asList(todasParticipacoes.toArray()));
		if (todasParticipacoes.size() == 1) {
			ModelAndView m = new ModelAndView();
			return m.addObject(emissaoPDF(request, todasParticipacoes.get(0), principal));
		}
		ModelAndView mv = new ModelAndView(PESQUISA_CERTIFICADOS__VIEW);
		mv.addObject("participacao", todasParticipacoes);
		return mv;
	}
	


	// @RequestMapping(value = "/certificados/emissao", method =
	// RequestMethod.POST)
	// public ModelAndView edicao(@ModelAttribute("participacao") Participacao
	// part, Principal principal) {
	// System.out.println(principal.getName());
	// System.out.println(part.getParticipante().getCpf());
	// if
	// (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName()))
	// {
	// ModelAndView mv = new ModelAndView("AcessoNegado");
	// return mv;
	// }
	// // Certificado certificado =
	// // certificadoService.buscarCertificado(part.getEvento().getId());
	// Certificado certificado = part.getEvento().getCertificado();
	// String ch = null;
	// if (part.getTipoParticipante() == null) {
	// ch = part.getEvento().getCargaHoraria();
	// } else {
	// ch = part.getTipoParticipante().getCarga_horaria();
	// }
	// String c = certificadoService.personalizar(certificado,
	// part.getParticipante(),
	// part.getEvento(), part.getTipoParticipante());
	// certificado.setArquivo(c);
	// ModelAndView mv = new ModelAndView("EmitirCertificado");
	// mv.addObject("certificado", certificado);
	// return mv;
	// }

	@RequestMapping(value = "/certificados/emissao", method = RequestMethod.POST)
	/** @ResponseBody **/
	public ResponseEntity<byte[]> emissaoPDF(HttpServletRequest request, @ModelAttribute("participacao") Participacao part, Principal principal) {

		if (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName())) {
			return null;
		}
		// Certificado certificado =
		// certificadoService.buscarCertificado(part.getEvento().getId());
		Certificado certificado = part.getEvento().getCertificado();

		// CertificadoPdf cpdf = certificadoService.personalizar(certificado,
		// part.getParticipante(), part.getEvento(),
		// part.getTipoParticipante());

		String urlHash = "http://"+request.getLocalName()+"/autentica/"; 
		
		AutenticacaoCertificado autenticacao = autenticServ.buscar(part.getId());
		if (autenticacao == null) {
			String hash= autenticServ.gerarHash(part);
			autenticacao = autenticServ.salvar(new AutenticacaoCertificado(hash, part));
		}

		java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();

		try {
			CertificadoPdf cpdf = new CertificadoPdf();
			b = cpdf.gerar("Certificado", certificado, cpdf.formatar(urlHash+autenticacao.getHash(), part.getParticipante(),
					part.getEvento(), part.getTipoParticipante()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// inserir document

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "certificado.pdf";
		// headers.add("content-disposition", "inline;filename=" + filename);
		// headers.setContentDispositionFormData("inline", filename);
		// headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(b.toByteArray(), headers, HttpStatus.OK);
		return response;

	}
	// @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	// public @ResponseBody String receber(@PathVariable Long codigo) {
	// return cadastroTituloService.receber(codigo);
	// }

}
