package ar.com.twoboot.microman.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.util.DetectorConexion;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.dominio.OnUsuario;
import ar.com.twoboot.panico.objetos.Usuario;
import ar.com.twoboot.panico.servicios.UbicacionOtra;
import ar.com.twoboot.panico.servicios.UbicacionServicioWidget;

public class BaseWidget extends AppWidgetProvider {
    public final String ACTION_SEND_SMSYREST = "SMSYREST";
    public final String ACTION_SEND_DB = "DB";
    public final String ACTION_SEND_WS = "WS";
    public final String ACTION_SEND_REST = "REST";
    private RemoteViews vista;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Intent sIntent = new Intent(context, UbicacionOtra.class);
        Bundle b = intent.getExtras();
        SharedPreferences prefs = context.getSharedPreferences("configuracion", Context.MODE_PRIVATE);
        Boolean utilizar = prefs.getBoolean("uwidget", true);
        if (b != null) {
            String action = (String) b.get("ACCION");
            if (action != null && action.equals(ACTION_SEND_SMSYREST)) {
                MicroMan.detectorConexion = new DetectorConexion(context);
                MicroMan.mTrans = new Transaccion(context);
                MicroMan.mTrans.conectarDB();
                OnUsuario oUsuario = new OnUsuario(MicroMan.mTrans);
                final Usuario usuario = oUsuario.comprobarAutoLogin();
                if (usuario == null) {
                    Toast.makeText(context, "Usted no esta logueado!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (utilizar) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                    ComponentName thisWidget = new ComponentName(context, BaseWidget.class);
                    remoteViews.setTextViewText(R.id.txt_estado_widget, "Pidiendo Ayuda...");
                    appWidgetManager.updateAppWidget(thisWidget, remoteViews);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.stopService(sIntent);
                        context.startForegroundService(sIntent);
                    } else {
                        context.stopService(sIntent);
                        context.startService(sIntent);
                    }
                } else {
                    Toast.makeText(context, "Widget Desactivado!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    @Override

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[0];
            vista = new RemoteViews(context.getPackageName(), R.layout.widget);
            Intent intent = new Intent(context, BaseWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra("ACCION", ACTION_SEND_SMSYREST);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            vista.setOnClickPendingIntent(R.id.bPanico, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, vista);
        }
    }
}