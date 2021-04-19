package ar.com.twoboot.panico.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

public class Alerta {
	public static final int DEFAULT = 0;
	private Location ubicacion;
	private int codigo;
	private Usuario usuario;

	public Alerta() {
		super();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Alerta(Location ubicacion, int codigo, Usuario usuario) {
		super();
		this.ubicacion = ubicacion;
		this.codigo = codigo;
		this.usuario = usuario;
	}

	public Location getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Location ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		JSONObject datos = new JSONObject();
		JSONObject usuariojson = new JSONObject();
		try {
			datos.put("lat", ubicacion.getLatitude());
			datos.put("long", ubicacion.getLongitude());
			datos.put("precision", ubicacion.getAccuracy());
			datos.put("cod", codigo);
			usuariojson.put(usuario.getUsuario(), datos);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuariojson.toString();
	}

}
