package br.ufpr.ds151.mutantes.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mutante")
public class Mutante implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="id_mutante")
	private Long id;
	
	@Column(name="nome_mutante")
	private String nome;
	
	@Column(name="id_usuario")
	private Long idUsuario;

	@Column(name="foto_mutante")
	private String foto;

	public Mutante() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Mutante(Long id, String nome, Long idUsuario, String foto) {
		super();
		this.id = id;
		this.nome = nome;
		this.idUsuario = idUsuario;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
