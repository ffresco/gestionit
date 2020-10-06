package com.gestionit.base.repository;


import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.gestionit.base.domain.Amenaza;




public interface AmenzaRepository extends CrudRepository<Amenaza, Long> {


              
    List<Amenaza> findByOrigenId(Long id);

}
