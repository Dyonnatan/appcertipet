package com.pet.certipet.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "participacoes")
public class Participacao {

	private Long id;
	private Participante participante;
	private Evento evento;
	private TipoParticipante tipoParticipante;
	private Presenca presenca;
	private char pagamento;
	private List<Resposta> respostas;

	public Participacao() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Participacao(Long id) {
		super();
		this.id = id;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public TipoParticipante getTipoParticipante() {
		return tipoParticipante;
	}

	public void setTipoParticipante(TipoParticipante tipoParticipante) {
		this.tipoParticipante = tipoParticipante;
	}

	@Enumerated(EnumType.STRING)
	public Presenca getPresenca() {
		return presenca;
	}

	public void setPresenca(Presenca presenca) {
		this.presenca = presenca;
	}

	public void confirmaPresenca() {
		this.presenca = Presenca.PRESENTE;
	}

	public char getPagamento() {
		return pagamento;
	}

	public void setPagamento(char pagou) {
		this.pagamento = pagou;
	}
	
	@Transient
	public boolean isPresente() {
		return presenca.equals(Presenca.PRESENTE);

	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "participacao_id")
	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Participacao other = (Participacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Participacao [" +id+ participante.getNome() + ", " + tipoParticipante + ", em " + evento.getNome()
				+ ", confirmacao=" + presenca + "]";
	}

}
