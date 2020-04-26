/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
public class ReportesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportesController.class);

    @RequestMapping("/reportes")
    public ModelAndView getMainPage() {
        return new ModelAndView("reportes");
    }
}
