/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;








import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Proyecto;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.ProyectoDTO;
import com.gestionit.base.domain.dto.ProyectoSearchDTO;
import com.gestionit.base.domain.dto.RiesgoAuditDTO;
import com.gestionit.base.service.ProyectoService;
import com.gestionit.base.utils.FormatUtils;



/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/proyectos")
public class ProyectoController implements CrudControllerInterface<ProyectoSearchDTO,ProyectoDTO>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProyectoController.class);
    private ProyectoService proyectoService;
    private DataMaster dataMaster;


    @Autowired
    public ProyectoController(ProyectoService proyectoService, DataMaster dataMaster) {
        this.proyectoService = proyectoService;
        this.dataMaster = dataMaster;

    }

    @Override
    public ModelAndView getMainPage(ProyectoSearchDTO searchDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("proyectos");
        mav.addObject("proyectos", proyectoService.findAll());
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

    @Override
    public ModelAndView search(@ModelAttribute (value = "searchDTO") ProyectoSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        return new ModelAndView("proyectos", "proyectos", proyectoService.findAllContaining(searchDTO));
    }

	@Override
	public ModelAndView getCreatePage(ProyectoDTO objectDTO, BindingResult bindingResult) {
		 LOGGER.debug("-------Le peuge a main page------------");

	        //Genero el DTO
	        ProyectoDTO proyectoDTO = new ProyectoDTO(new Proyecto());
	        LOGGER.info("Cree el siguiente dto para operar : " + proyectoDTO);

	        //Preparo el moddel and view
	        ModelAndView mav = new ModelAndView("proyecto_create");
	        mav.addObject("proyectoDTO", proyectoDTO);
	        return mav;
	}

	@Override
	@RequestMapping(value = "/save", params = {"guardar"})
	public ModelAndView save(ProyectoDTO proyectoDTO, BindingResult bindingResult) {
		LOGGER.info("-----Entre al save de Proyectos------");
	   ModelAndView mav = new ModelAndView("proyecto_create");
	   proyectoDTO.getProyecto().setFechaFin(FormatUtils.getFormatedLocalDate(proyectoDTO.getFechaFin()));
	   proyectoDTO.getProyecto().setFechaInicio(FormatUtils.getFormatedLocalDate(proyectoDTO.getFechaInicio()));
	   List<Riesgo> riesgos = new ArrayList<Riesgo>();
	   proyectoDTO.getProyecto().setRiesgo(riesgos);
	   if(proyectoDTO.getRiesgo()!=null) {
		   //Asumo que el proyecto tiene asociado solo un riesgo
		   proyectoDTO.getProyecto().getRiesgos().add(proyectoDTO.getRiesgo());
	   }

	   proyectoDTO.setProyecto(proyectoService.saveOrUpdate(proyectoDTO.getProyecto()));
        mav.addObject("proyectoDTO", proyectoDTO);
        return mav;
	}
	
	
	@ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}


	@Override
	public ModelAndView delete(@PathVariable Long id) {
		Proyecto proyectoABorrar = proyectoService.getById(id);
		proyectoService.delete(proyectoABorrar);
        return getMainPage(new ProyectoSearchDTO(), null);
	}

	@Override
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        Proyecto proyectoAEditar = proyectoService.getById(id);
        ProyectoDTO dto = new ProyectoDTO(proyectoAEditar);
        dto.setFechaFin(proyectoAEditar.getFechaFin().format(DateTimeFormatter.ISO_LOCAL_DATE));
        dto.setFechaInicio(proyectoAEditar.getFechaInicio().format(DateTimeFormatter.ISO_LOCAL_DATE));
        //Asumo que los proyectos tienen asociado solo un riesgo
        if(!proyectoAEditar.getRiesgos().isEmpty()) {
        	dto.setRiesgo(proyectoAEditar.getRiesgos().get(0));
        }
        
        //habilito la aprobacion si hay un solo usuario o es distinto al que lo creo

        LOGGER.info("DTO a editar "+dto);
        return new ModelAndView("proyecto_create", "proyectoDTO", dto);
	}
  
	@RequestMapping(value = "/view/{id}")
	public ModelAndView view(@PathVariable Long id) {
        LOGGER.info("Estoy en view este es el id " + id);
        Proyecto proyectoAEditar = proyectoService.getById(id);
        ProyectoDTO dto = new ProyectoDTO(proyectoAEditar);
        LOGGER.info("DTO a ver "+dto);
        return new ModelAndView("proyecto_view", "proyectoDTO", dto);
	}
	 	

		

	
		
}
