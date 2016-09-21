package com.pet.certipet.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.certipet.model.CampoChave;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;
import com.pet.certipet.repository.CertificadoRepo;

@Service
public class CertificadoService {

	@Autowired
	private CertificadoRepo certificado;

	public Certificado buscarCertificado(Long idevento) {
		// return new Certificado();
		return certificado.findEvento(idevento);
	}

	public CertificadoPdf personalizar(Certificado cert, Participante part, Evento even, TipoParticipante tPar) {
		String texto = tPar.getTextoCertificado();
		String sp = "|";
		String op = CampoChave.NOME.name() + sp + CampoChave.EVENTO.name() + sp + CampoChave.DATA_EVENTO.name() + sp
				+ CampoChave.DATA_EVENTO_INGLES.name() + sp + CampoChave.MATRICULA.name() + sp
				+ CampoChave.DATA_ATUAL.name();
		String regex = "(.*?)(" + op + ")(.+)";
		Pattern patt = Pattern.compile(regex, Pattern.DOTALL);
		Matcher m = patt.matcher(texto);
		if (m.find() && m.groupCount() == 3) {
			System.out.println(m.group(1));
		}
		String tflow = m.group(1);

		for (CampoChave cc : CampoChave.values()) {
			switch (cc) {
			case NOME:
				texto.replaceAll(cc.getChave(), part.getNome());
				break;
			case EVENTO:
				texto.replaceAll(cc.getChave(), even.getNome());
				break;
			case DATA_EVENTO:
				texto.replaceAll(cc.getChave(), setData(cc, even.getDataRealizacao()));
				break;
			case DATA_EVENTO_INGLES:
				texto.replaceAll(cc.getChave(), setData(cc, even.getDataRealizacao()));
				break;
			case MATRICULA:
				texto.replaceAll(cc.getChave(), part.getMatricula());
				break;
			case DATA_ATUAL:
				DateTimeFormatter f = DateTimeFormatter.ofPattern("'Goiânia,' dd 'de' MMMM 'de' yyyy",
						new Locale("pt", "br"));
				texto.replaceAll(cc.getChave(), f.format(Instant.now()));
				break;
//			case DATA_ATUAL_INGLES:
//				DateTimeFormatter f2 = DateTimeFormatter.ofPattern("'Goiania,' MMMM dd',' yyyy", new Locale("en"));
//				texto.replaceAll(cc.getChave(), f2.format(Instant.now()));
//				break;

			default:
				break;
			}
		}

		// certificado.replace("nome", nomeParticipante);
		// certificado.replace("$ch$", horas);
		// certificado.replace("$evento$", evento);
		// certificado.replace("$data$", data);

		return null;
	}

	private String setData(CampoChave cc, Date realizacao) {
		String data = "";
		try {
			if (cc.equals(CampoChave.DATA_EVENTO)) {
				SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "br"));
				data = format.format(realizacao);
			} else if (cc.equals(CampoChave.DATA_EVENTO_INGLES)) {

				Locale ingles = new Locale("en");
				data = realizacao.toString();
			}

		} catch (Exception e) {
		}
		return data;
	}

	public Certificado salvar(Certificado c) {
		return certificado.save(c);
	}

	public List<Certificado> buscaTodosSimple() {

		List<Certificado> a = certificado.findAllSimple();

		return a;
	}

	// public List<CertificadoDTO> buscaTodosSimples() {
	//
	// List<Certificado> a = certificado.findAllSimple();
	//
	// return a;
	// }

	
}
