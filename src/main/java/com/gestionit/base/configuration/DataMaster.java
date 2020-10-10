/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.configuration;

import com.gestionit.base.domain.Amenaza;
import com.gestionit.base.domain.Impacto;
import com.gestionit.base.domain.OrigenAmenaza;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.ProbalidadOcurrencia;
import com.gestionit.base.domain.RiesgoInherente;
import com.gestionit.base.domain.RiesgoResidual;
import com.gestionit.base.domain.SalvaguardaTipo;
import com.gestionit.base.domain.TopeCompra;
import com.gestionit.base.repository.AmenzaRepository;
import com.gestionit.base.repository.ImpactoRepo;
import com.gestionit.base.repository.OrigenAmenzaRepository;
import com.gestionit.base.repository.ParametroRepo;
import com.gestionit.base.repository.ProbabilidadOcurrenciaRepo;
import com.gestionit.base.repository.RiesgoInherenteRepo;
import com.gestionit.base.repository.RiesgoResidualRepo;
import com.gestionit.base.repository.SalvaguardaTipoRepository;
import com.gestionit.base.repository.TopesRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author fafre
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataMaster {

    private List<Parametro> monedas;
    private List<Parametro> entidades;
    private List<Parametro> tipoCambios;
    private List<Parametro> tipoOperaciones;
    private List<Parametro> instrumentos;
    private List<TopeCompra> topes;
    private Parametro monedaBase;
    private List<Parametro> cajas;
    private Parametro movIng;
    private Parametro movEgr;
    private Long idMBase;
    private TopeCompra topeVigente;

    //para abm clientes
    private List<Parametro> provincias;
    private List<Parametro> estadosCiviles;
    private List<Parametro> actividadesLaborales;
    private List<Parametro> paises;
    private List<Parametro> tiposDocumentos;
    
    //para Riesgos
    private List<Impacto> impactos;
    private List<ProbalidadOcurrencia> probabilidadesOcurrencias;
    private List<RiesgoInherente> riesgosInherentes;
    private List<RiesgoResidual> riesgosResiduales;
    private List<Amenaza> amenazas;
    private List<OrigenAmenaza> origenAmenazas;
    private List<SalvaguardaTipo> tiposSalvaguarda;


	//creado para pantalla operaciones especialmente
    private List<Parametro> tipoSubOps;

    public static final String MONEDAS = "MONEDAS";
    public static final String ENTIDADES = "ENTIDADES";
    public static final String TIPO_CAMBIOS = "TIPOS_CAMBIOS";
    public static final String TIPO_OPERACIONES = "TIPO_OPERACIONES";
    public static final String INSTRUMENTOS = "INSTRUMENTOS";
    public static final String TOPES = "TOPES";
    public static final String MONEDA_BASE_ID = "MONEDA_BASE_ID";
    public static final String CAJA = "CAJA";
    public static final String TIPO_MOV_IN = "TIPO_MOV_INGRESO";
    public static final String TIPO_MOV_EG = "TIPO_MOV_EGRESO";
    public static final String TIPO_OP_A_COMBINAR = "CMP-VTA";

    //para ABM CLientes
    public static final String PROVINCIAS = "PROVINCIAS";
    public static final String ESTADOS_CIVILES = "ESTADOS_CIVILES";
    public static final String ACTIVIDADES_LABORALES = "ACTIVIDADES_LABORALES";
    public static final String PAISES = "PAISES";
    public static final String TIPOS_DOCUMENTOS = "TIPOS_DOCUMENTOS";
    
    //para ABM de riesgo impacto
    public static final String INSIGNIFICANTE = "INSIGNIFICANTE";
    public static final String MENOR = "MENOR";
    public static final String MODERADO = "MODERADO";
    public static final String MAYOR = "MAYOR";
    public static final String SIGNIFICATIVO = "SIGNIFICATIVO";
    
  //para ABM de riesgo Probabilidad de ocurrencia
    public static final String IMPROBABLE = "IMPROBABLE";
    public static final String BAJA = "BAJA";
    public static final String MEDIA = "MEDIA";
    public static final String ALTA = "ALTA";
    public static final String MUY_ALTA = "MUY ALTA";
    
  //para ABM de riesgo Riesgo Inherente
    public static final String BAJO = "BAJO";
    public static final String MEDIO = "MEDIO";
    public static final String NO_ACEPTABLE = "NO ACEPTABLE";
    
  //para Abm de amenaza de riesgo
    
    public static final String APLICACIONES = "APLICACIONES";
    public static final String HARDWARE = "HARDWARE";
    public static final String INSTALACIONES = "INSTALACIONES";
    public static final String OTROS = "OTROS";
    public static final String PROVEEDORES = "PROVEEDORES";
    public static final String SOFTWARE_DE_BASE = "SOFTWARE DE BASE";
    public static final String TELECOMUNICACIONES = "TELECOMUNICACIONES";
    

    

    //private final ParametroRepo parametroRepo;
    //private final TopesRepo topesRepo;
    
    private final ImpactoRepo impactoRepo;
    
    private final ProbabilidadOcurrenciaRepo proOcuRepo;
    
    private final RiesgoInherenteRepo riesgoInheRepo;
	
    private final RiesgoResidualRepo riesgoResiRepo;
    
    private final AmenzaRepository amenazaRepo;
    
    private final OrigenAmenzaRepository origenAmenazaRepo;
    
    private final SalvaguardaTipoRepository salvaTipoRepo;

    @Autowired
    public DataMaster(ImpactoRepo impactoRepo, ProbabilidadOcurrenciaRepo proOcuRepo,
    		RiesgoInherenteRepo riesgoInheRepo, RiesgoResidualRepo riesgoResiRepo, AmenzaRepository amenazaRepo, OrigenAmenzaRepository origenAmenazaRepo,
    		SalvaguardaTipoRepository salvaTipoRepo) {
        //this.parametroRepo = parametroRepo;
        //this.topesRepo = tr;
        this.impactoRepo = impactoRepo;
        this.proOcuRepo = proOcuRepo;
        this.riesgoInheRepo = riesgoInheRepo;
        this.riesgoResiRepo = riesgoResiRepo;
        this.amenazaRepo = amenazaRepo;
        this.origenAmenazaRepo = origenAmenazaRepo;
        this.salvaTipoRepo = salvaTipoRepo;
    }


	public void intiData() {

        /*this.monedas = parametroRepo.findByTipo(MONEDAS);
        this.entidades = parametroRepo.findByTipo(ENTIDADES);
        this.tipoCambios = parametroRepo.findByTipo(TIPO_CAMBIOS);
        this.tipoOperaciones = parametroRepo.findByTipo(TIPO_OPERACIONES);
        this.instrumentos = parametroRepo.findByTipo(INSTRUMENTOS);
        this.topes = topesRepo.findAllByOrderByFechaAltaDesc();
        this.idMBase = Long.parseLong(parametroRepo.findByTipo(MONEDA_BASE_ID).get(0).getValor());
        this.monedaBase = parametroRepo.findOne(this.idMBase);
        this.cajas = parametroRepo.findByTipo(CAJA);
        this.movIng = parametroRepo.findByTipo(TIPO_MOV_IN).get(0);
        this.movEgr = parametroRepo.findByTipo(TIPO_MOV_EG).get(0);
        this.tipoSubOps = parametroRepo.findByTipo(TIPO_OPERACIONES);
        this.topeVigente = this.topes.get(0);
        //para abm clientes
        this.provincias = parametroRepo.findByTipo(PROVINCIAS);
        this.estadosCiviles=parametroRepo.findByTipo(ESTADOS_CIVILES);
        this.actividadesLaborales=parametroRepo.findByTipo(ACTIVIDADES_LABORALES);
        this.paises=parametroRepo.findByTipo(PAISES);
        this.tiposDocumentos=parametroRepo.findByTipo(TIPOS_DOCUMENTOS);*/
        
        //Riesgo
        this.impactos = (List<Impacto>) impactoRepo.findAll();
        this.probabilidadesOcurrencias = (List<ProbalidadOcurrencia>) proOcuRepo.findAll();
        this.riesgosInherentes = (List<RiesgoInherente>) riesgoInheRepo.findAll();
        this.riesgosResiduales = (List<RiesgoResidual>) riesgoResiRepo.findAll();
        this.amenazas = (List<Amenaza>) amenazaRepo.findByOrigenId(1l);
        this.origenAmenazas = (List<OrigenAmenaza>) origenAmenazaRepo.findAll();
        this.tiposSalvaguarda = (List<SalvaguardaTipo>) salvaTipoRepo.findAll();

    }
	


	public List<SalvaguardaTipo> getTiposSalvaguarda() {
		return tiposSalvaguarda;
	}


	public List<OrigenAmenaza> getOrigenAmenazas() {
		return origenAmenazas;
	}


	public List<Amenaza> getAmenazas() {
		return amenazas;
	}

    public List<RiesgoResidual> getRiesgosResiduales() {
		return riesgosResiduales;
	}

    
    public List<RiesgoInherente> getRiesgosInherentes() {
  		return riesgosInherentes;
  	}

    public List<Parametro> getMonedas() {
        return monedas;
    }
    
    public List<ProbalidadOcurrencia> getProbabilidadesOcurrencias() {
		return probabilidadesOcurrencias;
	}

	public List<Impacto> getImpactos() {
        return impactos;
    }

    public List<Parametro> getEntidades() {
        return entidades;
    }

    public List<Parametro> getTipoCambios() {
        return tipoCambios;
    }

    public List<Parametro> getTipoOperaciones() {
        return tipoOperaciones;
    }

    public List<Parametro> getInstrumentos() {
        return instrumentos;
    }

    public List<TopeCompra> getTopes() {
        return topes;
    }

    public Parametro getMonedaBase() {
        return monedaBase;
    }

    public List<Parametro> getCajas() {
        return cajas;
    }

    public void setCajas(List<Parametro> cajas) {
        this.cajas = cajas;
    }

    public Parametro getMovIng() {
        return movIng;
    }

    public void setMovIng(Parametro movIng) {
        this.movIng = movIng;
    }

    public Parametro getMovEgr() {
        return movEgr;
    }

    public void setMovEgr(Parametro movEgr) {
        this.movEgr = movEgr;
    }

    public Long getIdMBase() {
        return idMBase;
    }

    public void setIdMBase(Long idMBase) {
        this.idMBase = idMBase;
    }

    public List<Parametro> getTipoSubOps() {
        return tipoSubOps;
    }

    public void setTipoSubOps(List<Parametro> tipoSubOps) {
        this.tipoSubOps = tipoSubOps;
    }

    public TopeCompra getTopeVigente() {
        return topeVigente;
    }

    public void setTopeVigente(TopeCompra topeVigente) {
        this.topeVigente = topeVigente;
    }

    public List<Parametro> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Parametro> provincias) {
        this.provincias = provincias;
    }

    public List<Parametro> getEstadosCiviles() {
        return estadosCiviles;
    }

    public void setEstadosCiviles(List<Parametro> estadosCiviles) {
        this.estadosCiviles = estadosCiviles;
    }

    public List<Parametro> getActividadesLaborales() {
        return actividadesLaborales;
    }

    public void setActividadesLaborales(List<Parametro> actividadesLaborales) {
        this.actividadesLaborales = actividadesLaborales;
    }

    public List<Parametro> getPaises() {
        return paises;
    }

    public void setPaises(List<Parametro> paises) {
        this.paises = paises;
    }

    public List<Parametro> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<Parametro> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    
    private void quitarDuplicados(List<Parametro> tipoOperaciones) {
        String ant = "";
        Iterator<Parametro> it = tipoOperaciones.iterator();
        while (it.hasNext()) {
            Parametro nuevo = it.next();
            String nuevoValor = nuevo.getValor();
            if (nuevo.getValor().equals(ant)) {
                it.remove();
            }
            ant = nuevoValor;

        }

    }

}
