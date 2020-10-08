/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Amenaza;
import com.gestionit.base.domain.Riesgo;

import com.gestionit.base.domain.Salvaguarda;
import com.gestionit.base.domain.dto.RiesgoDTO;
import com.gestionit.base.domain.dto.RiesgoSearchDTO;
import com.gestionit.base.repository.AmenzaRepository;
import com.gestionit.base.repository.RiesgoInherenteRepo;
import com.gestionit.base.repository.RiesgoResidualRepo;
import com.gestionit.base.service.RiesgoService;
import com.gestionit.base.utils.FormatUtils;


/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/riesgos")
public class RiesgoController implements CrudControllerInterface<RiesgoSearchDTO,RiesgoDTO>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RiesgoController.class);
    private RiesgoService riesgoService;
    private DataMaster dataMaster;
    private RiesgoInherenteRepo riesgoinheRepo;
    private RiesgoResidualRepo riesgoResiRepo;
    private AmenzaRepository amenazaRepo;

    @Autowired
    public RiesgoController(RiesgoService riesgoService, DataMaster dataMaster, RiesgoInherenteRepo riesgoinheRepo,
    		RiesgoResidualRepo riesgoResiRepo, AmenzaRepository amenazaRepo) {
        this.riesgoService = riesgoService;
        this.dataMaster = dataMaster;
        this.riesgoinheRepo = riesgoinheRepo;
        this.riesgoResiRepo = riesgoResiRepo;
        this.amenazaRepo = amenazaRepo;

    }

    @Override
    public ModelAndView getMainPage(RiesgoSearchDTO searchDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("riesgos");
        mav.addObject("riesgos", riesgoService.findAll());
        mav.addObject("searchDTO", searchDTO);
        return mav;
    }

	@Override
	public ModelAndView search(RiesgoSearchDTO searchDTO, BindingResult bindingResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView getCreatePage(RiesgoDTO objectDTO, BindingResult bindingResult) {
		 LOGGER.debug("-------Le peuge a main page------------");

	        //Genero el DTO
	        RiesgoDTO riesgoDTO = new RiesgoDTO();
	        configAltaScreen(riesgoDTO);
	        Riesgo riesgo = new Riesgo();
	        Salvaguarda salvaguarda = new Salvaguarda();
	        riesgo.setSalvaguarda(salvaguarda);
	        riesgoDTO.setFechaAnalisis(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
	        riesgoDTO.setRiesgo(riesgo);
	        LOGGER.info("Cree el siguiente dto para operar : " + riesgoDTO);


	        //Preparo el moddel and view
	        ModelAndView mav = new ModelAndView("riesgo_create");
	        mav.addObject("riesgoDTO", riesgoDTO);
	        return mav;
	}

	@Override
	@RequestMapping(value = "/save", params = {"guardar"})
	public ModelAndView save(RiesgoDTO riesgoDTO, BindingResult bindingResult) {
		LOGGER.info("-----Entre al save de Riesgos------");
		riesgoDTO.getRiesgo().setFechaAnalisis(FormatUtils.getFormatedLocalDate(riesgoDTO.getFechaAnalisis()));
		riesgoDTO.setRiesgo(riesgoService.saveOrUpdate(riesgoDTO.getRiesgo()));
		riesgoDTO.setAmenazas(amenazaRepo.findByOrigenId(riesgoDTO.getOrigenAmenaza().getId()));
        ModelAndView mav = new ModelAndView("riesgo_create");
        mav.addObject("riesgoDTO", riesgoDTO);
        return mav;
	}
	

    @RequestMapping(value = "/save", params = {"procesar"})
    public ModelAndView process(@ModelAttribute(value = "riesgoDTO") RiesgoDTO riesgoDTO,
            BindingResult result) {
        LOGGER.debug("--------------------Entre al procesar------------------");
        ModelAndView mav = new ModelAndView("riesgo_create");
        mav.addObject("riesgoDTO", riesgoDTO);

        //recupero la operacion
        Riesgo riesgo = riesgoDTO.getRiesgo();
        
        riesgoDTO.setProcesado(true);
        
        riesgo = procesarRiesgo(riesgo);
        
        if(riesgoDTO.getOrigenAmenaza()!=null &&
        		riesgoDTO.getOrigenAmenaza().getId()!=null	) {
        	 riesgoDTO.setAmenazas(amenazaRepo.findByOrigenId(riesgoDTO.getOrigenAmenaza().getId()));
        }
        mav.addObject("riesgoDTO", riesgoDTO);
        return mav;
    }

	@Override
	public ModelAndView delete(@PathVariable Long id) {
		Riesgo riesgoABorrar = riesgoService.getById(id);
		riesgoService.delete(riesgoABorrar);
        return getMainPage(new RiesgoSearchDTO(), null);
	}

	@Override
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        Riesgo riesgoAEditar = riesgoService.getById(id);
        RiesgoDTO dto = new RiesgoDTO(riesgoAEditar);
        dto.configEditScreen();
        dto.setFechaAnalisis(riesgoAEditar.getFechaAnalisis().format(DateTimeFormatter.ISO_LOCAL_DATE));
        dto.setOrigenAmenaza(riesgoAEditar.getAmenaza().getOrigen());
        dto.setAmenazas(amenazaRepo.findByOrigenId(dto.getRiesgo().getAmenaza().getOrigen().getId()));
        LOGGER.info("DTO a editar "+dto);
        return new ModelAndView("riesgo_create", "riesgoDTO", dto);
	}
  
	   @ModelAttribute("dataMaster")
	    public DataMaster getDataMaster() {
	        System.out.println("--Me meti en el data master--");
	        return dataMaster;
	    }
    
	   
	   /*
	    * Se spuede modificar, no se puede guardar y no esta procesado
	    */
	    public void configAltaScreen(RiesgoDTO riesgoDTO) {
	    	riesgoDTO.setModificable(true);
	    	riesgoDTO.setProcesado(false);
	    	riesgoDTO.setReadOnly(false);
	    	riesgoDTO.setAmenazas((List<Amenaza>) amenazaRepo.findAll());

	    }
	    
	    private Riesgo procesarRiesgo(Riesgo riesgo) {
			
			getRiesgoInherente(riesgo);
			getMenorImpactoSalvaguarda(riesgo);
			getMenorProbabilidadSalvaguarda(riesgo);
			getRiesgoResidual(riesgo);
			

			return riesgo;
		}

		private void getMenorProbabilidadSalvaguarda(Riesgo riesgo) {
			
			try {
				Integer menorProbabilidad = 0;
				Integer probabilidadOcurrenciaValor = riesgo.getProbabilidadOcurrencia().getValor();
				Integer salvaguardaValor = riesgo.getSalvaguarda().getValor();
				if(salvaguardaValor==1) {
					menorProbabilidad = probabilidadOcurrenciaValor - 2;
				}else if(salvaguardaValor==2) {
					menorProbabilidad = probabilidadOcurrenciaValor - 1;
				}else if(salvaguardaValor==3 || salvaguardaValor==4 ) {
					menorProbabilidad = probabilidadOcurrenciaValor;
				}
				
				if(menorProbabilidad<1) {
					menorProbabilidad = 1;
				}

				riesgo.getSalvaguarda().setMenorProbabilidad(menorProbabilidad);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
			
		}

		private void getMenorImpactoSalvaguarda(Riesgo riesgo) {
			try {
				Integer menorImpacto = 0;
				Integer impactoValor = riesgo.getImpacto().getValor();
				Integer salvaguardaValor = riesgo.getSalvaguarda().getValor();
				if(salvaguardaValor==1) {
					menorImpacto = impactoValor - 2;
				}else if(salvaguardaValor==2) {
					menorImpacto = impactoValor - 1;
				}else if(salvaguardaValor==3 || salvaguardaValor==4 ) {
					menorImpacto = impactoValor;
				}
				
				if(menorImpacto<1) {
					menorImpacto = 1;
				}

				riesgo.getSalvaguarda().setMenorImpacto(menorImpacto);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
			
		}
		

		private void getRiesgoResidual(Riesgo riesgo) {
			
			try {
				Integer riesgoResidual = riesgo.getSalvaguarda().getMenorImpacto() * riesgo.getSalvaguarda().getMenorProbabilidad();
				riesgo.getRiesgoResidualValor().setValor(riesgoResidual);
				if(riesgoResidual <= 5) {
					riesgo.getRiesgoResidualValor().setRiesgoResidual(riesgoResiRepo.findOne(1l));		
				}else if (riesgoResidual >= 6 && riesgoResidual <= 10) {
					riesgo.getRiesgoResidualValor().setRiesgoResidual(riesgoResiRepo.findOne(2l));
				}else if (riesgoResidual >= 12 && riesgoResidual <= 15) {
					riesgo.getRiesgoResidualValor().setRiesgoResidual(riesgoResiRepo.findOne(3l));
				} else if (riesgoResidual >= 16 && riesgoResidual <= 20) {
					riesgo.getRiesgoResidualValor().setRiesgoResidual(riesgoResiRepo.findOne(4l));
				}else if(riesgoResidual == 25) {
					riesgo.getRiesgoResidualValor().setRiesgoResidual(riesgoResiRepo.findOne(5l));
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
		}

		private void getRiesgoInherente(Riesgo riesgo) {
			try {
				Integer riesgoInherente = riesgo.getProbabilidadOcurrencia().getValor() * riesgo.getImpacto().getValor();
				riesgo.getRiesgoInherenteValor().setValor(riesgoInherente);
				if(riesgoInherente <= 5) {
					riesgo.getRiesgoInherenteValor().setRiesgoInherente(riesgoinheRepo.findOne(1l));		
				}else if (riesgoInherente >= 6 && riesgoInherente <= 10) {
					riesgo.getRiesgoInherenteValor().setRiesgoInherente(riesgoinheRepo.findOne(2l));
				}else if (riesgoInherente >= 12 && riesgoInherente <= 15) {
					riesgo.getRiesgoInherenteValor().setRiesgoInherente(riesgoinheRepo.findOne(3l));
				} else if (riesgoInherente >= 16 && riesgoInherente <= 20) {
					riesgo.getRiesgoInherenteValor().setRiesgoInherente(riesgoinheRepo.findOne(4l));
				}else if(riesgoInherente == 25) {
					riesgo.getRiesgoInherenteValor().setRiesgoInherente(riesgoinheRepo.findOne(5l));
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
		}
		
}
