/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.configuration;


import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.AcumuladoCliente;
import com.gestionit.base.domain.Amenaza;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.Cotizacion;
import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.domain.Impacto;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.OrigenAmenaza;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.ProbalidadOcurrencia;
import com.gestionit.base.domain.RiesgoInherente;
import com.gestionit.base.domain.RiesgoResidual;
import com.gestionit.base.domain.Role;
import com.gestionit.base.domain.SalvaguardaTipo;
import com.gestionit.base.domain.SesionCaja;
import com.gestionit.base.domain.TopeCompra;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.builder.OpDTOBuilder;
import com.gestionit.base.repository.AcumuladoCajaRepo;
import com.gestionit.base.repository.AmenzaRepository;
import com.gestionit.base.repository.ClienteRepository;
import com.gestionit.base.repository.FileTextRegistryRepo;
import com.gestionit.base.repository.ImpactoRepo;
import com.gestionit.base.repository.OperacionRepo;
import com.gestionit.base.repository.OrigenAmenzaRepository;
import com.gestionit.base.repository.ParametroRepo;
import com.gestionit.base.repository.ProbabilidadOcurrenciaRepo;
import com.gestionit.base.repository.RiesgoInherenteRepo;
import com.gestionit.base.repository.RiesgoResidualRepo;
import com.gestionit.base.repository.SalvaguardaTipoRepository;
import com.gestionit.base.repository.SesionCajaRepo;
import com.gestionit.base.repository.TopesRepo;
import com.gestionit.base.repository.UserRepo;
import com.gestionit.base.service.CotizacionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ImpactoRepo itRRepo;
    private ProbabilidadOcurrenciaRepo probOcurrRepo;
    private RiesgoInherenteRepo riesgoinheRepo;
    private RiesgoResidualRepo riesgoResiRepo;
    private AmenzaRepository amenazaRepo;
    private OrigenAmenzaRepository origenAmenazaRepo;
    private SalvaguardaTipoRepository salvaTipoRepo; 
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String createUpdateDatabase;

    @Autowired
    public AppDemoData(ClienteRepository cliR, CotizacionService cotS,
            ParametroRepo pr, TopesRepo tr, DataMaster dm, UserRepo ur, SesionCajaRepo scr,
            AcumuladoCajaRepo acr, FileTextRegistryRepo ftrr, OperacionRepo opRepo,ImpactoRepo itRRepo,
            ProbabilidadOcurrenciaRepo probOcurrRepo, RiesgoInherenteRepo riesgoinheRepo,
            RiesgoResidualRepo riesgoResiRepo, AmenzaRepository amenazaRepo, OrigenAmenzaRepository origenAmenazaRepo,
            SalvaguardaTipoRepository salvaTipoRepo) {
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
        this.itRRepo = itRRepo;
        this.probOcurrRepo = probOcurrRepo;
        this.riesgoinheRepo = riesgoinheRepo;
        this.riesgoResiRepo = riesgoResiRepo;
        this.amenazaRepo = amenazaRepo;
        this.origenAmenazaRepo = origenAmenazaRepo;
        this.salvaTipoRepo = salvaTipoRepo;
        
                
    }

    public void initAppData() {
    	
    	if(createUpdateDatabase.equals("create-drop")) {

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

    		//impacto

    		Impacto imp = new Impacto(1, DataMaster.INSIGNIFICANTE);
    		Impacto imp1 = new Impacto(2, DataMaster.MENOR);
    		Impacto imp2 = new Impacto(3, DataMaster.MODERADO);
    		Impacto imp3 = new Impacto(4, DataMaster.MAYOR);
    		Impacto imp4 = new Impacto(5, DataMaster.SIGNIFICATIVO);


    		itRRepo.save(imp);
    		itRRepo.save(imp1);
    		itRRepo.save(imp2);
    		itRRepo.save(imp3);
    		itRRepo.save(imp4);


    		//Probabilidad Ocurrencia

    		ProbalidadOcurrencia prob = new ProbalidadOcurrencia(1, DataMaster.IMPROBABLE+" - Se espera que ocurran 1 vez cada 1 o 2 años.");
    		ProbalidadOcurrencia prob1 = new ProbalidadOcurrencia(2, DataMaster.BAJA+" - Se espera que ocurran 2 veces por año.");
    		ProbalidadOcurrencia prob2 = new ProbalidadOcurrencia(3, DataMaster.MEDIA+" - Se espera que ocurran 4 veces por año.");
    		ProbalidadOcurrencia prob3 = new ProbalidadOcurrencia(4, DataMaster.ALTA+" - Se espera que ocurran muchas veces por año.");
    		ProbalidadOcurrencia prob4 = new ProbalidadOcurrencia(5, DataMaster.MUY_ALTA+" -Se espera que ocurra frecuentemente.");


    		probOcurrRepo.save(prob);
    		probOcurrRepo.save(prob1);
    		probOcurrRepo.save(prob2);
    		probOcurrRepo.save(prob3);
    		probOcurrRepo.save(prob4);


    		//Riesgo Inherente

    		RiesgoInherente riesgoInhe = new RiesgoInherente(1, DataMaster.BAJO);
    		RiesgoInherente riesgoInhe1 = new RiesgoInherente(2, DataMaster.MENOR);
    		RiesgoInherente riesgoInhe2 = new RiesgoInherente(3, DataMaster.MEDIO);
    		RiesgoInherente riesgoInhe3 = new RiesgoInherente(4, DataMaster.MAYOR);
    		RiesgoInherente riesgoInhe4 = new RiesgoInherente(5, DataMaster.NO_ACEPTABLE);


    		riesgoinheRepo.save(riesgoInhe);
    		riesgoinheRepo.save(riesgoInhe1);
    		riesgoinheRepo.save(riesgoInhe2);
    		riesgoinheRepo.save(riesgoInhe3);
    		riesgoinheRepo.save(riesgoInhe4);


    		//Riesgo Residual

    		RiesgoResidual riesgoRes = new RiesgoResidual(1, DataMaster.BAJO);
    		RiesgoResidual riesgoRes1 = new RiesgoResidual(2, DataMaster.MENOR);
    		RiesgoResidual riesgoRes2 = new RiesgoResidual(3, DataMaster.MEDIO);
    		RiesgoResidual riesgoRes3 = new RiesgoResidual(4, DataMaster.MAYOR);
    		RiesgoResidual riesgoRes4 = new RiesgoResidual(5, DataMaster.NO_ACEPTABLE);


    		riesgoResiRepo.save(riesgoRes);
    		riesgoResiRepo.save(riesgoRes1);
    		riesgoResiRepo.save(riesgoRes2);
    		riesgoResiRepo.save(riesgoRes3);
    		riesgoResiRepo.save(riesgoRes4);

    		//Origen Amenzas

    		OrigenAmenaza origenAmenaza1 = new OrigenAmenaza( DataMaster.APLICACIONES);
    		OrigenAmenaza origenAmenaza2 = new OrigenAmenaza(DataMaster.HARDWARE);
    		OrigenAmenaza origenAmenaza3 = new OrigenAmenaza(DataMaster.INSTALACIONES);
    		OrigenAmenaza origenAmenaza4 = new OrigenAmenaza(DataMaster.PROVEEDORES);
    		OrigenAmenaza origenAmenaza5 = new OrigenAmenaza( DataMaster.SOFTWARE_DE_BASE);
    		OrigenAmenaza origenAmenaza6 = new OrigenAmenaza( DataMaster.TELECOMUNICACIONES);
    		OrigenAmenaza origenAmenaza7 = new OrigenAmenaza( DataMaster.OTROS);

    		this.origenAmenazaRepo.save(origenAmenaza1);
    		this.origenAmenazaRepo.save(origenAmenaza2);
    		this.origenAmenazaRepo.save(origenAmenaza3);
    		this.origenAmenazaRepo.save(origenAmenaza4);
    		this.origenAmenazaRepo.save(origenAmenaza5);
    		this.origenAmenazaRepo.save(origenAmenaza6);
    		this.origenAmenazaRepo.save(origenAmenaza7);


    		//AmenazaRepo 

    		Amenaza amenaza = new Amenaza("1.1", origenAmenaza1, "Errores de operación de los usuarios");
    		Amenaza amenaza1 = new Amenaza("2.1", origenAmenaza2, "Errores de Instalación/Configuración de los administradores");
    		Amenaza amenaza2 = new Amenaza("3.1", origenAmenaza3, "Accesos físicos no autorizados");
    		Amenaza amenaza3 = new Amenaza("4.1", origenAmenaza4, "Relación con el proveedor no formalizada");
    		Amenaza amenaza4 = new Amenaza("5.1", origenAmenaza5, "Errores de operación de los administradores");
    		Amenaza amenaza5 = new Amenaza("6.1", origenAmenaza6, "Ancho de banda insuficiente");
    		Amenaza amenaza6 = new Amenaza("7.1", origenAmenaza7, "Amenazas organizacionales, de gestión, estructura o limitación de recursos humanos, técnicos, económicos");


    		this.amenazaRepo.save(amenaza);
    		this.amenazaRepo.save(amenaza1);
    		this.amenazaRepo.save(amenaza2);
    		this.amenazaRepo.save(amenaza3);
    		this.amenazaRepo.save(amenaza4);
    		this.amenazaRepo.save(amenaza5);
    		this.amenazaRepo.save(amenaza6);


    		//Salvaguarda tipo

    		SalvaguardaTipo salvaguardaTipo = new SalvaguardaTipo(1, "Soportada por un sistema");
    		SalvaguardaTipo salvaguardaTipo1 = new SalvaguardaTipo(2, "Soportada por factor humano");
    		SalvaguardaTipo salvaguardaTipo2 = new SalvaguardaTipo(3, "No suficientemente soportada");
    		SalvaguardaTipo salvaguardaTipo3 = new SalvaguardaTipo(4, "Sin salvaguarda");



    		salvaTipoRepo.save(salvaguardaTipo);
    		salvaTipoRepo.save(salvaguardaTipo1);
    		salvaTipoRepo.save(salvaguardaTipo2);
    		salvaTipoRepo.save(salvaguardaTipo3);



    		//***************Un par de archivos opcammentirosos
    		this.ftrr.save(new FileTextRegistry(LocalDateTime.now(), LocalDate.now(),LocalDate.now(),LocalDate.now(), "opcam.txt", "ok", "Fue bien realizado"));

    		//*****************Asociar una caja a cada usuario********************/

    	}
        dm.intiData();
   
        
        //inicializo los saldos de cajas y tesoro en cero
      /*  for (Parametro caja : dm.getCajas()) {
            for (Parametro moneda : dm.getMonedas()) {
                 for (Parametro inst : dm.getInstrumentos()){
                  acr.save(new AcumuladoCaja(LocalDateTime.now(),inst, moneda, 0, 0, 0,caja)); 
                }
            }
        }*/
     

    }
}
