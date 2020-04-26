/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.dto.ClienteDTO;
import com.gestionit.base.domain.dto.ClienteSearchDTO;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.domain.validator.ClienteValidator;
import com.gestionit.base.repository.ClienteRepository;
import com.gestionit.base.service.ClienteService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/clientes")
public class ClientesController implements CrudControllerInterface<ClienteSearchDTO, ClienteDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientesController.class);

    private ClienteService clienteService;
    private ClienteValidator clienteValidator;
    private DataMaster dataMaster;

    @Autowired
    public ClientesController(ClienteService clienteService, ClienteValidator cv, DataMaster dm) {
        this.clienteService = clienteService;
        this.clienteValidator = cv;
        this.dataMaster = dm;
    }

    @Override
    public ModelAndView getMainPage(ClienteSearchDTO searchDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("clientes", "clientes", clienteService.findAll());
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

    @Override
    public ModelAndView search(@ModelAttribute (value = "searchDTO") ClienteSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        return new ModelAndView("clientes", "clientes", clienteService.findAllContaining(searchDTO));
    }

    @Override
    public ModelAndView getCreatePage(ClienteDTO dto, BindingResult bindingResult) {
        dto.createIfEmpty(dataMaster);
        dto.configAltaScreen();
     return new ModelAndView("cliente_create", "dto", dto);
    }

    @Override
    public ModelAndView save(@ModelAttribute("dto") ClienteDTO dto, BindingResult bindingResult) {
        LOGGER.debug("----En el save de clientes-------");
        clienteValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
           return getCreatePage(dto, bindingResult);
        }
        LOGGER.debug("DTO para gravar :" + dto);
        Cliente clienteAGravar = dto.construirClienteFromDTO();
        Cliente clienteGravado = clienteService.saveOrUpdate(clienteAGravar);
        return edit(clienteGravado.getId());

    }

    @Override
    public ModelAndView delete(@PathVariable Long id) {

        Cliente clienteABorrar = clienteService.getById(id);
        clienteService.delete(clienteABorrar);
        return getMainPage(new ClienteSearchDTO(), null);
    }

    @Override
    public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        Cliente clienteAEditar = clienteService.getById(id);
        ClienteDTO dto = new ClienteDTO(clienteAEditar);
        dto.configEditScreen();
        LOGGER.info("DTO a editar "+dto);
        return new ModelAndView("cliente_create", "dto", dto);

    }


    @ModelAttribute(value = "dataMaster")
    public DataMaster getDataMaster() {
        return dataMaster;
    }

}
