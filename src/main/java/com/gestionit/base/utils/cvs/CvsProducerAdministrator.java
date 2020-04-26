/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.utils.cvs;

import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.repository.FileTextRegistryRepo;
import com.gestionit.base.service.OperacionService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author fafre
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CvsProducerAdministrator {

    //Propiedades
    private static final int TRABAJOS_CONCURRENTES = 2;
    private int pedidos;
    @Autowired
    private CvsUtils cvsUtils;  

    
    
    
    
    
    private synchronized int encargarPedido(){
        this.pedidos++;
        return pedidos;
    }
    
    private synchronized int entregarPedido(){
        this.pedidos--;
        return pedidos;
    }
    
    /**
     * 
     * @param pedido
     * @return true si pudo encargar el pedido, falso si exiten mas pedidos de los que puede manejar 
     */
    public boolean encargarCVS(FileTextRegistry pedido,OperacionService service,
            FileTextRegistryRepo regRepo){
        //agrego un pedido a los encargados
        this.encargarPedido();
        if (this.pedidos>TRABAJOS_CONCURRENTES) {
            return false;
        }   
        System.out.println("Pedidos al momento "+this.pedidos);
        String productor = "Productor "+pedido.getFileName();
        Productor p1 = new Productor(productor, pedido, this,regRepo , service);
        //Creo el hilo para ejecutar el la generacion del archivo
        Thread tp1 = new Thread(p1);  
        tp1.setName("Hilo productor de CVS " + pedido.getFileName()); 
        tp1.start();     
        //dejo el hilo corriendo y me vuelvo a quien me llamo
        return true;

    }
    
    private class Productor implements Runnable{
        private String producerName;
        private FileTextRegistry fileRegistry;
        private CvsProducerAdministrator producerAdministrator;
        private FileTextRegistryRepo repo;
        private OperacionService service;

        public Productor(String name, FileTextRegistry file, CvsProducerAdministrator admin,
                FileTextRegistryRepo rep,OperacionService service) {
            this.producerName = name;
            this.fileRegistry = file;
            this.producerAdministrator = admin;
            this.repo = rep;
            this.service = service;
        }

 
        @Override
        public void run() {
            this.crearFile();
        }
        
        /**
         * Se encarga de generar un archivo txt de opcam, si logra hacerlo escribe un detalle del archivo
         * generado con estado ok, si no escribe el detalle con estado fallido
         */
        private void crearFile() {
            System.out.println("Productor de archivos opcam " + this.producerName);;
            System.out.println("Comenzando generacion de archivo " + this.fileRegistry.getFileName());
            System.out.println("Dentro del hilo, productor :"+ producerName+ "pedidos " + this.producerAdministrator.pedidos );
            System.out.println("--pedidos ahora" + this.producerAdministrator.pedidos);
            boolean exito = cvsUtils.writeWithCsvBeanWriter(service, fileRegistry);
            if (exito) {
                fileRegistry.setEstado("ok");                
            }else{
                fileRegistry.setEstado("fallido");
                
            }
            
            repo.save(fileRegistry);
            producerAdministrator.entregarPedido();
        }
        
        
        
}
    
}
