package com.gestionit.base.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.gestionit.base.domain.Requerimiento;




/**
 * 
 * @author cbova
 *
 */

public interface RequerimientoRepository extends PagingAndSortingRepository<Requerimiento, Long> {

	@Query("select re from Requerimiento re where  upper(re.solicitante) like ?1% "
            + "and upper(re.categoria) like ?2%  " + "and upper(re.area) like ?3%  " )
	List<Requerimiento> findAllContaining(String solicitante, String categoria, String area);

	@Query("select re from Requerimiento re where  upper(re.solicitante) like ?1% "
            + "and upper(re.categoria) like ?2%  " + "and upper(re.area) like ?3%  " )
	Page<Requerimiento> findAllContaining(String solicitante, String categoria, String area, Pageable pageable);

}
