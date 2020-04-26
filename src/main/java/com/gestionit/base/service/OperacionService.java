/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.AcumuladoCliente;
import com.gestionit.base.domain.FileTextRegistry;
import com.gestionit.base.domain.Operacion;
import com.gestionit.base.domain.OperacionItem;
import com.gestionit.base.domain.Parametro;
import com.gestionit.base.domain.SesionCaja;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.ClienteSearchDTO;
import com.gestionit.base.domain.dto.OperacionDTO;
import com.gestionit.base.domain.dto.OperacionSearchDTO;
import com.gestionit.base.domain.dto.builder.OpDTOBuilder;
import com.gestionit.base.repository.AcumuladoCajaRepo;
import com.gestionit.base.repository.OperacionRepo;
import com.gestionit.base.repository.ParametroRepo;
import com.gestionit.base.repository.SesionCajaRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestionit.base.repository.AcumuladoClienteRepo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fafre
 */
@Service
public class OperacionService {

    private OperacionRepo opRep;
    private AcumuladoClienteRepo acRep;
    private ParametroRepo paramRepo;
    private SesionCajaRepo scr;
    private AcumuladoCajaRepo acr;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public OperacionService(OperacionRepo opRep, AcumuladoClienteRepo acRep,
            ParametroRepo pr, SesionCajaRepo scr, AcumuladoCajaRepo acr) {
        this.opRep = opRep;
        this.acRep = acRep;
        this.paramRepo = pr;
        this.scr = scr;
        this.acr = acr;
    }

    /**
     *
     * @param op
     * @param ac
     * @return Retorna el objeto acumulado cliente igual, o con acumulado mes y
     * año reseteado a cero si el mes o el año es distinto al de la operacion
     */
    public AcumuladoCliente configAcumuladoCliente(Operacion op, AcumuladoCliente ac) {
        Month opMonth = op.getFechaHora().getMonth();
        int opYear = op.getFechaHora().getYear();
        System.out.println("Comparando el año del acumulado : " + ac.getAno() + " año de la op " + opYear
                + " imprimo como int para ver diferencia " + ac.getAno());
        System.out.println("Comparando meses " + ac.getMes() + " con op  " + op.getFechaHora().getMonth());
        if (opYear != ac.getAno()) {
            //cambio año y mes
            ac.setAno(op.getFechaHora().getYear());
            ac.setMes(op.getFechaHora().getMonth());
            ac.setAcumuladoAno(new Float(0));
            ac.setAcumuladoMes(new Float(0));
            System.out.println("Distinto año " + ac.toString());
        }
        if (opMonth != ac.getMes()) {
            ac.setAcumuladoMes(new Float(0));
            System.out.println("Solo distinto mes " + ac.toString());
        }
        return ac;

    }

    /**
     *
     * @param op
     * @param monto
     * @return El objeto acumulado cliente con los acumulados actualizados con
     * los montos de la ultima operacion
     */
    public AcumuladoCliente calcAcumuladoCliente(AcumuladoCliente acuCli, float monto) {
        float acuMes = 0;
        float acuAno = 0;
        acuCli.getAcumuladoAno();
        System.out.println("Recibi estos acumulados " + acuMes + "ano " + acuAno + "monto " + monto);
        //si el mes y año es el mismo acumulo, si no cambio el mes y arranco de cero
        Month mesActual = LocalDate.now().getMonth();
        int anoActual = LocalDate.now().getYear();
        if ((acuCli.getMes().equals(mesActual)) && (acuCli.getAno() == anoActual)) {
            acuMes = acuCli.getAcumuladoMes() + monto;
        } else {
            acuCli.setMes(mesActual);
            acuMes = monto;
        }
        //si el año es el mismo acumulo, si no cambio el año y arranco en cero
        if (acuCli.getAno() == anoActual) {
            acuAno = acuCli.getAcumuladoAno() + monto;
        } else {
            acuCli.setAno(anoActual);
            acuAno = monto;
        }
        acuCli.setAcumuladoAno(acuAno);
        acuCli.setAcumuladoMes(acuMes);
        System.out.println("Retorno el ojbeto asi " + acuCli);
        return acuCli;

    }

