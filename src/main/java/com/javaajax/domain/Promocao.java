package com.javaajax.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "promocoes")
public class Promocao implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Um titulo é requerido.")
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@NotBlank(message = "O link da promoção é requerido.")
	@Column(name = "link_promocao", nullable =  false)
	private String linkPromocao;
	
	@Column(name = "site_promocao", nullable = false)
	private String site;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "link_imagem", nullable = false)
	private String linkImagem;
	
	@NotNull(message = "O preço é requerido.")
	@NumberFormat(pattern = "#,##0.00", style = Style.CURRENCY)
	@Column(name = "preco_promocao", nullable = false)
	private BigDecimal preco;
	
	@Column(name = "total_likes")
	private int likes;
	
	@Column(name = "data_cadastro", nullable = false)
	private LocalDate dataCadastro;
	
	@ManyToOne
	@JoinColumn(name = "categoria_fk")
	private Categoria categoria;

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

	public String getLinkPromocao() {
		return linkPromocao;
	}

	public void setLinkPromocao(String linkPromocao) {
		this.linkPromocao = linkPromocao;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String link_imagem) {
		this.linkImagem = link_imagem;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Promocao [id=" + id + ", titulo=" + titulo + ", linkPromocao=" + linkPromocao + ", site=" + site
				+ ", descricao=" + descricao + ", link_imagem=" + linkImagem + ", preco=" + preco + ", likes=" + likes
				+ ", dataCadastro=" + dataCadastro + ", categoria=" + categoria + "]";
	}
	
	
}
