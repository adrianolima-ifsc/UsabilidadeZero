package daos;

import java.util.Optional;

import io.ebean.Finder;
import models.TokenSistema;

public class TokenSistemaDAO {
	
	private static Finder<Long, TokenSistema> find = new Finder<>(TokenSistema.class);

	public Optional<TokenSistema> comCodigo(String codigo) {
		
		TokenSistema token = find
				.query()
				.where()
				.eq("codigo", codigo)
				.findOne();
		
		return Optional.ofNullable(token);
	}
	
	public Optional<TokenSistema> comId(Long id) {
		
		TokenSistema token = find.byId(id);
		
		return Optional.ofNullable(token);
				
	}

}
