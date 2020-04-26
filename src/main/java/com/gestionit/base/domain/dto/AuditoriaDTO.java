/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

/**
 *
 * @author fafre
 */
public class AuditoriaDTO {
    private String semana;
    private String fechaDesde;
    private String fechaHasta;
    private boolean porSemana;

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean isPorSemana() {
        return porSemana;
    }

    public void setPorSemana(boolean porSemana) {
        this.porSemana = porSemana;
    }

    @Override
    public String toString() {
        return "AuditoriaDTO{" + "semana=" + semana + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", porSemana=" + porSemana + '}';
    }
    
    
    
}
