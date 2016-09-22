package com.pet.certipet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Usuario;

@Service
public class CurrentUserDetailService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public CurrentUserDetailService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UsuarioLogin loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Usuario user = userService.getUserByCpf(cpf);
		if (user != null)// return new UsuarioLogin(user);
			return new UsuarioLogin(user.getCpf(), user.getSenha(), user);
		else
			throw new UsernameNotFoundException("Usuario não encontrado");// new
																			// UsuarioLogin("","",new
																			// Usuario());
	}

}
