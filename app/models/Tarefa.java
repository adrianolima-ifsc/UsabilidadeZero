package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.ebean.Finder;
import io.ebean.Model;

@Entity
public class Tarefa extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;
	public String codigo;
	@ManyToOne
	public Estudo estudo;
	public Long evento;
	public Date dataHoraInicio;
	public Date dataHoraFim;
	public int cliques;
	public boolean concluidoReal;
	public boolean concluidoPercebido;
	@OneToOne
	private TokenSistema token;

	public static Finder<Long, Tarefa> find = new Finder<>(Tarefa.class);

	public Tarefa(Estudo estudo, Date time) {

		this.estudo = estudo;
		this.dataHoraInicio = time;
		this.cliques = 0;
		this.concluidoReal = false;
		this.concluidoPercebido = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Estudo getEstudo() {
		return estudo;
	}

	public void setEstudo(Estudo estudo) {
		this.estudo = estudo;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public int getCliques() {
		return cliques;
	}

	public void setCliques(int cliques) {
		this.cliques = cliques;
	}

	public boolean isConcluidoReal() {
		return concluidoReal;
	}

	public void setConcluidoReal(boolean concluidoReal) {
		this.concluidoReal = concluidoReal;
	}

	public boolean isConcluidoPercebido() {
		return concluidoPercebido;
	}

	public void setConcluidoPercebido(boolean concluidoPercebido) {
		this.concluidoPercebido = concluidoPercebido;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
	}

	public TokenSistema getToken() {
		return token;
	}

	public void setToken(TokenSistema token) {
		this.token = token;
	}
}
