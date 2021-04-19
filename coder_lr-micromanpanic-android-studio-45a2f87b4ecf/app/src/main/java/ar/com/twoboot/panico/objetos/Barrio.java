/***********************************************************************
 * Module:  Barrios.java
 * Author:  Sebastian
 * Purpose: Defines the Class Barrios
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid b9ac2595-0392-4473-88cb-5466531cc984 */
public class Barrio extends DalusObject {
	/** @pdOid 3d22817a-cee5-4e5f-9b31-b5d599769b18 */
	private int idBarrio;
	/** @pdOid 8d2d5415-a4f7-4eb4-b1c1-c1cc0632e678 */
	private java.lang.String nombre;
	/** @pdOid 6c1243b1-7731-4664-a9fb-3e79b0bc1076 */
	private int estado;
	/** @pdOid 7c1596e4-eab4-4a78-a014-e1da42eac3f5 */
	private int idUsuario;
	/** @pdOid f28a467b-5daa-4815-8451-35e430bd7a76 */
	private java.util.Date fechaCarga;
	/** @pdOid 1dae3a14-4659-4b98-8625-5fd413378ebe */
	private float cantManzanas;
	/** @pdOid beb98b1e-0ace-4aa6-9012-0b9fee5ff5e0 */
	private int zona;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre;
	}

	/** @pdRoleInfo migr=no name=Cuestionario assc=cuestionarioBarrio mult=0..1 */
	private java.util.List callejeros;
	/**
	 * @pdRoleInfo migr=no name=Localidades assc=localidadBarrios mult=0..1 side=A
	 */
	private Localidad localidades;

	/** @pdOid 8039ca27-d037-4025-82ce-cec224e04e9e */
	public int getIdBarrio() {
		return idBarrio;
	}

	/**
	 * @param newIdBarrio
	 * @pdOid 10e846b7-fb53-4ab0-8281-200f922c370b
	 */
	public void setIdBarrio(int newIdBarrio) {
		idBarrio = newIdBarrio;
	}

	/** @pdOid cb470f99-bec3-490a-afd1-7d7f95568cc4 */
	public java.lang.String getNombre() {
		return nombre;
	}

	/**
	 * @param newNombre
	 * @pdOid 851cfca3-14ff-4c49-a03e-61552df1e6d4
	 */
	public void setNombre(java.lang.String newNombre) {
		nombre = newNombre;
	}

	/** @pdOid 10592fa0-5556-457d-a844-e1c188138717 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid ed03c568-3a85-4a9c-995c-def68fc03201
	 */
	public void setEstado(int newEstado) {
		estado = newEstado;
	}

	/** @pdOid c07798a3-3560-4e19-9e66-db022215a7ea */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 1995b89c-117d-49fe-b6cf-98ae7baaad68
	 */
	public void setIdUsuario(int newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid 0c57c06a-a264-4508-a6de-5f6abd785111 */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid 9a5d52d5-f871-405e-89f6-3fa9b3d451ea
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	/** @pdOid 0f560ddf-ce31-41f9-a4ad-ee7489b7d7f3 */
	public float getCantManzanas() {
		return cantManzanas;
	}

	/**
	 * @param newCantManzanas
	 * @pdOid 9b5f8e56-5283-4084-ae4b-599f9c15b569
	 */
	public void setCantManzanas(float newCantManzanas) {
		cantManzanas = newCantManzanas;
	}

	/** @pdOid 668805df-130d-4d78-9cdb-8ff2c0c05c21 */
	public int getZona() {
		return zona;
	}

	/**
	 * @param newZona
	 * @pdOid a9782959-5d9e-4473-886e-bc5d9ee49e49
	 */
	public void setZona(int newZona) {
		zona = newZona;
	}

	/** @pdGenerated default parent getter */
	public Localidad getLocalidades() {
		return localidades;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newLocalidades
	 */
	public void setLocalidades(Localidad newLocalidades) {
		if (this.localidades == null || !this.localidades.equals(newLocalidades)) {
			if (this.localidades != null) {
				Localidad oldLocalidades = this.localidades;
				this.localidades = null;
				oldLocalidades.removeBarrios(this);
			}
			if (newLocalidades != null) {
				this.localidades = newLocalidades;
				this.localidades.addBarrios(this);
			}
		}
	}

	public java.util.List getCallejeros() {
		return callejeros;
	}

	public void setCallejeros(java.util.List callejeros) {
		this.callejeros = callejeros;
	}

}