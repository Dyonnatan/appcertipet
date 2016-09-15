package com.pet.certipet.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pet.certipet.model.Curso;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Instituicao;
import com.pet.certipet.model.Nivel;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.Sexo;
import com.pet.certipet.service.CadastroUsuarioService;
import com.pet.certipet.service.CursoService;
import com.pet.certipet.service.InstituicaoService;

@Controller
@RequestMapping(value = "/dashboard/usuario")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CadastroParticipanteAdmController {

	private static final String CONTA_USUARIO = "ContaUsuario";
	private static final String CADASTRO_USUARIO = "CadastroUsuario";

	@Autowired
	private CadastroUsuarioService cadUserService;
	@Autowired
	private InstituicaoService instituicaoService;
	@Autowired
	private CursoService cursoService;

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView goCadastro(Participante p,  BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView(CADASTRO_USUARIO);
		mv.addObject("sexos", Arrays.asList(Sexo.values()));
		mv.addObject("niveis", Arrays.asList(Nivel.values()));
		mv.addObject(p);
		return mv;
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Participante p, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return goCadastro(p, result, attributes);
		}
		
		try {
			p.getUsuario().setCpf(p.getCpf());
			p.setEmail(p.getUsuario().getEmail());
			cadUserService.salvar(p);
		} catch (Exception e) {
			System.out.println(e);
			result.addError(new ObjectError("message", "Não foi possível cadastrar. Verifique se já não está cadastrado."));
			return goCadastro(p, result, attributes);
		}
		ModelAndView mv = new ModelAndView(CADASTRO_USUARIO);// "redirect:/home");
		mv.addObject("mensagem", "Cliente cadastrado com sucesso.");
		return mv;
	}
	
	@RequestMapping(value = "/participante/conta", method = RequestMethod.POST)
	public ModelAndView alterar(@Validated Participante p, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView(CONTA_USUARIO);
			mv.addObject(p);
			return mv;
		}

		try {
			cadUserService.salvar(p);
		} catch (Exception e) {
			result.addError(new ObjectError("message", "Não foi possível fazer a alteração"));
			return alterar(p, result, attributes);
		}
		ModelAndView mv = new ModelAndView(CONTA_USUARIO);// "redirect:/home");
		mv.addObject("mensagem", "Dados alterados com sucesso.");
		return mv;
	}

	@ModelAttribute("todasInstituicoes")
	public List<Instituicao> todasInstituicoes() {
		List<Instituicao> l = instituicaoService.buscarTodos();
		return l;
		// return Arrays.asList(TipoEvento.values());
	}

	@ModelAttribute("todosCursos")
	public List<Curso> todosCursos() {
		List<Curso> l = cursoService.buscarTodos();
		
		return l;
		// return Arrays.asList(TipoEvento.values());
	}

	@ModelAttribute("participantes")
	public List<Participante> listarEventos() {
		List<Participante> lista = cadUserService.buscarTodos();
		return lista;
	}
	
	// @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	// public @ResponseBody String receber(@PathVariable Long codigo) {
	// return "ola recebe";
	// }

	// @RequestMapping(value="/logout", method = RequestMethod.GET)
	// public String logoutPage(HttpServletRequest request, HttpServletResponse
	// response) {
	// Authentication auth =
	// SecurityContextHolder.getContext().getAuthentication();
	// if (auth != null){
	// new SecurityContextLogoutHandler().logout(request, response, auth);
	// }
	// return "redirect:/login?logout";//You can redirect wherever you want, but
	// generally it's a good practice to show login screen again.
	// }
}
