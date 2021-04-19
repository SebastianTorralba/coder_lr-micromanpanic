/***********************************************************************
 * Module:  Sistema.java
 * Author:  Sebastian
 * Purpose: Defines the Class Sistema
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 4dc921be-a915-439a-829e-d12a9d136e0a */
public class Sistema extends DalusObject {
	/** @pdOid b3a0ba70-b296-48d7-b306-5f5e6fba2468 */
	private java.lang.String version;
	/** @pdOid 4b30a599-de2c-4ad7-9866-04c0f6975950 */
	private java.util.Date fechaActual;

	/** @pdOid 4f4f8f78-16ab-43f5-b14d-154a0fad471c */
	public java.lang.String getVersion() {
		return version;
	}

	/**
	 * @param newVersion
	 * @pdOid 583cfe9a-aa7c-4515-b18f-67dfa4cd0dee
	 */
	public void setVersion(java.lang.String newVersion) {
		version = newVersion;
	}

}