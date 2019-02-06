
package daos;

import models.*;

import java.util.*;
import io.ebean.Finder;

public class UsuarioDAO {
	
	private static Finder<Long, Usuario> find = new Finder<>(Usuario.class);
	
	public Optional<Usuario> comEmail(String email) {
		
		Usuario usuario = find
				.query()
				.where()
				.eq("email", email)
				.findOne();
		
		return Optional.ofNullable(usuario);
	}

	public Optional<Usuario> comEmailSenha(String email, String criptoSenha) {
		
		Usuario usuario = find
				.query()
				.where()
				.eq("email", email)
				.eq("senha", criptoSenha)
				.findOne();
		
		return Optional.ofNullable(usuario);
	}
	
	public List<Usuario> mostraTodos() {
		
		return find.all();
	}

	public Optional<Usuario> comToken(String codigo) {
		
		Usuario usuario = find
				.query()
				.where()
				.eq("token.codigo", codigo)
				.findOne();
		
		return Optional.ofNullable(usuario);
	}

}
