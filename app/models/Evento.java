package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.validation.Constraints.*;

@Entity
public class Evento extends Model implements Comparable<Evento> {
	
	private static final Long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;	
	private String nome;	
	private String sigla;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	@Column(columnDefinition = "TEXT")
	private String programa;
	@Column(columnDefinition = "TEXT")
	private String informacoes;
	private String local;
	private int data;
	private double preco;
	private String categoria;
	
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getInformacoes() {
		return informacoes;
	}
	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	@Override
	public int compareTo(Evento e) {

		return this.getData() - e.getData();
	}
}
	
