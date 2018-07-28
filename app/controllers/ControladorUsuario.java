package controllers;

import models.*;
import views.html.*;
import validadores.*;
import daos.*;

import java.util.*;
import javax.inject.Inject;

import autenticadores.UsuarioAutenticado;
import play.api.libs.mailer.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.mvc.Security.Authenticated;

public class ControladorUsuario extends Controller {

	private final FormFactory formFactory;
	private Form<Usuario> usuarioForm;
	private Validador validador;
	private MailerClient mailer;
	@Inject
	private TokenCadastroDAO tokenCadastroDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	
	public static final String AUTH = "auth";

	@Inject
	public ControladorUsuario(FormFactory formFactory, Validador validador, MailerClient mailer) {

		this.formFactory = formFactory;
		this.usuarioForm = formFactory.form(Usuario.class);
		this.validador = validador;
		this.mailer = mailer;
	}

	public Result lista() {

//		List<Usuario> usuarios = Usuario.find.all();

//		return ok(telaPrincipal.render(usuarios));
		return ok();
	}

	public Result entrar() {
		
		Form<Usuario> form = usuarioForm.bindFromRequest();
		
        if(form.hasErrors()) {
        	flash("danger", "Foram identificados problemas no cadastro!");
    		return badRequest(telaLogin.render(usuarioForm));        	
        }
 
        Usuario usuario = form.get();
        String email = usuario.getEmail();
        String criptoSenha = Encriptador.crypt(usuario.getSenha());
        
        Optional<Usuario> possivelUsuario = usuarioDAO.comEmail(email);
        
        if(possivelUsuario.isPresent()) {
        	
        	Usuario usuarioCadastrado = possivelUsuario.get();
        	String senhaCadastrada = usuarioCadastrado.getSenha();
        	
        	if(senhaCadastrada.equals(criptoSenha)) {
        	
	        	if(usuarioCadastrado.isVerificado()) {
	        		
	        		session(AUTH, usuario.getEmail());
	        		flash("success", "Login realizado com sucesso!");
	        		
	        		return redirect(routes.ControladorUsuario.mostraPainel());
	        		
	        	} else {
	        		
	        		flash("danger", "Usuario não confirmado!");     
	        		return redirect(routes.HomeController.index());        	   	
	        	}
        	
        	} else {
        		
        		flash("danger", "Senha não confere!");
        		return redirect(routes.HomeController.index());
        	}
        	
        } else {
        
	        usuario.setSenha(criptoSenha);        
	        usuario.save();
	        
	        TokenCadastro token = new TokenCadastro(usuario);
	        token.save();
	        
	        mailer.send(new EmailCadastro(token));
	        
	        flash("success", "Usuario cadastrado com sucesso!");
        
        }
//        
        return redirect(routes.HomeController.index());
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
        
	}
	
	public Result confirmaCadastro(String email, String codigo) {
		
		Optional<TokenCadastro> possivelToken = TokenCadastroDAO.comCodigo(codigo);
		Optional<Usuario> possivelUsuario = usuarioDAO.comEmail(email);
		
		if(possivelToken.isPresent() && possivelUsuario.isPresent()) {
			
			TokenCadastro token = possivelToken.get();
			Usuario usuario = possivelUsuario.get();
			
			if(token.getUsuario().equals(usuario)) {
				
				token.delete();
				usuario.setVerificado(true);
				usuario.update();
				
				flash("success", "Seu usuário foi confirmado com sucesso!");
				
				// TODO logar usuario;
				
				return redirect("/usuario/painel"); // TODO rota
			}
		}
		
		flash("danger", "Algo deu errado ao tentar confirmar o seu cadastro!");
		
		return redirect("/login"); // TODO rota
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostraPainel() {

		List<Usuario> usuarios = usuarioDAO.mostraTodos();

		return ok(painel.render(usuarios));
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result sair() {
		
		session().clear();
		flash("success", "Você saiu do sistema!");
		
		return redirect(routes.HomeController.index());
	}

	public Result detalhar(Long id) {

//		Form<Usuario> usuForm = formFactory.form(Usuario.class).fill(Usuario.find.byId(id));

		return ok();
	}

//	public Result alterar(Long id) {
//
////		formFactory.form(Usuario.class).fill(Usuario.find.byId(id));
////		Form<Usuario> alterarForm = formFactory.form(Usuario.class).bindFromRequest();
////
////		if (alterarForm.hasErrors()) {
////			return badRequest();
////		}
////
////		alterarForm.get();
////
////		flash("sucesso", "Usuario " + alterarForm.get().getEmail() + " alterado com sucesso");
//
//		return redirect(routes.ControladorUsuario.lista());
//
//	}

	public Result remover(Long id) {

//		Usuario.find.ref(id).delete();

		flash("sucesso", "Usuario removido com sucesso");

		return lista();
	}

}
