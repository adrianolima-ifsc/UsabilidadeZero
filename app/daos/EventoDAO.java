package daos;

import models.*;

import java.util.*;
import io.ebean.Finder;

public class EventoDAO {
	
	private static Finder<Long, Evento> find = new Finder<>(Evento.class);
	
	public List<Evento> mostraTodos() {
		
		return find.all();
	}

	public Evento comId(Long id) {
		
		return find.query().where().eq("id", id).findOne();
	}

}
