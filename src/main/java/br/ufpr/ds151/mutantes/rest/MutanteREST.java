package br.ufpr.ds151.mutantes.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.ds151.mutantes.dto.HabilidadeDTO;
import br.ufpr.ds151.mutantes.dto.MutanteDTO;
import br.ufpr.ds151.mutantes.dto.UsuarioDTO;
import br.ufpr.ds151.mutantes.model.Habilidade;
import br.ufpr.ds151.mutantes.model.Mutante;
import br.ufpr.ds151.mutantes.repository.MutanteRepository;
import br.ufpr.ds151.mutantes.repository.UsuarioRepository;
import br.ufpr.ds151.mutantes.repository.HabilidadeRepository;
import br.ufpr.ds151.mutantes.model.Mutante;

@CrossOrigin
@RestController
public class MutanteREST {

	public static List<Mutante> lista = new ArrayList<>();

	@Autowired
	private MutanteRepository mutanteRepo;

	@Autowired
	private HabilidadeRepository habilidadeRepo;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/mutantes/count")
	public long contaMutantes() {

		return mutanteRepo.count();
	
	}

	@GetMapping("/habilidades/top")
	public List<String> topHabilidades() {

		return habilidadeRepo.findTopHabilities();
	
	}

	
	@GetMapping("/mutantes")
	public List<MutanteDTO> obterTodosMutantes() {
		List<Mutante> lista = mutanteRepo.findByOrderByNomeAsc();
		// Converte lista de Entity para lista DTO
		return lista.stream()
				.map(e -> mapper.map(e, MutanteDTO.class))
				.collect(Collectors.toList());
	}

	@GetMapping("/mutantes/{hab}/habilidades")
	public List<MutanteDTO> obterMutantesPorHabilidade(@PathVariable("hab") String hab) {
		List<Mutante> lista = mutanteRepo.findMutanteByHability(hab);
		System.out.println("Size:" + lista.size());
		// Converte lista de Entity para lista DTO
		return lista.stream()
				.map(e -> mapper.map(e, MutanteDTO.class))
				.collect(Collectors.toList());
	}

	@GetMapping("/mutantes/{id}")
	public ResponseEntity<MutanteDTO> obterMutantePorId(@PathVariable("id") Long id) {
	
		MutanteDTO mutanteDTO = mapper.map(mutanteRepo.findById(id), MutanteDTO.class);
		
		if (mutanteDTO != null) {
			
			List<Habilidade> listaHabilidade = habilidadeRepo.findAllByIdMutante(id);
			
			List<HabilidadeDTO> listaHabilidadeDTO = listaHabilidade.stream()
					.map(e -> mapper.map(e, HabilidadeDTO.class))
					.collect(Collectors.toList());	
			
			mutanteDTO.setHabilidades(listaHabilidadeDTO);
			
			return ResponseEntity.ok().body(mutanteDTO);

		} else 
			return ResponseEntity.status(404).build();
			
	}

	@PostMapping("/mutantes")
	public ResponseEntity<MutanteDTO> inserir(@RequestBody MutanteDTO mutanteDTO) {

		try {
			Mutante mutante = mutanteRepo.save(mapper.map(mutanteDTO, Mutante.class));
	
			for (HabilidadeDTO habilidadeDTO : mutanteDTO.getHabilidades() ) {
				habilidadeDTO.setIdMutante(mutante.getId());
				habilidadeRepo.save(mapper.map(habilidadeDTO, Habilidade.class));
			}
			
			return ResponseEntity.ok().body(mapper.map(mutante, MutanteDTO.class));

		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(409).build();
		}
	}
	

	@PutMapping("/mutantes/{id}")
	@Transactional 
	public ResponseEntity<MutanteDTO> alterar(@PathVariable("id") Long id, @RequestBody MutanteDTO mutanteDTO) {

		mutanteDTO.setId(id);
		
		try {
			Mutante mutante = mutanteRepo.save(mapper.map(mutanteDTO, Mutante.class));
	
			habilidadeRepo.deleteAllByIdMutante(id);
			
			for (HabilidadeDTO habilidadeDTO : mutanteDTO.getHabilidades() ) {
				habilidadeDTO.setIdMutante(mutante.getId());
				habilidadeRepo.save(mapper.map(habilidadeDTO, Habilidade.class));
			}
			
			return ResponseEntity.ok().body(mapper.map(mutante, MutanteDTO.class));

		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(409).build();
		}
	}

	@DeleteMapping("/mutantes/{id}")
	@Transactional 
	public ResponseEntity<MutanteDTO> remover(@PathVariable("id") Long id) {

		habilidadeRepo.deleteAllByIdMutante(id);

		mutanteRepo.deleteById(id);

		return ResponseEntity.ok().body(new MutanteDTO(id, null, null, null, null));

	}

}