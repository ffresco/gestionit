package com.gestionit.base.domain.dto;


import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.MethodUtils;
import org.hibernate.envers.RevisionType;

import com.gestionit.base.domain.MyRevision;
import com.gestionit.base.domain.Riesgo;


public class RiesgoAuditDTO extends RiesgoDTO {

	private MyRevision revision;
	
	private RevisionType revisionType;
	
	private Riesgo previousVersion; //como su nombre lo dice representa la version anterior
	
	private Riesgo nextVersion;

	public RiesgoAuditDTO() {
		super();
		
	}

	public RiesgoAuditDTO(Riesgo riesgo) {
		super(riesgo);
		
	}
	
	public RiesgoAuditDTO(Riesgo riesgo, MyRevision revision, RevisionType revisionType ) {
		super(riesgo);
		this.revision = revision;
		this.revisionType = revisionType;
		
	} 

	public MyRevision getRevision() {
		return revision;
	}

	public void setRevision(MyRevision revision) {
		this.revision = revision;
	}

	public RevisionType getRevisionType() {
		return revisionType;
	}

	public void setRevisionType(RevisionType revisionType) {
		this.revisionType = revisionType;
	}
	
	
    public String getOperacion() {


		switch ( revisionType.getRepresentation() ) {
			case 0: {
				return "Creacion";
			}
			case 1: {
				return "Modificacion";
			}
			case 2: {
				return "Borrado";
			}
			default: {
				throw new IllegalArgumentException( "Unknown representation: " + revisionType.getRepresentation() );
			}
		}
	}

	public Riesgo getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(Riesgo previousVersion) {
		this.previousVersion = previousVersion;
	}
	
	public Riesgo getNextVersion() {
		return nextVersion;
	}

	public void setNextVersion(Riesgo nextVersion) {
		this.nextVersion = nextVersion;
	}
	
