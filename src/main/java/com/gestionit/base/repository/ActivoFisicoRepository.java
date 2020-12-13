package com.gestionit.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gestionit.base.domain.ActivoFisico;

/**
 * 
 * @author cbova
 *
 */

public interface ActivoFisicoRepository extends CrudRepository<ActivoFisico, Long> {

	@Query("select af from ActivoFisico af where  upper(af.codigo) like ?1% "
            + "and upper(af.nombre) like ?2%  " + "and upper(af.propietario) like ?3%  " )
	List<ActivoFisico> findAllContaining(String codigo, String nombre, String propietario); 

}
