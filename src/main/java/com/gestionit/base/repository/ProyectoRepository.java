package com.gestionit.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gestionit.base.domain.Proyecto;



public interface ProyectoRepository extends CrudRepository<Proyecto , Long>{


	@Query("select p from Proyecto p where ( p.id = ?1 or ?1 is null) "
            + "and upper(p.objetivo) like ?2% and upper(p.alcance) like ?3% ")
	public List<Proyecto> findByValoresLike(Long codigo,
            String objetivo,String alcance);
	

}
