/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.domain.dto.builder.OpContableDTOBuilder;
import com.gestionit.base.domain.dto.builder.OpDTOBuilder;
import com.gestionit.base.domain.enums.Estado;
import com.gestionit.base.domain.validator.OperacionProcessValidator;
import com.gestionit.base.repository.AcumuladoCajaRepo;
import com.gestionit.base.service.ClienteService;
import com.gestionit.base.service.CotizacionService;
import com.gestionit.base.service.OperacionService;
import com.gestionit.base.service.UserService;
import com.gestionit.base.service.strategy.operacion.OperacionHandler;
import com.gestionit.base.service.strategy.operacion.OperacionHandlerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
@RequestMapping("/cajas")
public class CajasController implements CrudControllerInterface<Object, OperacionDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CajasController.class);
    private OperacionService operacionService;
    private UserService userService;
    private DataMaster dataMaster;
    private OperacionProcessValidator opProcValidator;
    private OperacionHandlerFactory operacionHandlerFactory;
    private OpDTOBuilder dTOBuilder;
    private AcumuladoCajaRepo acumuladoCajaRepo;

    @Autowired
    public CajasController(OperacionService operacionService, UserService userService,
            DataMaster dataMaster, OperacionProcessValidator opProcValidator,
            OperacionHandlerFactory operacionHandlerFactory, OpContableDTOBuilder dTOBuilder,
            AcumuladoCajaRepo acr) {
        this.operacionService = operacionService;
        this.userService = userService;
        this.dataMaster = dataMaster;
        this.opProcValidator = opProcValidator;
        this.operacionHandlerFactory = operacionHandlerFactory;
        this.dTOBuilder = dTOBuilder;
        this.acumuladoCajaRepo = acr;
    }

    @Override
    public ModelAndView getMainPage(Object searchDTO, BindingResult bindingResult) {
        return new ModelAndView("cajas", "acumulados", acumuladoCajaRepo.findAll());
    }

    @Override
    public ModelAndView search(Object searchDTO, BindingResult bindingResult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelAndView getCreatePage(OperacionDTO opDTOparam, BindingResult bindingResult) {
        LOGGER.debug("-------Le peuge a main page------------");

        //Genero el DTO
        OperacionDTO opDTO = new OperacionDTO();

        dTOBuilder.buildHeader(opDTO, bindingResult);
        dTOBuilder.buildBody(opDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return getMainPage(null, bindingResult);
        }

        LOGGER.info("Cree el siguiente dto para operar : " + opDTO);

        //seto los flags de configuracion
        opDTO.configAltaScreen(opDTO);

        //Preparo el moddel and view
        ModelAndView mav = new ModelAndView("caja_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;
    }

    /**
     * 
     * @param opDTO
     * @param result
     * @return opDTO con operacion preprocesada antes de guardar 
     */
    @RequestMapping(value = "/save", params = {"procesar"})
    public ModelAndView process(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.debug("--------------------Entre al procesar------------------");
        ModelAndView mav = new ModelAndView("caja_create");
        mav.addObject("operacionDTO", opDTO);

        //recupero la operacion
        Operacion op = opDTO.getOperacion();
        LOGGER.info("Esta es la op procesar : " + op);
        System.out.println(op.getTipoMov());

        //implemantar un factory que me de un manejador
        OperacionHandler opHandler = operacionHandlerFactory.getHandler(op.getTipoMov());

        //no encontro manejador
        if (opHandler == null) {
            opDTO.configAltaScreen(opDTO);
            result.reject("operacion.not.implemented", "la operacion: " + op.getTipoMov() + "no"
                    + " ha sido implementada o el sistema, verifique la carga de parametros, si la operacion"
                    + " corresponde a un nuevo tipo solicite la implementacion de la funcionalidad ");
            return mav;
        }

        //valido la seleccion de combos (por ahora la validacion es general para todas las op)
        //opProcValidator.validate(opDTO, result);
        if (result.hasErrors()) {
            //seto los flags de configuracion de pantalla
            opDTO.configAltaScreen(opDTO);
            return mav;
        }

       
        opHandler.generarOperacion(opDTO, result);
        //si el handler tuvo algun error
        if (result.hasErrors()) {
            opDTO.configAltaScreen(opDTO);
            return mav;
        }
        //pongo un flag para invalidar el resto de los campos
        opDTO.configProcessScreen(opDTO);

        //Cambio el estado de la Cotizacion a procesado
        op.setEstado(Estado.PROCESADO.getEstado());

        //guado todo en el mav
        mav.addObject("operacionDTO", opDTO);
        return mav;
    }
    
    
   @RequestMapping(value = "/save", params = {"modificar"})
    public ModelAndView modif(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.debug("----------Le pegue al modificar---------");
        //Seteo nuevamente los flags de modificacion
        opDTO.configAltaScreen(opDTO);

        //Cambio el estado a nuevo
        opDTO.getOperacion().setEstado(Estado.NUEVO.getEstado());

        //guado todo en el mav
        ModelAndView mav = new ModelAndView("caja_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;

    }

    @Override
    @RequestMapping(value = "/save", params = {"guardar"})
    public ModelAndView save(@ModelAttribute(value = "operacionDTO") OperacionDTO opDTO,
            BindingResult result) {
        LOGGER.info("-----Entre al save de operaciones Contables------");
        LOGGER.info("OperacionDTO recuperada" + opDTO.getOperacion());

        //Le cambio el estado al OK
        opDTO.getOperacion().setEstado(Estado.OK.getEstado());
        
        dTOBuilder.setTipoMov(opDTO);
       

        //construyo  el objeto a grabar
        Operacion opAGravar = dTOBuilder.buildOpToSaveFromDTO(opDTO);
        LOGGER.info("Esta es la operacion a grabar " + opAGravar);

        //Si el estado es observado lo guardo con comentarios
        //Guardo la operacion
        Operacion op = operacionService.saveContable(opDTO);
        
        opDTO.setOperacion(op);
        LOGGER.debug("operacion gravada " + op);
        opDTO.configReadOnlyScreen(opDTO);
        ModelAndView mav = new ModelAndView("caja_create");
        mav.addObject("operacionDTO", opDTO);
        return mav;

    }
    
    
   
    @Override
    public ModelAndView delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelAndView edit(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "/detalle/{id}")
    public ModelAndView detalle(@PathVariable Long id) {
    	AcumuladoCaja acumCaja = acumuladoCajaRepo.findOne(id);
        List<Operacion> operaciones = operacionService.findAllByCajaIdAndInstrumentoIdAndMonedaId(
        													acumCaja.getCaja().getId(),
        													acumCaja.getInstrumento().getId(),
        													acumCaja.getMoneda().getId());
    	ModelAndView mav = new ModelAndView("cajas_detalle");
        mav.addObject("operaciones", operaciones);    	
    	return mav;
    }

    @ModelAttribute("dataMaster")
    public DataMaster getDataMaster() {
        System.out.println("--Me meti en el data master de CajasController--");
        return dataMaster;
    }
}
