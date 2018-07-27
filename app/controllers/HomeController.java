package controllers;

import javax.inject.Inject;

import models.*;
import views.html.*;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

	private Form<Usuario> usuarioForm;

	@Inject
	public HomeController(FormFactory formFactory) {

		this.usuarioForm = formFactory.form(Usuario.class);
	}

	public Result index() {

		return ok(telaLogin.render(usuarioForm));
	}
}
