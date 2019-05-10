package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.ebean.Model;

@Entity
public class RelatorioEstudo extends Model {
	
	private static final Long serialVersionUID = 1L;

	@Id
	public Long id;
	@OneToOne
	public Estudo estudo;
	public Long tempo;
	public Long cliques;
	public Long percebida;
	public Long medida;
	public Double satisfacao;
	
	public RelatorioEstudo(Estudo estudo) {

		this.estudo = estudo;
	}

	public Estudo getEstudo() {
		return estudo;
	}
	
	public void setEstudo(Estudo estudo) {
		this.estudo = estudo;
	}
	
	public Long getTempo() {
		return tempo;
	}
	
	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
	
	public Long getCliques() {
		return cliques;
	}
	
	public void setCliques(Long cliques) {
		this.cliques = cliques;
	}
	
	public Long getPercebida() {
		return percebida;
	}
	
	public void setPercebida(Long percebida) {
		this.percebida = percebida;
	}
	
	public Long getMedida() {
		return medida;
	}
	
	public void setMedida(Long medida) {
		this.medida = medida;
	}
	
	public Double getSatisfacao() {
		return satisfacao;
	}
	
	public void setSatisfacao(Double satisfacao) {
		this.satisfacao = satisfacao;
	}
}
