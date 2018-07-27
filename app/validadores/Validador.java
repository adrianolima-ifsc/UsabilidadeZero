package validadores;

import models.*;

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
		return false;
	}

	private void validaSenha(Form<Usuario> form) {
		String senha = form.field("senha").valueOr("");
	}

}
