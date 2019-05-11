package controllers;

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
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
import models.RelatorioEstudo;
import models.Sus;
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
import views.html.estudoCasoInstrucao;
import views.html.relatorioTarefa;
import views.html.relatorioParcial;
import views.html.relatorioFinal;
import views.html.sus;
import views.html.tarefa;

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
	private Form<Sus> susForm;

	public static final String AUTH = "auth";
	
	@Inject
	public ControladorEstudos(FormFactory formFactory) {

		this.estudoForm = formFactory.form(Estudo.class);
		this.tarefaForm = formFactory.form(Tarefa.class);
		this.inscricaoForm = formFactory.form(Inscricao.class);
		this.susForm = formFactory.form(Sus.class);
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarEstudo() {
		
		Estudo form = estudoForm.bindFromRequest().get();
		Estudo estudo = estudoDAO.comId(form.getId()).get();
			
		if (estudo.isTipo()) {
			
			Estudo estudoZero = estudo.getRelacionado();
			concluirEstudo(estudo);
			return ok(relatorioFinal.render(estudoZero.getRelatorio(), estudo.getRelatorio(), estudoForm));
		
		} else {
			return ok(estudoCasoInstrucao.render(true)); 
		}
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoZero() {	
		
		Estudo estudo;
		
		Optional<Estudo> possivelEstudo = estudoDAO.comToken(session(AUTH));
		if (possivelEstudo.isPresent()) {
			
			estudo = possivelEstudo.get();

			if (estudo.getTarefas().size() > 0 || estudo.isTipo()) {
			
				concluirEstudo(estudo);
				estudo = criarNovoEstudo(false);
			}
			
		} else {
			
			estudo = criarNovoEstudo(false);
		}
		
        estudo.save();
        
	    return ok(tarefa.render(estudo, estudoForm, 1));        
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarEstudoUm() {	
		
		Estudo estudo;
		
		Optional<Estudo> possivelEstudo = estudoDAO.comToken(session(AUTH));
		if (possivelEstudo.isPresent()) {
			
			estudo = possivelEstudo.get();

			if (!estudo.isTipo()) {
			
				Estudo estudoZero = estudo;
				concluirEstudo(estudo);
				
				estudo = criarNovoEstudo(true);
				estudo.setRelacionado(estudoZero);
				
				estudoZero.setRelacionado(estudo);
				estudoZero.update();
			}
			
		} else {
			
			estudo = criarNovoEstudo(true);
		}
		
        estudo.save();
        
	    return ok(tarefa.render(estudo, estudoForm, 1));        
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result continuarEstudo() {
		
		Estudo estudo = estudoDAO.comToken(session(AUTH)).get();
		
		int numTarefa = (estudo.getTarefas().size() + 1); 
		
		if (estudo.getTarefas().size() == 3) return ok(sus.render(estudo, susForm));		
        
	    return ok(tarefa.render(estudo, estudoForm, numTarefa));        
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public void concluirEstudo(Estudo estudo) {
		
		if (estudo.getToken() != null) {
			
			List<Tarefa> tarefas = estudo.getTarefas();
			
			if (tarefas.size() == 3) gerarRelatorio(estudo);
			
			estudo.setToken(null);
			estudo.update();		
		}
	}

	private void gerarRelatorio(Estudo estudo) {
		
		RelatorioEstudo relatorio = new RelatorioEstudo(estudo);
		List<Tarefa> tarefas = estudo.getTarefas();
				
		Long tempo = 0L;
		Long cliques = 0L;
		Long percebida = 0L;
		Long medida = 0L;

		int numTarefas = 0;
		for (Tarefa tarefa : tarefas) {

			tempo += (tarefa.getDataHoraFim().getTime() - tarefa.getDataHoraInicio().getTime());

			cliques += tarefa.getCliques();

			if (tarefa.isConcluidoPercebido()) percebida++;
			if (tarefa.isConcluidoReal()) medida++;

			numTarefas++;
		}

		percebida = (percebida > 0) ? percebida * 100 / numTarefas : percebida;
		medida = (medida > 0) ? medida * 100 / numTarefas : medida;

		relatorio.setTempo(tempo/1000);
		relatorio.setCliques(cliques);
		relatorio.setPercebida(percebida);
		relatorio.setMedida(medida);

		Double satisfacao = estudo.getSus().getTotal();
		relatorio.setSatisfacao(satisfacao);

		relatorio.setEstudo(estudo);
		estudo.setRelatorio(relatorio);
		relatorio.save();
	}

	@Authenticated(UsuarioAutenticado.class)
	public Result iniciarTarefa() {
		
		Estudo estudo = estudoDAO.comToken(session(AUTH)).get();	
		List<Evento> eventos = eventoDAO.mostraTodos();
		
		Tarefa tarefa = criarNovaTarefa(estudo);

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

		if (tarefa.getDataHoraFim() == null) {
			
			Calendar calendario;
			calendario = Calendar.getInstance();
			tarefa.setDataHoraFim(calendario.getTime());
		}
		
		tarefa.setConcluidoPercebido(true);
		
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorioTarefa.render(tarefa, tarefaForm, estudo.isTipo(), estudoForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result desistirTarefa() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();

		if (tarefa.getDataHoraFim() == null) {
			
			Calendar calendario;
			calendario = Calendar.getInstance();
			tarefa.setDataHoraFim(calendario.getTime());
		}
		
		tarefa.setConcluidoPercebido(false);
			
		tarefa.update();
		
		Long idEstudo = tarefa.getEstudo().getId();
		Estudo estudo = estudoDAO.comId(idEstudo).get();
		
		return ok(relatorioTarefa.render(tarefa, tarefaForm, estudo.isTipo(), estudoForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result enviarPesquisa() {
		
		Estudo estudo = estudoDAO.comToken(session(AUTH)).get();
		
		Sus pesquisa = susForm.bindFromRequest().get();
		
		Long total = pesquisa.getQ1() - 1;
		total = total + 5 - pesquisa.getQ2();
		total = total + pesquisa.getQ3() - 1;
		total = total + 5 - pesquisa.getQ4();
		total = total + pesquisa.getQ5() - 1;
		total = total + 5 - pesquisa.getQ6();
		total = total + pesquisa.getQ7() - 1;
		total = total + 5 - pesquisa.getQ8();
		total = total + pesquisa.getQ9() - 1;
		total = total + 5 - pesquisa.getQ10();
		pesquisa.setTotal(total * 2.5);
		
		if (estudo.getSus() == null) {
			
			pesquisa.setEstudo(estudo);
			estudo.setSus(pesquisa);
			pesquisa.save();

		} else {
			
			pesquisa.setEstudo(estudo);
			estudo.setSus(pesquisa);
			pesquisa.update();
		}
		
		Long satisfacao = estudo.getSus().getTotal().longValue(); 
		
		return ok(relatorioParcial.render(satisfacao, estudo.getTarefas(), estudo, estudoForm));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result adicionaCliquesTarefa(Long id) {
		
		Tarefa tarefa = tarefaDAO.comId(id).get();
		
		if (tarefa.getDataHoraFim() == null) {
			
			Long cliques = tarefa.getCliques();
			cliques++;
			tarefa.setCliques(cliques);
			
			tarefa.update();
		}
		
		return ok();
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result setConcluido(Long id) {
		
		Tarefa tarefa = tarefaDAO.comId(id).get();
		
		tarefa.setConcluidoReal(true);
		
		tarefa.update();
		
		return ok();
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Tarefa criarNovaTarefa(Estudo estudo) {
		
		Tarefa tarefa;
		
		List<Tarefa> tarefas = estudo.getTarefas();
		
		String codigo = "EC" + (estudo.isTipo() ? "1" : "0");
		Calendar calendario;
		calendario = Calendar.getInstance();
		
		if (tarefas.size() == 0) {
			
			codigo = codigo + "1";
		
		} else {
			
			Tarefa ultimaTarefa = tarefas.get(tarefas.size() - 1);
			
			if (ultimaTarefa.getDataHoraFim() == null) {
				
				tarefa = ultimaTarefa;
				tarefa.save();
				
				return tarefa;
			
			} else {
				
				String numTarefa = Integer.toString(tarefas.size() + 1);
				codigo = codigo + numTarefa.toString();
			}
			
		}
		
		tarefa = new Tarefa(codigo, estudo, calendario.getTime());
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
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		Estudo estudo = tarefa.getEstudo();	
		List<Evento> eventos = eventoDAO.mostraTodos();	
		
		if (evento.getSigla().equals("BRACIS")) { //Trocar o teste pelo id
		
			if (testarInscricao(form)) {

				setConcluido(tarefa.getId());	
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
