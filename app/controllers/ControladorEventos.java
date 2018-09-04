package controllers;

import daos.*;
import models.*;
import views.html.*;

import javax.inject.Inject;
import java.util.*;
import play.mvc.*;

public class ControladorEventos extends Controller {
	
	@Inject
	EventoDAO eventoDAO;
	
	public Result detalhar(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
		return ok(telaEvento.render(evento));
	}
	
	public Result mostrarPrograma(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
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
	
	public Result mostrarPagamento(Long id) {
		
		Evento evento = eventoDAO.comId(id);
		
		return ok(pagamentoEstudo0.render(evento));
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
		
		return ok(localEstudo0.render(evento, dataInicial, dataFinal));
	}

}
