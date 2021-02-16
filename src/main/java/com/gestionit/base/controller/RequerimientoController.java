/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.configuration.DataMaster;

import com.gestionit.base.domain.Requerimiento;
import com.gestionit.base.domain.dto.RequerimientoDTO;
import com.gestionit.base.domain.dto.RequerimientoSearchDTO;
import com.gestionit.base.service.RequerimientoService;




/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/requerimientos")
public class RequerimientoController extends CrudControllerPaginationInterface<RequerimientoSearchDTO,RequerimientoDTO, Requerimiento>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RequerimientoController.class);
    private RequerimientoService requerimientoService;
    private DataMaster dataMaster;


    @Autowired
    public RequerimientoController(RequerimientoService requerimientoService, DataMaster dataMaster) {
        this.requerimientoService = requerimientoService;
        this.dataMaster = dataMaster;

    }

    @Override
    public String getMainPage(RequerimientoSearchDTO searchDTO, BindingResult bindingResult) {
        return "redirect:/requerimientos/paginated/" + getPageNumber() + "/" + getPageSize();
    }
    
    @Override
    @GetMapping(value = "/paginated/{page}/{page-size}")
	ModelAndView getMainPagePaginated(@PathVariable(name = "page") Integer pageNumber, @PathVariable(name = "page-size") Integer pageSize, RequerimientoSearchDTO searchDTO,
			BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("requerimientos");
        mav.addObject("requerimientos", requerimientoService.getPaginatedRequerimientos(pageNumber, pageSize));
        mav.addObject("searchDTO", searchDTO);
        return mav;
	}

	@Override
    @RequestMapping(value = "/search/{page}/{page-size}", method = RequestMethod.POST)
    public ModelAndView search(@PathVariable(name = "page") final int pageNumber,
            @PathVariable(name = "page-size") final int pageSize,@ModelAttribute (value = "searchDTO") RequerimientoSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        final Page<Requerimiento> paginatedRequerimientos = requerimientoService.getPaginatedRequerimientos(pageNumber, pageSize, searchDTO);
        return new ModelAndView("requerimientos", "searchDTO", getResponseDto(paginatedRequerimientos, pageNumber, searchDTO));
    }

	@Override
	public ModelAndView getCreatePage(RequerimientoDTO objectDTO, BindingResult bindingResult) {
		 LOGGER.debug("-------Le peuge a main page------------");

	        //Genero el DTO
	        RequerimientoDTO requerimientoDTO = new RequerimientoDTO(new Requerimiento());
	        LOGGER.info("Cree el siguiente dto para operar : " + requerimientoDTO);

	        //Preparo el moddel and view
	        ModelAndView mav = new ModelAndView("requerimiento_create");
	        mav.addObject("requerimientoDTO", requerimientoDTO);
	        return mav;
	}

	@Override
	@RequestMapping(value = "/save", params = {"guardar"})
	public ModelAndView save(RequerimientoDTO objetDTO, BindingResult bindingResult) {
		LOGGER.info("-----Entre al save de Proyectos------");
	   ModelAndView mav = new ModelAndView("requerimiento_create");
	   objetDTO.setRequerimiento(requerimientoService.saveOrUpdate(objetDTO.getRequerimiento()));
        mav.addObject("requerimientoDTO", objetDTO);
        return mav;
	}
	
	
	@ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}


	@Override
	public String delete(@PathVariable Long id) {
		Requerimiento requerimientoABorrar = requerimientoService.getById(id);
		requerimientoService.delete(requerimientoABorrar);
        return "redirect:/requerimientos/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
	}

	@Override
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        Requerimiento requerimientoAEditar = requerimientoService.getById(id);
        RequerimientoDTO dto = new RequerimientoDTO(requerimientoAEditar);
        //habilito la aprobacion si hay un solo usuario o es distinto al que lo creo

        LOGGER.info("DTO a editar "+dto);
        return new ModelAndView("requerimiento_create", "requerimientoDTO", dto);
	}
  
	@RequestMapping(value = "/view/{id}")
	public ModelAndView view(@PathVariable Long id) {
        LOGGER.info("Estoy en view este es el id " + id);
        Requerimiento requerimientoAEditar = requerimientoService.getById(id);
        RequerimientoDTO dto = new RequerimientoDTO(requerimientoAEditar);
        LOGGER.info("DTO a ver "+dto);
        return new ModelAndView("requerimiento_view", "requerimientoDTO", dto);
	}




		

	
		
}
