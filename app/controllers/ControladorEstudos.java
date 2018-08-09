package controllers;

import autenticadores.*;
import daos.*;
import models.*;
import validadores.*;
import views.html.*;

import java.util.List;

import javax.inject.Inject;

import play.api.libs.mailer.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.mvc.Security.Authenticated;

public class ControladorEstudos extends Controller {
	
	@Inject
	private EventoDAO eventoDAO;
	
	public static final String AUTH = "auth";
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoDeCaso(boolean ec) {
		
		return ok(tarefa1.render(ec));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa1() {
		
		List<Evento> eventos = eventoDAO.mostraTodos();
		
		return ok(portal.render(eventos));
	}
	
}
