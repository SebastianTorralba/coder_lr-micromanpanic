/***********************************************************************
 * Module:  TipoRepuesta.java
 * Author:  Sebastian
 * Purpose: Defines the Class TipoRepuesta
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 2ad97a2c-191a-4355-9cea-9b7cdb6f0c0d */
public class TipoRespuesta extends DalusObject {
	/** @pdOid 3231ae1d-b0f9-4a28-aa5b-ebff219bcdff */
	private java.lang.String codTipoRespuesta;
	/** @pdOid b974298b-65c7-465c-8a7d-76f2d869343d */
	private java.lang.String tipoRespuesta;

	/**
	 * @pdRoleInfo migr=no name=Preguntas assc=tiporespuestasPreguntas
	 *             coll=java.util.List impl=java.util.ArrayList mult=0..*
	 */
	public java.util.List<Pregunta> preguntas;

	/** @pdOid da0f69a9-623b-41c6-985b-90ef6e7a618f */
	public java.lang.String getCodTipoRespuesta() {
		return codTipoRespuesta;
	}

	/**
	 * @param newCodTipoRespuesta
	 * @pdOid bd9bb657-bc12-4d22-87b5-992d22016182
	 */
	public void setCodTipoRespuesta(java.lang.String newCodTipoRespuesta) {
		codTipoRespuesta = newCodTipoRespuesta;
	}

	/** @pdOid 974bafe4-52a5-4d85-857b-57951e4d0f49 */
	public java.lang.String getTipoRespuesta() {
		return tipoRespuesta;
	}

	/**
	 * @param newTipoRespuesta
	 * @pdOid a7ef5fc3-60ae-4a72-8cbf-d222877fa37e
	 */
	public void setTipoRespuesta(java.lang.String newTipoRespuesta) {
		tipoRespuesta = newTipoRespuesta;
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
	public void setPreguntas(java.util.List<Pregunta> newPreguntas) {
		removeAllPreguntas();
		for (java.util.Iterator iter = newPreguntas.iterator(); iter.hasNext();)
			addPreguntas((Pregunta) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newPreguntas
	 */
	public void addPreguntas(Pregunta newPreguntas) {
		if (newPreguntas == null)
			return;
		if (this.preguntas == null)
			this.preguntas = new java.util.ArrayList<Pregunta>();
		if (!this.preguntas.contains(newPreguntas)) {
			this.preguntas.add(newPreguntas);
			newPreguntas.setTipoRepuesta(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldPreguntas
	 */
	public void removePreguntas(Pregunta oldPreguntas) {
		if (oldPreguntas == null)
			return;
		if (this.preguntas != null)
			if (this.preguntas.contains(oldPreguntas)) {
				this.preguntas.remove(oldPreguntas);
				oldPreguntas.setTipoRepuesta((TipoRespuesta) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllPreguntas() {
		if (preguntas != null) {
			Pregunta oldPreguntas;
			for (java.util.Iterator iter = getIteratorPreguntas(); iter.hasNext();) {
				oldPreguntas = (Pregunta) iter.next();
				iter.remove();
				oldPreguntas.setTipoRepuesta((TipoRespuesta) null);
			}
		}
	}

}