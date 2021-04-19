package ar.com.twoboot.panico.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import ar.com.twoboot.panico.MicroMan;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.objetos.Categoria;
import ar.com.twoboot.panico.objetos.Cuestionario;
import ar.com.twoboot.panico.objetos.CuestionarioPreguntas;
import ar.com.twoboot.panico.objetos.Pregunta;
import ar.com.twoboot.panico.objetos.Respuesta;

public class FactoryPreguntas {
	private Context contexto;
	private final static String TAG_OBJETOS = "cuestionario";
	private View vista;
	private HashMap<Pregunta, View> respuestas;
	private HashMap<Pregunta, View> respuestasExtensiones;
	private Categoria categoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public HashMap<Pregunta, View> getRespuestasExtensiones() {
		return respuestasExtensiones;
	}

	public HashMap<Pregunta, View> getRespuestas() {
		return respuestas;
	}

	public FactoryPreguntas(Context contexto, View vista) {
		this.contexto = contexto;
		this.vista = vista;
		respuestas = new HashMap<Pregunta, View>();
		respuestasExtensiones = new HashMap<Pregunta, View>();
		if (vista instanceof LinearLayout) {
			((LinearLayout) vista).removeAllViews();
		}
	}

	private int eliminarPreguntas() {
		int rtn = 0;
		if (vista instanceof LinearLayout) {
			int cantidadObjetos = ((LinearLayout) vista).getChildCount();
			for (int i = 0; i < cantidadObjetos; i++) {
				View v = ((LinearLayout) vista).getChildAt(i);
				if (v.getTag().equals(TAG_OBJETOS))
					;
				((LinearLayout) vista).removeAllViews();
			}
		}
		return rtn;
	}

	public void generarPreguntas(Pregunta pregunta) {

		if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("CHECKBOX")) {
			CheckBox cb = new CheckBox(contexto);
			cb.setTag(TAG_OBJETOS);
			cb.setText(pregunta.getPregunta());
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(cb);
			}
			respuestas.put(pregunta, cb);
		}
		if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("COMBOBOX")) {
			TextView tv = new TextView(contexto);
			tv.setTextAppearance(contexto, R.style.ZondaPregunta);

			tv.setText(pregunta.getPregunta());
			tv.setTag(TAG_OBJETOS);
			Spinner sp = new Spinner(contexto);
			ArrayAdapter<Respuesta> apRespuestas = new ArrayAdapter<Respuesta>(contexto,
					android.R.layout.simple_spinner_item, pregunta.getrespuestasPregunta());
			apRespuestas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(apRespuestas);
			sp.setTag(TAG_OBJETOS);
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(tv);
				((LinearLayout) vista).addView(sp);
			}
			respuestas.put(pregunta, sp);

		}
		if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("NUMERICA")) {
			TextView tv = new TextView(contexto);
			tv.setTextAppearance(contexto, R.style.ZondaPregunta);
			tv.setText(pregunta.getPregunta());
			tv.setTag(TAG_OBJETOS);
			EditText et = new EditText(contexto);
			et.setTag(TAG_OBJETOS);
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(tv);
				((LinearLayout) vista).addView(et);
			}
			respuestas.put(pregunta, et);

		}
		if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("TEXTO")) {
			TextView tv = new TextView(contexto);
			tv.setText(pregunta.getPregunta());

			tv.setTextAppearance(contexto, R.style.ZondaPregunta);
			EditText et = new EditText(contexto);
			tv.setTag(TAG_OBJETOS);
			et.setTag(TAG_OBJETOS);
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(tv);
				((LinearLayout) vista).addView(et);
			}
			respuestas.put(pregunta, et);
		}
		if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("FECHA")) {
			TextView tv = new TextView(contexto);
			tv.setText(pregunta.getPregunta());

			tv.setTextAppearance(contexto, R.style.ZondaPregunta);
			EditText et = new EditText(contexto);
			tv.setTag(TAG_OBJETOS);
			et.setTag(TAG_OBJETOS);
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(tv);
				((LinearLayout) vista).addView(et);
			}
			respuestas.put(pregunta, et);
		}
		if (pregunta.getEsExtendible() == 1) {
			EditText et = new EditText(contexto);
			et.setTag(TAG_OBJETOS);
			if (vista instanceof LinearLayout) {
				((LinearLayout) vista).addView(et);
				et.setText("");
			}
			respuestasExtensiones.put(pregunta, et);
		}
	}

	public void consolidarRespuestasCuestionario(Cuestionario cuestionario) {
		ArrayList<CuestionarioPreguntas> cuestionarioPreguntas = new ArrayList<CuestionarioPreguntas>();
		Set<Entry<Pregunta, View>> a = respuestas.entrySet();
		for (Entry<Pregunta, View> entry : a) {
			CuestionarioPreguntas cuestionarioPregunta = new CuestionarioPreguntas();
			cuestionarioPregunta.setCuestionario(cuestionario);
			Pregunta pregunta = entry.getKey();
			cuestionarioPregunta.setPregunta(pregunta);
			if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("CHECKBOX")) {
				CheckBox cb = (CheckBox) entry.getValue();
				if (cb.isChecked()) {
					cuestionarioPregunta.setRespuesta(new OnRespuesta(MicroMan.mTrans).extraer("SI"));
				} else {
					cuestionarioPregunta.setRespuesta(new OnRespuesta(MicroMan.mTrans).extraer("NO"));
				}
			}
			if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("COMBOBOX")) {
				Spinner sp = (Spinner) entry.getValue();
				Respuesta r = (Respuesta) sp.getSelectedItem();
				cuestionarioPregunta.setRespuesta(r);
			}
			if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("NUMERICA")) {
				EditText et = (EditText) entry.getValue();
				cuestionarioPregunta.setRespuesta(new Respuesta(et.getText().toString()));
			}
			if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("TEXTO")) {
				EditText et = (EditText) entry.getValue();

				cuestionarioPregunta.setRespuesta(new Respuesta(et.getText().toString()));
			}
			if (pregunta.getTipoRepuesta().getCodTipoRespuesta().equals("FECHA")) {
				EditText et = (EditText) entry.getValue();

				cuestionarioPregunta.setRespuesta(new Respuesta(et.getText().toString()));
			}
			if (pregunta.getEsExtendible() == 1) {
				// aca buscar en extendible
				EditText et = (EditText) respuestasExtensiones.get(pregunta);
				if (et != null) {
					cuestionarioPregunta.setExtension(et.getText().toString());
				}
			}
			cuestionarioPreguntas.add(cuestionarioPregunta);
		}
		cuestionario.setCuestionarioPreguntas(cuestionarioPreguntas);
	}
}
