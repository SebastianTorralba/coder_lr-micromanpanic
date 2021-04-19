package ar.com.twoboot.panico;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {
    private Switch switch_pd, switch_uw, switch_gps;
    private EditText segundos;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        switch_pd = (Switch) findViewById(R.id.switch_pd);
        switch_uw = (Switch) findViewById(R.id.switch_uw);
        switch_gps = (Switch) findViewById(R.id.switch_gps);
        segundos = (EditText) findViewById(R.id.edit_segs);
        prefs = getSharedPreferences("configuracion", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Boolean mostrarenbloqueo = prefs.getBoolean("mbloqueo", true);
        int seg = prefs.getInt("sbloqueo", 5);
        Boolean utilizarenwidget = prefs.getBoolean("uwidget", true);
        Boolean mantenergpsact = prefs.getBoolean("mgpsact", false);
        switch_pd.setChecked(mostrarenbloqueo);
        segundos.setText(String.valueOf(seg));
        switch_uw.setChecked(utilizarenwidget);
        switch_gps.setChecked(mantenergpsact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void guardar(View v) {
        String s = segundos.getText().toString();
        if (!s.isEmpty() && Integer.valueOf(s) >= 5) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("mbloqueo", switch_pd.isChecked());
            editor.putBoolean("uwidget", switch_uw.isChecked());
            editor.putBoolean("mgpsact", switch_gps.isChecked());
            editor.putInt("sbloqueo", Integer.valueOf(s));
            editor.commit();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

}
