package ar.com.twoboot.panico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.dominio.OnCuestionario;

public class SincronizacionActivity extends Activity {

	private ListView lvSync;
	EditText input;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Sincronizacion");
		setContentView(R.layout.activity_sincronizacion);
		context = this;
		lvSync = (ListView) findViewById(id.lvSync);
		lvSync.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				String item = (String) lvSync.getAdapter().getItem(position);
				if (item.equals("Actualizar")) {
					Intent intent = new Intent(getApplicationContext(), ActualizarActivity.class);
					startActivity(intent);
				} else if (item.equals("Reset DB Local")) {
					AlertDialog.Builder adRuta = new AlertDialog.Builder(view.getContext());
					adRuta.setTitle("Eliminacion Los Datos de la BD");
					adRuta.setMessage("Desea Continuar?");
					adRuta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							OnCuestionario oCuestionario = new OnCuestionario(MicroMan.mTrans);

							oCuestionario.eliminarTodo();
							Toast.makeText(context, "Base Reiniciada", Toast.LENGTH_SHORT).show();
						}
					});
					adRuta.setNegativeButton("No", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the Cancel button is
							// clicked
						}
					});
					adRuta.show();
				}
			}
		});

		// Show the Up button in the action bar.
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

}
