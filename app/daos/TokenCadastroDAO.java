package daos;

import models.*;

import java.util.Optional;
import io.ebean.Finder;

public class TokenCadastroDAO {
	
	private static Finder<Long, TokenCadastro> tokens = new Finder<>(TokenCadastro.class);

	public static Optional<TokenCadastro> comCodigo(String codigo) {
		
		TokenCadastro token = tokens.query().where().eq("codigo", codigo).findOne();
		return Optional.ofNullable(token);
	}

}
