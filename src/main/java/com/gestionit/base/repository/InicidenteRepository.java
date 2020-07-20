package com.gestionit.base.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gestionit.base.domain.Incidente;

public interface InicidenteRepository extends CrudRepository<Incidente,Long> {
	
	
	 @Query("select c from Incidente c where  c.responsableResolucion like ?1% "
	            + "and  c.solucion like ?2% ")

	List<Incidente> findByValoresLike(String responsable, String solucion, Sort sort);


}
