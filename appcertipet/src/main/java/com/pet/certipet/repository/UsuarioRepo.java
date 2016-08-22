package com.pet.certipet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.certipet.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

	Usuario findByCpf(String cpf);

	Usuario findByCpfAndSenha(String cpf, String senha);

}
