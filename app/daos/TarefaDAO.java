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

}
