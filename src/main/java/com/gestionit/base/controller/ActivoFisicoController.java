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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class ActivoFisicoController extends CrudControllerPaginationInterface<ActivoFisicoSearchDTO,ActivoFisicoDTO,ActivoFisico>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivoFisicoController.class);
    private ActivoFisicoService activoFisicoService;
    private DataMaster dataMaster;
    

    @Autowired
    public ActivoFisicoController(ActivoFisicoService activoFisicoService, DataMaster dataMaster) {
        this.dataMaster = dataMaster;
        this.activoFisicoService = activoFisicoService;


    }
    
    @Override
    public String getMainPage(ActivoFisicoSearchDTO searchDTO, BindingResult bindingResult) {
        return "redirect:/activos_fisicos/paginated/" + getPageNumber() + "/" + getPageSize();
    }
    
    @Override
    @GetMapping(value = "/paginated/{page}/{page-size}")
	ModelAndView getMainPagePaginated( @PathVariable(name = "page") final Integer pageNumber,
		    @PathVariable(name = "page-size") final Integer pageSize,@ModelAttribute ActivoFisicoSearchDTO searchDTO, BindingResult bindingResult) {
    	ModelAndView mav = new ModelAndView("activos_fisicos");
        mav.addObject("activosFisicos", activoFisicoService.findAll());
        final Page<ActivoFisico> paginatedActivos = activoFisicoService.getPaginatedActivos(pageNumber, pageSize);
        mav.addObject("searchDTO", getResponseDto(paginatedActivos, pageNumber, searchDTO)); 
        mav.addObject("searchDTO", searchDTO);
		return mav;
	}

    @Override
    @RequestMapping(value = "/search/{page}/{page-size}", method = RequestMethod.POST)
    public  ModelAndView search(@PathVariable(name = "page") final int pageNumber,
            @PathVariable(name = "page-size") final int pageSize,@ModelAttribute (value = "searchDTO") ActivoFisicoSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        final Page<ActivoFisico> paginatedActivos = activoFisicoService.getPaginatedActivos(pageNumber, pageSize, searchDTO);
        return new ModelAndView("activos_fisicos", "searchDTO", getResponseDto(paginatedActivos, pageNumber, searchDTO));
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
	    activoFisicoDTO.getActivoFisico().setRiesgoResidual(getRiesgoResidual(activoFisicoDTO.getActivoFisico()));
	    activoFisicoService.saveOrUpdate(activoFisicoDTO.getActivoFisico());
	    ModelAndView mav = new ModelAndView("activo_fisico_create");
	    mav.addObject("riesgos", activoFisicoDTO.getActivoFisico().getRiesgos());
        mav.addObject("activoDTO", activoFisicoDTO);
        return mav;
	}
	


	private Integer getRiesgoResidual(ActivoFisico activoFisico) {
		Integer riesgoResidual = 0;
		for (Iterator<Riesgo> iterator = activoFisico.getRiesgos().iterator(); iterator.hasNext();) {
			Riesgo riesgo = (Riesgo) iterator.next();
			if(riesgoResidual < riesgo.getRiesgoResidualValor().getValor() ) {
				riesgoResidual = riesgo.getRiesgoResidualValor().getValor();
			}

		}
		return riesgoResidual;
	}


	private String getCodigo(ActivoFisico activoFisico) {

		return activoFisico.getTipo().getCodigo()+"-"+activoFisico.getUbicacion().getCodigo()+"-"+String.format("%05d", activoFisico.getId());
	}

	@Override
	public String delete(@PathVariable Long id) {
		ActivoFisico activoFisicoABorrar = activoFisicoService.getById(id);
		activoFisicoService.delete(activoFisicoABorrar);
		return "redirect:/activos_fisicos/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
	}

	@Override
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        ActivoFisico activoFisicoAEditar = activoFisicoService.getById(id);
        ActivoFisicoDTO activoFisicoDTO = new ActivoFisicoDTO();
        activoFisicoDTO.setActivoFisico(activoFisicoAEditar);
        activoFisicoDTO.setFechaClasificacion(activoFisicoAEditar.getFechaClasificacion().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ModelAndView mav = new ModelAndView("activo_fisico_create");
        mav.addObject("activoFisicoDTO", activoFisicoDTO);
        mav.addObject("riesgos", activoFisicoAEditar.getRiesgos());
        LOGGER.info("DTO a editar "+activoFisicoDTO);
        return mav;
	}
  

	@ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}

	//https://stackoverflow.com/questions/48891551/thymeleaf-refresh-value-with-ajax
	@RequestMapping(value="/riesgo_residual", method=RequestMethod.GET)
	public String getRiesgoResidual(ModelMap map) {
	    // TODO: retrieve the new value here so you can add it to model map
	    map.addAttribute("riesgoResidual", 9);

	    return "activo_fisico_create :: #riesgoResidual";
	}
	

		
}
