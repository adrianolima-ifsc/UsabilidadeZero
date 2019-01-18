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

	private Form<Usuario> usuarioForm;
	@Inject
	private Validador validador;
	@Inject
	private MailerClient mailer;
	@Inject
	private TokenCadastroDAO tokenCadastroDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private EstudoDAO estudoDAO;
	
	public static final String AUTH = "auth";

	@Inject
	public ControladorUsuario(FormFactory formFactory) {

		this.usuarioForm = formFactory.form(Usuario.class);
	}

	public Result lista() {

//		List<Usuario> usuarios = Usuario.find.all();

//		return ok(telaPrincipal.render(usuarios));
		return ok();
	}

	public Result entrar() {
		
		Form<Usuario> form = usuarioForm.bindFromRequest();
		
        if(validador.temErros(form)) {
        	
        	flash("danger", "Foram identificados problemas no cadastro!");
    		return badRequest(login.render(usuarioForm));        	
        }
 
        Usuario usuario = form.get();
        String email = usuario.getEmail();
        
        String criptoUsuario = Encriptador.crypt(usuario.getEmail());
        String criptoSenha = Encriptador.crypt(usuario.getSenha());
        
        Optional<Usuario> possivelUsuario = usuarioDAO.comEmail(email);
        
        if(possivelUsuario.isPresent()) {
        	
        	Usuario usuarioCadastrado = possivelUsuario.get();
        	String senhaCadastrada = usuarioCadastrado.getSenha();
        	
        	if(senhaCadastrada.equals(criptoSenha)) {
        	
	        	if(usuarioCadastrado.isVerificado()) {
	        		
	        		session(AUTH, usuarioCadastrado.getToken().getCodigo());
	        		flash("success", "Login realizado com sucesso!");
	        		
	        		return redirect(routes.ControladorUsuario.mostrarPainel());
	        		
	        	} else {
	        		
	        		flash("danger", "Usuário não confirmado!");     
	        		return redirect(routes.HomeController.index());        	   	
	        	}
        	
        	} else {
        		
        		String msg = String.format("A senha digitada não partence ao usuário %s. Tente novamente.", email);
        		
        		flash("danger", msg);
        		return redirect(routes.HomeController.index());
        	}
        	
        } else {
        
	        usuario.setSenha(criptoSenha);        
	        usuario.save();
	        
	        TokenCadastro token = new TokenCadastro(usuario);
	        token.save();
	        
	        mailer.send(new EmailCadastro(token));
	        
	        String msg = String.format("Usuário cadastrado com sucesso! Verifique o email enviado para o endereço %s "
	        		+ "para confirmação do seu cadastro.", email);
	        
	        flash("success", msg);
        
        }
        
        return redirect(routes.HomeController.index());        
	}
	
	public Result confirmarCadastro(String email, String codigo) {
		
		Optional<TokenCadastro> possivelToken = TokenCadastroDAO.comCodigo(codigo);
		Optional<Usuario> possivelUsuario = usuarioDAO.comEmail(email);
		
		if(possivelToken.isPresent() && possivelUsuario.isPresent()) {
			
			TokenCadastro tokenCadastro = possivelToken.get();
			Usuario usuario = possivelUsuario.get();
			
			if(tokenCadastro.getUsuario().equals(usuario)) {
				
				tokenCadastro.delete();
				usuario.setVerificado(true);
				
				TokenSistema tokenSistema = new TokenSistema(usuario);
				tokenSistema.save();
				usuario.setToken(tokenSistema);
				
				usuario.update();
				
				flash("success", "Seu usuário foi confirmado com sucesso!");
				
				session(AUTH, usuario.getToken().getCodigo());
				
				return redirect(routes.ControladorUsuario.mostrarPainel());
			}
		}
		
		flash("danger", "Algo deu errado ao tentar confirmar o seu cadastro!");
		
		return redirect(routes.HomeController.index());
	}
	
	@Authenticated(UsuarioAutenticado.class)
	public Result mostrarPainel() {
		
		List<Estudo> estudos = estudoDAO.mostraTodos();
		
		String codigo = session(AUTH);
        Usuario usuario = usuarioDAO.comToken(codigo).get();

		return ok(painel.render(resultados.render(estudos)));
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

	public Result remover(Long id) {

//		Usuario.find.ref(id).delete();

		flash("sucesso", "Usuario removido com sucesso");

		return lista();
	}

}
