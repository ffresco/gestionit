/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.repository;

import com.gestionit.base.domain.Riesgo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author cbova
 */
public interface RiesgoRepository extends PagingAndSortingRepository<Riesgo,Long>{
    
	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% ")
	    List<Riesgo> findByValoresLike(Long codigo,
	            String tipoAmenza,String responsable);
	   /**
	    * 
	    * @param codigo
	    * @param tipoAmenza
	    * @param responsable
	    * @param aprobado el riesgo esta aprobado si la fecha de aprobacion es distinta de null
	    * @return
	    */
	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% and ((r.fechaAprobacion is null and ?4=false ) or (r.fechaAprobacion is not null and ?4=true  )) ")
	    List<Riesgo> findByValoresLike(Long codigo,
	            String tipoAmenza,String responsable, Boolean aprobado);
	   
	   

	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% and r.riesgoResidualValor.riesgoResidual.valor > 3 and r.proyectos is empty")
	    List<Riesgo> findByValoresLikeSinProyecto(Long codigo,
	            String tipoAmenza,String responsable);
	   
	   /**
	    * 
	    * @param codigo
	    * @param tipoAmenza
	    * @param responsable
	    * @param aprobado el riesgo esta aprobado si la fecha de aprobacion es distinta de null
	    * @return
	    */
	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% and ((r.fechaAprobacion is null and ?4=false ) or (r.fechaAprobacion is not null and ?4=true  )) ")
	   Page<Riesgo> findByValoresLike(Long codigo,
	            String tipoAmenza,String responsable, Boolean aprobado, Pageable pageable);
	   
	   

	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% and r.riesgoResidualValor.riesgoResidual.valor > 3 and r.proyectos is empty")
	   Page<Riesgo> findByValoresLikeSinProyecto(Long codigo,
	            String tipoAmenza,String responsable, Pageable pageable);
	   
	   
	   @Query("select r from Riesgo r where ( r.id = ?1 or ?1 is null) "
	            + "and upper(r.amenaza.origen.origen) like ?2% and upper(r.responsable) like ?3% ")
	    Page<Riesgo> findByValoresLike(Long codigo,
	            String tipoAmenza,String responsable, Pageable pageable);
}
