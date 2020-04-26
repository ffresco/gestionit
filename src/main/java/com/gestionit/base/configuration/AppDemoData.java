/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.configuration;

import com.gestionit.base.controller.CotizacionesController;
import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.AcumuladoCliente;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.Role;
import com.gestionit.base.domain.SesionCaja;
import com.gestionit.base.domain.TopeCompra;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.builder.OpDTOBuilder;
import com.gestionit.base.repository.AcumuladoCajaRepo;
import com.gestionit.base.repository.ClienteRepository;
import com.gestionit.base.repository.CotizacionRepository;
import com.gestionit.base.repository.FileTextRegistryRepo;
import com.gestionit.base.repository.OperacionRepo;
import com.gestionit.base.repository.ParametroRepo;
import com.gestionit.base.repository.SesionCajaRepo;
import com.gestionit.base.repository.TopesRepo;
import com.gestionit.base.repository.UserRepo;
import com.gestionit.base.service.CotizacionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fafre
 */
@Service
public class AppDemoData {

    private ClienteRepository cliR;
    private CotizacionService cotS;
    private ParametroRepo pr;
    private TopesRepo topesRepo;
    private DataMaster dm;
    private UserRepo ur;
    private SesionCajaRepo scr;
    private AcumuladoCajaRepo acr;
    private FileTextRegistryRepo ftrr;
    private OperacionRepo opRepo;

    @Autowired
    public AppDemoData(ClienteRepository cliR, CotizacionService cotS,
            ParametroRepo pr, TopesRepo tr, DataMaster dm, UserRepo ur, SesionCajaRepo scr,
            AcumuladoCajaRepo acr, FileTextRegistryRepo ftrr, OperacionRepo opRepo) {
        this.cliR = cliR;
        this.cotS = cotS;
        this.pr = pr;
        this.topesRepo = tr;
        this.dm = dm;
        this.ur = ur;
        this.scr = scr;
        this.acr = acr;
        this.ftrr = ftrr;
        this.opRepo = opRepo;
                
    }

