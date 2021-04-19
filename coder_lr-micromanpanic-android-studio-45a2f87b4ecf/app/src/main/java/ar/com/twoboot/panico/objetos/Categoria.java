/***********************************************************************
 * Module:  Categorias.java
 * Author:  Sebastian
 * Purpose: Defines the Class Categorias
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import java.io.Serializable;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 0cb02ae0-d4e5-4921-9dfd-2c8a96716e47 */
public class Categoria extends DalusObject implements Serializable {
	/** @pdOid c0ce241d-ccf3-4df9-a6ca-26c48f0b4ee4 */
	private java.lang.String codCategoria;
	/** @pdOid f3d8ed46-0323-4e98-af05-2666845aa6b2 */
	private java.lang.String caterogia;

	@Override
	public String toString() {
		return caterogia;
	}

	/**
	 * @pdRoleInfo migr=no name=Preguntas assc=categoriaPreguntas
	 *             coll=java.util.List impl=java.util.ArrayList mult=0..*
	 */
	private java.util.List<Pregunta> preguntas;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCategoria == null) ? 0 : codCategoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codCategoria == null) {
			if (other.codCategoria != null)
				return false;
		} else if (!codCategoria.equals(other.codCategoria))
			return false;
		return true;
	}

	/** @pdOid 8cf0daa9-3ed2-4beb-9267-a69c2f027bb5 */
	public java.lang.String getCodCategoria() {
		return codCategoria;
	}

	/**
	 * @param newCodCategoria
	 * @pdOid 40009735-f04c-4a6a-8e3f-2c4014201aab
	 */
	public void setCodCategoria(java.lang.String newCodCategoria) {
		codCategoria = newCodCategoria;
	}

	/** @pdOid f35cce55-c70f-4afe-aca0-493af3f07dc7 */
	public java.lang.String getCaterogia() {
		return caterogia;
	}

	/**
	 * @param newCaterogia
	 * @pdOid 7bb11ad2-2d24-4730-8aa6-c057d35438a6
	 */
	public void setCaterogia(java.lang.String newCaterogia) {
		caterogia = newCaterogia;
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
			newPreguntas.setCategorias(this);
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
				oldPreguntas.setCategorias((Categoria) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllPreguntas() {
		if (preguntas != null) {
			Pregunta oldPreguntas;
			for (java.util.Iterator iter = getIteratorPreguntas(); iter.hasNext();) {
				oldPreguntas = (Pregunta) iter.next();
				iter.remove();
				oldPreguntas.setCategorias((Categoria) null);
			}
		}
	}

}