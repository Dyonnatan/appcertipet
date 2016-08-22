package com.pet.certipet.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pet.certipet.model.Instituicao;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.Sexo;
import com.pet.certipet.service.CadastroUsuarioService;
import com.pet.certipet.service.CursoService;
import com.pet.certipet.service.InstituicaoService;

@Controller
public class CadastroParticipanteController {

	private static final String CADASTRO_USUARIO = "CadastroUsuario";

	@Autowired
	private CadastroUsuarioService cadUserService;
	@Autowired
	private InstituicaoService instituicaoService;
	@Autowired
	private CursoService cursoService;

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView goCadastro(Participante p) {
		ModelAndView mv = new ModelAndView(CADASTRO_USUARIO);
		mv.addObject("sexos", Arrays.asList(Sexo.values()));
		mv.addObject(p);
		return mv;
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Participante p, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return goCadastro(p);
		}

		try {
			cadUserService.salvarParticipanteUsuario(p);
		} catch (Exception e) {
			result.addError(new ObjectError("message", e.getMessage()));
			return goCadastro(p);
		}
		ModelAndView mv = new ModelAndView(CADASTRO_USUARIO);// "redirect:/home");
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso.");
		return mv;
	}

	@ModelAttribute("todasInstituicoes")
	public List<Instituicao> todasInstituicoes() {
		List<Instituicao> l = instituicaoService.buscarTodos();
		l.add(0, null);
		return l;
		// return Arrays.asList(TipoEvento.values());
	}

	@ModelAttribute("todosCursos")
	public List<Curso> todosCursos() {
		List<Curso> l = cursoService.buscarTodos();
		l.add(0, null);
		return l;
		// return Arrays.asList(TipoEvento.values());
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
