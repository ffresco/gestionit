package com.gestionit.base.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="riesgo_residual_valor")
public class RiesgoResidualValor  {
	

	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;
		
		private Integer valor;
		
		@OneToOne
	    @JoinColumn(name="fk_riesgo_residual")
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
