/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.ActivoFisico;
import com.gestionit.base.domain.MyRevision;
import com.gestionit.base.domain.Proyecto;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.RiesgoAuditDTO;
import com.gestionit.base.domain.dto.RiesgoSearchDTO;
import com.gestionit.base.repository.ActivoFisicoRepository;
import com.gestionit.base.repository.ProyectoRepository;
import com.gestionit.base.repository.RiesgoRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cbova
 */
@Service
public class RiesgoService implements BasicService<Riesgo>{
    
    private RiesgoRepository riesgoRepo;
    private ProyectoRepository proyectoRepo;
    private EventoService eventoService;
    private ActivoFisicoRepository activoFisicoRepo;
    
    
    private AuditReader reader;


    @Autowired
    public RiesgoService(RiesgoRepository riesgoRepo, AuditReader reader, ProyectoRepository proyectoRepo, EventoService eventoService, ActivoFisicoRepository activoFisicoRepo) {
        this.riesgoRepo = riesgoRepo;
        this.reader = reader;
        this.proyectoRepo = proyectoRepo;
        this.eventoService = eventoService;
        this.activoFisicoRepo = activoFisicoRepo;
        
    }

    @Override
    @Transactional
    public void delete(Riesgo riesgo) {
    	for (Iterator<Proyecto> iterator = riesgo.getProyectos().iterator(); iterator.hasNext();) {
			Proyecto proyecto = (Proyecto) iterator.next();
			proyecto.getRiesgos().remove(riesgo);
			proyectoRepo.save(proyecto);
		}
    	
    	for (Iterator<ActivoFisico> iterator = riesgo.getActivosFisicos().iterator(); iterator.hasNext();) {
    		ActivoFisico activoFisico = (ActivoFisico) iterator.next();
    		activoFisico.getRiesgos().remove(riesgo);
    		activoFisicoRepo.save(activoFisico);
		}
    	//elimino los eventos asociados al riesgo
    	eventoService.delete(riesgo);
    	riesgoRepo.delete(riesgo);
    }

    @Override
    public Riesgo getById(Long id) {
        return riesgoRepo.findOne(id);
    }

    @Transactional
    @Override
    public Riesgo saveOrUpdate(Riesgo entity) {
    	
        Riesgo riesgo = riesgoRepo.save(entity);
        System.out.println("Riesgo Gravado" + riesgo);
        return riesgo;
    }

    @Override
    public List<Riesgo> findAll() {
        return (List<Riesgo>) riesgoRepo.findAll();
    }   
 
    
    public long getRiesgosCount() {

        return riesgoRepo.count();
    }
 
    public Page<Riesgo> getPaginatedRiesgos(final int pageNumber, final int pageSize) {
        final Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
        return riesgoRepo.findAll(pageable);
    }
    
    public Page<Riesgo> getPaginatedRiesgos(final int pageNumber, final int pageSize, RiesgoSearchDTO searchDTO ) {
        final Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
        return this.findAllContaining(searchDTO, pageable);
    }

