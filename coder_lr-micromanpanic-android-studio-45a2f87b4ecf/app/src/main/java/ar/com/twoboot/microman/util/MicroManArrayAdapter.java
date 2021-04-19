package ar.com.twoboot.microman.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class MicroManArrayAdapter<T> extends ArrayAdapter implements Filterable {
	public MicroManArrayAdapter(Context context, int textViewResourceId,
			List objects) {
		super(context, textViewResourceId, objects);

		this.items = (ArrayList<String>) objects;
		this.itemsAll = (ArrayList<String>) items.clone();
		this.suggestions = new ArrayList<String>();
	}

	 private ArrayList<String> items;
	    private ArrayList<String> itemsAll;
	    private ArrayList<String> suggestions;

	@Override
	public Filter getFilter() {
		Filter custom_filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				if (constraint != null) {
					suggestions.clear();
					for (Iterator iterator = itemsAll.iterator(); iterator
							.hasNext();) {
						String idMedidor = (String) iterator.next();
						if (idMedidor.toLowerCase().contains(
								constraint.toString().toLowerCase())) {
							suggestions.add(idMedidor);
						}
					}

					FilterResults filterResults = new FilterResults();
					filterResults.values = suggestions;
					filterResults.count = suggestions.size();
					return filterResults;
				} else {
					return new FilterResults();
				}
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				ArrayList<String> filteredList = (ArrayList<String>) results.values;
	            if(results != null && results.count > 0) {
	            	items.clear();
	            	items.addAll((Collection<? extends String>) results.values);
	                notifyDataSetChanged();
	                
	            }else{notifyDataSetInvalidated();}
			
			}
		};
		return custom_filter;
	}
}
