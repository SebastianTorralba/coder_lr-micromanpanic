/***********************************************************************
 * Module:  Localidades.java
 * Author:  Sebastian
 * Purpose: Defines the Class Localidades
 ***********************************************************************/
package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 82566935-64e5-4fd3-a153-19e689627d1d */
public class Localidad extends DalusObject {
	/** @pdOid 1d6e86c3-3cd4-49de-aa98-513d68e47a2f */
	private int idLocalidad;
	/** @pdOid 749fd27f-8eed-403b-9889-7f94c7c278e8 */
	private java.lang.String localidad;
	/** @pdOid 90d17dc9-5e5a-4944-940f-d7b9c47e72a0 */
	private java.lang.String ccoddpto;
	/** @pdOid 4e5c8aa0-9a96-43db-bc33-135fae22f707 */
	private java.lang.String departamento;

	/**
	 * @pdRoleInfo migr=no name=Barrios assc=localidadBarrios coll=java.util.List
	 *             impl=java.util.ArrayList mult=0..*
	 */
	public java.util.List<Barrio> barrios;

	/** @pdOid b3218644-9a18-4a60-b2fd-568c24758bb1 */
	public int getIdLocalidad() {
		return idLocalidad;
	}

	/**
	 * @param newIdLocalidad
	 * @pdOid c73dbf24-aee9-4991-ac77-1d93582db844
	 */
	public void setIdLocalidad(int newIdLocalidad) {
		idLocalidad = newIdLocalidad;
	}

	/** @pdOid bc12773e-5760-4954-9232-e2ebd3591cc3 */
	public java.lang.String getLocalidad() {
		return localidad;
	}

	/**
	 * @param newLocalidad
	 * @pdOid 43e593d3-7997-4c54-80fe-adf921f5ef3f
	 */
	public void setLocalidad(java.lang.String newLocalidad) {
		localidad = newLocalidad;
	}

	/** @pdOid a8c39f45-0d22-4da2-8203-30dafa81abad */
	public java.lang.String getCcoddpto() {
		return ccoddpto;
	}

	/**
	 * @param newCcoddpto
	 * @pdOid caea26b0-1842-4fca-8b14-84c4129293c2
	 */
	public void setCcoddpto(java.lang.String newCcoddpto) {
		ccoddpto = newCcoddpto;
	}

	/** @pdOid 216efcbb-896e-4cfe-9d38-f6a6b9a99e90 */
	public java.lang.String getDepartamento() {
		return departamento;
	}

	/**
	 * @param newDepartamento
	 * @pdOid c8da6774-0135-4393-b6c4-93fad0c738eb
	 */
	public void setDepartamento(java.lang.String newDepartamento) {
		departamento = newDepartamento;
	}

	/** @pdGenerated default getter */
	public java.util.List<Barrio> getBarrios() {
		if (barrios == null)
			barrios = new java.util.ArrayList<Barrio>();
		return barrios;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorBarrios() {
		if (barrios == null)
			barrios = new java.util.ArrayList<Barrio>();
		return barrios.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newBarrios
	 */
	public void setBarrios(java.util.List<Barrio> newBarrios) {
		removeAllBarrios();
		for (java.util.Iterator iter = newBarrios.iterator(); iter.hasNext();)
			addBarrios((Barrio) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newBarrios
	 */
	public void addBarrios(Barrio newBarrios) {
		if (newBarrios == null)
			return;
		if (this.barrios == null)
			this.barrios = new java.util.ArrayList<Barrio>();
		if (!this.barrios.contains(newBarrios)) {
			this.barrios.add(newBarrios);
			newBarrios.setLocalidades(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldBarrios
	 */
	public void removeBarrios(Barrio oldBarrios) {
		if (oldBarrios == null)
			return;
		if (this.barrios != null)
			if (this.barrios.contains(oldBarrios)) {
				this.barrios.remove(oldBarrios);
				oldBarrios.setLocalidades((Localidad) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllBarrios() {
		if (barrios != null) {
			Barrio oldBarrios;
			for (java.util.Iterator iter = getIteratorBarrios(); iter.hasNext();) {
				oldBarrios = (Barrio) iter.next();
				iter.remove();
				oldBarrios.setLocalidades((Localidad) null);
			}
		}
	}

}