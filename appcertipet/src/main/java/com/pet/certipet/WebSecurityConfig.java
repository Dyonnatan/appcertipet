package com.pet.certipet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pet.certipet.model.Nivel;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomSuccesHandler successHandler;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
		;
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/home", "/cadastro", "/images/**", "/css/**", "/js/**", "/fonts/**")
				.permitAll()
				;http.authorizeRequests()
				.anyRequest().denyAll()
				// .antMatchers("/", "/home", "/cadastro").permitAll()
				.filterSecurityInterceptorOncePerRequest(true).
				antMatchers("/participante/**").hasAuthority("["+Nivel.ROLE_PARTICIPANTE.toString()+"]")
				.antMatchers("/dashboard/**").hasAuthority(Nivel.ROLE_ADMINISTRADOR.toString())// .access("hasRole["+Nivel.ADMINISTRADOR.toString()+"]")
				.anyRequest().denyAll().anyRequest().fullyAuthenticated()
				.and().formLogin().loginPage("/login").successHandler(successHandler)
				// .defaultSuccessUrl("/eventos", true)
				.failureUrl("/login?error")
				// .usernameParameter("cpf").passwordParameter("j_password")
				.permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// .logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout").permitAll();
				http.csrf().disable();
	}

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth
	// .inMemoryAuthentication()
	// .withUser("user").password("pass").roles("USER");
	// }
}
