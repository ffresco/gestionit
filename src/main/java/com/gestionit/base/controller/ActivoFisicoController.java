/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;







import java.time.LocalDate;
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
import com.gestionit.base.domain.ActivoFisico;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.dto.ActivoFisicoDTO;
import com.gestionit.base.domain.dto.ActivoFisicoSearchDTO;

import com.gestionit.base.service.ActivoFisicoService;
import com.gestionit.base.utils.FormatUtils;




/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/activos_fisicos")
public class ActivoFisicoController implements CrudControllerInterface<ActivoFisicoSearchDTO,ActivoFisicoDTO>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivoFisicoController.class);
    private ActivoFisicoService activoFisicoService;
    private DataMaster dataMaster;
    

    @Autowired
    public ActivoFisicoController(ActivoFisicoService activoFisicoService, DataMaster dataMaster) {
        this.dataMaster = dataMaster;
        this.activoFisicoService = activoFisicoService;


    }

    @Override
    public ModelAndView getMainPage(ActivoFisicoSearchDTO searchDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("activos_fisicos");
        mav.addObject("activosFisicos", activoFisicoService.findAll());
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

    @Override
    public ModelAndView search(@ModelAttribute (value = "searchDTO") ActivoFisicoSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        return new ModelAndView("activos_fisicos", "activosFisicos", activoFisicoService.findAllContaining(searchDTO));
    }


	@Override
	public ModelAndView getCreatePage(ActivoFisicoDTO activoFisicoDTO, BindingResult bindingResult) {
		 LOGGER.debug("-------Le peuge a main page------------");

	        ActivoFisico activoFisico = new ActivoFisico();
	        activoFisico.setFechaClasificacion(LocalDate.now());
	        activoFisicoDTO.setFechaClasificacion(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
	        List<Riesgo> riesgos = new ArrayList<Riesgo>();
	        activoFisico.setRiesgos(riesgos);
	        activoFisicoDTO.setActivoFisico(activoFisico);
	        LOGGER.info("Cree el siguiente dto para operar : " + activoFisicoDTO);
	        //Preparo el moddel and view
	        ModelAndView mav = new ModelAndView("activo_fisico_create");
	        mav.addObject("riesgos", riesgos);
	        mav.addObject("activoFisicoDTO", activoFisicoDTO);
	        return mav;
	}

	@Override
	@RequestMapping(value = "/save", params = {"guardar"})
	public ModelAndView save(ActivoFisicoDTO activoFisicoDTO, BindingResult bindingResult) {
		LOGGER.info("-----Entre al save de Activos------");
	    activoFisicoDTO.getActivoFisico().setFechaClasificacion(FormatUtils.getFormatedLocalDate(activoFisicoDTO.getFechaClasificacion()));
        activoFisicoDTO.setActivoFisico(activoFisicoService.saveOrUpdate(activoFisicoDTO.getActivoFisico()));
        //Completo el codigo del activo con el id que le asigno la base de datos
        String codigo = getCodigo(activoFisicoDTO.getActivoFisico());
	    activoFisicoDTO.getActivoFisico().setCodigo(codigo);
	    activoFisicoService.saveOrUpdate(activoFisicoDTO.getActivoFisico());
	    ModelAndView mav = new ModelAndView("activo_fisico_create");
	    mav.addObject("riesgos", activoFisicoDTO.getActivoFisico().getRiesgos());
        mav.addObject("activoDTO", activoFisicoDTO);
        return mav;
	}
	


	private String getCodigo(ActivoFisico activoFisico) {

		return activoFisico.getTipo().getCodigo()+"-"+activoFisico.getUbicacion().getCodigo()+"-"+String.format("%05d", activoFisico.getId());
	}

	@Override
	public ModelAndView delete(@PathVariable Long id) {
		ActivoFisico activoFisicoABorrar = activoFisicoService.getById(id);
		activoFisicoService.delete(activoFisicoABorrar);
        return getMainPage(new ActivoFisicoSearchDTO(), null);
	}

	@Override
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        ActivoFisico activoFisicoAEditar = activoFisicoService.getById(id);
        ActivoFisicoDTO activoFisicoDTO = new ActivoFisicoDTO();
        activoFisicoDTO.setActivoFisico(activoFisicoAEditar);
        activoFisicoDTO.setFechaClasificacion(activoFisicoAEditar.getFechaClasificacion().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ModelAndView mav = new ModelAndView("activo_fisico_create", "activoFisicoDTO", activoFisicoDTO);
        mav.addObject("riesgos", activoFisicoAEditar.getRiesgos());
        LOGGER.info("DTO a editar "+activoFisicoDTO);
        return mav;
	}
  

	@ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}
	

	
		
}
