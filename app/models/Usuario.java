package models;

import javax.persistence.*;

import io.ebean.*;
import play.data.validation.Constraints.*;

@Entity
public class Usuario extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;	
	@Required(message = "Você precisa fornecer um email válido!")
	private String email;	
	@Required(message = "Você precisa fornecer uma senha!")
	private String senha;
	private boolean verificado;
	@OneToOne(mappedBy = "usuario")
	private TokenSistema token;

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

	public TokenSistema getToken() {
		return token;
	}

	public void setToken(TokenSistema token) {
		this.token = token;
	}
}
