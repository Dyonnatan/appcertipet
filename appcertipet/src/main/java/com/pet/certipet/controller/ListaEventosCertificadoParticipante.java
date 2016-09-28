package com.pet.certipet.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.service.AutenticacaoCertificadoService;
import com.pet.certipet.service.CertificadoPdf;
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
	public Object pesquisar(HttpServletRequest request, Principal principal) {
		String cpf = principal.getName();
		List<Participacao> todasParticipacoes = participacaoService.filtrar(cpf);

		// System.out.println(Arrays.asList(todasParticipacoes.toArray()));
		if (todasParticipacoes.size() == 1) {
			// System.out.println(todasParticipacoes.get(0));
			return emissaoPDF(request, todasParticipacoes.get(0),
					todasParticipacoes.get(0).getCategoriaParticipante(), principal);
		}
		ModelAndView mv = new ModelAndView(PESQUISA_CERTIFICADOS__VIEW);
		mv.addObject("participacao", todasParticipacoes);
		return mv;
	}

	// @RequestMapping(value = "/certificado/emissao", method =
	// RequestMethod.POST)
	// public ResponseEntity<byte[]> emissaoPDFDeprecated(HttpServletRequest
	// request, @ModelAttribute("participacao") Participacao part, Principal
	// principal) {
	//
	// if
	// (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName()))
	// {
	// return null;
	// }
	// // Certificado certificado =
	// // certificadoService.buscarCertificado(part.getEvento().getId());
	// Certificado certificado = part.getEvento().getCertificado();
	//
	// // CertificadoPdf cpdf = certificadoService.personalizar(certificado,
	// // part.getParticipante(), part.getEvento(),
	// // part.getTipoParticipante());
	//
	// String urlHash = "http://"+request.getLocalName()+"/autentica/";
	//
	// AutenticacaoCertificado autenticacao = autenticServ.buscar(part.getId());
	// if (autenticacao == null) {
	// String hash= autenticServ.gerarHash(part);
	// autenticacao = autenticServ.salvar(new AutenticacaoCertificado(hash,
	// part));
	// }
	//
	// java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();
	//
	// try {
	// CertificadoPdf cpdf = new CertificadoPdf();
	//
	// b = cpdf.gerar("Certificado", certificado,
	// cpdf.formatar(urlHash+autenticacao.getHash(), part.getParticipante(),
	// part.getEvento(), part.getTipoParticipant()));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// HttpHeaders headers = new HttpHeaders();
	// headers.setContentType(MediaType.parseMediaType("application/pdf"));
	//// String filename = "certificado.pdf";
	// // headers.add("content-disposition", "inline;filename=" + filename);
	// // headers.setContentDispositionFormData("inline", filename);
	// // headers.setContentDispositionFormData(filename, filename);
	// headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	// ResponseEntity<byte[]> response = new
	// ResponseEntity<byte[]>(b.toByteArray(), headers, HttpStatus.OK);
	// return response;
	//
	// }

	public ResponseEntity<byte[]> emissaoPDF(HttpServletRequest request,
			@ModelAttribute("participacao") Participacao part, @ModelAttribute("categoria") TipoParticipante cat,
			Principal principal) {

		if (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName())) {
			return null;
		}
		// Certificado certificado =
		// certificadoService.buscarCertificado(part.getEvento().getId());
		Certificado certificado = part.getEvento().getCertificado();

		// CertificadoPdf cpdf = certificadoService.personalizar(certificado,
		// part.getParticipante(), part.getEvento(),
		// part.getTipoParticipante());

		String urlHash = "http://" + request.getLocalName() + "/autentica/";

		AutenticacaoCertificado autenticacao = autenticServ.buscar(part.getId());
		if (autenticacao == null) {
			String hash = autenticServ.gerarHash(part);
			autenticacao = autenticServ.salvar(new AutenticacaoCertificado(hash, part));
		}

		java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();

		try {
			CertificadoPdf cpdf = new CertificadoPdf();

			b = cpdf.gerar("Certificado", certificado,
					cpdf.formatar(urlHash + autenticacao.getHash(), part.getParticipante(), part.getEvento(), cat));
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		// String filename = "certificado.pdf";
		// headers.add("content-disposition", "inline;filename=" + filename);
		// headers.setContentDispositionFormData("inline", filename);
		// headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(b.toByteArray(), headers, HttpStatus.OK);
		return response;

	}

	// @RequestMapping(value = "/{codigo}")
	// public ResponseEntity<byte[]> receber2(@PathVariable Long codigo,
	// Principal principal, HttpServletRequest request) {
	// Participacao p = participacaoService.buscar(codigo);
	// return emissaoPDF(request, p, principal);
	// }

	@RequestMapping(value = "/{codigo}")
	 public ResponseEntity<byte[]> receber(@PathVariable("codigo") Long codigoPart, Principal principal, HttpServletRequest request) {
		 Participacao p = participacaoService.buscar(codigoPart);
			if(p.getParticipante().getCpf().equals(principal.getName()))
				return emissaoPDF(request, p, p.getCategoriaParticipante(), principal);
		 return null;
	 }
}
