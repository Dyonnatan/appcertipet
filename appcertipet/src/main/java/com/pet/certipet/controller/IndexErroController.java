package com.pet.certipet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
	public class IndexErroController implements ErrorController{

	    private static final String PATH = "/error";


	    @ExceptionHandler(value = Exception.class)
	    @RequestMapping(value = PATH)
	    public ModelAndView error(HttpServletResponse response, HttpServletRequest req) {
	    	ModelAndView mv = new ModelAndView("AcessoNegado");
	    	mv.addObject("error", HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
	    	mv.addObject("status", response.getStatus());
	        return mv;
	    }

	    @Override
	    public String getErrorPath() {
	        return PATH;
	    }
	}

