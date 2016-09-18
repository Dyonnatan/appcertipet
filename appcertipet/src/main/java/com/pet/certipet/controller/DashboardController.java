package com.pet.certipet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class DashboardController {

	@RequestMapping(value = { "/dashboard" })
	public String goHome() {
		return "Dashboard";
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

}
