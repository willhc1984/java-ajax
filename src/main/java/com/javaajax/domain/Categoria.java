package com.javaajax.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "titulo", nullable = false, unique = true)
	private String titulo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "categoria")
	private List<Promocao> promoçoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Promocao> getPromoçoes() {
		return promoçoes;
	}

	public void setPromoçoes(List<Promocao> promoçoes) {
		this.promoçoes = promoçoes;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", titulo=" + titulo + "]";
	}

}
