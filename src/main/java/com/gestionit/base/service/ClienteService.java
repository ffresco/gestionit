/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.service;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.AcumuladoCaja;
import com.gestionit.base.domain.AcumuladoCliente;
import com.gestionit.base.domain.Cliente;
import com.gestionit.base.domain.dto.ClienteDTO;
import com.gestionit.base.domain.dto.ClienteSearchDTO;
import com.gestionit.base.repository.AcumuladoClienteRepo;
import com.gestionit.base.repository.ClienteRepository;
import com.gestionit.base.repository.ParametroRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fafre
 */
@Service
public class ClienteService implements BasicService<Cliente>{
    
    private ClienteRepository cliRepo;
    private ParametroRepo parametroRepo;
    private AcumuladoClienteRepo acumuladoClienteRepo;
    private DataMaster dm;

    @Autowired
    public ClienteService(ClienteRepository cliRepo, ParametroRepo pr, AcumuladoClienteRepo acr,
            DataMaster dm) {
        this.cliRepo = cliRepo;
        this.parametroRepo = pr;
        this.acumuladoClienteRepo = acr;
        this.dm = dm;
        
    }

    @Override
    public void delete(Cliente entity) {
        cliRepo.delete(entity);
    }

    @Override
    public Cliente getById(Long id) {
        return cliRepo.findOne(id);
    }

    @Transactional
    @Override
    public Cliente saveOrUpdate(Cliente entity) {
        //si no existe el acumulado se lo creo
        buildAcumuladoCliente(entity);
        Cliente clienteGravado = cliRepo.save(entity);
        System.out.println("Cliente Gravado" + clienteGravado);
        return clienteGravado;
    }

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) cliRepo.findAll();
    }

    public Cliente findByDocumento(String documento) {
        return cliRepo.findByDocumento(documento);
    }

    public List<Cliente> findAllContaining(ClienteSearchDTO searchDTO) {
      String documento = searchDTO.getDocumento()!=null?searchDTO.getDocumento():"";
      String nombre = searchDTO.getNombre()!=null?searchDTO.getNombre():"";
      String apellido = searchDTO.getApellido()!=null?searchDTO.getApellido():"";
      PageRequest pageRequest = new PageRequest(0,5, new Sort(Sort.Direction.DESC,"apellido"));
      Page<Cliente> page = cliRepo.getAllLikeNombreApellidoDocumento(nombre, apellido, documento, pageRequest);
      return page.getContent();
               
    }

    public void buildAcumuladoCliente(Cliente clienteAGravar) {
        //si es la primera vez lo estoy creando le hago un acumulado en cero
        if (clienteAGravar.getId()==null) {
            System.out.println("consturyendo el acumulado");
            LocalDateTime hoy = LocalDateTime.now();
            //el cliente se pone para despues de crear el cliente haga un update de acumulados
            AcumuladoCliente acumulado = new AcumuladoCliente(clienteAGravar, hoy.getMonth(), hoy.getYear(), 0f, 0f,dm.getMonedaBase(),hoy);
            //el acumulado si se setea pra que genere el insert de acumulados cuando guarde el cliente
            clienteAGravar.setAcumulado(acumulado);
        }
    }

    public boolean existClient(Cliente cliente) {
       return !(cliRepo.findByDocumento(cliente.getDocumento())==null);
    }


    
 
    
    
}
