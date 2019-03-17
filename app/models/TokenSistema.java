package models;

import java.security.SecureRandom;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.ebean.Model;
import validadores.Encriptador;

@Entity
public class TokenSistema extends Model {
	
	private SecureRandom random;
	@Id @GeneratedValue
	private long id;	
	@OneToOne
	private Usuario usuario;	
	@OneToOne(mappedBy = "token")
	private Estudo estudo;
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
	
	public Estudo getEstudo() {
		return estudo;
	}

	public void setEstudo(Estudo estudo) {
		this.estudo = estudo;
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
