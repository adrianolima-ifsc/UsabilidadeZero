package controllers;

import java.util.Calendar;
import java.util.Collections;
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
import models.Inscricao;
import models.Tarefa;
import models.TokenSistema;
import models.Usuario;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.estudo0portal;
import views.html.estudo1portal;
import views.html.relatorio;
import views.html.tarefa1;
import views.html.tarefa2;

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

	public static final String AUTH = "auth";
	
	@Inject
	public ControladorEstudos(FormFactory formFactory) {

		this.estudoForm = formFactory.form(Estudo.class);
		this.tarefaForm = formFactory.form(Tarefa.class);
		this.inscricaoForm = formFactory.form(Inscricao.class);
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoDeCaso() {
		
		Estudo form = estudoForm.bindFromRequest().get();	
		Estudo estudo;
		
		Optional<Estudo> possivelEstudo = estudoDAO.comToken(session(AUTH));
		if(possivelEstudo.isPresent()) {
			
			estudo = possivelEstudo.get();

			if(estudo.isTipo() ^ form.isTipo()) {
			
				estudo.setToken(null);
				estudo.update();
				estudo = criarNovoEstudo(form.isTipo());
			}
			
		} else {
			
			estudo = criarNovoEstudo(form.isTipo());
		}
		
        estudo.save();
        
        switch (estudo.getTarefas().size()) {
		
        case 0:	return ok(tarefa1.render(estudo, estudoForm));

        case 1: return ok(tarefa2.render(estudo, estudoForm));
		
        default: return ok("Tarefa 3");
		}
        
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa1() {
		
		Estudo form = estudoForm.bindFromRequest().get();	
		
		Estudo estudo = estudoDAO.comId(form.getId()).get();	
		List<Evento> eventos = eventoDAO.mostraTodos();
		
		Tarefa tarefa = criarNovaTarefa(estudo, 1L);

		if(estudo.isTipo()) {
			
			return ok(estudo1portal.render(tarefa, tarefaForm, eventos));
		
		} else {
			
			Collections.shuffle(eventos);
			return ok(estudo0portal.render(tarefa, tarefaForm, eventos));
		}
		
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa2() {
		
		Estudo form = estudoForm.bindFromRequest().get();	
		
		Estudo estudo = estudoDAO.comId(form.getId()).get();	
		List<Evento> eventos = eventoDAO.mostraTodos();	
		
		Tarefa tarefa = criarNovaTarefa(estudo, 2L);

		if(estudo.isTipo()) {
			
			return ok(estudo1portal.render(tarefa, tarefaForm, eventos));
		
		} else {
			
			Collections.shuffle(eventos);
			return ok(estudo0portal.render(tarefa, tarefaForm, eventos));
		}
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result concluirTarefa() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();

		Calendar calendario;
		calendario = Calendar.getInstance();
		tarefa.setDataHoraFim(calendario.getTime());
		
		tarefa.setConcluidoPercebido(true);
		
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorio.render(tarefa, tarefaForm, estudo.getTarefas(), estudoForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result desistirTarefa() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();

		Calendar calendario;
		calendario = Calendar.getInstance();
		tarefa.setDataHoraFim(calendario.getTime());
		
		tarefa.setConcluidoPercebido(false);
			
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorio.render(tarefa, tarefaForm, estudo.getTarefas(), estudoForm));
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
	
	@Authenticated(UsuarioAutenticado.class)
	public Result setConcluidoReal(Long id) {
		
		Tarefa tarefa = tarefaDAO.comId(id).get();
		
		tarefa.setConcluidoReal(true);
		
		tarefa.update();
		
		return ok();
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Tarefa criarNovaTarefa(Estudo estudo, Long numTarefa) {
		
		if(numTarefa <= estudo.getTarefas().size()) 
			return estudo.getTarefas().get(numTarefa.intValue() - 1);
		
		String codigo = "EC" +
				(estudo.isTipo() ? "1" : "0") +
				numTarefa.toString();

		Calendar calendario;
		calendario = Calendar.getInstance();
		Tarefa tarefa = new Tarefa(codigo, estudo, calendario.getTime());
		tarefa.save();
		
		return tarefa;
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Estudo criarNovoEstudo(boolean tipo) {

		Calendar calendario;
		calendario = Calendar.getInstance();
		Usuario usuario = usuarioDAO.comToken(session(AUTH)).get();
		
		Estudo estudo = new Estudo(tipo, usuario, calendario.getTime());
		
		TokenSistema token = tokenSistemaDAO.comCodigo(session(AUTH)).get();
		estudo.setToken(token);
		
		return estudo;
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result fazerInscricao() {
		
		Inscricao form = inscricaoForm.bindFromRequest().get();	
		
		Tarefa tarefa = tarefaDAO.comId(form.getTarefa()).get();
		Estudo estudo = tarefa.getEstudo();	
		List<Evento> eventos = eventoDAO.mostraTodos();	
		
		if (form.getEvento() == 2) {
		
			if (testarInscricao(form)) {

				setConcluidoReal(tarefa.getId());	
				tarefa.update();
			}
		}
		
		if(estudo.isTipo()) {
			
			return ok(estudo1portal.render(tarefa, tarefaForm, eventos));
		
		} else {
			
			Collections.shuffle(eventos);
			return ok(estudo0portal.render(tarefa, tarefaForm, eventos));
		}
	}

	@Authenticated(UsuarioAutenticado.class)
	private boolean testarInscricao(Inscricao form) {

		if (!form.getNumCartao().replaceAll("\\s+", "").equals("4609868766944752")) {return false;}
		else if (!form.getValidade().equals("09/2030")) {return false;}
		else if (!form.getCodigoSeguranca().equals("902")) {return false;}
			
		return true;
	}
}
