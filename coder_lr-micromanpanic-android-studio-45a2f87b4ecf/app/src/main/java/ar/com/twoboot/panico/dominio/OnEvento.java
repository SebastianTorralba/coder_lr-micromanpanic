package ar.com.twoboot.panico.dominio;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.Transaccionable;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.objetos.Evento;

public class OnEvento extends OnNegocio implements Transaccionable {
    private Evento evento;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public OnEvento(Transaccion paramTransaccion) {
        super(paramTransaccion);
        setNombreTabla("eventos_enviados");
        // TODO Auto-generated constructor stub
    }

    @Override
    public int actualizar() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int eliminar() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insertar() {
        // TODO Auto-generated method stub
        long rtn = 0;

        try {
            getTransaccion().baseDatos.beginTransaction();
            valores.put("id_usuario", evento.getUsuario().getIdUsuario());
            valores.put("lat", evento.getLat());
            valores.put("lon", evento.getLon());
            valores.put("precision", evento.getPrecision());
            valores.put("tipo", evento.getTipo());
            valores.put("fecha", evento.getFecha().getTime());
            rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(), null, getValores());
            if (rtn > 0) {
                getTransaccion().baseDatos.setTransactionSuccessful();
                Log.i(Util.APP, getNombreTabla() + "Insertar" + rtn);
            } else {
                // getTransaccion().baseDatos.
            }
            getTransaccion().baseDatos.endTransaction();
        } catch (SQLException e) {
            // TODO: handle exception
            Log.e(Util.APP, e.getMessage());
        }
        return (int) rtn;
    }

    public ArrayList<Evento> getEventos() {
        ArrayList<Evento> eventos = new ArrayList();
        String selectQuery = "select * from eventos_enviados where id_usuario=" + MicroMan.idUsuario;
        Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
        Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()) {
            Evento evento = new Evento();
            evento.setLat(cursor.getDouble(1));
            evento.setLon(cursor.getDouble(2));
            evento.setPrecision(cursor.getFloat(3));
            Date fecha = new Date(cursor.getLong(5));
            evento.setFecha(fecha);
            eventos.add(evento);
        }
        cursor.close();
        return eventos;
    }

    @Override
    public int validar() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void eliminarTodo(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminarTodo() {
        // TODO Auto-generated method stub

    }

}
