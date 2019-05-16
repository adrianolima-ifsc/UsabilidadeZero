package models;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class Estudo extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;	
	@Constraints.Required
	public boolean tipo;
	@ManyToOne
	public Usuario usuario;
	public Date inicio;
	public Date fim;
	@OneToMany(mappedBy = "estudo")
	public List<Tarefa> tarefas;
	@OneToOne(mappedBy = "estudo")
	public Sus sus;
	@OneToOne(mappedBy = "estudo")
	public RelatorioEstudo relatorio;
	@OneToOne
	private TokenSistema token;
	@OneToOne
	private Estudo relacionado;
	
	public static Finder<Long, Estudo> find = new Finder<>(Estudo.class);

	public Estudo(boolean tipo, Usuario usuario, Date inicio) {

		this.tipo = tipo;
		this.usuario = usuario;
		this.inicio = inicio;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public boolean isTipo() {
		return tipo;
	}
	
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public Date getInicio() {
		return inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public List<Tarefa> getTarefas() {
		
		Collections.sort(tarefas);
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	
	public Sus getSus() {
		return sus;
	}

	public void setSus(Sus sus) {
		this.sus = sus;
	}

	public TokenSistema getToken() {
		return token;
	}

	public void setToken(TokenSistema token) {
		this.token = token;
	}

	public RelatorioEstudo getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioEstudo relatorio) {
		this.relatorio = relatorio;
	}

	public Estudo getRelacionado() {
		return relacionado;
	}

	public void setRelacionado(Estudo relacionado) {
		this.relacionado = relacionado;
	}
}
