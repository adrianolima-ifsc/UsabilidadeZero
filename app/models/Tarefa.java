package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Tarefa extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;
	
	@Constraints.Required
	public boolean tipo;

	public boolean eficaciaPercebida;

	public boolean eficaciaMedida;

	public int cliques;

	public int tempo;
	
	public static Finder<Long, Tarefa> find = new Finder<>(Tarefa.class);

}
