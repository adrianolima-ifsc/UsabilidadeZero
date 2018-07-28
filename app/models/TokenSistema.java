package models;

import validadores.*;

import java.security.SecureRandom;
import java.util.*;
import javax.persistence.*;
import io.ebean.Model;

@Entity
public class TokenSistema extends Model {
	
	private SecureRandom random;

	@Id @GeneratedValue
	private long id;
	@OneToOne
	private Usuario usuario;
	private String codigo;
	private Date expiracao;
	
	public TokenSistema(Usuario usuario) {
		
		this.usuario = usuario;
		this.expiracao = new Date();
		this.codigo = Encriptador.crypt(expiracao.toString()+usuario.toString()+this.gerarToken());
	}
	
	public long getId() {
		return id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public String getCodigo() {
		return codigo;
	}
	public Date getExpiracao() {
		return expiracao;
	}
	
	public String gerarToken() {
		this.random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes.toString();
		
	}
}
