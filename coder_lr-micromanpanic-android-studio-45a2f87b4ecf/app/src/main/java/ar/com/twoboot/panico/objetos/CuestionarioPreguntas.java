/***********************************************************************
 * Module:  CuestionarioPreguntas.java
 * Author:  Sebastian
 * Purpose: Defines the Class CuestionarioPreguntas
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 4f32d920-fe5d-42f6-9064-8029002322fd */
public class CuestionarioPreguntas extends DalusObject {
	/** @pdOid 8162ade6-0358-48b2-b831-a5ffb59b84e4 */
	private Respuesta respuesta;
	/** @pdOid b5d3b3d0-1b07-42be-a17d-908959f88161 */
	private java.lang.String extension;

	private Cuestionario cuestionario;
	private Pregunta pregunta;

	/** @pdOid 83986229-36d7-4ba3-b3f6-6d6ab7b2d4fd */

	/**
	 * @param newCodRespuesta
	 * @pdOid 77376cf9-0f43-40d8-afde-764b066d3227
	 */

	/** @pdOid 829e4492-51a1-4a6b-98c4-b069ae3fe44b */
	public java.lang.String getExtension() {
		return extension;
	}

	/**
	 * @param newExtension
	 * @pdOid c2c50aad-ef88-4851-9950-52ba63e47bb5
	 */
	public void setExtension(java.lang.String newExtension) {
		extension = newExtension;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Cuestionario getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(Cuestionario cuestionario) {
		this.cuestionario = cuestionario;
	}

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

}