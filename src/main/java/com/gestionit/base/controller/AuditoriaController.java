/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;



import com.gestionit.base.domain.dto.RiesgoAuditDTO;
import com.gestionit.base.service.RiesgoService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/auditoria")
public class AuditoriaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditoriaController.class);
    private RiesgoService riesgoService;
    

    @Autowired
    public AuditoriaController(RiesgoService riesgoService) {
        this.riesgoService = riesgoService;
      
    }

    @RequestMapping("/")
    public ModelAndView getMainPage() {
    	LOGGER.info("Estoy en eliminados");
    	List<RiesgoAuditDTO> riesgosDeleted = null;
		try {
			riesgosDeleted = riesgoService.findAllDeleted();
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("Error",e);
		}
		return new ModelAndView("riesgo_audit_deleted", "auditRiesgosDTO", riesgosDeleted);
    }
    
	
}
