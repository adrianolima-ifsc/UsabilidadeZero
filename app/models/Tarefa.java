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
	public String codigo;
	
	@ManyToOne
	public Estudo estudo;

	public Date dataHoraInicio;
	public Date dataHoraFim;
	public int cliques;

	public static Finder<Long, Tarefa> find = new Finder<>(Tarefa.class);

}
