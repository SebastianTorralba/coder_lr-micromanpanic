package ar.com.twoboot.panico;

import org.mindrot.jbcrypt.BCrypt;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.ISync;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.util.DetectorConexion;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.dialogabm.DialogRegistro;
import ar.com.twoboot.panico.dominio.OnConfiguracion;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.dominio.sincronizacion.SyncUsuario;
import ar.com.twoboot.panico.objetos.Usuario;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends FragmentActivity implements ISync {
	private UserLoginTask mAuthTask = null;
	private String mEmail;
	private String mPassword;
	private EditText etEmail;
	private EditText etPassword;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private Button bRegistrarse;
	private ISync sincro;
	private Usuario usuario = new Usuario();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MicroMan.detectorConexion = new DetectorConexion(getApplicationContext());
		MicroMan.mTrans = new Transaccion(getApplicationContext());
		MicroMan.mTrans.conectarDB();
		TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		MicroMan.imei = mngr.getDeviceId();
		MicroMan.configuracion = new OnConfiguracion(MicroMan.mTrans).extraer();
		setContentView(R.layout.activity_login);
		etEmail = (EditText) findViewById(R.id.email);
		etEmail.setText(mEmail);
		etPassword = (EditText) findViewById(R.id.password);
		etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin(false);
					return true;
				}
				return false;
			}
		});
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin(false);
				//MicroMan.idUsuario = 30;
				//MicroMan.estaLogueado = true;
				//Intent intent = new Intent(getApplicationContext(), MicroMan.class);
				//startActivity(intent);
				//finish();
			}
		});
		bRegistrarse = (Button) findViewById(R.id.bRegistrarse);
		bRegistrarse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				registracion();
			}
		});
		OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
		Usuario usuario = oUsuario.comprobarAutoLogin();
		if (usuario != null) {
			etEmail.setText(usuario.getUsuario());
			etPassword.setText(usuario.getClave());
			attemptLogin(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form. If
	 * there are form errors (invalid email, missing fields, etc.), the errors are
	 * presented and no actual login attempt is made.
	 */
	public void attemptLogin(boolean autologin) {
		if (mAuthTask != null) {
			return;
		}
		// Reset errors.
		etEmail.setError(null);
		etPassword.setError(null);
		// Store values at the time of the login attempt.
		mEmail = etEmail.getText().toString();
		mPassword = etPassword.getText().toString();
		boolean cancel = false;
		View focusView = null;
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			etPassword.setError(getString(R.string.error_field_required));
			focusView = etPassword;
			cancel = true;
		} else if (mPassword.length() < 3) {
			etPassword.setError(getString(R.string.error_invalid_password));
			focusView = etPassword;
			cancel = true;
		}
		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			etEmail.setError(getString(R.string.error_field_required));
			focusView = etEmail;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			etEmail.setError(getString(R.string.error_invalid_email));
			focusView = etEmail;
			cancel = true;
		}
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			SyncUsuario syncUsuario = new SyncUsuario(MicroMan.configuracion, this);
			sincro = this;
			syncUsuario.setmObjetoSync(sincro);
			usuario.setUsuario(etEmail.getText().toString());
			usuario.setClave(etPassword.getText().toString());
			if (!MicroMan.detectorConexion.hayConexion() || autologin) {
				mAuthTask = new UserLoginTask();
				mAuthTask.execute((Void) null);
			} else {
				syncUsuario.setmTrans(MicroMan.mTrans);
				syncUsuario.setUsuario(usuario);
				syncUsuario.execute(Sincronizador.OP_IMPORTAR);
			}
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private void registracion() {
		FragmentManager fm = getSupportFragmentManager();
		DialogRegistro dlgRegistro = new DialogRegistro(OnNegocio.ACCION_NUEVO, new Usuario(),
				R.layout.dlg_registracion);
		dlgRegistro.setRetainInstance(true);
		dlgRegistro.setActividadLlamada(this);
		dlgRegistro.show(fm, "Registracion");
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate the
	 * user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			Boolean resultado = false;
			try {
				OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
				resultado = oUsuario.autorizarUsuario(mEmail, mPassword);

			} catch (Exception e) {
				return false;
			}

			if (!resultado) {
				return false;
			}
			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;

			if (success) {
				sincro.ejecucionCorrecta();

			} else {
				sincro.ejecucionIncorrecta();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}

	@Override
	public void ejecutarSync() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejecucionCorrecta() {
		OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
		usuario = oUsuario.extraer(usuario.getUsuario(), usuario.getClave());
		oUsuario.setUsuario(usuario);
		oUsuario.getUsuario().setEstado(1);
		oUsuario.resetEstado();
		MicroMan.idUsuario = usuario.getIdUsuario();
		oUsuario.actualizarEstado();
		this.finish();
		Intent intent = new Intent(getApplicationContext(), MicroMan.class);
		startActivity(intent);
	}

	@Override
	public void ejecucionIncorrecta() {
		etPassword.setError(getString(R.string.error_incorrect_password));
		etPassword.requestFocus();
	}
}
