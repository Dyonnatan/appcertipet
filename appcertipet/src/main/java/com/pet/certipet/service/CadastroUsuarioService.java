package com.pet.certipet.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Nivel;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.Usuario;
import com.pet.certipet.repository.ParticipanteRepo;
import com.pet.certipet.repository.UsuarioRepo;

@Service
public class CadastroUsuarioService {

	@Autowired
	private ParticipanteRepo participante;
	@Autowired
	private UsuarioRepo user;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	// @Autowired
	// private UsuarioRepo usuarioRepo;

	@Autowired
	EntityManagerFactory emf;

	// public Participante buscar(String cpf) {
	// return participante.findByCpf(cpf);
	// }
	//
	// public Page<Participante> buscaPaginada(int page, int size) {
	// return participante.findAll(new PageRequest(page, size));
	// }

	public List<Participante> buscarTodos() {
		return participante.findAll();
	}

	public Participante buscar(String cpf) {
		return participante.findByCpf(cpf);
	}
	
	public String salvarParticipanteUsuario(Participante p) throws Exception {

		EntityManager em = emf.createEntityManager();
		// em.getTransaction();
		// em.getTransaction().commit();
		// em.close();
		try {
			em.getTransaction().begin();
			Usuario user = new Usuario();
			user.setCpf(p.getCpf());
			user.setEnabled(true);
			user.setNivel(Nivel.ROLE_PARTICIPANTE);
			em.persist(user);
			em.persist(p);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new Exception(
					"Ocorreu um erro inesperado ao cadastrar um novo participante. Talvez já esteja cadastrado.");
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		return p.getNome();
	}

	public Participante salvar(Participante p) throws Exception {
		Usuario u = p.getUsuario();
		u.setSenha(passwordEncoder.encode(p.getUsuario().getSenha()));
		u = user.save(u);
		p.setUsuario(u);
		return participante.save(p);
	}
	

	// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)

	// public String salvarParticipanteUsuario(Participante p) throws Exception
	// {
	// Participante part;
	// Usuario u;
	// try {
	// part = participante.saveAndFlush(p);
	// if(part==null){
	// throw new Exception("Ocorreu um erro inesperado ao cadastrar um novo
	// participante.");
	// }
	// Usuario user = new Usuario();
	// user.setCpf(p.getCpf());
	// user.setEnabled(true);
	// user.setNivel(Nivel.PARTICIPANTE);
	// user.setSenha("pass");
	// u = usuarioRepo.saveAndFlush(user);
	// } catch (Exception e) {
	// throw new Exception("Ocorreu um erro inesperado ao cadastrar um novo
	// participante.\n"+e.getMessage());
	// }
	// if(u==null){
	// throw new Exception("Ocorreu um erro inesperado ao cadastrar um novo
	// usuário.");
	// }
	// return part.getNome();
	// }
}
