package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Estudo extends Model {
	
	private static final Long serialVersionUID = 1L;
	
	@Id
	public Long id;
	
	@Constraints.Required
	public boolean tipo;

	public Usuario usuario;
	
	@Constraints.Required
	@Formats.DateTime(pattern="dd/MM/yyyy")
	public Date data;

	public Tarefa tarefa1;

	public Tarefa tarefa2;

	public Tarefa tarefa3;
	
	public static Finder<Long, Estudo> find = new Finder<>(Estudo.class);

}
