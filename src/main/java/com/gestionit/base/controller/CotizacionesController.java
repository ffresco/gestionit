    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.dto.CotizacionDTO;
import com.gestionit.base.domain.dto.CotizacionSearchDTO;
import com.gestionit.base.domain.validator.CotizacionValidator;
import com.gestionit.base.service.CotizacionService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
@RequestMapping(value="/cotizaciones")
public class CotizacionesController implements CrudControllerInterface<CotizacionSearchDTO,CotizacionDTO> {

    private final Logger LOGGER = LoggerFactory.getLogger(CotizacionesController.class);
    private final CotizacionService cotizacionService;
    private final DataMaster dataMaster;
    private final CotizacionValidator cotizacionValidator;

    @Autowired
    public CotizacionesController(CotizacionService cotizacionService, DataMaster dataMaster, CotizacionValidator cv) {
        this.cotizacionService = cotizacionService;
        this.dataMaster = dataMaster;
        this.cotizacionValidator = cv;
    }
    
    

    @Override
    public ModelAndView getMainPage(@ModelAttribute(value="cotizacionsearch") CotizacionSearchDTO cs,
            BindingResult bindingResult) {
        LOGGER.debug("--Entre a getMainPage Cotizaciones Controller--");
        List<Cotizacion> cotizaciones = cotizacionService.getAllContaining(cs);
        LOGGER.debug("listado de cotizaciones " + cotizaciones);
        ModelAndView mav = new ModelAndView("cotizaciones");
        mav.addObject("cotizaciones", cotizaciones);
        mav.addObject("dataMaster", dataMaster);
        mav.addObject("cotizacionSearch",cs);
        return mav;
    }

    @Override
    public ModelAndView getCreatePage(CotizacionDTO objectDTO, BindingResult bindingResult) {
        LOGGER.debug("--Entre a getCreatePage Cotizaciones Controller--");
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFecha(LocalDateTime.now());
        cotizacion.setMonedaBase(dataMaster.getMonedaBase());
        cotizacion.setComisionCmp(new Float(0));
        cotizacion.setComisionVta(new Float(0));
        cotizacion.setCotizacionCmp(new Float(0));
        cotizacion.setCotizacionVta(new Float(0));
        cotizacion.setCanje(0.1f);
        System.out.println(cotizacion);
        CotizacionDTO cotDTO = new CotizacionDTO();
        cotDTO.setCotizacion(cotizacion);
        ModelAndView mav = new ModelAndView("cotizacion_create");
        mav.addObject("cotizacionDTO", cotDTO);
        mav.addObject("dataMaster", dataMaster);
        return mav;
    }

    //@RequestMapping(value = "/cotizaciones/save", method = RequestMethod.POST)
    @Override
    public ModelAndView save(@Valid @ModelAttribute(value="cotizacionDTO") CotizacionDTO cotDTO, BindingResult result) {
        LOGGER.debug("--Entre a save Cotizaciones Controller--");
        if (result.hasErrors()) {
            LOGGER.debug(result.toString());
        }
        System.out.println("cotizacion recu "+cotDTO);
        Cotizacion cotizacionAGravar = cotDTO.getCotizacion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        cotizacionAGravar.setFecha(LocalDateTime.parse(cotDTO.getFecha(), formatter));
        LOGGER.debug("Cotizacion grabada " + cotizacionService.saveOrUpdate(cotizacionAGravar));
        return getCreatePage(null, result);
    }

   
    @Override
    public ModelAndView search(@ModelAttribute(value="cotizacionsearch") CotizacionSearchDTO cs, BindingResult bindingResult) {
        LOGGER.debug("--Entre a search Cotizaciones Controller--");
        if (bindingResult.hasErrors()) {
            LOGGER.debug(bindingResult.toString());
        }
        LOGGER.debug(cs.toString());
        LOGGER.debug(cs.getFechaDesde());
        if (cs.getFechaDesde()!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate formatDateTime = LocalDate.parse(cs.getFechaDesde(), formatter);
            LOGGER.debug(formatDateTime.toString());

        }
        return getMainPage(cs,bindingResult);
    }

    
    @Override
    public ModelAndView delete(@PathVariable Long id) {
        LOGGER.debug("--Entre a delete Cotizaciones Controller-- Con id a borrar" + id);
        cotizacionService.delete(cotizacionService.getById(id));
        return getMainPage(new CotizacionSearchDTO(),null);
    }

    
    @Override
    public ModelAndView edit(@PathVariable Long id) {
        LOGGER.debug("--Entre a edit Cotizaciones Controller--");
        CotizacionDTO cotDTO = new CotizacionDTO();
        cotDTO.setCotizacion(cotizacionService.getById(id));
        ModelAndView mav = new ModelAndView("cotizacion_create");
        mav.addObject("cotizacionDTO",cotDTO );
        mav.addObject("dataMaster", dataMaster);
        return mav;
    }

   

 





}
