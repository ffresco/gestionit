package com.gestionit.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestionit.base.domain.Proyecto;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.ProyectoSearchDTO;
import com.gestionit.base.repository.ProyectoRepository;

/**
*
* @author cbova
*/
@Service
public class ProyectoService implements BasicService<Proyecto>{
	
	private ProyectoRepository proyectoRepo;
	
	@Autowired
	public ProyectoService(ProyectoRepository proyectoRepo) {
		super();
		this.proyectoRepo = proyectoRepo;
	}


	public Object findAllContaining(ProyectoSearchDTO searchDTO) {
		return proyectoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getAlcance(), searchDTO.getObjetivo());
	}

	@Override
	@Transactional
	public Proyecto saveOrUpdate(Proyecto entity) {
		return proyectoRepo.save(entity);
	}

	@Override
	public void delete(Proyecto entity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Proyecto getById(Long id) {
		// TODO Auto-generated method stub
		return proyectoRepo.findOne(id);
	}

	@Override
	public List<Proyecto> findAll() {
		return (List<Proyecto>) proyectoRepo.findAll();
	}

	@Transactional
	public Proyecto addRiesgo(Proyecto proyecto, Riesgo riesgo) {
		proyecto = this.getById(proyecto.getId());
		proyecto.getRiesgos().clear();
		proyecto.getRiesgos().add(riesgo);
		
		return this.saveOrUpdate(proyecto);
		
	}


}
