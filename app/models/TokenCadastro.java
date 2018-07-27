package models;

import validadores.*;

import javax.persistence.*;
import io.ebean.Model;
import java.security.SecureRandom;

@Entity
public class TokenCadastro extends Model {
	
	SecureRandom random;
	
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private Usuario usuario;
	private String codigo;
	
	public TokenCadastro(Usuario usuario) {
		
		this.usuario = usuario;
		this.codigo = Encriptador.crypt(usuario.getId()+usuario.getEmail()+this.gerarToken());
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String gerarToken() {
		this.random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes.toString();
		
	}

}
