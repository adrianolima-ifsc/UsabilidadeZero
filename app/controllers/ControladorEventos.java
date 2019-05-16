package controllers;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import autenticadores.UsuarioAutenticado;
import daos.EstudoDAO;
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
import play.mvc.Security.Authenticated;
import views.html.*;

public class ControladorEventos extends Controller {
	
	@Inject
	private EventoDAO eventoDAO;	
	@Inject
	private TarefaDAO tarefaDAO;
	@Inject
	private EstudoDAO estudoDAO;
	
	private Form<Tarefa> tarefaForm;
	private Form<Inscricao> inscricaoForm;
	
	public static final String AUTH = "auth";
	
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
		
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		evento.setPrograma(calcularPrograma(evento));
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			int[] datas = calcularDataEvento(evento);
			String periodo = dataEmString(datas, "-");
			
			return ok(estudo1evento.render(tarefa, tarefaForm, evento, inscricaoForm, periodo));
		}
		
		return ok(estudo0evento.render(tarefa, tarefaForm, evento));
	}

	private String calcularPrograma(Evento evento) {
		
		int[] datas = calcularDataEvento(evento);
		
		String dia1 = Integer.toString(datas[0]) + "/" + Integer.toString(datas[1]);
		String dia2 = Integer.toString(datas[0] + 1) + "/" + Integer.toString(datas[1]);
		String dia3 = Integer.toString(datas[0] + 2) + "/" + Integer.toString(datas[1]);
		
		String programa = evento.getPrograma();
		
		programa = programa.replace("@dia1", dia1);
		programa = programa.replace("@dia2", dia2);
		programa = programa.replace("@dia3", dia3);
		
		return programa;
	}

	public Result mostrarPrograma() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		String programa = calcularPrograma(evento);
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			return ok(estudo1programa.render(tarefa, tarefaForm, evento.getNome(), programa));
		}
		
		return ok(estudo0programa.render(tarefa,tarefaForm, evento.getSigla(), programa));
	}
	
	public Result mostrarLocal() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		int[] datas = calcularDataEvento(evento);
		
		String dataInicial = Integer.toString(datas[0]);
		String dataFinal = Integer.toString(datas[0] + 2) + 
				"/" + Integer.toString(datas[1]) +
				"/" + Integer.toString(datas[2]);
		
		return ok(estudo0local.render(tarefa, tarefaForm, evento, dataInicial, dataFinal));
	}
	
	public Result mostrarInformacoes() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		Estudo estudo = tarefa.getEstudo();
		
		if(estudo.isTipo()) {
			
			return ok(estudo1informacoes.render(tarefa, tarefaForm, evento));
		}

		return ok(estudo0informacoes.render(tarefa, tarefaForm, evento));
	}
	
	public Result mostrarPagamento() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();		
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();		
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		String data = calcularDataPagamento(evento);
		
		return ok(estudo0pagamento.render(tarefa, tarefaForm, evento, data));
	}
	
	public Result mostrarParticipe() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			String data = calcularDataPagamento(evento);
			return ok(estudo1participe.render(tarefa, tarefaForm, inscricaoForm, evento, data));
		}
		
		return ok(estudo0participe.render(tarefa, tarefaForm, inscricaoForm, evento));
	}
	
	public Result mostrarInscricao() {
		
		Inscricao form = inscricaoForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getTarefa()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		return ok(estudo1inscricao.render(tarefa, tarefaForm, inscricaoForm, evento));
	}
	
	public Result mostrarCertificadoForm() {
		
		Tarefa form = tarefaForm.bindFromRequest().get();
		Tarefa tarefa = tarefaDAO.comId(form.getId()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		Estudo estudo = tarefa.getEstudo();
		if(estudo.isTipo()) {
			
			String data = calcularDataPagamento(evento);
			return ok(estudo1participe.render(tarefa, tarefaForm, inscricaoForm, evento, data));
		}
		
		return ok(estudo0certificadoForm.render(tarefa, tarefaForm, inscricaoForm, evento));		
	}
	
	public Result mostrarCertificado() {
		
		Inscricao form = inscricaoForm.bindFromRequest().get();	
		
		Tarefa tarefa = tarefaDAO.comId(form.getTarefa()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		
		boolean participou = false;
		
		if (evento.getSigla().equals("SBSI")) {
		
			if (testarCertificado(form)) {

				tarefa.setConcluidoReal(true);
				tarefa.update();
				participou = true;
			}
		}
		
		Estudo estudo = estudoDAO.comToken(session(AUTH)).get();
		
		if (estudo.isTipo()) {
			
			return ok(estudo1certificado.render(tarefa, tarefaForm, evento, participou));		
		}
		
		return ok(estudo0certificado.render(tarefa, tarefaForm, evento, participou));	
	}
	
	public int[] calcularDataEvento(Evento evento) {
		
		Calendar hoje = Calendar.getInstance(TimeZone.getDefault());
		
		int data = evento.getData();
		int passado = hoje.get(Calendar.DAY_OF_YEAR) + data; 
				
		passado = (passado < 0) ? (passado / 365) - 1 : 0;
		
		int dia = Math.abs(hoje.get(Calendar.DAY_OF_YEAR) + data) % 30;
		int mes = Math.abs((hoje.get(Calendar.DAY_OF_YEAR) + data) / 30) + 1;
		int ano = hoje.get(Calendar.YEAR) + passado;
		
		if (dia == 0) dia++;
		if (dia > 28) dia = dia - 2;
		
		int[] datas = {dia, mes, ano};
		
		return datas;
	}

	private String dataEmString(int[] datas, String separador) {

		return Integer.toString(datas[0]) +
				" " + separador + " " +
				Integer.toString(datas[0] + 2) + "/" + 
				Integer.toString(datas[1]) + "/" + 
				Integer.toString(datas[2]);
	}
	
	public String calcularDataPagamento(Evento evento) {
		
		int[] datas = calcularDataEvento(evento);
		
		return Integer.toString(datas[0]) + 
				"/" + Integer.toString(datas[1] - 2) + 
				"/" + Integer.toString(datas[2]);
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result fazerInscricao() {
		
		Inscricao form = inscricaoForm.bindFromRequest().get();	
		
		Tarefa tarefa = tarefaDAO.comId(form.getTarefa()).get();
		Evento evento = eventoDAO.comId(form.getEvento()).get();
		Estudo estudo = tarefa.getEstudo();	
		List<Evento> eventos = eventoDAO.mostraTodos();	
		
		if (testarInscricao(form)) {

			tarefa.setConcluidoReal(true);	
			tarefa.update();
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

	private boolean testarCertificado(Inscricao form) {

		if (!form.getNome().toLowerCase().equals("martin fowler")) {return false;}
		else if (!form.getEmail().toLowerCase().equals("martin@martinfowler.com")) {return false;}
		else if (!form.getCpf().equals("683.563.770-64")) {return false;}
		else if (!form.getEndereco().equals("1234")) {return false;}
			
		return true;
	}

}
