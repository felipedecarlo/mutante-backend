package br.ufpr.ds151.mutantes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import br.ufpr.ds151.mutantes.model.Mutante;

public interface MutanteRepository extends JpaRepository<Mutante, Long> {
	
	@Query("SELECT DISTINCT m FROM Mutante m INNER JOIN Habilidade h ON m.id = h.idMutante WHERE UPPER(h.descricao) LIKE UPPER(CONCAT('%',:hab,'%'))")
	public List<Mutante> findMutanteByHability(@Param("hab") String hab);

	public List<Mutante> findByOrderByNomeAsc();

}