package com.gestionit.base.domain;

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
@Table(name="riesgo_inherente_valor")
@Audited
public class RiesgoInherenteValor  {
	

	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;
		
		private Integer valor;
		
		@OneToOne
	    @JoinColumn(name="fk_riesgo_inherente")
		@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
		private RiesgoInherente riesgoInherente;

	public Integer getValor() {
			return valor;
		}

		public void setValor(Integer valor) {
			this.valor = valor;
		}

		public RiesgoInherente getRiesgoInherente() {
			return riesgoInherente;
		}

		public void setRiesgoInherente(RiesgoInherente riesgoInherente) {
			this.riesgoInherente = riesgoInherente;
		}



}
