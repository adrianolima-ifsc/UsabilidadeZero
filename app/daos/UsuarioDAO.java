package daos;

import models.*;

import java.util.Optional;
import io.ebean.Finder;

public class UsuarioDAO {
	
	public static Finder<Long, Usuario> usuarios = new Finder<>(Usuario.class);
	
	public Optional<Usuario> comEmail(String email) {
		
		Usuario usuario = usuarios.query().where().eq("email", email).findOne();
		return Optional.ofNullable(usuario);
	}

}
