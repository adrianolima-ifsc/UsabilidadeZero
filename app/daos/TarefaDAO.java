package daos;

import models.*;

import java.util.*;
import io.ebean.Finder;

public class TarefaDAO {
	
	private static Finder<Long, Tarefa> find = new Finder<>(Tarefa.class);
	
	public List<Tarefa> mostraTodos() {
		
		return find.all();
	}

	public Optional<Tarefa> comId(Long id) {
		
		Tarefa tarefa = find
				.query()
				.where()
				.eq("id", id)
				.findOne();
		
		return Optional.ofNullable(tarefa);
	}

	public Optional<Tarefa> comToken(String codigo) {
		
		Tarefa tarefa = find
				.query()
				.where()
				.eq("token.codigo", codigo)
				.findOne();
		
		return Optional.ofNullable(tarefa);
	}

}
