package com.app.quanlychitieu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.quanlychitieu.R;
import com.app.quanlychitieu.model.KhoanThuChi;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class KhoanThuChiAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<KhoanThuChi> khoanThuChiList;
    NumberFormat numberFormat;

    public KhoanThuChiAdapter(Context context, int layout, List<KhoanThuChi> khoanThuChiList) {
        this.context = context;
        this.layout = layout;
        this.khoanThuChiList = khoanThuChiList;
        numberFormat = NumberFormat.getInstance();
    }

    public void setKhoanThuChiList(List<KhoanThuChi> khoanThuChiList) {
        this.khoanThuChiList = khoanThuChiList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return khoanThuChiList.size();
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
        convertView = inflater.inflate(layout, null, false);
        ImageView imageView = convertView.findViewById(R.id.imgdanhmuc2);
        TextView ten = convertView.findViewById(R.id.tenkhoanthuchi2);
        TextView ngay = convertView.findViewById(R.id.thoigian);
        TextView sotien = convertView.findViewById(R.id.sotien2);
        imageView.setImageBitmap(khoanThuChiList.get(position).getDanhMucThuChi().getHinhanh());
        ten.setText(khoanThuChiList.get(position).getTen());
        String str = numberFormat.format(khoanThuChiList.get(position).getTien());
        sotien.setText(str + "đ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ngay.setText(simpleDateFormat.format(khoanThuChiList.get(position).getThoigian().getTime()).toString());
        if(khoanThuChiList.get(position).getLoai()) convertView.setBackgroundColor(context.getResources().getColor(R.color.chi));
        else convertView.setBackgroundColor(context.getResources().getColor(R.color.thu));
        return convertView;
    }
}
