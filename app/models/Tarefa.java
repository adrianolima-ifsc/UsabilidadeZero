package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.ebean.Finder;
import io.ebean.Model;

@Entity
public class Tarefa extends Model implements Comparable<Tarefa> {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;
	public String codigo;
	@ManyToOne
	public Estudo estudo;
	public Long evento;
	public Date inicio;
	public Date fim;
	public Long cliques;
	public boolean concluidoReal;
	public boolean concluidoPercebido;

	public static Finder<Long, Tarefa> find = new Finder<>(Tarefa.class);

	public Tarefa(String codigo, Estudo estudo, Date inicio) {

		this.codigo = codigo;
		this.estudo = estudo;
		this.inicio = inicio;
		this.cliques = (long) 0;
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

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date dataHoraInicio) {
		this.inicio = dataHoraInicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date dataHoraFim) {
		this.fim = dataHoraFim;
	}

	public Long getCliques() {
		return cliques;
	}

	public void setCliques(Long cliques) {
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

	@Override
	public int compareTo(Tarefa t) {

		return (int) (this.getId() - t.getId());
	}
}
