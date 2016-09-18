package com.pet.certipet.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
	public class IndexErroController implements ErrorController{

	    private static final String PATH = "/error";

	    @RequestMapping(value = PATH)
	    public ModelAndView error() {
	    	ModelAndView mv = new ModelAndView("AcessoNegado");
	        return mv;
	    }

	    @Override
	    public String getErrorPath() {
	        return PATH;
	    }
	}

