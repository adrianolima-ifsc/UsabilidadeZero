package controllers;

import autenticadores.*;
import models.*;
import views.html.*;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;

import java.util.Date;
import java.util.Calendar;
import javax.inject.Inject;

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
