package com.pet.certipet.controller;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcessoParticipanteController {

	@RequestMapping(value = { "/", "/home" })
	public String goHome() {
		return "HomeLogado";
	}

	@RequestMapping(value = { "/participante" })
	public String goHomeLogado() {
		return "HomeLogado";
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AcessoParticipanteController.class);

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam Optional<String> error, Principal p) {
		if (p != null)
			return new ModelAndView("redirect:/home");

		LOGGER.debug("Getting login page, error={}", error);
		return new ModelAndView("LoginUser", "error", error);
	}

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
