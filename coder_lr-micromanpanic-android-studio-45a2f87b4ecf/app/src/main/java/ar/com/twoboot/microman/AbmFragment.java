package ar.com.twoboot.microman;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.OnNegocio.IAccion;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.ImagenCoDec;
import ar.com.twoboot.panico.MicroMan;

public class AbmFragment extends FragmentActivity implements IAccion{
	
	public AbmFragment() {
		super();
		mTrans=MicroMan.mTrans;
		
	}

	protected int accion;
	protected View rootView;
	protected AlertDialog.Builder builder;
	DalusObject objetoOriginal;
	DalusObject objetoInterno;
	OnNegocio oNegocio;
	private int layout;
	protected String titulo = "";
	protected Transaccion mTrans;
	protected Activity actividadLlamada;
	private boolean resultado;
	protected boolean usaSincronizador = false;
	private Sincronizador sincronizador;
	protected boolean grabacionLocal = false;
	protected boolean grabacionRemota = false;
	protected boolean existeConexionInternet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inicializacion();
		
		switch (accion) {
		case OnNegocio.ACCION_NUEVO:
			accionNuevo();
			break;
		case OnNegocio.ACCION_MODIFICAR:
			accionModificar();
			break;
		case OnNegocio.ACCION_VER:
			accionVer();
			break;
		case OnNegocio.ACCION_ELIMINAR:
			accionEliminar();
			break;

		default:
			break;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	public AbmFragment(int accion, DalusObject objeto) {
		super();
		mTrans = MicroMan.mTrans;
		this.accion = accion;
		this.objetoOriginal = objeto;
		//this.layout = layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	protected void inicializacion() {

	}

	protected void popular() {

	}

	

	public int aceptar(){
		int resultado = 0;
		Boolean cerrar = false;
		Boolean rtn = false;
		switch (accion) {
		case OnNegocio.ACCION_NUEVO:
			objetoInterno = consolidar();
			rtn = validar();
			if (rtn) {
				if (!usaSincronizador) {
					resultado = grabar(objetoInterno);
					if (resultado > 1) {
						grabacionLocal = true;
					}
					cerrar = true;
				} else {
					ejecutarSincronizador();
				}
			} else {
			}
			break;
		case OnNegocio.ACCION_MODIFICAR:
			objetoInterno = consolidar();
			rtn = validar();
			if (rtn) {
				resultado = grabar(objetoInterno);
				cerrar = true;
			} else {

			}
			break;
		case OnNegocio.ACCION_VER:
			break;
		case OnNegocio.ACCION_ELIMINAR:
			break;

		default:
			break;
		}
		return resultado;

	
	}

	@Override
	public int accionNuevo() {
		return 0;
	}

	@Override
	public int accionModificar() {
		popular();
		return 0;
	}

	@Override
	public int accionVer() {
		popular();
		return 0;
	}

	@Override
	public int accionEliminar() {
		popular();
		return 0;
	}

	@Override
	public DalusObject consolidar() {
		return null;
	}

	public boolean validar() {
		// TODO Auto-generated method stub
		return true;
	}

	public int grabar(DalusObject objeto) {
		return 0;

	}

	public static final int posicionEnSpinner(Spinner sp, Object o) {
		int pos = 0;
		if (o != null) {
			ArrayAdapter ags = (ArrayAdapter) sp.getAdapter();
			Log.i("DALUS SPINNER", o.toString());
			pos = ags.getPosition(o);
			Log.i("DALUS SPINNER", "Posicion: " + pos);
			sp.setSelection(pos);
		} else {
			pos = 0;
		}

		return pos;
	}

	private class CustomListener implements View.OnClickListener {
		private final Dialog dialog;

		public CustomListener(Dialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onClick(View v) {

			// Do whatever you want here

			// If tou want to close the dialog, uncomment the line below
			// dialog.dismiss();
		}
	}
	public Boolean cancelar(){
		//dismiss();
		return true;
	}
	public Activity getActividadLlamada() {
		return actividadLlamada;
	}

	public void setActividadLlamada(Activity actividadLlamada) {
		this.actividadLlamada = actividadLlamada;
	}
	
	protected void mostrarAdvertencia(String mensaje) {
		AlertDialog.Builder adHitos = new AlertDialog.Builder(this);
		adHitos.setTitle("Advertencia");
		adHitos.setMessage(mensaje);
		adHitos.setCancelable(true);
		adHitos.show();

	}
	protected void mostrarAdvertenciaToast(String mensaje) {
		Context context=this;
		if (context!=null)
		{Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();}

	}

	protected Boolean mostrarPregunta(String mensaje) {
		resultado=false;
		AlertDialog.Builder adHitos = new AlertDialog.Builder(this);
		adHitos.setTitle("Esta Seguro");
		adHitos.setMessage(mensaje);
		adHitos.setCancelable(true);
		adHitos.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resultado=true;
			}
			
		});
		adHitos.setNegativeButton("No",  new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resultado=true;
			}
			
		});

		adHitos.show();
		return resultado;
	}
	public void mostrarImagen(byte[] foto) {
	    Dialog builder = new Dialog(this);
	    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    builder.getWindow().setBackgroundDrawable(
	        new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialogInterface) {
	            //nothing;
	        }
	    });

	    ImageView imageView = new ImageView(this);
	    
	    Bitmap mImageBitmap = (Bitmap) ImagenCoDec.getImage(foto);
	    imageView.setImageBitmap(mImageBitmap);
	    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT, 
	            ViewGroup.LayoutParams.MATCH_PARENT));
	    builder.show();
	}	
	public void mostrarImagen(Bitmap imagenBitmap) {
	    Dialog builder = new Dialog(this);
	    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    builder.getWindow().setBackgroundDrawable(
	        new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialogInterface) {
	            //nothing;
	        }
	    });

	    ImageView imageView = new ImageView(this);
	    
	    imageView.setImageBitmap(imagenBitmap);
	   
	    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
	            ViewGroup.LayoutParams.MATCH_PARENT, 
	            ViewGroup.LayoutParams.MATCH_PARENT));
	    builder.show();
	}

	public Boolean ejecutarSincronizador() {
		return true;
		// TODO Auto-generated method stub

	}



	protected Boolean mostrarAdvertencia(String titulo, String mensaje) {
		resultado = false;
		AlertDialog.Builder adHitos = new AlertDialog.Builder(this);
		adHitos.setTitle(titulo);
		adHitos.setMessage(mensaje);
		adHitos.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						resultado = true;
					}

				});

		adHitos.show();

		return resultado;
	}

	public Sincronizador getSincronizador() {
		return sincronizador;
	}

	public void setSincronizador(Sincronizador sincronizador) {
		this.sincronizador = sincronizador;
	}

}