    public List<Riesgo> findAllContaining(RiesgoSearchDTO searchDTO) {
    	String filtro = searchDTO.getFiltro();
    	if(filtro==null || filtro.equals("Todos")) {
    		return (List<Riesgo>) riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable());
    	}else if (filtro.equals("FaltaProyecto")) {
    		return (List<Riesgo>) riesgoRepo.findByValoresLikeSinProyecto(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable());
		} else {
			return (List<Riesgo>) riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable(),filtro.equals("Aprobados"));
    		
    	}
        
    }   
    
    public Page<Riesgo> findAllContaining(RiesgoSearchDTO searchDTO, Pageable pageable) {
    	String filtro = searchDTO.getFiltro();
    	if(filtro==null || filtro.equals("Todos")) {
    		return  riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable(), pageable);
    	}else if (filtro.equals("FaltaProyecto")) {
    		return riesgoRepo.findByValoresLikeSinProyecto(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable(), pageable);
		} else {
			return riesgoRepo.findByValoresLike(searchDTO.getId(), searchDTO.getOrigenAmenaza(), searchDTO.getResponsable(),filtro.equals("Aprobados"), pageable);
    		
    	}
        
    }   
    
    
    public List<RiesgoAuditDTO> findAllDeleted() throws IllegalAccessException, InvocationTargetException {
    	List<RiesgoAuditDTO> result = new ArrayList<RiesgoAuditDTO>(); 
    	 AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    	            Riesgo.class, false, true);
    	    query.add(AuditEntity.revisionType().eq(RevisionType.DEL));

    	    List<Object[]> results = query.getResultList();
    	    for (Object[] obj : results) {
    	        Riesgo riesgo = (Riesgo) obj[0];
    	        MyRevision dre = (MyRevision) obj[1];
    	        RevisionType revType = (RevisionType) obj[2];
    	        
    	        
    	        AuditQuery queryAux = reader.createQuery().forRevisionsOfEntity(
                        Riesgo.class, false, true);
                queryAux.add(AuditEntity.revisionType().ne(RevisionType.DEL));
                queryAux.addProjection(AuditEntity.revisionNumber().max());
                queryAux.add(AuditEntity.id().eq(riesgo.getId()));
                     
                Number idUltimaRevision = (Number) queryAux.getSingleResult();
                
                queryAux = reader.createQuery().forRevisionsOfEntity(
            			Riesgo.class, false, false);
                queryAux.add(AuditEntity.id().eq(riesgo.getId()));
                queryAux.add(AuditEntity.revisionNumber().eq(idUltimaRevision));
            	results = queryAux.getResultList();
           	    Riesgo riesgo_ = (Riesgo) results.get(0)[0];
                RiesgoAuditDTO auditDTO = new RiesgoAuditDTO(riesgo_, dre, revType);
        	    result.add(auditDTO);
        	   System.out.println("Riesgo borrado " + riesgo.getId() + " en fecha "
        	                + dre.getRevisionDate());
           	    }
                
       	    return result;  
              
    	    }
    	   
        
    	    
 
    
    public RiesgoAuditDTO getLastAuditedRecord(Long id) {
    	AuditQuery queryAux = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, true);
    	queryAux.add(AuditEntity.revisionType().ne(RevisionType.DEL));
    	queryAux.addProjection(AuditEntity.revisionNumber().max());
    	queryAux.add(AuditEntity.id().eq(id));

    	Number idLastRevision = (Number) queryAux.getSingleResult();
    	Riesgo riesgo = reader.find(Riesgo.class,
    			id, idLastRevision);

    	return new RiesgoAuditDTO(riesgo);
    }


    public Date getCreateDate(Long id) {
    	AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, true);
    	query.add(AuditEntity.revisionType().eq(RevisionType.ADD));
    	query.addProjection(AuditEntity.revisionNumber().min());
    	query.add(AuditEntity.id().eq(id));

    	Number revision = (Number) query.getSingleResult();

    	return reader.getRevisionDate(revision);
    }
    
    public List<RiesgoAuditDTO> getAllAuditForId(Long id) {
    	List<RiesgoAuditDTO> audits = new ArrayList<RiesgoAuditDTO>(); 
    	AuditQuery query = reader.createQuery().forRevisionsOfEntity(
    			Riesgo.class, false, false);
    	query.add(AuditEntity.id().eq(id));
    	
    	   List<Object[]> results = query.getResultList();
    	   Riesgo previousVersion = null;
   	    for (Object[] obj : results) {
   	        Riesgo riesgo = (Riesgo) obj[0];
   	        MyRevision dre = (MyRevision) obj[1];
   	        RevisionType revType = (RevisionType) obj[2];
   	        RiesgoAuditDTO auditDTO = new RiesgoAuditDTO(riesgo, dre, revType);
   	       	auditDTO.setPreviousVersion(previousVersion==null?riesgo:previousVersion); //si no tiene annterior seteo el mismo objeto
   	        audits.add(auditDTO);
   	        previousVersion = riesgo;
   	    }
   	    //Recorro nuevamente la lista de auditoria para poder agregarle al resultado la auditoria siguiente
   	  for (int i = 1; i < results.size(); i++) {
		audits.get(i-1).setNextVersion((Riesgo)results.get(i)[0]);
    	}
   	    return audits;
    }
    
   public Map<Integer, Map<Integer, List<Riesgo>>> getMatrizDeRiesgo() {
	   Map<Integer, Map<Integer, List<Riesgo>>> result = initMap();
	  for (Iterator<Riesgo> iterator = this.findAll().iterator(); iterator.hasNext();) {
		Riesgo riesgo = (Riesgo) iterator.next();
		Map<Integer, List<Riesgo>> probabilidades = result.get(riesgo.getSalvaguarda().getProbabilidadFinal().getValor());
		List<Riesgo> riesgos = probabilidades.get(riesgo.getSalvaguarda().getImpactoFinal().getValor());
		riesgos.add(riesgo);
		probabilidades.put(riesgo.getSalvaguarda().getImpactoFinal().getValor(), riesgos);
		result.put(riesgo.getSalvaguarda().getProbabilidadFinal().getValor(), probabilidades);
	}
	   return result;
	   
   }
   
   @Transactional
   public Riesgo removeProyectos(Long id) {
	   Riesgo riesgo = this.getById(id);
	   for (Proyecto proyecto : riesgo.getProyectos()) {
		proyecto.getRiesgos().clear();
		proyectoRepo.save(proyecto);
	}
	   return this.saveOrUpdate(riesgo);
   }
   
   private Map<Integer, Map<Integer, List<Riesgo>>> initMap(){
	   Map<Integer, Map<Integer, List<Riesgo>>> result = new LinkedHashMap<Integer, Map<Integer, List<Riesgo>>>();
	   
	   for (int i = 5; i > 0; i--) {
		Map<Integer, List<Riesgo>> probabilidades = new HashMap<Integer, List<Riesgo>>();   
		 for (int j = 0; j < 5; j++) {
			 List<Riesgo> riesgos = new ArrayList<Riesgo>();
			 probabilidades.put(j+1, riesgos);
		 }
		result.put(i, probabilidades);
	}
	   
	   return result;
   }


    
}
