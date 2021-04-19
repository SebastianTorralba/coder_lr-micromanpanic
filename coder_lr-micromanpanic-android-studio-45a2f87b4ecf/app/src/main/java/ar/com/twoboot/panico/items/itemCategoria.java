package ar.com.twoboot.panico.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.twoboot.microman.items.ItemListAdapter;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.panico.R;
import ar.com.twoboot.panico.R.id;
import ar.com.twoboot.panico.objetos.Categoria;

public class itemCategoria extends ItemListAdapter {
	private TextView tvCategoria;
	private ImageView ivIcono;
	private Categoria categoria;
	private Activity actividad;

	public itemCategoria(Activity paramActivity, ArrayList paramArrayList) {

		super(paramActivity, paramArrayList);
		actividad = paramActivity;
	}

	@Override
	public void inicializarVistaItem(DalusObject paramDalusObject) {
		this.vi = ((LayoutInflater) this.activity.getSystemService("layout_inflater")).inflate(R.layout.item_categoria,
				null);
		categoria = (Categoria) paramDalusObject;
		tvCategoria = (TextView) vi.findViewById(id.tvCategoria);
		tvCategoria.setText(categoria.toString());
		ivIcono = (ImageView) vi.findViewById(id.iv_icono);
		if (categoria.getCodCategoria().equals("ALUPUB")) {
		}
		if (categoria.getCodCategoria().equals("ESPVERDE")) {
		}
		if (categoria.getCodCategoria().equals("SEMAFORO")) {
		}
		if (categoria.getCodCategoria().equals("BACHEO")) {
		}
		if (categoria.getCodCategoria().equals("OTROS")) {
		}
	}

}
