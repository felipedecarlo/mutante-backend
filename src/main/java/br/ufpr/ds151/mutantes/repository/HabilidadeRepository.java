package br.ufpr.ds151.mutantes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufpr.ds151.mutantes.model.Habilidade;

public interface HabilidadeRepository extends JpaRepository<Habilidade, Long> {
	
	public List<Habilidade> findAllByIdMutante(Long idMutante);

	public void deleteAllByIdMutante(Long idMutante);
	
	@Query("select descricao from Habilidade group by descricao order by count(*) desc")
	public List<String> findTopHabilities();

}