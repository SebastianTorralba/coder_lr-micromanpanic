/***********************************************************************
 * Module:  Calles.java
 * Author:  Sebastian
 * Purpose: Defines the Class Calles
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 4176ebdc-2402-4415-a5a2-f12489903512 */
public class Calle extends DalusObject {
	/** @pdOid be7f1572-de88-404f-85f3-3adf925b64b8 */
	private int idCalle;
	/** @pdOid d7571903-a57b-4bf4-9d3a-ab3e129689ec */
	private java.lang.String nombre;
	/** @pdOid fd9d5dcb-d42a-4dbf-8457-6b5109c100d1 */
	private int estado;
	/** @pdOid d29c4b1c-20f0-4d3c-9659-c98b87e3ae62 */
	private int idUsuario;
	/** @pdOid 595c8645-00f1-49ab-b912-177ea2207c2c */
	private java.util.Date fechaCarga;

	private java.util.List callejeros;

	/** @pdRoleInfo migr=no name=Cuestionario assc=cuestionarioCalle mult=0..1 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre;
	}

	/** @pdOid 6152c25d-a056-487b-8e14-46c235dc5aa1 */
	public int getIdCalle() {
		return idCalle;
	}

	/**
	 * @param newIdCalle
	 * @pdOid 3c6cfc7f-afbc-49d0-9851-e67b58dde79c
	 */
	public void setIdCalle(int newIdCalle) {
		idCalle = newIdCalle;
	}

	/** @pdOid 0ea955ce-cab1-4184-bb57-b725b05cbed8 */
	public java.lang.String getNombre() {
		return nombre;
	}

	/**
	 * @param newNombre
	 * @pdOid 81b83ff5-5129-44b9-8646-b64abafd6e35
	 */
	public void setNombre(java.lang.String newNombre) {
		nombre = newNombre;
	}

	/** @pdOid d7b9f652-37c2-47e2-a947-a2cda3f17852 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 0182efa1-82fd-47ee-aa9b-488b0e06b454
	 */
	public void setEstado(int newEstado) {
		estado = newEstado;
	}

	/** @pdOid 0e5c5015-bfb5-421d-9ccd-c420a5943ca4 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 2776780b-8a57-47e9-9152-fbec2fea54cf
	 */
	public void setIdUsuario(int newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid 5819ef0f-2448-40ac-aeab-8d4b779543cf */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid 059fe9e2-ebdb-4458-a03c-43a7e7b028e3
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	public java.util.List getCallejeros() {
		return callejeros;
	}

	public void setCallejeros(java.util.List callejeros) {
		this.callejeros = callejeros;
	}

}