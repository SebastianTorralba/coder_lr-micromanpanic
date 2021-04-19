package ar.com.twoboot.panico.dialogabm;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.twoboot.microman.dialogabm.DialogBaseAbm;
import ar.com.twoboot.microman.dominio.sincronizacion.ISync;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.remotos.Secuencializador;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncSecuencializador;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncUsuario;
import ar.com.twoboot.panico.objetos.Usuario;

public class DialogRegistro extends DialogBaseAbm implements ISync {

	private Usuario usuario;
	private OnUsuario oUsuario;
	private EditText etUsuario;
	private EditText etApellidoNombre;
	private EditText etDni;
	private EditText etClave;
	private EditText etClaveConfirmacion;
	private OnConfiguracion oConfig;
	private SyncSecuencializador syncSecuencializador;
	private Secuencializador secuencializador = new Secuencializador("usuarios");
	private ISync sincro;
	private SyncUsuario syncUsuario;
	private TextView etTelefono;

	public DialogRegistro(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		usuario = (Usuario) objeto;
		oConfig = new OnConfiguracion(mTrans);
		oUsuario = new OnUsuario(mTrans);
		titulo = "Registracion";
		sincro = this;
		usaSincronizador = true;
		syncSecuencializador = new SyncSecuencializador(MicroMan.configuracion, getActivity());
		syncSecuencializador.setmObjetoSync(this);
		syncSecuencializador.setSecuencializador(secuencializador);
		syncSecuencializador.execute(Sincronizador.OP_IMPORTAR);
	}

	@Override
	public DalusObject consolidar() {
		usuario.setApellidoNombre(etApellidoNombre.getText().toString());
		usuario.setUsuario(etUsuario.getText().toString());
		usuario.setClave(etClave.getText().toString());
		usuario.setImei(MicroMan.imei);
		usuario.setDni(etDni.getText().toString());
		usuario.setTelefono(etTelefono.getText().toString());
		usuario.setEstado(0);
		return super.consolidar();
	}

	@Override
	protected void inicializacion() {
		etApellidoNombre = (EditText) rootView.findViewById(id.etApellidoNombre);
		etUsuario = (EditText) rootView.findViewById(id.etUsuario);
		etClave = (EditText) rootView.findViewById(id.etClave);
		etClaveConfirmacion = (EditText) rootView.findViewById(id.etClaveConfirmacion);
		etDni = (EditText) rootView.findViewById(id.etDni);
		TelephonyManager tMgr = (TelephonyManager) getActividadLlamada().getSystemService(Context.TELEPHONY_SERVICE);
		String numeroTelefono = tMgr.getLine1Number();
		etTelefono = (TextView) rootView.findViewById(id.etTelefono);
		etTelefono.setText(numeroTelefono);

	}

	@Override
	public boolean validar() {
		Boolean resultado = true;
		String baseDatos;
		if (etApellidoNombre.getText().toString().isEmpty()) {
			etApellidoNombre.setError("Ingrese su Apellido y Nombre");
			return false;
		}
		if (etDni.getText().toString().isEmpty()) {
			etDni.setError("Ingrese su numero de DNI");
			return false;
		} else {
			if (etDni.getText().toString().length() < 7 || etDni.getText().toString().length() > 8) {
				etDni.setError("El DNI debe tener 7 u 8 caracteres");
				return false;
			}
			if (etTelefono.getText().toString().isEmpty()) {
				etTelefono.setError("Ingrese su numero telefonico");
				return false;
			} else {
				if (etTelefono.getText().toString().length() < 10) {
					etTelefono.setError("El Numero Telefonico debe tener al menos 10 caracteres");
					return false;
				}

			}
		}
		if (etUsuario.getText().toString().isEmpty()) {
			etUsuario.setError("Ingrese Email");
			return false;
		} else if (!etUsuario.getText().toString().contains("@")) {
			etUsuario.setError("Email Invalido");
			return false;
		}

		if (etClave.getText().toString().isEmpty()) {
			etClave.setError("Ingrese Clave");
			return false;
		} else {
			if (etClave.getText().toString().length() < 5) {
				etClave.setError("La clave debe contener al menos 5 caracteres");
				return false;
			}

		}
		resultado = oUsuario.confirmarContrasenia(etClave.getText().toString(),
				etClaveConfirmacion.getText().toString());
		if (!resultado) {
			mostrarAdvertencia("No Coinciden las Claves");
		} else {

		}
		return resultado;
	}

	@Override
	public int grabar(DalusObject objeto) {
		return 0;
	}

	@Override
	public void ejecutarSync() {
		// TODO Auto-generated method stub
	}

	@Override
	public void ejecucionCorrecta() {
		// TODO Auto-generated method stub
		mostrarAdvertenciaToast("Recibira un Mail de Activacion");
		dismiss();
	}

	@Override
	public void ejecucionIncorrecta() {
		// TODO Auto-generated method stub
		mensajeSincronizador = syncUsuario.getMensaje();
		if (mensajeSincronizador.length() > 0) {
			mostrarAdvertencia(mensajeSincronizador);
		}
	}

	@Override
	public Boolean ejecutarSincronizador() {
		if (secuencializador.getId() > 0) {
			usuario.setIdUsuario(secuencializador.getId().intValue());
			syncUsuario = new SyncUsuario(MicroMan.configuracion, getActivity());
			syncUsuario.setmObjetoSync(this);
			syncUsuario.setUsuario(usuario);
			syncUsuario.setMensaje(mensajeSincronizador);
			syncUsuario.execute(Sincronizador.OP_EXPORTAR);
		}
		return true;
	}

}
