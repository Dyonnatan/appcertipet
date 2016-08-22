package com.pet.certipet.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.service.CertificadoService;
import com.pet.certipet.service.ParticipacaoService;

@Controller
@RequestMapping("/participante/certificados")
public class ListaEventosCertificadoParticipante {

	private static final String PESQUISA_CERTIFICADOS__VIEW = "PesquisaCertificados";

	@Autowired
	private ParticipacaoService participacaoService;

	@Autowired
	private CertificadoService certificadoService;

	// @RequestMapping("/novo")
	// public ModelAndView novo() {
	// ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
	// mv.addObject(new Titulo());
	// return mv;
	// }
	//
	// @RequestMapping(method = RequestMethod.POST)
	// public String salvar(@Validated Titulo titulo, Errors errors,
	// RedirectAttributes attributes) {
	// if (errors.hasErrors()) {
	// return CADASTRO_VIEW;
	// }
	//
	// try {
	// cadastroTituloService.salvar(titulo);
	// attributes.addFlashAttribute("mensagem", "TÃ­tulo salvo com sucesso!");
	// return "redirect:/titulos/novo";
	// } catch (IllegalArgumentException e) {
	// errors.rejectValue("dataVencimento", null, e.getMessage());
	// return CADASTRO_VIEW;
	// }
	// }

	@RequestMapping
	public ModelAndView pesquisar(
			/* @ModelAttribute("filtro") TituloFilter filtro, */ Principal principal) {
		String cpf = principal.getName();
		List<Participacao> todasParticipacoes = participacaoService.filtrar(cpf);

		System.out.println(Arrays.asList(todasParticipacoes.toArray()));
		if (todasParticipacoes.size() == 1) {
			return edicao(todasParticipacoes.get(0), principal);
		}
		ModelAndView mv = new ModelAndView(PESQUISA_CERTIFICADOS__VIEW);
		mv.addObject("participacao", todasParticipacoes);
		return mv;
	}

	// @RequestMapping(value = "/certificado/{id}", method = RequestMethod.GET)
	// public ModelAndView edicao(@PathVariable("id") Participacao part,
	// Principal principal) {
	// System.out.println(principal.getName());
	// System.out.println(part.getParticipante().getCpf());
	// if
	// (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName()))
	// {
	// ModelAndView mv = new ModelAndView("AcessoNegado");
	// return mv;
	// }
	// Certificado certificado =
	// certificadoService.buscarCertificado(part.getEvento());
	// String c = certificadoService.personalizar(certificado.getArquivo(),
	// part.getParticipante().getNome(),
	// part.getEvento().getNome(), part.getEvento().getDataRealizacao(),
	// part.getTipoParticipante().getCarga_horaria());
	// certificado.setArquivo(c);
	// ModelAndView mv = new ModelAndView("EmitirCertificado");
	// mv.addObject("certificado", certificado);
	// return mv;
	// }
	@RequestMapping(value = "/certificados/emissao", method = RequestMethod.POST)
	public ModelAndView edicao(@ModelAttribute("participacao") Participacao part, Principal principal) {
		System.out.println(principal.getName());
		System.out.println(part.getParticipante().getCpf());
		if (!part.getParticipante().getCpf().equalsIgnoreCase(principal.getName())) {
			ModelAndView mv = new ModelAndView("AcessoNegado");
			return mv;
		}
//		Certificado certificado = certificadoService.buscarCertificado(part.getEvento().getId());
		Certificado certificado = part.getEvento().getCertificado();
		String ch=null;
		if(part.getTipoParticipante()==null){
			ch = part.getEvento().getCargaHoraria();
		}else{
			ch = part.getTipoParticipante().getCarga_horaria();
		}
		String c = certificadoService.personalizar(certificado.getArquivo(), part.getParticipante().getNome(),
				part.getEvento().getNome(), part.getEvento().getDataRealizacao(),
				ch);
		certificado.setArquivo(c);
		ModelAndView mv = new ModelAndView("EmitirCertificado");
		mv.addObject("certificado", certificado);
		return mv;
	}
	// @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	// public @ResponseBody String receber(@PathVariable Long codigo) {
	// return cadastroTituloService.receber(codigo);
	// }

}