	public String getDiffAmenaza() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAmenaza", "getOrigen");
	}

	public String getDiffResponsable() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getResponsable");
	}
	
	public String getDiffFechaAnalisis() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getFechaAnalisis");
		
	}
	
	public String getDiffDescripcion() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getDescripcion");
		
	}
	
	public String getDiffProyecto() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Boolean ok = false;
		if(previousVersion!=null) {
			//Esto me devuelve true si los dos proyectos son iguales o los riesgos no tienen proyecto asociado
			ok = (previousVersion.getProyectos().size()>0 && this.getRiesgo().getProyectos().size() > 0 && diffProyecto(previousVersion)) || 
				(previousVersion.getProyectos().isEmpty() && this.getRiesgo().getProyectos().isEmpty())	;
		}
		
		Boolean ok2 = false;
		//Esto me devuelve true si los dos proyectos son iguales o los riesgos no tienen proyecto asociado
		if(nextVersion!=null) {
			ok2 = (nextVersion.getProyectos().size()>0 && this.getRiesgo().getProyectos().size() > 0 && diffProyecto(nextVersion)) || 
				(nextVersion.getProyectos().isEmpty() && this.getRiesgo().getProyectos().isEmpty())	;
		}
		
		return  (ok || ok2 )?":":"bg-danger";
		
	}
	
	public String getDiffRiesgoInherente() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getRiesgoInherenteValor", "getRiesgoInherente", "getCalificacion");
		
	}
	
	public String getDiffRiesgoResidual() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getRiesgoResidualValor", "getRiesgoResidual", "getCalificacion");
		
	}
	
	public String getDiffdiffOrigenAmenaza() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAmenaza", "getOrigen", "getOrigen");
		
	}
	
	
	public String getDiffImpacto() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getImpacto", "getCalificacion");
		
	}
	
	public String getDiffTipoAmenaza() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAmenaza", "getTipo");
		
	}
	
	
	
	public String getDiffProbabilidadOcurrencia() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getProbabilidadOcurrencia", "getCalificacion");
		
	}

	private String diff(String methodName, String secondMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Boolean ok = diffPrevious(methodName, secondMethodName);
		Boolean ok2 = diffNext(methodName, secondMethodName);
		
		return  (ok || ok2 )?"bg-danger":"";
		
	}

	
	private String diff(String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Boolean ok = diffPrevious(methodName);
		Boolean ok2 = diffNext(methodName);
		
		return  (ok || ok2 )?"bg-danger":"";
		
	}
	
	public String getCodigoFormulario() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getCodigoFormulario");
	}
	
	public String getDiffAfectaDisponibilidad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAfectaDisponibilidad");
	}
	
	public String getDiffAfectaIntegridad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAfectaIntegridad");
	}
	
	public String getDiffAfectaConfidencialidad() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getAfectaConfidencialidad");
	}
	
	public String getDiffFormulario() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return diff("getCodigoFormulario");
	}
	
	
	private String diff(String methodName, String secondMethodName, String thirdMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Boolean ok = diffPrevious(methodName, secondMethodName, thirdMethodName);
		Boolean ok2 = diffNext(methodName, secondMethodName, thirdMethodName);
		
		return  (ok || ok2 )?"bg-danger":"";
		
	}
	
	//Asumo que riesgo tiene proyecto
	private Boolean diffProyecto(Riesgo riesgo) {
		return !previousVersion.getProyectos().isEmpty() && previousVersion.getProyectos().get(0).equals(riesgo.getProyectos().get(0));
	}
	
	private Boolean diffPrevious(String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return previousVersion!=null && !MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null).equals(MethodUtils.invokeExactMethod(previousVersion, methodName, null));
	}
	
	private Boolean diffNext(String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return nextVersion!=null && !MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null).equals(MethodUtils.invokeExactMethod(nextVersion, methodName, null));
	}
	
	private Boolean diffPrevious(String methodName, String secondMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(previousVersion!=null) {
			Object object = MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null);
			Object result = MethodUtils.invokeExactMethod(object, secondMethodName, null);
			Object previousObject = MethodUtils.invokeExactMethod(previousVersion, methodName, null);
			Object resultPrevious =  MethodUtils.invokeExactMethod(previousObject, secondMethodName, null);
			return !result.equals(resultPrevious);
		}else {
			return false;
		}
	
	}
	
	private Boolean diffNext(String methodName, String secondMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(nextVersion!=null) {
			Object object = MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null);
			Object result =  MethodUtils.invokeExactMethod(object, secondMethodName, null);
			Object nextObject = MethodUtils.invokeExactMethod(nextVersion, methodName, null);
			Object resultPrevious = MethodUtils.invokeExactMethod(nextObject, secondMethodName, null);
			return !result.equals(resultPrevious);
		}else {
			return false;
		}
	}
	
	private Boolean diffPrevious(String methodName, String secondMethodName, String thirdMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(previousVersion!=null) {
			Object object = MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null);
			object = MethodUtils.invokeExactMethod(object, secondMethodName, null);
			Object result = MethodUtils.invokeExactMethod(object, thirdMethodName, null);
			Object previousObject = MethodUtils.invokeExactMethod(previousVersion, methodName, null);
			previousObject = MethodUtils.invokeExactMethod(previousObject, secondMethodName, null);
			Object resultPrevious =  MethodUtils.invokeExactMethod(previousObject, thirdMethodName, null);
			return !result.equals(resultPrevious);
		}else {
			return false;
		}
	
	}
	
	private Boolean diffNext(String methodName, String secondMethodName, String thirdMethodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if(nextVersion!=null) {
			Object object = MethodUtils.invokeExactMethod(this.getRiesgo(), methodName, null);
			object = MethodUtils.invokeExactMethod(object, secondMethodName, null);
			Object result =  MethodUtils.invokeExactMethod(object, thirdMethodName, null);
			Object nextObject = MethodUtils.invokeExactMethod(nextVersion, methodName, null);
			nextObject = MethodUtils.invokeExactMethod(nextObject, secondMethodName, null);
			Object resultPrevious = MethodUtils.invokeExactMethod(nextObject, thirdMethodName, null);
			return !result.equals(resultPrevious);
		}else {
			return false;
		}
	}
}
