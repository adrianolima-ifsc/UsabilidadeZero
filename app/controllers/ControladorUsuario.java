package controllers;

import models.*;
import views.html.*;
import validadores.*;

import java.security.MessageDigest;
import java.util.*;
import javax.inject.Inject;
import play.api.data.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

public class ControladorUsuario extends Controller {

	private final FormFactory formFactory;
	private Form<Usuario> usuarioForm;
	private Validador validador;

	@Inject
	public ControladorUsuario(FormFactory formFactory, Validador validador) {

		this.formFactory = formFactory;
		this.usuarioForm = formFactory.form(Usuario.class);
		this.validador = validador;
	}

	public Result lista() {

//		List<Usuario> usuarios = Usuario.find.all();

//		return ok(telaPrincipal.render(usuarios));
		return ok();
	}

	public Result entrar() {
		
		Form<Usuario> form = usuarioForm.bindFromRequest();
		Usuario usuario = form.get();
		String criptoSenha = Encriptador.crypt(usuario.getSenha());
		usuario.setSenha(criptoSenha);
		
		usuario.save();
		flash("success", "Usuario cadastrado com sucesso!");
		
        if(form.hasErrors()) {
        	flash("danger", "Foram identificados problemas no cadastro!");
    		return badRequest(telaLogin.render(usuarioForm));        	
        }  
//        
//        Usuario usuario = form.get();
//        
//		try {
//			
//			Usuario cadastrado = Usuario.find.query().where().eq("email", usuario.email).findOne();
//			
//			if(cadastrado==null) {
//		        
//		        usuario.save();        
//		        flash("success", "Registro gravado com sucesso");
//		        		        
//			} else if(!usuario.senha.equals(cadastrado.senha)) {
//				
//		        flash("erro", "Senha incorreta!");
//	    		return ok(telaLogin.render(usuarioForm));
//				
//			}
//			
//		} catch (Exception e) {
//        	flash("erro", "Registro duplicado:" + usuario.email);
//    		return ok(telaLogin.render(usuarioForm));
//		}
//        
//        return redirect(routes.ControladorUsuario.lista());
        return redirect("/login"); // TODO rota
        
	}

	public Result detalhar(Long id) {

//		Form<Usuario> usuForm = formFactory.form(Usuario.class).fill(Usuario.find.byId(id));

		return ok();
	}

	public Result alterar(Long id) {

//		formFactory.form(Usuario.class).fill(Usuario.find.byId(id));
//		Form<Usuario> alterarForm = formFactory.form(Usuario.class).bindFromRequest();
//
//		if (alterarForm.hasErrors()) {
//			return badRequest();
//		}
//
//		alterarForm.get();
//
//		flash("sucesso", "Usuario " + alterarForm.get().getEmail() + " alterado com sucesso");

		return redirect(routes.ControladorUsuario.lista());

	}

	public Result remover(Long id) {

//		Usuario.find.ref(id).delete();

		flash("sucesso", "Usuario removido com sucesso");

		return lista();
	}
	
//	public class Encriptador {
//		
//		private static MessageDigest md;
//
//		public static String comMD5(String senha) {
//			try {
//				md = MessageDigest.getInstance("MD5");
//				byte[] passBytes = senha.getBytes();
//				md.reset();
//				byte[] digested = md.digest(passBytes);
//				StringBuffer sb = new StringBuffer();
//				for (int i = 0; i < digested.length; i++) {
//					sb.append(Integer.toHexString(0xff & digested[i]));
//				}
//				return sb.toString();
//			} catch (NoSuchAlgorithmException ex) {
//				Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			return null;
//
//		}
//	}

}
