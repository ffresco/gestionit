/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author fafre
 */
@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent> {
    
  @Autowired  
  private AppDemoData appDD;  
  
  /**
   * This event is executed as late as conceivably possible to indicate that 
   * the application is ready to service requests.
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    System.out.println("Entre en el iniciador de app");
    appDD.initAppData();
 
  }
    
}
