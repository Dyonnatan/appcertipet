package com.pet.certipet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Usuario;
import com.pet.certipet.repository.UsuarioRepo;

@Service
public class UserServiceImpl implements UserService {

	private final UsuarioRepo usuarioRepo;

	@Autowired
	public UserServiceImpl(UsuarioRepo usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@Override
	public Usuario getUserByCpf(String cpf) {
		Usuario u = usuarioRepo.findByCpf(cpf);
		return u;

	}
}
