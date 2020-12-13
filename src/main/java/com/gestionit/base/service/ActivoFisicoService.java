package com.gestionit.base.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestionit.base.domain.ActivoFisico;
import com.gestionit.base.domain.dto.ActivoFisicoSearchDTO;
import com.gestionit.base.repository.ActivoFisicoRepository;


/**
*
* @author Cbova
*/
@Service
public class ActivoFisicoService implements BasicService<ActivoFisico>{

	   private ActivoFisicoRepository activoFisicoRepo;
	   

	    @Autowired
	    public ActivoFisicoService(ActivoFisicoRepository activoFisicoRepo) {
	        this.activoFisicoRepo = activoFisicoRepo;
	       
	        
	    }


@Override
public void delete(ActivoFisico entity) {
	activoFisicoRepo.delete(entity);
	
}

@Override
public ActivoFisico getById(Long id) {
	
	return activoFisicoRepo.findOne(id);
}

@Override
public ActivoFisico saveOrUpdate(ActivoFisico entity) {
	
	return activoFisicoRepo.save(entity);
}

@Override
public List<ActivoFisico> findAll() {
	
	return (List<ActivoFisico>) activoFisicoRepo.findAll();
}


public List<ActivoFisico> findAllContaining(ActivoFisicoSearchDTO searchDTO) {

	return activoFisicoRepo.findAllContaining(searchDTO.getCodigo().toUpperCase(), searchDTO.getNombre().toUpperCase(), searchDTO.getPropietario().toUpperCase());
}

}
