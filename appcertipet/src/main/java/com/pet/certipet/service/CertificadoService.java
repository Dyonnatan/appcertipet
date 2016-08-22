package com.pet.certipet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.repository.CertificadoRepo;

@Service
public class CertificadoService {

	@Autowired
	private CertificadoRepo certificado;

	// public Certificado buscarCertificado(Long idEvento) {
	// return certificado.findByEvento(idEvento);
	// }

	// public String buscarArqCertificado(Evento evento) {
	// return certificado.findOneByEvento(evento).getArquivo();
	// }

	public Certificado buscarCertificado(Long idevento) {
//		return new Certificado();
		 return certificado.findEvento(idevento);
	}

	public String personalizar(String certificado, String nomeParticipante, String evento, Date dataEvento,
			String horas) {
		HashMap<Integer, String> mes = new HashMap<>();
		mes.put(1, "janeiro");
		mes.put(2, "fevereiro");
		mes.put(3, "março");
		mes.put(4, "abril");
		mes.put(5, "maio");
		mes.put(6, "junho");
		mes.put(7, "julho");
		mes.put(8, "agosto");
		mes.put(9, "setembro");
		mes.put(10, "outubro");
		mes.put(11, "novembro");
		mes.put(12, "dezembro");
		String data = "";
		try {

			SimpleDateFormat format = new SimpleDateFormat("dd 'de' ?? 'de' yyyy");
			SimpleDateFormat month = new SimpleDateFormat("MM");
			data = format.format(dataEvento);
			data.replace("??", mes.get(month.format(dataEvento)));

		} catch (Exception e) {
		}
		StringBuffer b = new StringBuffer(certificado);
		int start = b.indexOf("@nome@");
		if(start!=-1)
		b.replace(start, start + 6, nomeParticipante);
		start = b.indexOf("@evento@");
		if(start!=-1)
		b.replace(start, start + 8, evento);
		// certificado.replace("nome", nomeParticipante);
		// certificado.replace("$ch$", horas);
		// certificado.replace("$evento$", evento);
		// certificado.replace("$data$", data);

		return b.toString();
	}

	public Certificado salvar(Certificado c) {
		return certificado.save(c);
	}

	public List<Certificado> buscaTodosSimple() {

		List<Certificado> a = certificado.findAllSimple();
		
		return a;
	}
	
//	public List<CertificadoDTO> buscaTodosSimples() {
//
//		List<Certificado> a = certificado.findAllSimple();
//		
//		return a;
//	}
}
