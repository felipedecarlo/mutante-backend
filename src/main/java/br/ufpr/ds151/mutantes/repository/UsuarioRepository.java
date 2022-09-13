package br.ufpr.ds151.mutantes.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import br.ufpr.ds151.mutantes.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("from Usuario where nome = :login and senha = :senha")
	public Usuario findByLoginAndSenha(
			@Param("login") String login,
			@Param("senha") String senha);
	
}