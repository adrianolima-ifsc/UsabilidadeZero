package controllers;

import javax.inject.Inject;

import autenticadores.UsuarioAutenticado;
import models.Estudo;
import models.Usuario;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.instrucao;
import views.html.login;
import views.html.sobre;

public class HomeController extends Controller {

	private Form<Usuario> usuarioForm;
	private Form<Estudo> estudoForm;

	@Inject
	public HomeController(FormFactory formFactory) {

		this.usuarioForm = formFactory.form(Usuario.class);
		this.estudoForm = formFactory.form(Estudo.class);
	}

	public Result index() {

		return ok(login.render(usuarioForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarSobre() {

		return ok(sobre.render(
				"UsabilidadeZero",
				play.core.PlayVersion.current()));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarInstrucoes() {
		
		Estudo form = estudoForm.bindFromRequest().get();
		
		return ok(instrucao.render(form.isTipo()));
	}
}
