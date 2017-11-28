package com.example.ccy.tes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.ccy.tes.R;
import com.example.ccy.tes.ui.activity.MyImageView;
import com.example.ccy.tes.model.LinkNode;

import java.util.List;


public class MenuAdapter extends ArrayAdapter<LinkNode> {
	private int resource;


	public MenuAdapter(Context context, int resource, List<LinkNode> objects) {
		super(context,resource,objects);
		this.resource = resource;


	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinkNode item = getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, null);
			viewHolder = new ViewHolder();
			viewHolder.view = (MyImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}
		//Bitmap bitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.paper_back_eight)).getBitmap();
		//viewHolder.view.setImageBitmap(bitmap);
//		viewHolder.view.drawTextAtBitmap(bitmap,item.getTitle());
		return convertView;
	}

	class ViewHolder {
		MyImageView view;
	}
}
