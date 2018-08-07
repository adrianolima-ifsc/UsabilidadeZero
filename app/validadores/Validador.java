package validadores;

import models.*;

import java.util.Optional;

import javax.inject.Inject;

import daos.UsuarioDAO;
import play.data.Form;

public class Validador {
	
	private UsuarioDAO usuarioDAO;
	
	@Inject
	public Validador(UsuarioDAO usuarioDAO) {
		
		this.usuarioDAO = usuarioDAO;
	}

	public boolean temErros(Form<Usuario> form) {

		validaSenha(form);
		return form.hasErrors();
	}

	private void validaSenha(Form<Usuario> form) {
		Optional<String> senha = form.field("senha").getValue();
	}

}
