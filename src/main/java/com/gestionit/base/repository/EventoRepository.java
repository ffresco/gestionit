package com.gestionit.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gestionit.base.domain.Evento;



public interface EventoRepository  extends CrudRepository<Evento, Long>{

	@Query("select e from Evento e where  e.id = ?1 "
            + "or upper(e.descripcion) like ?2%  ")
	
	List<Evento> findByValoresLike(Long id, String descripcion);

	List<Evento> findByRiesgoId(Long riesgoID);
    
}


