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
import play.routing.JavaScriptReverseRouter;
import views.html.estudoCasoInstrucao;
import views.html.login;
import views.html.painel;
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

		return ok(painel.render(sobre.render(
				"UsabilidadeZero",
				play.core.PlayVersion.current())));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarEstudoDeCaso(boolean tipo) {
		
		return ok(painel.render(estudoCasoInstrucao.render(estudoForm, tipo)));
	}
}
