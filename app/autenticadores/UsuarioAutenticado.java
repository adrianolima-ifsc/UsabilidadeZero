package autenticadores;

import controllers.*;
import play.mvc.Http.Context;
import play.mvc.*;
import play.mvc.Security.Authenticator;

public class UsuarioAutenticado extends Authenticator {

	@Override
	public String getUsername(Context context) {
		
		return context.session().get(ControladorUsuario.AUTH);
	}
	
	@Override
	public Result onUnauthorized(Context context) {
		
		context.flash().put("danger", "Não autorizado!");
		
		return redirect(routes.HomeController.index());
	}
}
