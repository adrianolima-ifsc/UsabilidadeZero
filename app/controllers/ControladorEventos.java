package controllers;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;

import daos.EventoDAO;
import daos.TarefaDAO;
import models.Evento;
import models.Inscricao;
import models.Tarefa;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.estudo0informacoes;
import views.html.estudo0local;
import views.html.estudo0pagamento;
import views.html.estudo0participe;
import views.html.estudo0programa;
import views.html.telaEvento;

public class ControladorEventos extends Controller {
	
	@Inject
	private EventoDAO eventoDAO;
	
	@Inject
	private TarefaDAO tarefaDAO;
	
	private Form<Tarefa> tarefaForm;
	private Form<Inscricao> inscricaoForm;
	
	@Inject	
	public ControladorEventos(FormFactory formFactory) {

		this.tarefaForm = formFactory.form(Tarefa.class);
		this.inscricaoForm = formFactory.form(Inscricao.class);
	}
	
	public Result detalhar() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		
		Long cliquesForm = form.getCliques();
		
		if (cliquesForm > tarefa.getCliques()) tarefa.setCliques(cliquesForm);
		
		tarefa.update();
		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		return ok(telaEvento.render(tarefa, tarefaForm, evento));
	}

	public Result mostrarPrograma() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		
		Evento evento = eventoDAO.comId(form.getEvento());
		String siglaEvento = evento.getSigla();
		
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
		
		return ok(estudo0programa.render(tarefa, siglaEvento, programa));
	}
	
	public Result mostrarLocal() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento());
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		String dataInicial = Integer.toString(dia);
		String dataFinal = Integer.toString(dia + 2) + 
				"/" + Integer.toString(mes) +
				"/" + Integer.toString(ano);
		
		return ok(estudo0local.render(tarefa, evento, dataInicial, dataFinal));
	}
	
	public String calcularData(int data) {
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + data) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + data) / 30) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		String dataCalculada = Integer.toString(dia) +
				" - " +
				Integer.toString(dia + 2) + 
				"/" + Integer.toString(mes) +
				"/" + Integer.toString(ano);
		
		return dataCalculada;
	}
	
	public Result mostrarInformacoes() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento());

		return ok(estudo0informacoes.render(tarefa, evento));
	}
	
	public Result mostrarPagamento() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30 - 2) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		String data = Integer.toString(dia) + "/" + Integer.toString(mes) +	"/" + Integer.toString(ano);
		
		return ok(estudo0pagamento.render(tarefa, evento, data));
	}
	
	public Result mostrarParticipe() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		return ok(estudo0participe.render(tarefa, inscricaoForm, evento));
	}

}
