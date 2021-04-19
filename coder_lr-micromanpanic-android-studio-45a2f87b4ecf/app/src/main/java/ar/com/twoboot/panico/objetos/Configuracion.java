/***********************************************************************
 * Module:  Configuracion.java
 * Author:  Sebastian
 * Purpose: Defines the Class Configuracion
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 3944b418-004c-4f0d-b36f-2563e566d052 */
public class Configuracion extends DalusObject {
	/** @pdOid 3f89c05b-dd75-4af7-bfc1-0c68377073aa */
	public static final int PROTOCOLO_SQL = 0;
	public static final int PROTOCOLO_REST = 1;
	private java.lang.String usuario;
	private String usuario_rest;
	private int protocolo;
	/** @pdOid b116139d-842c-407a-8f0f-43602c5ed4a3 */
	private java.lang.String password;
	private String password_rest;
	/** @pdOid 7cbf022d-c85d-4dd6-ae7a-a4d917c1ac87 */
	private java.lang.String sqlserverUrl;
	private String restserverUrl;
	/** @pdOid ee7c0484-ee6f-495a-9b1d-79649f5aa775 */
	private java.lang.String sqlserverInstancia;
	/** @pdOid 2f4dead7-bea5-445c-9f1f-b039efcb2f98 */
	private java.lang.String ftpUrl;
	/** @pdOid d6b909d1-2985-46ba-921e-40ad9fcc8774 */
	private int cantidadCuestionarios;
	/** @pdOid 23b0f002-ae38-4922-81ac-f652736d5505 */
	private int idCuestionarioApp;
	/** @pdOid 4ad7fb67-a36c-417d-a67e-ffa7250eedf5 */
	private int idCuestionarioBd;

	private String baseDatos;

	/** @pdOid 245b5b27-cf94-4ce7-bc04-c7bfb7ddda17 */
	public java.lang.String getUsuario() {
		return usuario;
	}

	/**
	 * @param newUsuario
	 * @pdOid dbad1eb7-8977-4b80-b895-7e6f333786a2
	 */
	public void setUsuario(java.lang.String newUsuario) {
		usuario = newUsuario;
	}

	/** @pdOid 947081dc-75a5-4578-bf40-87990a623068 */
	public java.lang.String getPassword() {
		return password;
	}

	/**
	 * @param newPassword
	 * @pdOid abcb91f4-08c2-4fe6-ae69-44a08bbb837f
	 */
	public void setPassword(java.lang.String newPassword) {
		password = newPassword;
	}

	/** @pdOid 30b9b011-286a-4e6d-b32f-aebc10681da3 */
	public java.lang.String getSqlserverUrl() {
		return sqlserverUrl;
	}

	public String getUsuario_rest() {
		return usuario_rest;
	}

	public void setUsuario_rest(String usuario_rest) {
		this.usuario_rest = usuario_rest;
	}

	public int getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(int protocolo) {
		this.protocolo = protocolo;
	}

	public String getPassword_rest() {
		return password_rest;
	}

	public void setPassword_rest(String password_rest) {
		this.password_rest = password_rest;
	}

	public String getRestserverUrl() {
		return restserverUrl;
	}

	public void setRestserverUrl(String restserverUrl) {
		this.restserverUrl = restserverUrl;
	}

	/**
	 * @param newSqlserverUrl
	 * @pdOid 4a3d6cb5-f816-44ad-80f4-18c5050eee19
	 */
	public void setSqlserverUrl(java.lang.String newSqlserverUrl) {
		sqlserverUrl = newSqlserverUrl;
	}

	/** @pdOid 38684568-d679-452d-8caa-599f488155c7 */
	public java.lang.String getSqlserverInstancia() {
		return sqlserverInstancia;
	}

	/**
	 * @param newSqlserverInstancia
	 * @pdOid 2afede9e-0465-42fc-9739-9a5726aeaa7b
	 */
	public void setSqlserverInstancia(java.lang.String newSqlserverInstancia) {
		sqlserverInstancia = newSqlserverInstancia;
	}

	/** @pdOid 744f90d1-6d6b-4049-9329-92c970f1c2b6 */
	public java.lang.String getFtpUrl() {
		return ftpUrl;
	}

	/**
	 * @param newFtpUrl
	 * @pdOid 429e2ca6-134f-43dd-b361-85a62f81857d
	 */
	public void setFtpUrl(java.lang.String newFtpUrl) {
		ftpUrl = newFtpUrl;
	}

	/** @pdOid cce5d49b-eddb-4e47-98ff-d40c7d56613f */
	public int getCantidadCuestionarios() {
		return cantidadCuestionarios;
	}

	/**
	 * @param newCantidadCuestionarios
	 * @pdOid 1b90c3c7-da3f-46fa-b836-10bdea156989
	 */
	public void setCantidadCuestionarios(int newCantidadCuestionarios) {
		cantidadCuestionarios = newCantidadCuestionarios;
	}

	/** @pdOid 8abe5894-77b6-43ea-9209-627b1448fcc0 */
	public int getIdCuestionarioApp() {
		return idCuestionarioApp;
	}

	/**
	 * @param newIdCuestionarioApp
	 * @pdOid 7129b0da-23aa-4390-9073-fc78a3c321a2
	 */
	public void setIdCuestionarioApp(int newIdCuestionarioApp) {
		idCuestionarioApp = newIdCuestionarioApp;
	}

	/** @pdOid 16f95800-79df-4e8e-8006-0adfaa794831 */
	public int getIdCuestionarioBd() {
		return idCuestionarioBd;
	}

	/**
	 * @param newIdCuestionarioBd
	 * @pdOid dc5e0c25-397e-4c00-8e21-8121c74cc381
	 */
	public void setIdCuestionarioBd(int newIdCuestionarioBd) {
		idCuestionarioBd = newIdCuestionarioBd;
	}

	public String getBaseDatos() {
		// TODO Auto-generated method stub
		return baseDatos;
	}

}