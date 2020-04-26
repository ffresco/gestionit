/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.controller;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author fafre
 */
@Controller
public class LoginController {
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error){
        return new ModelAndView("login","error",error);
    }
    
    @RequestMapping(value="/main")
    public String getMainPage(){
        return "main";
    }
    

}