    /**
     * Al grabar una operacion tambien se debe actualizar los acumulados del
     * cliente y los de las cajas, Este metodo es para gravar operaciones
     * comerciales las que hacen los cajero
     *
     * @param op
     */
    @Transactional(rollbackFor = {Exception.class})
    public Operacion saveComercial(Operacion op) {
        try {
            //Actualizo los acumulados del cliente
            AcumuladoCliente acum = actualizarAcumuladoCliente(op);
            acRep.save(acum);

            //Actualizo los saldos de caja
            //en la comercial el origin te da el ingreso a tu caja
            OperacionItem opO = op.getOpItemO();
            AcumuladoCaja acumO = registrarIngresoAcumCaja(opO);
            //el destino es lo que le entrego al cliente y es el egreso
            OperacionItem opD = op.getOpItemD();
            AcumuladoCaja acumD = registrarEgresoAcumCaja(opD);
            acr.save(acumO);
            acr.save(acumD);

            //Gravo la operacion
            Operacion opGravada = opRep.save(op);
            return opGravada;
        } catch (Exception e) {
            em.flush();
            throw e;
        }

    }



    @Transactional(rollbackFor = {Exception.class})
    public Operacion saveContable(OperacionDTO opDTO) {
        try {

            Operacion op = opDTO.getOperacion();
            Operacion opGravada = opRep.save(op);

            //En la contabiliad saco del origen etonces es egreso
            OperacionItem opO = op.getOpItemO();
            AcumuladoCaja acumO = registrarEgresoAcumCaja(opO);

            //El destino recibe la plata es ingreso
            OperacionItem opD = op.getOpItemD();
            AcumuladoCaja acumD = registrarIngresoAcumCaja(opD);

            opDTO.setAcumCajaO(acr.save(acumO));
            opDTO.setAcumCajaD(acr.save(acumD));
            return opGravada;
        } catch (Exception e) {
            em.flush();
            throw e;
        }

    }

        private AcumuladoCaja registrarIngresoAcumCaja(OperacionItem opItem) {
        AcumuladoCaja acum = registrarIngresoEgresoAcumCaja(opItem, opItem.getMonto(), 0f);
        return acum;
    }

    private AcumuladoCaja registrarEgresoAcumCaja(OperacionItem opItem) {
        AcumuladoCaja acum = registrarIngresoEgresoAcumCaja(opItem, 0f, opItem.getMonto());
        return acum;
    }

    private AcumuladoCaja registrarIngresoEgresoAcumCaja(OperacionItem opItem, float ingreso, float egreso) {
        //busco el acumulado para la caja y el instrumento
        AcumuladoCaja acum = acr.findTopByMonedaIdAndInstrumentoIdAndCajaId(opItem.getMoneda().getId(),
                opItem.getInstrumento().getId(), opItem.getCaja().getId());
        acum.upadateIngreso(ingreso);
        acum.upadateEgreso(egreso);
        acum.upadateSaldo();
        acum.setFechaActualizacion(LocalDateTime.now());
        return acum;
    }
    private AcumuladoCliente actualizarAcumuladoCliente(Operacion op) {
        //tomo de la operacion cuanto fue el monto de moneda Base para calcular tope del cliente
        AcumuladoCliente acum = op.getCliente().getAcumulado();
        for (OperacionItem t : op.getOperacionItems()) {
            if (t.getMoneda().getValor().equals("AR$")) {
                acum = calcAcumuladoCliente(acum, t.getMonto());
            }

        }
        return acum;
    }

