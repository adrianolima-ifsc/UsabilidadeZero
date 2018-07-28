package autenticadores;

import controllers.*;
import daos.*;
import models.*;

import java.util.Optional;
import javax.inject.Inject;
import play.mvc.Http.Context;
import play.mvc.*;
import play.mvc.Security.Authenticator;

public class UsuarioAutenticado extends Authenticator {
	
	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public String getUsername(Context context) {
		
		String codigo = context.session().get(ControladorUsuario.AUTH);
		Optional<Usuario> possivelUsuario = usuarioDAO.comToken(codigo);
		
		if(possivelUsuario.isPresent()) {
			
			return possivelUsuario.get().getEmail();
		}
		
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		
		context.flash().put("danger", "Não autorizado!");
		
		return redirect(routes.HomeController.index());
	}
}
