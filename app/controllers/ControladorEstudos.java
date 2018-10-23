package controllers;

import autenticadores.*;
import daos.*;
import models.*;
import validadores.*;
import views.html.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.inject.Inject;
import javax.swing.Timer;

import play.api.libs.mailer.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.mvc.Security.Authenticated;

public class ControladorEstudos extends Controller {

	@Inject
	private EventoDAO eventoDAO;
	private Timer timer1;
	private Timer timer2;
	private Timer timer3;

	public static final String AUTH = "auth";

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoDeCaso(boolean ec) {

		return ok(tarefa1.render(ec));
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa1(boolean estudo) {
		
		timer1 = new Timer(0, new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {

				if (evt.getActionCommand().equals(anObject)) {
					timer.stop();
					//...Update the GUI...
	        	}
			}    
		});
		
		List<Evento> eventos = eventoDAO.mostraTodos();
		
		if(estudo) {
			
			return ok(estudo1portal.render(eventos));
		}
		
		return ok(estudo0portal.render(eventos));
	}

}
