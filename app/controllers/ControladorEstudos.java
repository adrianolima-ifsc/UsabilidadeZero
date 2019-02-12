package controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import autenticadores.UsuarioAutenticado;
import daos.EstudoDAO;
import daos.EventoDAO;
import daos.TarefaDAO;
import daos.TokenSistemaDAO;
import daos.UsuarioDAO;
import models.Estudo;
import models.Evento;
import models.Tarefa;
import models.TokenSistema;
import models.Usuario;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.estudo0portal;
import views.html.tarefa1;

public class ControladorEstudos extends Controller {

	@Inject
	private EventoDAO eventoDAO;	
	@Inject
	private UsuarioDAO usuarioDAO;	
	@Inject 
	private EstudoDAO estudoDAO;
	@Inject
	private TarefaDAO tarefaDAO;
	@Inject
	private TokenSistemaDAO tokenSistemaDAO;
	
	private Form<Estudo> estudoForm;
	private Form<Tarefa> tarefaForm;

	public static final String AUTH = "auth";
	
	@Inject
	public ControladorEstudos(FormFactory formFactory) {

		this.estudoForm = formFactory.form(Estudo.class);
		this.tarefaForm = formFactory.form(Tarefa.class);
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoDeCaso() {
		
		Form<Estudo> form = estudoForm.bindFromRequest();
		Estudo estudo = form.get();	
		
		Calendar calendario = Calendar.getInstance();
		estudo.setData(calendario.getTime());
		
        Usuario usuario = usuarioDAO.comToken(session(AUTH)).get();
        estudo.setUsuario(usuario);
		
        estudo.save();
        
		return ok(tarefa1.render(estudo, estudoForm, usuario));
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa1() {
		
		String codigoSessao = session(AUTH);
		
		Form<Estudo> form = estudoForm.bindFromRequest();
		Estudo formEstudo = form.get();	
		
		Estudo estudo = estudoDAO.comId(formEstudo.getId()).get();		
		List<Evento> eventos = eventoDAO.mostraTodos();		
		
		Tarefa tarefa;
		Optional<Tarefa> possivelTarefa = tarefaDAO.comToken(codigoSessao);
		
		if(possivelTarefa.isPresent()) {
			
			tarefa = possivelTarefa.get();
			tarefa.update();
			
		} else {
			
			Calendar calendario = Calendar.getInstance();			
			tarefa = new Tarefa(estudo, calendario.getTime());
			
			TokenSistema token = tokenSistemaDAO.comCodigo(codigoSessao).get();
			token.setTarefa(tarefa);
			token.update();
			
			tarefa.setToken(token);
			
			if(estudo.isTipo()) {
				
				tarefa.setCodigo("EC11");
			
			} else {
			
				tarefa.setCodigo("EC01");
			}
			
			tarefa.save();
		}
		
		return ok(estudo0portal.render(tarefa, tarefaForm, eventos));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa2() {
		
		return ok();
	}
	
	@AddCSRFToken
	@Authenticated(UsuarioAutenticado.class)
	public Result concluirTarefa() {
		
//		if(concluiu) {
//			
//			return ok("verdade");
//		}
	
		return ok("Valeu!!!");
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result desistirTarefa() {
		
		return ok();
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result adicionaCliquesTarefa(Long id) {
		
		Tarefa tarefa = tarefaDAO.comId(id).get();
		
		Long cliques = tarefa.getCliques();
		cliques++;
		tarefa.setCliques(cliques);
		
		tarefa.update();
		
		return ok();
	}
}
