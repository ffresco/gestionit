package com.gestionit.base.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Table(name="riesgo_residual_valor")
@Audited
public class RiesgoResidualValor implements Serializable {
	

	
	   /**
	 * 
	 */
	private static final long serialVersionUID = -7797282696995863121L;

	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;
		
		private Integer valor;
		
		@OneToOne
	    @JoinColumn(name="fk_riesgo_residual")
		@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
		private RiesgoResidual riesgoResidual;

		public Integer getValor() {
			return valor;
			}

		public void setValor(Integer valor) {
			this.valor = valor;
		}

		public RiesgoResidual getRiesgoResidual() {
			return riesgoResidual;
		}

		public void setRiesgoResidual(RiesgoResidual riesgoResidual) {
			this.riesgoResidual = riesgoResidual;
		}



}
