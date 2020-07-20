    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;


import com.gestionit.base.domain.Incidente;
import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.dto.IncidenteDTO;
import com.gestionit.base.domain.dto.IncidenteSearchDTO;
import com.gestionit.base.domain.validator.CotizacionValidator;
import com.gestionit.base.service.IncidenteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Cbova
 */
@Controller
@RequestMapping(value="/incidentes")
public class IncidenteController implements CrudControllerInterface<IncidenteSearchDTO,IncidenteDTO> {

    private final Logger LOGGER = LoggerFactory.getLogger(IncidenteController.class);
    private final IncidenteService inicidenteService;
    private final DataMaster dataMaster;
    private final CotizacionValidator cotizacionValidator;

    @Autowired
    public IncidenteController(IncidenteService inicidenteService, DataMaster dataMaster, CotizacionValidator cv) {
        this.inicidenteService = inicidenteService;
        this.dataMaster = dataMaster;
        this.cotizacionValidator = cv;
    }
    
    

    @Override
    public ModelAndView getMainPage(@ModelAttribute(value="incidentesearch") IncidenteSearchDTO cs,
            BindingResult bindingResult) {
        LOGGER.debug("--Entre a getMainPage Incidentes Controller--");
        List<Incidente> incidentes = inicidenteService.getAllContaining(cs);
        LOGGER.debug("listado de Incidentes " + incidentes);
        ModelAndView mav = new ModelAndView("incidentes");
        mav.addObject("incidentes", incidentes);
        mav.addObject("dataMaster", dataMaster);
        mav.addObject("incidenteSearch",cs);
        return mav;
    }

    @Override
    public ModelAndView getCreatePage(IncidenteDTO objectDTO, BindingResult bindingResult) {
        LOGGER.debug("--Entre a getCreatePage Incidentes Controller--");
        Incidente incidente = new Incidente();
        incidente.setFechaHora(LocalDateTime.now());
        System.out.println(incidente);
        IncidenteDTO incDTO = new IncidenteDTO();
        incDTO.setIncidente(incidente);
        ModelAndView mav = new ModelAndView("incidente_create");
        mav.addObject("incidenteDTO", incDTO);
        mav.addObject("dataMaster", dataMaster);
        return mav;
    }

    //@RequestMapping(value = "/cotizaciones/save", method = RequestMethod.POST)
    @Override
    public ModelAndView save(@Valid @ModelAttribute(value="incidenteDTO") IncidenteDTO incDTO, BindingResult result) {
        LOGGER.debug("--Entre a save Incidentes Controller--");
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
        }
        System.out.println("incidente recu "+incDTO);
        Incidente incidenteAGravar = incDTO.getIncidente();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        incidenteAGravar.setFechaHora(LocalDateTime.parse(incDTO.getFecha(), formatter));
        LOGGER.debug("Incidente grabado " + inicidenteService.saveOrUpdate(incidenteAGravar));
        return getCreatePage(null, result);
    }

   
    @Override
    public ModelAndView search(@ModelAttribute(value="incidentesearch") IncidenteSearchDTO cs, BindingResult bindingResult) {
        LOGGER.debug("--Entre a search Incidentes Controller--");
        if (bindingResult.hasErrors()) {
            LOGGER.debug(bindingResult.toString());
        }
        LOGGER.debug(cs.toString());
        LOGGER.debug(cs.getFecha());
        if (cs.getFecha()!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate formatDateTime = LocalDate.parse(cs.getFecha(), formatter);
            LOGGER.debug(formatDateTime.toString());

        }
        return getMainPage(cs,bindingResult);
    }

    
    @Override
    public ModelAndView delete(@PathVariable Long id) {
        LOGGER.debug("--Entre a delete incidentes Controller-- Con id a borrar" + id);
        inicidenteService.delete(inicidenteService.getById(id));
        return getMainPage(new IncidenteSearchDTO(),null);
    }

    
    @Override
    public ModelAndView edit(@PathVariable Long id) {
        LOGGER.debug("--Entre a edit Incidentes Controller--");
        IncidenteDTO incDTO = new IncidenteDTO();
        incDTO.setIncidente(inicidenteService.getById(id));
        ModelAndView mav = new ModelAndView("incidente_create");
        mav.addObject("incidenteDTO",incDTO );
        mav.addObject("dataMaster", dataMaster);
        return mav;
    }

   

 





}
