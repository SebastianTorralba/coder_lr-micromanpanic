/***********************************************************************
 * Module:  Callejeros.java
 * Author:  Sebastian
 * Purpose: Defines the Class Callejeros
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 52b92dfd-97be-4585-aebe-b57df43d4bb5 */
public class Callejero extends DalusObject {
	/** @pdOid f069b67a-dd9f-4e02-b06c-0085dbf22466 */
	private int estado;
	/** @pdOid b80a034f-28c2-4ee5-94e1-d0abd8d932f9 */
	private int idUsuario;
	/** @pdOid c5e96973-651c-4079-993e-22a3776ca223 */
	private java.util.Date fechaCarga;

	private Calle calle;
	private Barrio barrio;

	public Calle getCalle() {
		return calle;
	}

	public void setCalle(Calle calle) {
		this.calle = calle;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	/** @pdOid 5da7cab4-0734-459a-929b-feb84959ec78 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 6a4617c5-e34b-4298-bf7e-3e6c8d5c0f8f
	 */
	public void setEstado(int newEstado) {
		estado = newEstado;
	}

	/** @pdOid c25ae9e3-da84-412f-9d96-73ea96ac54a9 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 97730a12-2ddc-4042-8fae-28efa6b262e5
	 */
	public void setIdUsuario(int newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid 50355718-d301-4e86-9a28-3aa61225a850 */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid ff6e3933-3afc-46e7-a3db-b2fc05765874
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

}