package ar.com.twoboot.panico.dominio.sincronizacion;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.helpers.HttpRest;
import ar.com.twoboot.panico.objetos.Configuracion;
import ar.com.twoboot.panico.objetos.Usuario;

public class SyncUsuario extends Sincronizador {
	private Usuario usuario;
	private OnUsuario oUsuario;

	@Override
	public boolean importar() {
		Boolean resultado = false;
		try {
			if (!autorizarUsuario()) {
				setEstadoEjecucion(EJECUCION_INCORRECTA);
				return false;
			}
			switch (configuracion.getProtocolo()) {
			case Configuracion.PROTOCOLO_SQL:
				String sqlQuery = "SELECT * FROM usuarios " + "where usuario='" + usuario.getUsuario() + "' and clave='"
						+ usuario.getClave() + "'";
				PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				stmt.execute();
				Log.i(Util.APP, sqlQuery);
				ContentValues valores = new ContentValues();
				ResultSet rs = stmt.getResultSet();
				contarRegistros(rs);
				rtn = mTrans.baseDatos.delete(getmNombreTabla(), "usuario='" + usuario.getUsuario() + "'", null);
				while (rs.next()) {
					valores.put("id_usuario", rs.getInt(1));
					valores.put("usuario", rs.getString(2));
					valores.put("clave", rs.getString(3));
					valores.put("Imei", rs.getString(4));
					valores.put("apellido_nombre", rs.getString(5));
					valores.put("estado", rs.getInt(6));
					Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
					Log.i(Util.APP, valores.toString());
					rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
					if (rtn > 0) {
						setEstadoEjecucion(EJECUCION_CORRECTA);
						resultado = true;
					} else {

						setEstadoEjecucion(EJECUCION_INCORRECTA);
						resultado = false;
					}
					if (!resultado) {

						setEstadoEjecucion(EJECUCION_INCORRECTA);
						return false;
					}
					publishProgress(actualizarRegistrosImportacion());
					Log.i(Util.APP, "INSERTANDO " + rtn);
				}
				stmt.close();
				break;
			case Configuracion.PROTOCOLO_REST:
				HttpRest clienterest = new HttpRest();
				ContentValues valoresrest = new ContentValues();
				Request request = new Request.Builder().url(configuracion.getRestserverUrl() + "usuarios/login/"
						+ usuario.getUsuario() + "/" + Util.getMD5(usuario.getClave())).build();
				Response respuestahttp = clienterest.getClientehttp().newCall(request).execute();
				String resp = respuestahttp.body().string();
				rtn = mTrans.baseDatos.delete(getmNombreTabla(), "usuario='" + usuario.getUsuario() + "'", null);
				JSONObject jsonresp = new JSONObject(resp);
				int logueocod = jsonresp.getInt("status");
				if (logueocod == 100) {
					JSONObject datos = jsonresp.getJSONObject("data");
					valoresrest.put("id_usuario", datos.getInt("id"));
					valoresrest.put("usuario", datos.getString("username"));
					valoresrest.put("clave", usuario.getClave());
					valoresrest.put("Imei", "0000");
					valoresrest.put("apellido_nombre", "Nombre");
					valoresrest.put("estado", datos.getInt("estado"));
					rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valoresrest);
					if (rtn > 0) {
						setEstadoEjecucion(EJECUCION_CORRECTA);
						resultado = true;
					} else {
						setEstadoEjecucion(EJECUCION_INCORRECTA);
						resultado = false;
					}
					if (!resultado) {
						setEstadoEjecucion(EJECUCION_INCORRECTA);
						return false;
					}
				}
				break;
			}
		} catch (SQLException e) {
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		} catch (IOException e) {
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}
		return resultado;

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SyncUsuario(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("USUARIOS");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exportar() {
		registros = 0;
		setTotalRegistros(1);
		try {
			switch (configuracion.getProtocolo()) {
			case Configuracion.PROTOCOLO_SQL:
				mConexion.setAutoCommit(false);
				Log.i(Util.APP, "CONECTADO:" + usuario);
				PreparedStatement stmt = mConexion
						.prepareStatement("INSERT INTO usuarios " + "(ID_USUARIO,USUARIO,CLAVE,IMEI,APELLIDO_NOMBRE,"
								+ "ESTADO,TIPO,TELEFONO) " + "VALUES (?,?,?,?,?,?,?,?)");
				stmt.setLong(1, usuario.getIdUsuario());
				stmt.setString(2, usuario.getUsuario());
				stmt.setString(3, usuario.getClave());
				stmt.setString(4, MicroMan.imei);
				stmt.setString(5, usuario.getApellidoNombre());
				stmt.setInt(6, usuario.getEstado());
				stmt.setString(7, Util.TIPO_APP);
				stmt.setString(8, usuario.getTelefono());

				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);

				stmt.executeUpdate();
				Log.i(Util.APP, "Insertando en : " + stmt.toString());

				resultado = grabarBdLocal(usuario);
				if (resultado) {
					mConexion.commit();
					setMensaje("Bienvenido Usuario Registrado");
					setEstadoEjecucion(EJECUCION_CORRECTA);
				} else {
					mConexion.rollback();
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					setMensaje("Error de Datos");
				}
				registros++;
				actualizarRegistrosExportacion();
				publishProgress(registros);
				stmt.close();
				generarAutentificacion();
				setEstadoEjecucion(EJECUCION_CORRECTA);
				break;
			case Configuracion.PROTOCOLO_REST:
				HttpRest clienterest = new HttpRest();
				JSONObject jsoncuerpaaa = new JSONObject();
				jsoncuerpaaa.put("clave", usuario.getClave());
				jsoncuerpaaa.put("claveMd5", usuario.getClave());
				jsoncuerpaaa.put("estado", usuario.getEstado());
				jsoncuerpaaa.put("email", usuario.getUsuario());
				jsoncuerpaaa.put("username", usuario.getUsuario());
				//JSONObject jsonrol = new JSONObject();
				//jsonrol.put("denominacion", "ADMIN");
				//jsonrol.put("id", 1);
				//jsoncuerpaaa.put("roles", jsonrol);
				JSONObject jsonpersona = new JSONObject();
				jsonpersona.put("razonSocial", usuario.getApellidoNombre());
				jsonpersona.put("nombreFantasia", usuario.getApellidoNombre());
				jsonpersona.put("tel", usuario.getTelefono());
				jsonpersona.put("email", usuario.getUsuario());
				jsonpersona.put("dni", usuario.getDni());
				jsoncuerpaaa.put("persona", jsonpersona);
				MediaType tipoform = MediaType.parse("application/json; charset=utf-8");
				RequestBody formulario = RequestBody.create(tipoform, jsoncuerpaaa.toString());
				Request request = new Request.Builder()
						.url(MicroMan.configuracion.getRestserverUrl() + "usuarios/create").post(formulario).build();
				Response respuestahttp = clienterest.getClientehttp().newCall(request).execute();
				String resp = respuestahttp.body().string();
				JSONObject jsonresp = new JSONObject(resp);
				int codresp = jsonresp.getInt("status");
				if (codresp == 101) {
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					setMensaje("Error interno");
				}
				if (codresp == 102 || codresp == 103) {
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					setMensaje("Error el usuario ya esta registrado");
				}
				if (codresp == 100) {
					setMensaje("Bienvenido Usuario Registrado");
					setEstadoEjecucion(EJECUCION_CORRECTA);
				}
				else {
					setEstadoEjecucion(EJECUCION_INCORRECTA);
					setMensaje("Error desconocido");
				}
				break;
			}
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			setMensaje("Error desconocido");
		} catch (IOException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			setMensaje("Timeout");
		} catch (JSONException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			setMensaje("Error desconocido");
		}
	}

