/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.SesionCaja;
import com.gestionit.base.domain.User;
import com.gestionit.base.repository.SesionCajaRepo;
import com.gestionit.base.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;
    private final SesionCajaRepo sesionCajaRepo;

    @Autowired
    public UsersController(UserService userService, SesionCajaRepo sc) {
        this.userService = userService;
        this.sesionCajaRepo = sc;
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

    @RequestMapping("/users/create")
    public ModelAndView create() {
        return getUsersPage();
    }

    @RequestMapping("/users/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return getUsersPage();
    }

}
