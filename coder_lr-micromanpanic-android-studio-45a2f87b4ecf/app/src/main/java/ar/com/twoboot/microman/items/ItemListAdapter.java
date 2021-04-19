package ar.com.twoboot.microman.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import ar.com.twoboot.microman.objetos.DalusObject;

public class ItemListAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList items;
	protected View vi;

	public ItemListAdapter(Activity paramActivity, ArrayList paramArrayList) {
		this.activity = paramActivity;
		this.items = paramArrayList;
	}

	public int getCount() {
		return this.items.size();
	}

	public Object getItem(int paramInt) {
		return this.items.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		inicializarVistaItem((DalusObject) this.items.get(paramInt));
		return this.vi;
	}

	public void inicializarVistaItem(DalusObject paramDalusObject) {
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.items.ItemListAdapter JD-Core Version: 0.6.0
 */