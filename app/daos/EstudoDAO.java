package daos;

import models.*;

import java.util.*;
import io.ebean.Finder;

public class EstudoDAO {
	
	private static Finder<Long, Estudo> find = new Finder<>(Estudo.class);
	
	public List<Estudo> mostraTodos() {
		
		return find.all();
	}

	public Estudo comId(Long id) {
		
		return find.query().where().eq("id", id).findOne();
	}

}
