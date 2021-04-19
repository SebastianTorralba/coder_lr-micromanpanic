package ar.com.twoboot.panico;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.twoboot.microman.util.Permisos;
import ar.com.twoboot.microman.util.ServicioTool;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.servicios.BootServicio;

public class SplashActivity extends AppCompatActivity {
	private TextView tvVersion;
	private boolean solicitando;
	private Context contexto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tvVersion = (TextView) findViewById(id.tvVersion);
		tvVersion.setText("Version: " + Util.VERSION_MICROMAN.toString());
		solicitando = false;
		contexto = this;
		if (!ServicioTool.estaVivo(this, BootServicio.class)) {
			startService(new Intent(this, BootServicio.class));
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
		case Permisos.REQUEST: {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
					&& grantResults[1] == PackageManager.PERMISSION_GRANTED
					&& grantResults[2] == PackageManager.PERMISSION_GRANTED
					&& grantResults[3] == PackageManager.PERMISSION_GRANTED
					&& grantResults[4] == PackageManager.PERMISSION_GRANTED
					) {
				lanzarLogin();
				finish();
			} else {
				Toast.makeText(contexto, "Es necesario que concedas los permisos", Toast.LENGTH_LONG).show();
				solicitando = false;
				finish();
			}
		}
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!solicitando) {
			if (Permisos.check(contexto)) {
				lanzarLogin();
			} else {
				solicitando = true;
				Permisos.solicitar(contexto);
			}
		}
	}

	private void lanzarLogin() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}
}
