/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fafre
 */
@Entity
@Table(name = "sesion_caja")
public class SesionCaja implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "fk_user")
    private User user;
    
    @OneToOne
    @JoinColumn(name = "fk_caja")
    private Parametro caja;
    
    @Column(name = "inicio_sesion")
    private LocalDateTime inicioSesion;
    
    @Column(name = "fin_sesion")
    private LocalDateTime finSesion;

    public SesionCaja() {
    }

    public SesionCaja(User user, Parametro caja, LocalDateTime inicioSesion, LocalDateTime finSesion) {
        this.user = user;
        this.caja = caja;
        this.inicioSesion = inicioSesion;
        this.finSesion = finSesion;
    }
    
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Parametro getCaja() {
        return caja;
    }

    public void setCaja(Parametro caja) {
        this.caja = caja;
    }

    public LocalDateTime getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(LocalDateTime inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public LocalDateTime getFinSesion() {
        return finSesion;
    }

    public void setFinSesion(LocalDateTime finSesion) {
        this.finSesion = finSesion;
    }
    
    
}
