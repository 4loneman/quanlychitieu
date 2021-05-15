package com.app.quanlychitieu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.quanlychitieu.R;
import com.app.quanlychitieu.model.DanhMucThuChi;

import java.util.ArrayList;
import java.util.List;

public class DanhMucAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<DanhMucThuChi> danhMucThuChiList;

    public DanhMucAdapter(Context context, int layout, List<DanhMucThuChi> danhMucThuChiList) {
        this.context = context;
        this.layout = layout;
        this.danhMucThuChiList = danhMucThuChiList;
    }

    @Override
    public int getCount() {
        return danhMucThuChiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        TextView textView = convertView.findViewById(R.id.txtdanhmuc);
        textView.setText(danhMucThuChiList.get(position).getTen());
        ImageView imageView = convertView.findViewById(R.id.imgdanhmuc);
        imageView.setImageBitmap(danhMucThuChiList.get(position).getHinhanh());
        return convertView;
    }
}
