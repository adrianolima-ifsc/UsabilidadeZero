package controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import autenticadores.UsuarioAutenticado;
import daos.EstudoDAO;
import daos.EventoDAO;
import daos.TarefaDAO;
import daos.TokenSistemaDAO;
import daos.UsuarioDAO;
import models.Estudo;
import models.Evento;
import models.Inscricao;
import models.Tarefa;
import models.TokenSistema;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.*;

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
	private Form<Inscricao> inscricaoForm;
	DynamicForm testeForm;

	public static final String AUTH = "auth";
	
	@Inject
	public ControladorEstudos(FormFactory formFactory) {

		this.estudoForm = formFactory.form(Estudo.class);
		this.tarefaForm = formFactory.form(Tarefa.class);
		this.inscricaoForm = formFactory.form(Inscricao.class);
		this.testeForm = formFactory.form();
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
		
		Form<Estudo> form = estudoForm.bindFromRequest();
		Estudo formEstudo = form.get();	
		
		Estudo estudo = estudoDAO.comId(formEstudo.getId()).get();		
		List<Evento> eventos = eventoDAO.mostraTodos();		
		
		Tarefa tarefa;
		Optional<Tarefa> possivelTarefa = tarefaDAO.comToken(session(AUTH));
		
		if(possivelTarefa.isPresent()) {
			
			tarefa = possivelTarefa.get();
			
			if(tarefa.getEstudo().getId() != estudo.getId()) {
				
				tarefa.setToken(null);
				tarefa.update();
				tarefa = criarNovaTarefa(estudo);
			}
		
		} else {
			
			tarefa = criarNovaTarefa(estudo);
		}

		if(estudo.isTipo()) {
			
			tarefa.setCodigo("EC11");
			tarefa.update();
			
			return ok(estudo1portal.render(tarefa, tarefaForm, eventos));
		
		} else {
			
			tarefa.setCodigo("EC01");
			tarefa.update();
			
			return ok(estudo0portal.render(tarefa, tarefaForm, eventos));
		}
		
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa2() {
		
		return ok();
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result concluirTarefa() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		
		Calendar calendario = Calendar.getInstance();
		tarefa.setDataHoraFim(calendario.getTime());
		
		tarefa.setConcluidoPercebido(true);
		
		tarefa.setConcluidoReal(form.isConcluidoReal());
			
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorio.render(tarefa, tarefaForm, estudo.getTarefas()));
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
	
	public Tarefa criarNovaTarefa(Estudo estudo) {
		
		Calendar calendario = Calendar.getInstance();			
		Tarefa tarefa = new Tarefa(estudo, calendario.getTime());
		
		TokenSistema token = tokenSistemaDAO.comCodigo(session(AUTH)).get();
		token.setTarefa(tarefa);
		token.update();
		
		tarefa.setToken(token);
		
		tarefa.save();
		
		return tarefa;
	}
}
