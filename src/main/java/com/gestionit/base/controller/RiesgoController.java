/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * MIKUS
 */
package com.gestionit.base.controller;



import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gestionit.base.configuration.DataMaster;
import com.gestionit.base.domain.Amenaza;
import com.gestionit.base.domain.Proyecto;
import com.gestionit.base.domain.Riesgo;
import com.gestionit.base.domain.User;
import com.gestionit.base.domain.dto.RiesgoDTO;
import com.gestionit.base.domain.dto.RiesgoSearchDTO;
import com.gestionit.base.repository.AmenzaRepository;
import com.gestionit.base.repository.RiesgoInherenteRepo;
import com.gestionit.base.repository.RiesgoResidualRepo;
import com.gestionit.base.service.ProyectoService;
import com.gestionit.base.service.RiesgoService;
import com.gestionit.base.service.UserService;
import com.gestionit.base.utils.FormatUtils;
import com.gestionit.base.utils.RiesgoExcelExporter;
import com.ibm.icu.util.GregorianCalendar;


/**
 *
 * @cbova
 */
@Controller
@RequestMapping(value="/riesgos")
public class RiesgoController extends CrudControllerPaginationInterface<RiesgoSearchDTO,RiesgoDTO, Riesgo>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RiesgoController.class);
    private RiesgoService riesgoService;
    private DataMaster dataMaster;
    private RiesgoInherenteRepo riesgoinheRepo;
    private RiesgoResidualRepo riesgoResiRepo;
    private AmenzaRepository amenazaRepo;
    private UserService userService;
    private ProyectoService proyectoService;
    
    

    @Autowired
    public RiesgoController(RiesgoService riesgoService, DataMaster dataMaster, RiesgoInherenteRepo riesgoinheRepo,
    		RiesgoResidualRepo riesgoResiRepo, AmenzaRepository amenazaRepo, UserService userService,
    		ProyectoService proyectoService) {
        this.riesgoService = riesgoService;
        this.dataMaster = dataMaster;
        this.riesgoinheRepo = riesgoinheRepo;
        this.riesgoResiRepo = riesgoResiRepo;
        this.amenazaRepo = amenazaRepo;
        this.userService = userService;
        this.proyectoService = proyectoService;

    }
    
    @RequestMapping(value = "/")
    public String getMainPage(RiesgoSearchDTO searchDTO, BindingResult bindingResult) {
    	return "redirect:/riesgos/paginated/" + getPageNumber() + "/" + getPageSize();
    }
    
    @Override
    @GetMapping(value = "/paginated/{page}/{page-size}")
    public ModelAndView getMainPagePaginated(@PathVariable(name = "page") final int pageNumber,
            @PathVariable(name = "page-size") final int pageSize,RiesgoSearchDTO searchDTO, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("riesgos");
        final Page<Riesgo> paginatedRiesgos = riesgoService.getPaginatedRiesgos(pageNumber, pageSize);
        searchDTO.setCurrentUser(userService.getCurrentUser());
        searchDTO.setOnlyOneUser(userService.getAllUsers().size()==1);
        searchDTO.setMatrizDeRiesgo(riesgoService.getMatrizDeRiesgo());
        mav.addObject("searchDTO", getResponseDto(paginatedRiesgos, pageNumber, searchDTO));
        return mav;
    }



	@RequestMapping(value = "/search/{page}/{page-size}", method = RequestMethod.POST)
    public ModelAndView search(@PathVariable(name = "page") final int pageNumber,
            @PathVariable(name = "page-size") final int pageSize,@ModelAttribute (value = "searchDTO") RiesgoSearchDTO searchDTO, 
            BindingResult bindingResult) {
        LOGGER.debug("---Search------- con DTO :" + searchDTO);
        searchDTO.setCurrentUser(userService.getCurrentUser());
        final Page<Riesgo> paginatedRiesgos = riesgoService.getPaginatedRiesgos(pageNumber, pageSize, searchDTO);
        return new ModelAndView("riesgos", "searchDTO", getResponseDto(paginatedRiesgos, pageNumber, searchDTO));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView getCreatePage(RiesgoDTO objectDTO, BindingResult bindingResult) {
		 LOGGER.debug("-------Le peuge a main page------------");

	        //Genero el DTO
	        RiesgoDTO riesgoDTO = new RiesgoDTO();
	        configAltaScreen(riesgoDTO);
	        Riesgo riesgo = new Riesgo();
	        riesgoDTO.setFechaAnalisis(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
	        riesgoDTO.setRiesgo(riesgo);
	        List<Proyecto> proyectos = new ArrayList<Proyecto>();
	        riesgoDTO.getRiesgo().setProyectos(proyectos);
	        LOGGER.info("Cree el siguiente dto para operar : " + riesgoDTO);


	        //Preparo el moddel and view
	        ModelAndView mav = new ModelAndView("riesgo_create");
	        mav.addObject("riesgoDTO", riesgoDTO);
	        return mav;
	}


	@RequestMapping(value = "/save", params = {"guardar"})
	public ModelAndView save(RiesgoDTO riesgoDTO, BindingResult bindingResult) {
		LOGGER.info("-----Entre al save de Riesgos------");
		riesgoDTO.getRiesgo().setFechaAnalisis(FormatUtils.getFormatedLocalDate(riesgoDTO.getFechaAnalisis()));
		//Solo cargo el usuario creador al momento de la creacion del riesgo.
		if(riesgoDTO.getRiesgo().getId()==null) {
			riesgoDTO.getRiesgo().setUsuarioCreador(userService.getCurrentUser());
		}
		if(riesgoDTO.getProyecto()!=null) {
			//Asumo que solo puede tener asignado un solo proyecto
			if(riesgoDTO.getRiesgo().getId()!=null) {
				riesgoService.removeProyectos(riesgoDTO.getRiesgo().getId());
			}
			riesgoDTO.getProyecto().getRiesgos().add(riesgoDTO.getRiesgo());
			proyectoService.saveOrUpdate(riesgoDTO.getProyecto()); //por cascada tambien se salva el Riesgo

		}else {//Viene sin proyecto asociado
			if(riesgoDTO.getProyectoCopy()!=null) {//si ya tenia proyecto asociado lo elimino, asumo que solo tiene uno
				riesgoDTO.getProyectoCopy().getRiesgos().clear();
				proyectoService.saveOrUpdate(riesgoDTO.getProyectoCopy()); //por cascade tambien se actualiza el riesgo
				riesgoDTO.setProyectoCopy(null);
				
			}else {
				riesgoDTO.setRiesgo(riesgoService.saveOrUpdate(riesgoDTO.getRiesgo())); 
			}
		}
		
		
		
		riesgoDTO.setAmenazas(amenazaRepo.findByOrigenIdOrderByTipoAsc(riesgoDTO.getOrigenAmenaza().getId()));
		//Si hay un solo usuario o el usuario es distinto del creador dejo que el mismo lo apruebe
		riesgoDTO.setAprobacion(userService.getAllUsers().size()==1 || !isTheSameUser(riesgoDTO.getRiesgo().getUsuarioCreador()) );
        ModelAndView mav = new ModelAndView("riesgo_create");
        mav.addObject("riesgoDTO", riesgoDTO);
        return mav;
	}
	
	private boolean isTheSameUser(User user) {
		return user.getEmail().equals(userService.getCurrentUser().getEmail());
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
        	 riesgoDTO.setAmenazas(amenazaRepo.findByOrigenIdOrderByTipoAsc(riesgoDTO.getOrigenAmenaza().getId()));
        }
        mav.addObject("riesgoDTO", riesgoDTO);
        return mav;
    }

    @RequestMapping(value = "/save", params = {"aprobar"})
    public ModelAndView approve(@ModelAttribute(value = "riesgoDTO") RiesgoDTO riesgoDTO,
            BindingResult result) {
        LOGGER.debug("--------------------Entre al Aprobar------------------");
        LOGGER.info("-----Entre al save de Riesgos------");
		riesgoDTO.getRiesgo().setFechaAprobacion(FormatUtils.getFormatedLocalDate(riesgoDTO.getFechaAnalisis()));
		riesgoDTO.getRiesgo().setUsuarioAuditor(userService.getCurrentUser());
		riesgoDTO.getRiesgo().setFechaAnalisis(FormatUtils.getFormatedLocalDate(riesgoDTO.getFechaAnalisis()));
		riesgoDTO.setRiesgo(riesgoService.saveOrUpdate(riesgoDTO.getRiesgo()));
		riesgoDTO.setAmenazas(amenazaRepo.findByOrigenIdOrderByTipoAsc(riesgoDTO.getOrigenAmenaza().getId()));
		riesgoDTO.setAprobacion(false);
        ModelAndView mav = new ModelAndView("riesgo_view");
        mav.addObject("riesgoDTO", riesgoDTO);
        return mav;
    }


   
    @RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable Long id) {
		Riesgo riesgoABorrar = riesgoService.getById(id);
		riesgoService.delete(riesgoABorrar);
        return "redirect:/riesgos/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
	}


    @RequestMapping(value = "/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
        LOGGER.info("Estoy en edit este es el id " + id);
        Riesgo riesgoAEditar = riesgoService.getById(id);
        RiesgoDTO dto = new RiesgoDTO(riesgoAEditar);
        dto.configEditScreen();
        dto.setFechaAnalisis(riesgoAEditar.getFechaAnalisis().format(DateTimeFormatter.ISO_LOCAL_DATE));
        if(riesgoAEditar.getAmenaza()!=null) {
        	dto.setOrigenAmenaza(riesgoAEditar.getAmenaza().getOrigen());
        	dto.setAmenazas(amenazaRepo.findByOrigenIdOrderByTipoAsc(dto.getRiesgo().getAmenaza().getOrigen().getId()));
        }
        //asumo que tiene asignado un solo proyecto
        if(!riesgoAEditar.getProyectos().isEmpty()) {
        	dto.setProyecto(riesgoAEditar.getProyectos().get(0));
        }
        //habilito la aprobacion si hay un solo usuario o es distinto al que lo creo
        dto.setAprobacion(userService.getAllUsers().size()==1 || !userService.isCurrentUser(riesgoAEditar.getUsuarioCreador()));
        LOGGER.info("DTO a editar "+dto);
        return new ModelAndView("riesgo_create", "riesgoDTO", dto);
	}
  
	@RequestMapping(value = "/view/{id}")
	public ModelAndView view(@PathVariable Long id) {
        LOGGER.info("Estoy en view este es el id " + id);
        Riesgo riesgoAEditar = riesgoService.getById(id);
        RiesgoDTO dto = new RiesgoDTO(riesgoAEditar);
        dto.configEditScreen();
        dto.setFechaAnalisis(riesgoAEditar.getFechaAnalisis().format(DateTimeFormatter.ISO_LOCAL_DATE));
        if(riesgoAEditar.getAmenaza()!=null) {
        	dto.setOrigenAmenaza(riesgoAEditar.getAmenaza().getOrigen());
        	dto.setAmenazas(amenazaRepo.findByOrigenIdOrderByTipoAsc(dto.getRiesgo().getAmenaza().getOrigen().getId()));
        }
        LOGGER.info("DTO a ver "+dto);
        return new ModelAndView("riesgo_view", "riesgoDTO", dto);
	}
	
	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(GregorianCalendar.getInstance().getTime());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<Riesgo> riesgos = riesgoService.findAll();
         
        RiesgoExcelExporter excelExporter = new RiesgoExcelExporter(riesgos);
         
        excelExporter.export(response);    

	}  
	
	 
	
	@ModelAttribute("dataMaster")
	public DataMaster getDataMaster() {
		System.out.println("--Me meti en el data master--");
		return dataMaster;
	}
	

	
	@RequestMapping(value = "/audit/{id}")
    ModelAndView audit(@PathVariable Long id) {
		LOGGER.info("Estoy en audit este es el id " + id);
		return new ModelAndView("riesgo_audit", "auditRiesgosDTO", riesgoService.getAllAuditForId(id));
	}
	

	   
	   /*
	    * Se spuede modificar, no se puede guardar y no esta procesado
	    */
	    public void configAltaScreen(RiesgoDTO riesgoDTO) {
	    	riesgoDTO.setModificable(true);
	    	riesgoDTO.setProcesado(false);
	    	riesgoDTO.setReadOnly(false);
	    	riesgoDTO.setAmenazas((List<Amenaza>) amenazaRepo.findAll());
	    	riesgoDTO.setAprobacion(false);

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

				riesgo.getSalvaguarda().setProbabilidadFinal(dataMaster.getProbabilidadDeOcurrenciaById( menorProbabilidad.longValue()));
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

				riesgo.getSalvaguarda().setImpactoFinal(dataMaster.getImpactoById(menorImpacto.longValue()));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
			
		}
		

		private void getRiesgoResidual(Riesgo riesgo) {
			
			try {
				Integer riesgoResidual = riesgo.getSalvaguarda().getImpactoFinal().getValor() * riesgo.getSalvaguarda().getProbabilidadFinal().getValor();
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
