package com.pet.certipet.model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.util.DigestUtils;

@Entity
public class AutenticacaoCertificado {

	private String hash;
	private Participacao participacao;

	public AutenticacaoCertificado() {
		
	}
	
	
	public AutenticacaoCertificado(String hash, Participacao participacao) {
		super();
		this.hash = hash;
		this.participacao = participacao;
	}


	@Id
	@Column(length=32)
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@OneToOne(fetch = FetchType.LAZY, optional=false)
	public Participacao getParticipacao() {
		return participacao;
	}

	public void setParticipacao(Participacao participacao) {
		this.participacao = participacao;
	}
	
	
	
	public void gerarHash() {
		String h = participacao.getId()+participacao.getEvento().getNome()+participacao.getParticipante().getId();
		try {
			this.hash = DigestUtils.md5DigestAsHex(h.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			this.hash = DigestUtils.md5DigestAsHex(h.getBytes(Charset.forName("UTF-8")));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutenticacaoCertificado other = (AutenticacaoCertificado) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		return true;
	}

}
