package controllers;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import autenticadores.UsuarioAutenticado;
import daos.EstudoDAO;
import daos.TokenCadastroDAO;
import daos.UsuarioDAO;
import models.EmailCadastro;
import models.Estudo;
import models.TokenCadastro;
import models.TokenSistema;
import models.Usuario;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validadores.Encriptador;
import validadores.Validador;
import views.html.login;
import views.html.painel;
import views.html.resultados;

public class ControladorUsuario extends Controller {

	@Inject
	private Validador validador;
	@Inject
	private MailerClient mailer;
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private EstudoDAO estudoDAO;
	
	private Form<Usuario> usuarioForm;
	private Form<Estudo> estudoForm;
	
	public static final String AUTH = "auth";

	@Inject
	public ControladorUsuario(FormFactory formFactory) {

		this.usuarioForm = formFactory.form(Usuario.class);
		this.estudoForm = formFactory.form(Estudo.class);
	}

	public Result entrar() {
		
		Form<Usuario> form = usuarioForm.bindFromRequest();
		
        if(validador.temErros(form)) {
        	
        	flash("danger", "Foram identificados problemas no cadastro!");
    		return badRequest(login.render(usuarioForm));        	
        }
 
        Usuario usuario = form.get();
        String email = usuario.getEmail();
        
        String criptoSenha = Encriptador.crypt("senhaAutomatica");
        
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
	        
	        confirmarCadastro(email, token.getCodigo());
	        
			return redirect(routes.ControladorUsuario.mostrarPainel());
        }
	}

	public Result entrarComSenha() {
		
		Form<Usuario> form = usuarioForm.bindFromRequest();
		
        if(validador.temErros(form)) {
        	
        	flash("danger", "Foram identificados problemas no cadastro!");
    		return badRequest(login.render(usuarioForm));        	
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

		return ok(painel.render(estudoForm, resultados.render(estudos)));
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

}
