/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.domain.dto.MapaDeRiesgoDTO;
import com.gestionit.base.service.RiesgoService;

/**
 *
 * @author fafre
 */
@Controller
public class ReportesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportesController.class);
    private RiesgoService riesgoService;
    
    
    
    @Autowired
    public ReportesController(RiesgoService riesgoService) {
		this.riesgoService = riesgoService;
	}

    @RequestMapping("/reportes")
    public ModelAndView getMainPage() {
        return new ModelAndView("reportes");
    }
    
    @RequestMapping("/reportes/mapaDeRiesgo")
    public ModelAndView mapaDeRiesgo() {
    	
        return new ModelAndView("mapa_riesgo","dto",new MapaDeRiesgoDTO(riesgoService.getMatrizDeRiesgo()));
    }
    
    
}
