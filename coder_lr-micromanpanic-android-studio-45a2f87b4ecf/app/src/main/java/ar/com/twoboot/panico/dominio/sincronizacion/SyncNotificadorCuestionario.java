package ar.com.twoboot.panico.dominio.sincronizacion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.SincronizadorNotificador;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.dominio.OnCuestionario;
import ar.com.twoboot.panico.objetos.Configuracion;
import ar.com.twoboot.panico.objetos.Cuestionario;
import ar.com.twoboot.panico.objetos.CuestionarioPreguntas;

public class SyncNotificadorCuestionario extends SincronizadorNotificador {
	private Cuestionario cuestionario;
	private Long idCuestionarioRemoto;

	public Long getIdCuestionarioRemoto() {
		return idCuestionarioRemoto;
	}

	public Cuestionario getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(Cuestionario cuestionario) {
		this.cuestionario = cuestionario;
	}

	public SyncNotificadorCuestionario(Configuracion configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla(Util.TIPO_APP);
		// TODO Auto-generated constructor stub
	}

	public Long obtenerSecuencializador() {
		Long id = new Long(0);
		try {
			resultado = true;
			String sqlQuery = "SELECT id FROM secuencializadores " + "where secuencializador='cuestionarios'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			while (rs.next()) {
				id = rs.getLong(1);
				id++;
				PreparedStatement stmtUpdate = mConexion.prepareStatement(
						"update secuencializadores set id=?" + " where secuencializador='cuestionarios'");
				stmtUpdate.setLong(1, id);
				rtn = stmtUpdate.executeUpdate();
				stmtUpdate.close();
			}
			stmt.close();
		} catch (SQLException e)

		{
			setEstadoEjecucion(EJECUCION_INCORRECTA);
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
			id = new Long(0);
		}
		return id;
	}

	@Override
	public void exportar() {
		registros = 0;
		setTotalRegistros(1);
		try {
			mConexion.setAutoCommit(false);
			Log.i(Util.APP, "CONECTADO:" + cuestionario);
			PreparedStatement stmt = mConexion
					.prepareStatement("INSERT INTO cuestionario VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			if (cuestionario.getEstado() == 1) {
				stmt.setLong(1, cuestionario.getIdCuestionario());
			} else if (cuestionario.getEstado() == 0) {
				idCuestionarioRemoto = obtenerSecuencializador();
				if (idCuestionarioRemoto > 0) {
					stmt.setLong(1, idCuestionarioRemoto);
					cuestionario.setIdCuestionarioRemoto(idCuestionarioRemoto.longValue());
				}
			}
			stmt.setString(2, Util.TIPO_APP);
			if (cuestionario.getBarrios() != null) {
				stmt.setInt(3, cuestionario.getBarrios().getIdBarrio());
			}
			if (cuestionario.getCalles() != null) {
				stmt.setInt(4, cuestionario.getCalles().getIdCalle());
			}
			stmt.setString(5, cuestionario.getAltura());
			stmt.setDouble(6, cuestionario.getGpsLatitud());
			stmt.setDouble(7, cuestionario.getGpsLongitud());
			byte[] foto = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OnCuestionario oCuestionario = new OnCuestionario(mTrans);
			oCuestionario.setCuestionario(cuestionario);
			File fotoArchivo = oCuestionario.cargarArchivoFoto();
			try {
				if (fotoArchivo != null) {
					foto = org.apache.commons.io.FileUtils.readFileToByteArray(fotoArchivo);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			stmt.setBytes(8, foto);
			stmt.setInt(9, cuestionario.getEstado());
			stmt.setString(10, MicroMan.imei);
			java.sql.Date fecha = Util.dateToSqlDate(cuestionario.getFechaCarga());
			stmt.setDate(11, fecha);
			Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
			stmt.executeUpdate();
			Log.i(Util.APP, "Insertando en : " + stmt.toString());
			resultado = exportarRespuestas(cuestionario);
			if (resultado) {
				mConexion.commit();
				setEstadoEjecucion(EJECUCION_CORRECTA);
			} else {
				mConexion.rollback();
				setEstadoEjecucion(EJECUCION_INCORRECTA);
			}
			registros++;
			actualizarRegistrosExportacion();
			publishProgress(registros);
			stmt.close();
			setEstadoEjecucion(EJECUCION_CORRECTA);
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			setEstadoEjecucion(EJECUCION_INCORRECTA);
		}
	}

	private Boolean exportarRespuestas(Cuestionario cuestionario) {
		// TODO Auto-generated method stub
		ArrayList<CuestionarioPreguntas> respuestas = (ArrayList<CuestionarioPreguntas>) cuestionario
				.getCuestionarioPreguntas();
		for (Iterator iterator = respuestas.iterator(); iterator.hasNext();) {
			CuestionarioPreguntas cuestionarioPreguntas = (CuestionarioPreguntas) iterator.next();
			try {
				PreparedStatement stmt = mConexion
						.prepareStatement("INSERT INTO cuestionario_preguntas VALUES(?,?,?,?,?)");
				if (cuestionario.getEstado() == 1) {
					stmt.setLong(1, cuestionario.getIdCuestionario());
				} else if (cuestionario.getEstado() == 0) {
					if (idCuestionarioRemoto > 0) {
						stmt.setLong(1, idCuestionarioRemoto);
					}
				}
				stmt.setString(2, Util.TIPO_APP);
				stmt.setInt(3, cuestionarioPreguntas.getPregunta().getIdPregunta());
				stmt.setString(4, cuestionarioPreguntas.getRespuesta().getCodRespuesta());
				stmt.setString(5, cuestionarioPreguntas.getExtension());
				stmt.executeUpdate();

			} catch (SQLException e) {
				Log.e(Util.APP, "Statement error: " + e.getMessage());
				return false;
			}
		}
		return true;
	}

}