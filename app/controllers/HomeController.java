package controllers;

import autenticadores.*;
import models.*;
import views.html.*;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import javax.inject.Inject;

public class HomeController extends Controller {

	private Form<Usuario> usuarioForm;

	@Inject
	public HomeController(FormFactory formFactory) {

		this.usuarioForm = formFactory.form(Usuario.class);
	}

	public Result index() {

		return ok(login.render(usuarioForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarSobre() {

		return ok(painel.render(sobre.render(
				"UsabilidadeZero",
				play.core.PlayVersion.current())));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarEstudoDeCaso(boolean estudo) {

		return ok(painel.render(estudoCasoInstrucao.render(estudo)));
	}
	
}
