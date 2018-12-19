package com.vuong.ebookapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_Books extends ArrayAdapter<ItemBooks> {
	
	private Activity activity;
	private List<ItemBooks> itemsAllnews;
	private ItemBooks objAllBean;
	private int row;
	public ImageLoader imageLoader; 
	 
	
	public Adapter_Books(Activity act, int resource, List<ItemBooks> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllnews = arrayList;
		imageLoader=new ImageLoader(activity);
	 
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((itemsAllnews == null) || ((position + 1) > itemsAllnews.size()))
			return view;

		objAllBean = itemsAllnews.get(position);
		
		holder.txt=(TextView)view.findViewById(R.id.txt_allnews_categty);
        holder.txt2=(TextView)view.findViewById(R.id.txt_allnews_author);
		holder.img_cat=(ImageView)view.findViewById(R.id.img_cat);

		holder.txt.setText(objAllBean.getCategoryName().toString());
        holder.txt2.setText(objAllBean.getCategoryAuthorName().toString());
		imageLoader.DisplayImage(Constant.SERVER_IMAGE_CATEGORY+objAllBean.getCategoryImageurl().toString(), holder.img_cat);
		
		return view;
		
	}

	public class ViewHolder {
	 
		public TextView txt;
        public TextView txt2;
		public ImageView img_cat;
	}

}
