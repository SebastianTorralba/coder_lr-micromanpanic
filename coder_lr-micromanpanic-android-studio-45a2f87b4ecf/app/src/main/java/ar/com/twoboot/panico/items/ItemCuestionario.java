package ar.com.twoboot.panico.items;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.TextView;
import ar.com.twoboot.microman.items.ItemListAdapter;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.objetos.Cuestionario;

public class ItemCuestionario extends ItemListAdapter {
	private TextView tvIdCuestionario;
	private TextView tvEstado;
	private TextView tvfecha;
	private Cuestionario cuestionario;

	public ItemCuestionario(Activity paramActivity, ArrayList paramArrayList) {
		super(paramActivity, paramArrayList);
	}

	@Override
	public void inicializarVistaItem(DalusObject paramDalusObject) {
		this.vi = ((LayoutInflater) this.activity.getSystemService("layout_inflater"))
				.inflate(R.layout.item_cuestionario, null);
		cuestionario = (Cuestionario) paramDalusObject;
		tvIdCuestionario = (TextView) vi.findViewById(id.tvIdCuestionario);
		tvIdCuestionario.setText(String.valueOf(cuestionario.getIdCuestionario()));
		tvfecha = (TextView) vi.findViewById(id.tvFecha);
		tvfecha.setText(Util.formatearFecha(cuestionario.getFechaCarga()));
		tvEstado = (TextView) vi.findViewById(id.tvEstado);
		actualizarEstado();
	}

	public void actualizarEstado() {
		String estado = "";
		if (cuestionario.getEstado() == 2) {
			this.vi.setBackgroundColor(Color.BLUE);
			estado = "Transmitido";
		} else {
			estado = "Sin enviar";
		}
		tvEstado.setText(estado);
	}
}
