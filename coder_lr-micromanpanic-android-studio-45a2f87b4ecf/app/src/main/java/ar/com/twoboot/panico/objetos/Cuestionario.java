/***********************************************************************
 * Module:  Cuestionario.java
 * Author:  Sebastian
 * Purpose: Defines the Class Cuestionario
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import java.io.Serializable;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid b73e1a14-56c8-421f-8e95-2ecc27f782a7 */
public class Cuestionario extends DalusObject implements Serializable {
	/** @pdOid 856415ad-5994-4b3b-87d9-2e7d8e1f0736 */
	private long idCuestionario;
	private long idCuestionarioRemoto;
	/** @pdOid e0897a35-b9d7-48e4-81c9-c066a65388f5 */
	private double gpsLatitud;
	/** @pdOid bab48254-047b-4e6d-99e3-21a3bab03a3c */
	private double gpsLongitud;
	/** @pdOid 26547ac5-4b87-4e8c-80b2-41c650ef38a8 */
	private int estado;
	/** @pdOid 9171b173-d825-4300-b9c8-2eb3c798281f */
	private int idUsuario;
	/** @pdOid b7f3167d-cdbc-4203-92bd-b1981ea2ade4 */
	private java.util.Date fechaCarga;
	private String altura;
	private java.util.List cuestionarioPreguntas;
	/** @pdRoleInfo migr=no name=Barrios assc=cuestionarioBarrio mult=0..1 side=A */
	private Barrio barrios;
	/** @pdRoleInfo migr=no name=Calles assc=cuestionarioCalle mult=0..1 side=A */
	private Calle calles;
	private String fotoArchivo;

	public java.util.List getCuestionarioPreguntas() {
		return cuestionarioPreguntas;
	}

	public Barrio getBarrios() {
		return barrios;
	}

	public void setBarrios(Barrio barrios) {
		this.barrios = barrios;
	}

	public Calle getCalles() {
		return calles;
	}

	public void setCalles(Calle calles) {
		this.calles = calles;
	}

	/** @pdOid c583e857-1f89-4495-9bf7-3df627c4f449 */
	public long getIdCuestionario() {
		return idCuestionario;
	}

	/**
	 * @param newIdCuestionario
	 * @pdOid b3e9dbe1-a989-4cd4-8794-7a0d019b4daf
	 */
	public void setIdCuestionario(long newIdCuestionario) {
		idCuestionario = newIdCuestionario;
	}

	/** @pdOid b8fd0de9-2bec-4c8e-86b5-759de32d34d9 */
	public double getGpsLatitud() {
		return gpsLatitud;
	}

	/**
	 * @param newGpsLatitud
	 * @pdOid de20a51b-962d-4d92-9e63-3f75a1c5c653
	 */
	public void setGpsLatitud(double newGpsLatitud) {
		gpsLatitud = newGpsLatitud;
	}

	/** @pdOid dce3cf67-c3f7-4088-9bc0-214178d14b48 */
	public double getGpsLongitud() {
		return gpsLongitud;
	}

	/**
	 * @param newGpsLongitud
	 * @pdOid 88ab4c6d-9b99-4996-a8a5-fa415c9987fd
	 */
	public void setGpsLongitud(double newGpsLongitud) {
		gpsLongitud = newGpsLongitud;
	}

	/** @pdOid f12c072d-1c41-434d-af3e-3ef4f1c3dc3e */

	/** @pdOid 156b248a-0c90-4596-8282-242745b3de2f */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 9e40285a-2e6b-4f25-ac83-ce5e62822167
	 */
	public void setEstado(int newEstado) {
		estado = newEstado;
	}

	/** @pdOid 44df235b-7853-4066-8cb5-5d40b3a6f60a */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 38dd8769-3f56-4a55-83c9-810a92e4bba4
	 */
	public void setIdUsuario(int newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid c4d53630-d9eb-4d6e-bbe0-fd8e78bfea17 */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid 31d23e0e-836d-4b8c-8f8a-9810bcf57115
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	public void setCuestionarioPreguntas(java.util.List<CuestionarioPreguntas> newCuestionarioPreguntas) {
		removeAllCuestionarioPreguntas();
		for (java.util.Iterator iter = newCuestionarioPreguntas.iterator(); iter.hasNext();)
			addCuestionarioPreguntas((CuestionarioPreguntas) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newCuestionarioPreguntas
	 */
	public void addCuestionarioPreguntas(CuestionarioPreguntas newCuestionarioPreguntas) {
		if (newCuestionarioPreguntas == null)
			return;
		if (this.cuestionarioPreguntas == null)
			this.cuestionarioPreguntas = new java.util.ArrayList<CuestionarioPreguntas>();
		if (!this.cuestionarioPreguntas.contains(newCuestionarioPreguntas)) {
			this.cuestionarioPreguntas.add(newCuestionarioPreguntas);
			newCuestionarioPreguntas.setCuestionario(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldCuestionarioPreguntas
	 */
	public void removeCuestionarioPreguntas(CuestionarioPreguntas oldCuestionarioPreguntas) {
		if (oldCuestionarioPreguntas == null)
			return;
		if (this.cuestionarioPreguntas != null)
			if (this.cuestionarioPreguntas.contains(oldCuestionarioPreguntas)) {
				this.cuestionarioPreguntas.remove(oldCuestionarioPreguntas);
				oldCuestionarioPreguntas.setCuestionario((Cuestionario) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllCuestionarioPreguntas() {
		if (cuestionarioPreguntas != null) {
			CuestionarioPreguntas oldCuestionarioPreguntas;
			for (java.util.Iterator iter = cuestionarioPreguntas.iterator(); iter.hasNext();) {
				oldCuestionarioPreguntas = (CuestionarioPreguntas) iter.next();
				iter.remove();
				oldCuestionarioPreguntas.setCuestionario((Cuestionario) null);
			}
		}
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getFotoArchivo() {
		return fotoArchivo;
	}

	public void setFotoArchivo(String fotoArchivo) {
		this.fotoArchivo = fotoArchivo;
	}

	public long getIdCuestionarioRemoto() {
		return idCuestionarioRemoto;
	}

	public void setIdCuestionarioRemoto(long idCuestionarioRemoto) {
		this.idCuestionarioRemoto = idCuestionarioRemoto;
	}

}