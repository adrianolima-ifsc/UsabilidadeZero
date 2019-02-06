package daos;

import models.*;

import java.util.*;
import io.ebean.Finder;

public class EstudoDAO {
	
	private static Finder<Long, Estudo> find = new Finder<>(Estudo.class);
	
	public List<Estudo> mostraTodos() {
		
		return find.all();
	}

	public Optional<Estudo> comId(Long id) {
		
		Estudo estudo = find
				.query()
				.where()
				.eq("id", id)
				.findOne();
		
		return Optional.ofNullable(estudo);
	}

}
