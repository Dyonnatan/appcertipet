package com.pet.certipet.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.pet.certipet.model.AutenticacaoCertificado;
import com.pet.certipet.model.Participacao;
import com.pet.certipet.repository.AutenticaCertificadoRepo;

@Service
public class AutenticacaoCertificadoService {

	@Autowired
	private AutenticaCertificadoRepo repo;

	public AutenticacaoCertificado salvar(AutenticacaoCertificado a) {
		return repo.save(a);
	}
	
	public AutenticacaoCertificado buscar(String hash) {
		return repo.findOne(hash);
	}
	
	public AutenticacaoCertificado buscar(Long id) {
		return repo.findByParticipacao(new Participacao(id));
	}
	
	public String gerarHash(Participacao p) {
		String h = p.getId()+p.getEvento().getNome()+p.getParticipante().getId();
		String hash;
		try {
			hash = DigestUtils.md5DigestAsHex(h.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			hash = DigestUtils.md5DigestAsHex(h.getBytes(Charset.forName("UTF-8")));
		}
		return hash;
	}
}
