package com.gestionit.base.domain.dto;


import org.hibernate.envers.RevisionType;

import com.gestionit.base.domain.MyRevision;
import com.gestionit.base.domain.Riesgo;


public class RiesgoAuditDTO extends RiesgoDTO {

	private MyRevision revision;
	
	private RevisionType revisionType;

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

}
