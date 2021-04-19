package ar.com.twoboot.panico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import ar.com.twoboot.panico.adapters.RVAdapterTelefonos;

public class TelUtilesActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_utiles);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("Bomberos");
        telefonos.add("Policia");
        telefonos.add("Asistencia al Niño");
        telefonos.add("Hospital Dr. Enrique Vera Barros");
        telefonos.add("Emergencia Medica");
        telefonos.add("Violencia de Genero");
        RVAdapterTelefonos adapter = new RVAdapterTelefonos(telefonos, getApplicationContext());
        rv.setAdapter(adapter);
    }
}
