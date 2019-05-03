package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.ebean.Model;

@Entity
public class Sus extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;
	@OneToOne
	public Estudo estudo;
	public Long q1;
	public Long q2;
	public Long q3;
	public Long q4;
	public Long q5;
	public Long q6;
	public Long q7;
	public Long q8;
	public Long q9;
	public Long q10;
	public Double total;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Estudo getEstudo() {
		return estudo;
	}
	
	public void setEstudo(Estudo estudo) {
		this.estudo = estudo;
	}
	
	public Long getQ1() {
		return q1;
	}
	
	public void setQ1(Long q1) {
		this.q1 = q1;
	}
	
	public Long getQ2() {
		return q2;
	}
	
	public void setQ2(Long q2) {
		this.q2 = q2;
	}
	
	public Long getQ3() {
		return q3;
	}
	
	public void setQ3(Long q3) {
		this.q3 = q3;
	}
	
	public Long getQ4() {
		return q4;
	}
	
	public void setQ4(Long q4) {
		this.q4 = q4;
	}
	
	public Long getQ5() {
		return q5;
	}
	
	public void setQ5(Long q5) {
		this.q5 = q5;
	}
	
	public Long getQ6() {
		return q6;
	}
	
	public void setQ6(Long q6) {
		this.q6 = q6;
	}
	
	public Long getQ7() {
		return q7;
	}
	
	public void setQ7(Long q7) {
		this.q7 = q7;
	}
	
	public Long getQ8() {
		return q8;
	}
	
	public void setQ8(Long q8) {
		this.q8 = q8;
	}
	
	public Long getQ9() {
		return q9;
	}
	
	public void setQ9(Long q9) {
		this.q9 = q9;
	}
	
	public Long getQ10() {
		return q10;
	}
	
	public void setQ10(Long q10) {
		this.q10 = q10;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
