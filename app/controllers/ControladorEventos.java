package controllers;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;

import daos.EventoDAO;
import daos.TarefaDAO;
import models.Estudo;
import models.Evento;
import models.Inscricao;
import models.Tarefa;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

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
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			String data = calcularDataEvento(evento.getData(), "-");
			
			return ok(estudo1evento.render(tarefa, tarefaForm, evento, data));
		}
		
		return ok(estudo0evento.render(tarefa, tarefaForm, evento));
	}

	public Result mostrarPrograma() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento());
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + evento.getData()) / 30) + 1;
		
		String dia1 = Integer.toString(dia) + "/" + Integer.toString(mes);
		String dia2 = Integer.toString(dia + 1) + "/" + Integer.toString(mes);
		String dia3 = Integer.toString(dia + 2) + "/" + Integer.toString(mes);
		
		String programa = evento.getPrograma();
		
		programa = programa.replace("@dia1", dia1);
		programa = programa.replace("@dia2", dia2);
		programa = programa.replace("@dia3", dia3);
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			return ok(estudo1programa.render(tarefa, evento.getNome(), programa));
		}
		
		return ok(estudo0programa.render(tarefa, evento.getSigla(), programa));
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
	
	public Result mostrarInformacoes() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento());
		Estudo estudo = tarefa.getEstudo();
		
		if(estudo.isTipo()) {
			
			return ok(estudo1informacoes.render(tarefa, evento));
		}

		return ok(estudo0informacoes.render(tarefa, evento));
	}
	
	public Result mostrarPagamento() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento());
		
		String data = calcularDataPagamento(evento.getData());
		
		return ok(estudo0pagamento.render(tarefa, evento, data));
	}
	
	public Result mostrarParticipe() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento());
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			String data = calcularDataPagamento(evento.getData());
			return ok(estudo1participe.render(tarefa, inscricaoForm, evento, data));
		}
		
		return ok(estudo0participe.render(tarefa, inscricaoForm, evento));
	}
	
	public String calcularDataEvento(int data, String separador) {
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + data) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + data) / 30) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		return Integer.toString(dia) + 
				" " + separador + " " +
				Integer.toString(dia + 2) + 
				"/" + Integer.toString(mes) +
				"/" + Integer.toString(ano);
	}
	
	public String calcularDataPagamento(int data) {
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		int dia = (hoje.get(Calendar.DAY_OF_YEAR) + data) % 30;
		int mes = ((hoje.get(Calendar.DAY_OF_YEAR) + data) / 30 - 2) + 1;
		int ano = hoje.get(Calendar.YEAR);
		
		return Integer.toString(dia) + 
				"/" + Integer.toString(mes) + 
				"/" + Integer.toString(ano);
	}

}
