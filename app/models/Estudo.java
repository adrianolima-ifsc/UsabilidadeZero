package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.validation.*;

@Entity
public class Estudo extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;	
	@Constraints.Required
	public boolean tipo;
	@ManyToOne
	public Usuario usuario;
	public Date data;
	@OneToMany(mappedBy = "estudo")
	public List<Tarefa> tarefas;
	@OneToOne
	private TokenSistema token;
	
	public static Finder<Long, Estudo> find = new Finder<>(Estudo.class);

	public Estudo(boolean tipo, Usuario usuario, Date data) {

		this.tipo = tipo;
		this.usuario = usuario;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public boolean isTipo() {
		return tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Date getData() {
		return data;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public TokenSistema getToken() {
		return token;
	}

	public void setToken(TokenSistema token) {
		this.token = token;
	}
	}