	private Boolean grabarBdLocal(Usuario usuario) {
		// TODO Auto-generated method stub
		try {
			OnUsuario oUsuario = new OnUsuario(mTrans);
			oUsuario.setUsuario(usuario);
			oUsuario.insertar();
		} catch (Exception e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			return false;
		}
		return true;
	}

	private Boolean autorizarUsuario() {
		switch (configuracion.getProtocolo()) {
		case Configuracion.PROTOCOLO_SQL:
			String sqlQuery = "SELECT count(*) FROM usuarios " + "where usuario='" + usuario.getUsuario()
					+ "' and clave='" + usuario.getClave() + "'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt;
			try {
				stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				stmt.execute();
				Log.i(Util.APP, sqlQuery);
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					Long existe = rs.getLong(1);
					if (existe == 0) {
						return false;
					} else {
						return true;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Configuracion.PROTOCOLO_REST:
			HttpRest clienterest = new HttpRest();
			Request request = new Request.Builder().url(configuracion.getRestserverUrl() + "usuarios/login/"
					+ usuario.getUsuario() + "/" + Util.getMD5(usuario.getClave())).build();
			try {
				Response respuestahttp = clienterest.getClientehttp().newCall(request).execute();
				String resp = respuestahttp.body().string();
				JSONObject jsonresp = new JSONObject(resp);
				int logueocod = jsonresp.getInt("status");
				if (logueocod == 100) {
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		return false;
	}

	private Boolean validarUsuario() {
		switch (configuracion.getProtocolo()) {
		case Configuracion.PROTOCOLO_SQL:
			String sqlQuery = "SELECT count(*) FROM usuarios " + "where usuario='" + usuario.getUsuario() + "' ";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt;
			try {
				stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				stmt.execute();
				Log.i(Util.APP, sqlQuery);
				ResultSet rs = stmt.getResultSet();

				while (rs.next()) {
					Long existe = rs.getLong(1);
					if (existe == 0) {
						return true;
					} else {
						return false;
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Configuracion.PROTOCOLO_REST:
			HttpRest clienterest = new HttpRest();
			Request request = new Request.Builder().url(configuracion.getRestserverUrl() + "usuarios/login/"
					+ usuario.getUsuario() + "/" + Util.getMD5(usuario.getClave())).build();
			try {
				Response respuestahttp = clienterest.getClientehttp().newCall(request).execute();
				String resp = respuestahttp.body().string();
				JSONObject jsonresp = new JSONObject(resp);
				int logueocod = jsonresp.getInt("status");
				if (logueocod == 100) {
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		return false;

	}

	public OnUsuario getoUsuario() {
		return oUsuario;
	}

	public void setoUsuario(OnUsuario oUsuario) {
		this.oUsuario = oUsuario;
	}

	private int generarAutentificacion() {
		String namespace = "http://serviciosweb.zonda.twoboot.com.ar/";
		String URL = "http://104.131.72.170:8080/MicroManZonda/ZondaWS?wsdl";
		String METHOD_NAME = "registrarUsuario";
		String SOAP_ACTION = "http://serviciosweb.zonda.twoboot.com.ar/registrarUsuario";
		SoapObject request = new SoapObject(namespace, METHOD_NAME);
		request.addProperty("usuario", usuario.getUsuario());
		request.addProperty("clave", usuario.getClave());
		request.addProperty("imei", usuario.getImei());
		request.addProperty("apellidoNombre", usuario.getApellidoNombre());
		request.addProperty("tipo", "reclamo");
		request.addProperty("Telefono", usuario.getTelefono());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			String resultado = resultsRequestSOAP.toString();
			if (resultado == "Exito") {
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
		}
		return 0;
	}
}