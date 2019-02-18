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
import views.html.estudo0portal;
import views.html.painel;
import views.html.sobre;
import views.html.tarefa1;
import views.html.relatorio;

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
	
	@Authenticated(UsuarioAutenticado.class)
	public Result concluirTarefa() {
		
		DynamicForm teste = testeForm.bindFromRequest();
		Long id = Long.parseLong(teste.get("id"));
		
		Tarefa tarefa = tarefaDAO.comId(id).get();
		
		Calendar calendario = Calendar.getInstance();
		tarefa.setDataHoraFim(calendario.getTime());
		
		tarefa.setConcluidoPercebido(true);
		
		Boolean concluidoReal = Boolean.valueOf(teste.get("valor"));
		tarefa.setConcluidoReal(concluidoReal);
			
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorio.render(estudo.getTarefas()));
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
