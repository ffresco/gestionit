    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;


import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Evento;
import com.gestionit.base.domain.dto.EventoDTO;
import com.gestionit.base.domain.dto.EventoSearchDTO;
import com.gestionit.base.service.EventoService;
import com.gestionit.base.utils.FormatUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
@RequestMapping(value="/eventos")
public class EventoController implements CrudControllerInterface<EventoSearchDTO, EventoDTO> {

    private final Logger LOGGER = LoggerFactory.getLogger(EventoController.class);
    private final EventoService eventoService;
    private DataMaster dataMaster;

    @Autowired
    public EventoController(EventoService eventoService, DataMaster dataMaster) {
        this.eventoService = eventoService;
        this.dataMaster = dataMaster;
        
    }
    
    

    @Override
    public ModelAndView getMainPage( EventoSearchDTO ev,
            BindingResult bindingResult) {
    	 return new ModelAndView("eventos", "eventos", eventoService.findAll());
    }

    @Override
    public ModelAndView getCreatePage(EventoDTO objectDTO, BindingResult bindingResult) {
        LOGGER.debug("--Entre a getCreatePage Eventos Controller--");
        Evento evento = new Evento();
        evento.setFecha(LocalDate.now());
        System.out.println(evento);
        EventoDTO eventoDTO = new EventoDTO(evento);
        eventoDTO.setEvento(evento);
        ModelAndView mav = new ModelAndView("evento_create");
        mav.addObject("eventoDTO", eventoDTO);
        return mav;
    }


    @Override
    @RequestMapping(value = "/save", params = {"guardar"})
    public ModelAndView save(@ModelAttribute(value = "eventoDTO") EventoDTO evDTO,
            BindingResult result) {
        LOGGER.info("-----Entre al save de Eventos------");
        evDTO.getEvento().setFecha(FormatUtils.getFormatedLocalDate(evDTO.getFecha()));
		
        evDTO.setEvento(eventoService.save(evDTO.getEvento()));
        ModelAndView mav = new ModelAndView("evento_create");
        mav.addObject("eventoDTO", evDTO);
        return mav;

    }
    

   
    @Override
    public ModelAndView search(@ModelAttribute(value="eventosearch") EventoSearchDTO searchDTO, BindingResult bindingResult) {
        LOGGER.debug("--Entre a search Eventos Controller--");
        if (bindingResult.hasErrors()) {
            LOGGER.debug(bindingResult.toString());
        }
        return new ModelAndView("eventos", "eventos", eventoService.findAllContaining(searchDTO));
    }

    
    @Override
    public ModelAndView delete(@PathVariable Long id) {
        LOGGER.debug("--Entre a delete incidentes Controller-- Con id a borrar" + id);
        eventoService.delete(eventoService.getById(id));
        return getMainPage(new EventoSearchDTO(id),null);
    }

    
    @Override
    public ModelAndView edit(@PathVariable Long id) {
        LOGGER.debug("--Entre a edit Incidentes Controller--");
        EventoDTO evDTO = new EventoDTO();
        evDTO.setEvento(eventoService.getById(id));
        evDTO.setFecha(evDTO.getEvento().getFecha().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ModelAndView mav = new ModelAndView("evento_create");
        mav.addObject("eventoDTO",evDTO );
        return mav;
    }

    @ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}


}