    public Operacion buildOperacionFromDTO(OperacionDTO opDTO) {
        Operacion opAGravar = opDTO.getOperacion();

        //parametros de la operacion -->TOREFACT
        Parametro caja = paramRepo.findByValor(opAGravar.getCaja().getValor());
        Parametro tipoOp = paramRepo.findByValor(opAGravar.getTipoOp().getValor());
        Parametro tipoCambio = paramRepo.findByValor(opAGravar.getTipoCambio().getValor());
        Parametro estado = paramRepo.findByValor(opAGravar.getEstado().getValor());
        opAGravar.setCaja(caja);
        opAGravar.setTipoOp(tipoOp);
        opAGravar.setTipoCambio(tipoCambio);
        opAGravar.setEstado(estado);

        //parametrizo las suboperacions
        OperacionItem opO = opDTO.getOpO();
        Parametro monedaO = paramRepo.findByValor(opO.getMoneda().getValor());
        Parametro instO = paramRepo.findByValor(opO.getInstrumento().getValor());
        opO.setMoneda(monedaO);
        opO.setInstrumento(instO);
        opO.setOperacion(opAGravar);

        //parametrizo las suboperacion destino
        OperacionItem opD = opDTO.getOpD();
        Parametro monedaD = paramRepo.findByValor(opD.getMoneda().getValor());
        Parametro instD = paramRepo.findByValor(opD.getInstrumento().getValor());
        opD.setMoneda(monedaD);
        opD.setInstrumento(instD);
        opD.setOperacion(opAGravar);

        opAGravar.setOperacionItems(new ArrayList());
        opAGravar.getOperacionItems().add(opDTO.getOpO());
        opAGravar.getOperacionItems().add(opDTO.getOpD());

        return opAGravar;

    }

    public Operacion findOne(Long id) {
        return opRep.findOne(id);
    }

    public List<Operacion> findAll() {
        return (List<Operacion>) opRep.findAll();

    }

    public Parametro getCaja(User user) {
        System.out.println("tratando de ejecutar esto ");
        SesionCaja sc = scr.findTopByUserOrderByInicioSesionDesc(user);
        System.out.println(sc);
        return sc.getCaja();

    }

    /**
     * Metodo utilizado por el CVS opcam para abrir stream a la base con los archivos necesarios
     * @param fileReg
     * @return 
     */
    public Stream<Operacion> getStreamOpOpCam(FileTextRegistry fileReg) {
        LocalDateTime fechaDesde = LocalDateTime.of(fileReg.getFechaDesde(), LocalTime.MIDNIGHT);
        LocalDateTime fechaHasta = LocalDateTime.of(fileReg.getFechaHasta(), LocalTime.MAX);
        return opRep.findByFechaHoraAfterAndFechaHoraBeforeAndTipoMov(fechaDesde, fechaHasta,OpDTOBuilder.OPERACION_COMERCIAL);
    }
    
    public List<Operacion> findAllByTipoMovimiento(String tipoMov){
        return opRep.findByTipoMov(tipoMov);
    }
    
    public List<Operacion> findAllByCajaId(Long idCaja){
        return opRep.findByCajaId(idCaja);
    }

    public List<Operacion> findAllByCajaIdAndInstrumentoIdAndMonedaId(Long idCaja, Long idInstrumento, Long idMoneda){
        return opRep.findDistinctByCajaIdAndOperacionItemsInstrumentoIdAndOperacionItemsMonedaId(idCaja, idInstrumento, idMoneda);
    }
    /**
     * Si recibe un id busca por el id, si no solo busca por el dto usando like
     */
    public List<Operacion> findAllContaining(OperacionSearchDTO search,String tipoMov){
        Long id = search.getOperacion()==null?0:search.getOperacion();
        String documento = search.getDocumento()==null?"":search.getDocumento();
        String nombre = search.getNombre()==null?"":search.getNombre();
        String apellido = search.getApellido()==null?"":search.getApellido();
        if (id>0) {
         List<Operacion> list = new ArrayList();
         Operacion op = opRep.findOne(id);
         if (op!=null) list.add(op);
         return list; 
        }
        return opRep.findByTipoMovAndClienteDocumentoStartingWithAndClienteNombreStartingWithAndClienteApellidoStartingWith(tipoMov,documento, nombre, apellido);
       
    }

}
