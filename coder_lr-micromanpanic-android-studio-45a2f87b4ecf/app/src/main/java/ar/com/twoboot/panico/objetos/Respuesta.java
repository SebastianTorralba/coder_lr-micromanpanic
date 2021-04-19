/***********************************************************************
 * Module:  Respuestas.java
 * Author:  Sebastian
 * Purpose: Defines the Class Respuestas
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 715ac20e-9db8-488e-aca7-a6576d32a264 */
public class Respuesta extends DalusObject {
	@Override
	public String toString() {
		return respuesta;
	}

	public Respuesta(String codRespuesta) {
		super();
		this.codRespuesta = codRespuesta;
	}

	public Respuesta() {
		// TODO Auto-generated constructor stub
	}

	/** @pdOid ea53cf80-8521-47e9-a4e8-efb9903f365f */
	private java.lang.String codRespuesta;
	/** @pdOid 47fc370d-ac1b-4d37-868b-4fb293914f20 */
	private java.lang.String respuesta;

	/**
	 * @pdRoleInfo migr=no name=Preguntas assc=respuestasPregunta
	 *             coll=java.util.List impl=java.util.ArrayList mult=0..*
	 */
	private java.util.List<Pregunta> preguntas;

	/** @pdOid 84f3f78f-1822-4fd8-8765-0c449a46587d */
	public java.lang.String getCodRespuesta() {
		return codRespuesta;
	}

	/**
	 * @param newCodRespuesta
	 * @pdOid 8990c920-6394-4117-9a90-ce2a394ec4f8
	 */
	public void setCodRespuesta(java.lang.String newCodRespuesta) {
		codRespuesta = newCodRespuesta;
	}

	/** @pdOid c06e21d1-081e-4323-ab12-73e135c1dee1 */
	public java.lang.String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param newRespuesta
	 * @pdOid ebf27cf6-54ce-4686-b11c-55101995b3b0
	 */
	public void setRespuesta(java.lang.String newRespuesta) {
		respuesta = newRespuesta;
	}

	/** @pdGenerated default getter */
	public java.util.List<Pregunta> getPreguntas() {
		if (preguntas == null)
			preguntas = new java.util.ArrayList<Pregunta>();
		return preguntas;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorPreguntas() {
		if (preguntas == null)
			preguntas = new java.util.ArrayList<Pregunta>();
		return preguntas.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newPreguntas
	 */

}