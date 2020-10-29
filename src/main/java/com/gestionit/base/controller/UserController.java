/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;


import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.SesionCaja;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.UserCreateFormDTO;
import com.gestionit.base.service.UserService;
import com.gestionit.base.domain.validator.UserCreateFormValidator;
import com.gestionit.base.repository.SesionCajaRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;
    private final SesionCajaRepo sesionCajaRepo;

    @Autowired
    public UserController(UserService userService, UserCreateFormValidator userCreateFormValidator, SesionCajaRepo sesionCajaRepo ) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
        this.sesionCajaRepo = sesionCajaRepo;
    }

    @InitBinder("form") //restringe la aplicacion del init binder solo al atributo form 
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }
    
    @RequestMapping("/usuarios")
    public ModelAndView getUsersPage() {
        LOGGER.debug("Getting users page");
        List<SesionCaja> cajas = (List<SesionCaja>) sesionCajaRepo.findAll();
        List<User> users = (List<User>) userService.getAllUsers();
        List<SesionCaja> resultante = new ArrayList<>();
        boolean exist;
        for (User user : users) {
            exist = existUser(cajas, user);
            if (!exist) {
                SesionCaja nuevaSC = new SesionCaja(user, new Parametro("-SIN CAJA-", ""), LocalDateTime.now(), LocalDateTime.now());
                resultante.add(nuevaSC);
            }

        }
        resultante.addAll(cajas);
        return new ModelAndView("users", "users", resultante);
    }

    private boolean existUser(List<SesionCaja> cajas, User user) {
        boolean exist = false;
        for (SesionCaja sc : cajas) {
            if (sc.getUser().getId().equals(user.getId())) {
                exist = true;
            }
        }
        return exist;
    }

 
    
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        return new ModelAndView("user", "user", userService.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new UserCreateFormDTO());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserCreateFormDTO form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        
        try {
            userService.create(form);
        } catch (Exception e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "user_create";
        }
        return "redirect:/users";
    }

}
