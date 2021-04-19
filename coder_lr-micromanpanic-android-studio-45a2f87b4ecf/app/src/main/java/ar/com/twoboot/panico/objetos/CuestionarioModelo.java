/***********************************************************************
 * Module:  CuestionarioModelo.java
 * Author:  Sebastian
 * Purpose: Defines the Class CuestionarioModelo
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 924cf9c4-a210-446e-9ba1-f4020da82879 */
public class CuestionarioModelo extends DalusObject {
	/** @pdOid de983fcf-3fad-4978-9984-0c536fe5c2c0 */
	private int secuencia;

	/** @pdRoleInfo migr=no name=Preguntas assc=modeloPreguntas mult=0..1 side=A */
	private Pregunta preguntas;

	/** @pdOid afe0097b-248d-43ee-b538-07ce0b175bfc */
	public int getSecuencia() {
		return secuencia;
	}

	/**
	 * @param newSecuencia
	 * @pdOid eb8ea96c-15e5-4d09-b89f-c688f0f98a8e
	 */
	public void setSecuencia(int newSecuencia) {
		secuencia = newSecuencia;
	}

	/** @pdGenerated default parent getter */
	public Pregunta getPreguntas() {
		return preguntas;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newPreguntas
	 */
	public void setPreguntas(Pregunta newPreguntas) {
		this.preguntas = newPreguntas;

	}

}