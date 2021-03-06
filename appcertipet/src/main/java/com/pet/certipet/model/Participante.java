package com.pet.certipet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "participantes")
// @NamedNativeQuery(name= "fazerFK", query="ALTER TABLE 'participantes' ADD
// CONSTRAINT 'FK_gfhgft' FOREIGN KEY ('cpf') REFERENCES 'usuarios' ('cpf');")
public class Participante {

	private Long id;
	private String nome;
	private String sobrenome;
	private Sexo sexo;
	private Date dataNascimento;
	private String cpf;
	private String email;
	private String telefone;
	private Curso curso;
	private Instituicao instituicao;
	private String matricula;
	private Usuario usuario;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank(message = "Nome é obrigatório!")
	@Size(max = 45, message = "O nome não pode conter mais de 45 caracteres")
	@Column(length = 45, nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	@NotBlank(message = "CPF é obrigatório!")
	@Size(max = 11, message = "O CPF não pode conter mais de 11 caracteres")
	@Column(length = 11, unique = true)	
	@CPF(message="CPF inválido. Utilize apenas números.")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@NotBlank(message = "Sobrenome é obrigatório!")
	@Size(max = 80, message = "O sobrenome não pode conter mais de 45 caracteres")
	@Column(length = 80, nullable = false)
	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	@Email(message = "E-mail inválido!")
	@Column(length = 70, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 15)
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Enumerated(EnumType.STRING)
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Size(max = 50, message = "A matrícula não pode conter mais de 50 caracteres")
	@Column(length = 50, unique = false)
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	@OneToOne(fetch = FetchType.LAZY, optional=false)
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Participante other = (Participante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[nome=" + nome + "]";
	}

}