    public void initAppData() {

        //usuarios
        User fer =ur.save(new User("ffresco@gd","1",Role.ADMIN));
        User rodri =ur.save(new User("mulo@mulo.com", "1", Role.USER));
        ur.save(new User("ffresco@gmail.com", "1", Role.ADMIN));
        User cin= ur.save(new User("czinna@clg.com.ar","1",Role.ADMIN));        
      
      
        /**Parametro*****************************/
        Parametro pOpCmpVta = pr.save(new Parametro("VTA","","A11", DataMaster.TIPO_OPERACIONES));
        Parametro pOpCmpVta2 = pr.save(new Parametro("CMP","","A11", DataMaster.TIPO_OPERACIONES));
        Parametro pOpArb= pr.save(new Parametro("ARBITRAJE", "","A11",DataMaster.TIPO_OPERACIONES));
        Parametro pOpCanje= pr.save(new Parametro("CANJE", "","A11",DataMaster.TIPO_OPERACIONES));
        Parametro pEntidad= pr.save(new Parametro("EMPRESA", DataMaster.ENTIDADES));
        Parametro pTcMin= pr.save(new Parametro("MINORISTA", DataMaster.TIPO_CAMBIOS));
        Parametro pTcesp= pr.save(new Parametro("PREFER-1", DataMaster.TIPO_CAMBIOS));

        
        Parametro pMUsd= pr.save(new Parametro("USD", DataMaster.MONEDAS));
        Parametro pMEur= pr.save(new Parametro("EUR", DataMaster.MONEDAS));
        Parametro pMReal= pr.save(new Parametro("REAL", DataMaster.MONEDAS));
        Parametro pMArs= pr.save(new Parametro("AR$", DataMaster.MONEDAS));
        Parametro pIBillete= pr.save(new Parametro("BILLETE", DataMaster.INSTRUMENTOS));
        Parametro pICheque = pr.save(new Parametro("CHEQUE", DataMaster.INSTRUMENTOS));
        Parametro pCaja = pr.save(new Parametro("CAJA-1", DataMaster.CAJA));
        Parametro pCaja2 = pr.save(new Parametro("CAJA-2", DataMaster.CAJA));
        Parametro pCaja3 = pr.save(new Parametro("CAJA-3", DataMaster.CAJA));
        pr.save(new Parametro("TESORO",DataMaster.CAJA));
        pr.save(new Parametro("T_EXT",DataMaster.CAJA));        
        Parametro pMovIng = pr.save(new Parametro("INGRESO", DataMaster.TIPO_MOV_IN));
        Parametro pMovEg = pr.save(new Parametro("EGRESO", DataMaster.TIPO_MOV_EG));
        
        //de la moneda base guardo el id
        Parametro pMBase = pr.save(new Parametro(pMArs.getId().toString(), DataMaster.MONEDA_BASE_ID));
        
        //Asociacion a cajas
        System.out.println("probando eesto sesion caja");
        scr.save(new SesionCaja(fer, pCaja, LocalDateTime.now(), null));
        scr.save(new SesionCaja(rodri, pCaja2, LocalDateTime.now(), null));
        scr.save(new SesionCaja(cin, pCaja3, LocalDateTime.now(), null));

        //estados
        Parametro pENuevo = new Parametro("NUEVO", "ESTADO");
        pr.save(pENuevo);
        pr.save(new Parametro("PROCESADO", "ESTADO"));
        pr.save(new Parametro("OK", "ESTADO"));
        pr.save(new Parametro("OBSERVADO", "ESTADO"));
        
        //Provincias
        Parametro pProv = pr.save(new Parametro("BSAS","01",DataMaster.PROVINCIAS));
        //Paises
        Parametro pPaisArg = pr.save(new Parametro("ARG","02",DataMaster.PAISES));
        //estado civil
        Parametro pEstCivil = pr.save(new Parametro("SOLTERO",DataMaster.ESTADOS_CIVILES));
        //Actividad Laboral
        Parametro pActLaboral = pr.save(new Parametro("EMPLEADO",DataMaster.ACTIVIDADES_LABORALES));
        //Tipo Doc
        Parametro pTipoDoC = pr.save(new Parametro("DNI",DataMaster.TIPOS_DOCUMENTOS));
        

        //topes
        LocalDateTime fechaAlta = LocalDateTime.now();
        topesRepo.save(new TopeCompra(5000f, 50000f, fechaAlta));
      
  

        //*******Clientes data**********
        Cliente c1 = new Cliente(pTipoDoC, "27444999", LocalDate.now(), LocalDate.now(), "FERNANDO", "FRESCO", pEstCivil, 
                pPaisArg,pPaisArg,"45098877" ,"ffresco@gg.com", null);
        c1.setDireccion("Lincoln 239");
        c1.setFechaFirmaPep(LocalDate.now());
        //Acumulado siempre debo poner el padre en el dueño de la realacion, para que me guarde la relacion
        AcumuladoCliente ac1 = new AcumuladoCliente(c1, Month.MAY, 0, new Float(20),
                new Float(100), pMBase, LocalDateTime.now());
        ac1.setCliente(c1);
        //esta es la realion que manda para que se grave en la base de datos, setear el acumulado al cliente
        //la anterior setear el clente al acumulado genera un update
        c1.setAcumulado(ac1);
        //     c2.setAcumulado(ac1);
        //     c3.setAcumulado(ac1);
        c1 = cliR.save(c1);




        /**
         * *****Cotizaciones+++++++++++++++
         */
        Cotizacion cot1 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 27, 0, 0), pMUsd, pEntidad, pTcMin, 20f, 22f,
                0.1f, 0.12f, pIBillete,  pMBase,0.1f);
        Cotizacion cot2 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 26, 0, 0), pMUsd, pEntidad, pTcMin, 9f, 10f,
                0.1f, 0.12f, pIBillete,  pMBase,0.2f);
        Cotizacion cot3 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 25, 0, 0), pMUsd, pEntidad, pTcMin, 8f, 9f,
                0.1f, 0.12f, pIBillete,  pMBase,0.4f);
        Cotizacion cot4 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 3, 0, 0), pMReal, pEntidad, pTcMin, 30f, 32f,
                0.1f, 0.12f, pIBillete,  pMBase,0.4f);
        Cotizacion cot5 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0), pMReal, pEntidad, pTcMin, 8f, 9f,
                0.1f, 0.12f, pIBillete,  pMBase,0.5f);
        Cotizacion cot6 = new Cotizacion(LocalDateTime.of(2018, Month.FEBRUARY, 1, 0, 0), pMReal, pEntidad, pTcMin, 8f, 9f,
                0.1f, 0.12f, pIBillete,  pMBase,0.10f);
        
        cotS.saveOrUpdate(cot1);
        cotS.saveOrUpdate(cot2);
        cotS.saveOrUpdate(cot3);
        cotS.saveOrUpdate(cot4);
        cotS.saveOrUpdate(cot6);
        
        //Algunas operaciones
        Operacion op = new Operacion(fechaAlta, c1, pCaja, pOpCmpVta, pTcMin, pENuevo, "", null, 20f );
        ArrayList<OperacionItem> opItems = new ArrayList();
        OperacionItem opI1 = new OperacionItem(op, pMUsd, pIBillete, 10f, 10f, "INGRESO", pMovIng, 1, cot6, pCaja);
        OperacionItem opI2 = new OperacionItem(op, pMUsd, pIBillete, 10f, 10f, "EGRESO", pMovEg, 2, cot6, pCaja);
        opItems.add(opI2);
        opItems.add(opI1);
        op.setOperacionItems(opItems);
        op.setTipoMov(OpDTOBuilder.OPERACION_COMERCIAL);
        op.setUser(fer);
        opRepo.save(op);
        
        
        //***************Un par de archivos opcammentirosos
        this.ftrr.save(new FileTextRegistry(LocalDateTime.now(), LocalDate.now(),LocalDate.now(),LocalDate.now(), "opcam.txt", "ok", "Fue bien realizado"));
        
        //*****************Asociar una caja a cada usuario********************/
        dm.intiData();
   
        
        //inicializo los saldos de cajas y tesoro en cero
        for (Parametro caja : dm.getCajas()) {
            for (Parametro moneda : dm.getMonedas()) {
                 for (Parametro inst : dm.getInstrumentos()){
                  acr.save(new AcumuladoCaja(LocalDateTime.now(),inst, moneda, 0, 0, 0,caja)); 
                }
            }
        }
     

    }
}
