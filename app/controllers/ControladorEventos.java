package controllers;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;

import daos.EventoDAO;
import daos.TarefaDAO;
import models.Evento;
import models.Tarefa;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.estudo0informacoes;
import views.html.estudo0local;
import views.html.estudo0pagamento;
import views.html.estudo0programa;
import views.html.telaEvento;

public class ControladorEventos extends Controller {
	
	@Inject
	private EventoDAO eventoDAO;
	
	@Inject
	private TarefaDAO tarefaDAO;
	
	private Form<Tarefa> tarefaForm;
	
	@Inject	
	public ControladorEventos(FormFactory formFactory) {

		this.tarefaForm = formFactory.form(Tarefa.class);
	}
	
	public Result detalhar() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		
		int cliques = form.getCliques();
		tarefa.setCliques(cliques);
		
		tarefa.update();
		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		return ok(telaEvento.render(tarefa, tarefaForm, evento));
	}

	public Result mostrarPrograma() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		String programa = evento.getPrograma();

		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30) + 1;
		
		String dia1 = Integer.toString(dia) + "/" + Integer.toString(mes);
		String dia2 = Integer.toString(dia + 1) + "/" + Integer.toString(mes);
		String dia3 = Integer.toString(dia + 2) + "/" + Integer.toString(mes);
		
		programa = programa.replace("@dia1", dia1);
		programa = programa.replace("@dia2", dia2);
		programa = programa.replace("@dia3", dia3);
		
		return ok(estudo0programa.render(evento, programa));
	}
	
	public Result mostrarLocal(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		String dataInicial = Integer.toString(dia);
		String dataFinal = Integer.toString(dia + 2) + 
				"/" + Integer.toString(mes) +
				"/" + Integer.toString(ano);
		
		return ok(estudo0local.render(evento, dataInicial, dataFinal));
	}
	
	public Result mostrarInformacoes(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
		return ok(estudo0informacoes.render(evento));
	}
	
	public Result mostrarPagamento(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30 - 2) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		String data = Integer.toString(dia) + "/" + Integer.toString(mes) +	"/" + Integer.toString(ano);
		
		return ok(estudo0pagamento.render(evento, data));
	}

}
