package br.ufpr.ds151.mutantes.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.ds151.mutantes.dto.LoginDTO;
import br.ufpr.ds151.mutantes.dto.UsuarioDTO;
import br.ufpr.ds151.mutantes.model.Usuario;
import br.ufpr.ds151.mutantes.repository.UsuarioRepository;

@CrossOrigin
@RestController
public class AuthREST {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO login) {
		
		Usuario usuario = repo.findByLoginAndSenha(login.getLogin(), login.getSenha());
		
		if (usuario != null) {
			return ResponseEntity.ok().body(mapper.map(usuario, UsuarioDTO.class));
		} else {
			return ResponseEntity.status(401).build();
		}
	}
}