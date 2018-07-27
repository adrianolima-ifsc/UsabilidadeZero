package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import play.data.validation.Constraints.*;

@Entity
public class Usuario extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;	
	@Required
	private String email;	
	@Required(message = "Este campo não pode estar vazio!")
	private String senha;
	private boolean verificado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}
	
	

}
