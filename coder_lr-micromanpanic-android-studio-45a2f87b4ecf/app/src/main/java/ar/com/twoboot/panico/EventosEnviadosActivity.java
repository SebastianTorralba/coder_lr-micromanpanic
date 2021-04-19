package ar.com.twoboot.panico;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.util.DetectorConexion;
import ar.com.twoboot.panico.dominio.OnEvento;
import ar.com.twoboot.panico.objetos.Evento;

public class EventosEnviadosActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ArrayList<Evento> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_enviados);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa_eventos);
        mapFragment.getMapAsync(this);
        MicroMan.detectorConexion = new DetectorConexion(getApplicationContext());
        MicroMan.mTrans = new Transaccion(getApplicationContext());
        MicroMan.mTrans.conectarDB();
        OnEvento oevento = new OnEvento(MicroMan.mTrans);
        eventos = oevento.getEventos();
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
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng lr = new LatLng(-29.418190, -66.861634);
        mMap.addMarker(new MarkerOptions().position(lr).title("La Rioja"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lr, 14));
        if (eventos != null) {
            for (Evento evento :
                    eventos) {
                LatLng posicion = new LatLng(evento.getLat(), evento.getLon());
                Date fecha = evento.getFecha();
                SimpleDateFormat dformat = new SimpleDateFormat("dd/M/yyyy");
                String titulo = dformat.format(fecha);
                String datos = "Radio " + String.valueOf(evento.getPrecision() + " mts");
                CircleOptions circleOptions = new CircleOptions()
                        .center(posicion)
                        .radius(evento.getPrecision())
                        .fillColor(Color.TRANSPARENT)
                        .strokeColor(Color.RED)
                        .strokeWidth(5);
                mMap.addCircle(circleOptions);
                mMap.addMarker(new MarkerOptions()
                        .position(posicion)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.iso_1_min))
                        .snippet(datos)
                        .title(titulo));
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}
