package controllers;

import autenticadores.*;
import daos.*;
import models.*;
import validadores.*;
import views.html.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.swing.Timer;

import play.api.libs.mailer.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticated;

public class ControladorEstudos extends Controller {

	@Inject
	private EventoDAO eventoDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	private Form<Estudo> estudoForm;
	private Form<Usuario> usuarioForm;

	public static final String AUTH = "auth";
	
	@Inject
	public ControladorEstudos(FormFactory formFactory) {

		this.estudoForm = formFactory.form(Estudo.class);
		this.usuarioForm = formFactory.form(Usuario.class);
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoDeCaso() {
		
		Form<Estudo> form = estudoForm.bindFromRequest();
		Estudo estudo = form.get();
		
		Calendar calendario = Calendar.getInstance();
		estudo.setData(calendario.getTime());
		
		String codigo = session(AUTH);
        Usuario usuario = usuarioDAO.comToken(codigo).get();
        estudo.setUsuario(usuario);
		
        estudo.save();
        
		return ok(tarefa1.render(estudo, usuario));
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa1(boolean estudo) {
		
		List<Evento> eventos = eventoDAO.mostraTodos();
		
		if(estudo) {
			
			return ok(estudo1portal.render(eventos));
		}
		
		return ok(estudo0portal.render(eventos));
	}
}
