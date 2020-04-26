/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.utils.FormatUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author fafre
 */
public class ClienteDTO {

    private Cliente cliente;
    private String fechaNacimiento;
    private String fechaVencimiento;
    private String fechaFirmaPep;
    
    //Screen flags
    private boolean edit;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.cliente = cliente;
        this.fechaNacimiento = cliente.getFechaNacimiento().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.fechaVencimiento = cliente.getVencimientoDoc().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.fechaFirmaPep = cliente.getFechaFirmaPep().format(DateTimeFormatter.ISO_LOCAL_DATE);        
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaFirmaPep() {
        return fechaFirmaPep;
    }

    public void setFechaFirmaPep(String fechaFirmaPep) {
        this.fechaFirmaPep = fechaFirmaPep;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Si el dto esta vacio y no tiene cliente arma uno si no mantengo los datos
     * que ya trae
     */
    public void createIfEmpty(DataMaster dm) {
        if (this.isEmpty()) {
            crearEmptyDTO(dm);
        }
    }

    private boolean isEmpty() {
        boolean empty = true;
        empty = (cliente == null);
        return empty;
    }

    public ClienteDTO(String fechaFirmaPep) {
        this.fechaFirmaPep = fechaFirmaPep;
    }

    public boolean isEdit() {
        return edit;
    }


    
    public void configAltaScreen(){
        this.edit=false;
    }
    public void configEditScreen(){
        this.edit = true;
    }

    /**
     * Crea datos vacios si el cliente es nulo para la pantalla de creacion
     *
     */
    private void crearEmptyDTO(DataMaster dm) {
        Parametro blank = new Parametro();
        this.cliente = new Cliente(blank, "", null, null, "", "", blank,
                blank, blank, "", "", null);
        ;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "cliente=" + cliente + ", fechaNacimiento=" + fechaNacimiento + ", fechaVencimiento=" + fechaVencimiento + ", fechaFirmaPep=" + fechaFirmaPep + '}';
    }

    public Cliente construirClienteFromDTO() {
        Cliente clienteToBuild = this.getCliente();
        clienteToBuild.setFechaNacimiento(FormatUtils.getFormatedLocalDate(fechaNacimiento));
        clienteToBuild.setVencimientoDoc(FormatUtils.getFormatedLocalDate(fechaVencimiento));
        clienteToBuild.setFechaFirmaPep(clienteToBuild.isPep()?FormatUtils.getFormatedLocalDate(fechaFirmaPep):LocalDate.ofYearDay(9999, 1));
        return clienteToBuild;
    }

}
