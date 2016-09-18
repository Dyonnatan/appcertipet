package com.pet.certipet;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/home").setViewName("home");
		// registry.addViewController("/").setViewName("home");
		// registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("LoginUser");
		registry.addViewController("/erro").setViewName("AcessoNegado");
		registry.addStatusController("/erro", HttpStatus.INTERNAL_SERVER_ERROR);
		registry.addStatusController("/erro", HttpStatus.NOT_FOUND);
//		registry.addViewController("/dashboard").setViewName("Dashboard");
	}

}
