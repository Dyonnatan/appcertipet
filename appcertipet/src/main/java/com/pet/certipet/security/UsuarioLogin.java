package com.pet.certipet.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.pet.certipet.model.Nivel;
import com.pet.certipet.model.Usuario;

public class UsuarioLogin extends User {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public UsuarioLogin(String username, String password, Usuario u) {
		super(username, password, AuthorityUtils.createAuthorityList(u.getNivelToString()));

		System.out.println(username + password);
		System.out.println(u.getNivelToString());
		this.usuario = u;
	}

	public UsuarioLogin(Usuario usuario) {
		super(usuario.getCpf(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getNivel().toString()));
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Nivel getNivel() {
		return usuario.getNivel();
	}
}
