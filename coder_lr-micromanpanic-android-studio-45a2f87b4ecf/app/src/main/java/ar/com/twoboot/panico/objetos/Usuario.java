/***********************************************************************
 * Module:  Usuarios.java
 * Author:  Sebastian
 * Purpose: Defines the Class Usuarios
 ***********************************************************************/

package ar.com.twoboot.panico.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import ar.com.twoboot.microman.objetos.DalusObject;

/** @pdOid 062303fd-1852-49cb-9af3-1ad136766974 */
public class Usuario extends DalusObject {
	/** @pdOid 713c57c4-8729-47dd-9033-8ceac3414455 */
	private int idUsuario;
	/** @pdOid 67a68e31-e338-4d30-a9c5-848130dfbf9f */
	private java.lang.String usuario;
	/** @pdOid 58e89e1f-4397-4177-9439-b817afe90e9c */
	private java.lang.String clave;
	/** @pdOid 0a352288-7e32-4654-88da-a4ccae1e8945 */
	private java.lang.String imei;
	private java.lang.String telefono;
	/** @pdOid 108d924b-611a-418f-b335-34cf47337e0a */
	private java.lang.String apellidoNombre;
	/** @pdOid 7bdd0498-61c1-43dd-b136-670ddb964b43 */
	private int estado;
	
	private String dni;

	/**
	 * @pdRoleInfo migr=no name=UsuarioPerfil assc=usuarioPefiles
	 *             coll=java.util.List impl=java.util.ArrayList mult=0..*
	 */
	public java.util.List<UsuarioPerfil> usuarioPerfil;

	/** @pdOid e2b13ef0-05e3-48fa-96a6-08c17e8dfe1d */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 60dcfb34-f906-4be4-b27f-4ba96392aae8
	 */
	public void setIdUsuario(int newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid f09b5ea9-3cac-48ef-90af-8c75079ba73b */
	public java.lang.String getUsuario() {
		return usuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @param newUsuario
	 * @pdOid 044e49a6-4f68-42a8-bbc8-cf92e5bda4fc
	 */
	public void setUsuario(java.lang.String newUsuario) {
		usuario = newUsuario;
	}

	/** @pdOid 89b6dda8-35cd-402c-be6f-00d612bcd2cd */
	public java.lang.String getClave() {
		return clave;
	}

	/**
	 * @param newClave
	 * @pdOid 4a397aa0-fce0-4eb7-aaf8-b228a3bc8cb2
	 */
	public void setClave(java.lang.String newClave) {
		clave = newClave;
	}

	/** @pdOid f230a6bc-00bd-4269-b959-b658d7baf52e */
	public java.lang.String getImei() {
		return imei;
	}

	/**
	 * @param newImei
	 * @pdOid 232a4968-dff0-4e5d-9555-33b8accb6b01
	 */
	public void setImei(java.lang.String newImei) {
		imei = newImei;
	}

	/** @pdOid 30023716-11f0-4c95-a8b1-5c40504035f2 */
	public java.lang.String getApellidoNombre() {
		return apellidoNombre;
	}

	/**
	 * @param newApellidoNombre
	 * @pdOid 1e2cf668-8e8b-4869-a187-78d452e98f13
	 */
	public void setApellidoNombre(java.lang.String newApellidoNombre) {
		apellidoNombre = newApellidoNombre;
	}

	/** @pdOid 4c77a708-6e59-4d1a-9a25-f32bc4cc9389 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 42a06360-0719-4e5d-9381-bae703975f8e
	 */
	public void setEstado(int newEstado) {
		estado = newEstado;
	}

	/** @pdGenerated default getter */
	public java.util.List<UsuarioPerfil> getUsuarioPerfil() {
		if (usuarioPerfil == null)
			usuarioPerfil = new java.util.ArrayList<UsuarioPerfil>();
		return usuarioPerfil;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorUsuarioPerfil() {
		if (usuarioPerfil == null)
			usuarioPerfil = new java.util.ArrayList<UsuarioPerfil>();
		return usuarioPerfil.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newUsuarioPerfil
	 */
	public void setUsuarioPerfil(java.util.List<UsuarioPerfil> newUsuarioPerfil) {
		removeAllUsuarioPerfil();
		for (java.util.Iterator iter = newUsuarioPerfil.iterator(); iter.hasNext();)
			addUsuarioPerfil((UsuarioPerfil) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newUsuarioPerfil
	 */
	public void addUsuarioPerfil(UsuarioPerfil newUsuarioPerfil) {
		if (newUsuarioPerfil == null)
			return;
		if (this.usuarioPerfil == null)
			this.usuarioPerfil = new java.util.ArrayList<UsuarioPerfil>();
		if (!this.usuarioPerfil.contains(newUsuarioPerfil)) {
			this.usuarioPerfil.add(newUsuarioPerfil);
			newUsuarioPerfil.setUsuarios(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldUsuarioPerfil
	 */
	public void removeUsuarioPerfil(UsuarioPerfil oldUsuarioPerfil) {
		if (oldUsuarioPerfil == null)
			return;
		if (this.usuarioPerfil != null)
			if (this.usuarioPerfil.contains(oldUsuarioPerfil)) {
				this.usuarioPerfil.remove(oldUsuarioPerfil);
				oldUsuarioPerfil.setUsuarios((Usuario) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllUsuarioPerfil() {
		if (usuarioPerfil != null) {
			UsuarioPerfil oldUsuarioPerfil;
			for (java.util.Iterator iter = getIteratorUsuarioPerfil(); iter.hasNext();) {
				oldUsuarioPerfil = (UsuarioPerfil) iter.next();
				iter.remove();
				oldUsuarioPerfil.setUsuarios((Usuario) null);
			}
		}
	}

	public java.lang.String getTelefono() {
		return telefono;
	}

	public void setTelefono(java.lang.String telefono) {
		this.telefono = telefono;
	}	

}