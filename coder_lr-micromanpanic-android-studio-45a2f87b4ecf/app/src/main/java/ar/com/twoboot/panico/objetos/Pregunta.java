/***********************************************************************
 * Module:  Preguntas.java
 * Author:  Sebastian
 * Purpose: Defines the Class Preguntas
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid d828c6e2-20c3-4e0d-a3b8-b5032ef9fdd8 */
public class Pregunta extends DalusObject {
	/** @pdOid dacd2a69-97ec-4401-9fda-5fed19978985 */
	private int idPregunta;
	/** @pdOid a31f6045-a570-4b24-848d-e6a8dce6889f */
	private java.lang.String pregunta;
	/** @pdOid 776d26c4-cbd3-4086-b863-ce6804b0f054 */
	private int esExtendible;

	/**
	 * @pdRoleInfo migr=no name=Respuestas assc=preguntaRespuestas
	 *             coll=java.util.ArrayList mult=0..*
	 */
	private java.util.ArrayList<Respuesta> respuestasPregunta;
	/**
	 * @pdRoleInfo migr=no name=CuestionarioModelo assc=modeloPreguntas
	 *             coll=java.util.ArrayList impl=java.util.ArrayList mult=0..*
	 */
	private CuestionarioPreguntas cuestionarioPreguntas;
	/**
	 * @pdRoleInfo migr=no name=Categorias assc=categoriaPreguntas mult=0..1 side=A
	 */
	private Categoria categorias;
	/**
	 * @pdRoleInfo migr=no name=TipoRepuesta assc=tiporespuestasPreguntas mult=0..1
	 *             side=A
	 */
	private TipoRespuesta tipoRepuesta;
	/**
	 * @pdRoleInfo migr=no name=Respuestas assc=respuestasPregunta mult=0..1 side=A
	 */
	private Respuesta respuestaDefecto;

	/** @pdOid f1248e12-7e25-4d54-9a24-69436576f7f0 */
	public int getIdPregunta() {
		return idPregunta;
	}

	/**
	 * @param newIdPregunta
	 * @pdOid f71ad64b-6693-46e1-b37f-8b35cd4053e6
	 */
	public void setIdPregunta(int newIdPregunta) {
		idPregunta = newIdPregunta;
	}

	/** @pdOid 5bb713de-8242-4e5d-ab26-982b668d3dc4 */
	public java.lang.String getPregunta() {
		return pregunta;
	}

	/**
	 * @param newPregunta
	 * @pdOid 60632531-1696-4843-a584-cb537240e766
	 */
	public void setPregunta(java.lang.String newPregunta) {
		pregunta = newPregunta;
	}

	/** @pdOid 5c525b25-8c9e-4249-b7ea-916f67071331 */
	public int getEsExtendible() {
		return esExtendible;
	}

	/**
	 * @param newEsExtendible
	 * @pdOid f5eb0424-d8cd-41c7-895d-52b788882fbf
	 */
	public void setEsExtendible(int newEsExtendible) {
		esExtendible = newEsExtendible;
	}

	/** @pdGenerated default getter */
	public java.util.ArrayList<Respuesta> getrespuestasPregunta() {
		if (respuestasPregunta == null)
			respuestasPregunta = new java.util.ArrayList<Respuesta>();
		return respuestasPregunta;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorrespuestasPregunta() {
		if (respuestasPregunta == null)
			respuestasPregunta = new java.util.ArrayList<Respuesta>();
		return respuestasPregunta.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newrespuestasPregunta
	 */
	public void setrespuestasPregunta(java.util.ArrayList<Respuesta> newrespuestasPregunta) {
		removeAllrespuestasPregunta();
		for (java.util.Iterator iter = newrespuestasPregunta.iterator(); iter.hasNext();)
			addrespuestasPregunta((Respuesta) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newRespuestas
	 */
	public void addrespuestasPregunta(Respuesta newRespuestas) {
		if (newRespuestas == null)
			return;
		if (this.respuestasPregunta == null)
			this.respuestasPregunta = new java.util.ArrayList<Respuesta>();
		if (!this.respuestasPregunta.contains(newRespuestas))
			this.respuestasPregunta.add(newRespuestas);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldRespuestas
	 */
	public void removerespuestasPregunta(Respuesta oldRespuestas) {
		if (oldRespuestas == null)
			return;
		if (this.respuestasPregunta != null)
			if (this.respuestasPregunta.contains(oldRespuestas))
				this.respuestasPregunta.remove(oldRespuestas);
	}

	/** @pdGenerated default removeAll */
	public void removeAllrespuestasPregunta() {
		if (respuestasPregunta != null)
			respuestasPregunta.clear();
	}

	/** @pdGenerated default getter */

	/** @pdGenerated default parent getter */
	public Categoria getCategorias() {
		return categorias;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newCategorias
	 */
	public void setCategorias(Categoria newCategorias) {
		if (this.categorias == null || !this.categorias.equals(newCategorias)) {
			if (this.categorias != null) {
				Categoria oldCategorias = this.categorias;
				this.categorias = null;
				oldCategorias.removePreguntas(this);
			}
			if (newCategorias != null) {
				this.categorias = newCategorias;
				this.categorias.addPreguntas(this);
			}
		}
	}

	/** @pdGenerated default parent getter */
	public TipoRespuesta getTipoRepuesta() {
		return tipoRepuesta;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newTipoRepuesta
	 */
	public void setTipoRepuesta(TipoRespuesta newTipoRepuesta) {
		if (this.tipoRepuesta == null || !this.tipoRepuesta.equals(newTipoRepuesta)) {
			if (this.tipoRepuesta != null) {
				TipoRespuesta oldTipoRepuesta = this.tipoRepuesta;
				this.tipoRepuesta = null;
				oldTipoRepuesta.removePreguntas(this);
			}
			if (newTipoRepuesta != null) {
				this.tipoRepuesta = newTipoRepuesta;
				this.tipoRepuesta.addPreguntas(this);
			}
		}
	}

	/** @pdGenerated default parent getter */
	public Respuesta getRespuestaDefecto() {
		return respuestaDefecto;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newRespuestas
	 */
	public void setRespuestaDefecto(Respuesta newRespuestas) {
		this.respuestaDefecto = newRespuestas;

	}

}