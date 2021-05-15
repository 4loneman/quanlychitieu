package com.app.quanlychitieu.adapter;

import android.app.Dialog;
import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.app.quanlychitieu.fragment.FragmentWallet;
import com.app.quanlychitieu.model.ViTien;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import androidx.fragment.app.Fragment;

public class ViAdapter extends BaseAdapter {
    Context context;
    Integer layout;
    List<ViTien> viList;
    NumberFormat numberFormat;


    public ViAdapter(Context context, Integer layout, List<ViTien> viList) {
        this.context = context;
        this.layout = layout;
        this.viList = viList;
        numberFormat = NumberFormat.getInstance();
    }

    public void setViList(List<ViTien> viList) {
        this.viList = viList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null, false);
        ImageView imageView = convertView.findViewById(R.id.wallet_img);
        final TextView textView = convertView.findViewById(R.id.tenthe);
        textView.setText(viList.get(position).getTen());
        TextView tongtien = convertView.findViewById(R.id.tongtien);
        String str = numberFormat.format(viList.get(position).getSodu());
        tongtien.setText(str + "đ");
        if (viList.get(position).getLoai() == 1) {
            imageView.setImageResource(R.drawable.tien_mat);
        }
        if (viList.get(position).getLoai() == 2) {
            imageView.setImageResource(R.drawable.credit_card);
        }

        if (layout == R.layout.item_list_vi) {
            ImageView edit = (ImageView) convertView.findViewById(R.id.edit_wallet);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_sua_so_du);
                    final EditText editText = dialog.findViewById(R.id.edtsodu);
                    editText.setText(viList.get(position).getSodu().toString());
                    TextView editText1 = dialog.findViewById(R.id.tenvichuyen);
                    editText1.setText(viList.get(position).getTen());
                    dialog.show();
                    Button huy = dialog.findViewById(R.id.btnthoat);
                    Button xacnhan = dialog.findViewById(R.id.btnluu);
                    huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Float sotien = Float.parseFloat(editText.getText().toString());
                            viList.get(position).setSodu(sotien);
                            MainActivity.viDao.upDateSodu(sotien, viList.get(position).getId());
                            notifyDataSetChanged();
                            MainActivity mainActivity = (MainActivity) context;
                            FragmentWallet fragmentWallet = (FragmentWallet) mainActivity.wallet;
                            fragmentWallet.setTongTien();
                            dialog.dismiss();
                        }
                    });

                }
            });


            ImageView send = (ImageView) convertView.findViewById(R.id.send_wallet);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_chuyen_khoan);
                    dialog.show();
                    final Spinner spinner = dialog.findViewById(R.id.spnchonvi);
                    ViAdapter viAdapter = new ViAdapter(context, R.layout.item_list_vi_2, viList);
                    spinner.setAdapter(viAdapter);
                    spinner.setSelection(0);
                    TextView tenvichuyen = dialog.findViewById(R.id.tenvichuyen);
                    tenvichuyen.setText(viList.get(position).getTen());
                    Button huy = dialog.findViewById(R.id.huy);
                    Button xacnhan = dialog.findViewById(R.id.xn);
                    final EditText sotien = dialog.findViewById(R.id.sotien);
                    huy.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   dialog.dismiss();
                                               }
                                           }
                    );
                    xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i = spinner.getSelectedItemPosition();
                            Float tienChuyen = Float.parseFloat(sotien.getText().toString());
                            Float sdtk1 = viList.get(position).getSodu() - tienChuyen;
                            Float sdtk2 = viList.get(i).getSodu() + tienChuyen;
                            viList.get(position).setSodu(sdtk1);
                            viList.get(i).setSodu(sdtk2);
                            MainActivity.viDao.upDateSodu(sdtk1, viList.get(position).getId());
                            MainActivity.viDao.upDateSodu(sdtk2, viList.get(i).getId());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                }
            });

            ImageView delete = (ImageView) convertView.findViewById(R.id.delete_wallet);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_xoa_vi);
                    dialog.show();
                    TextView tenvi = dialog.findViewById(R.id.tenvi);
                    tenvi.setText(viList.get(position).getTen());
                    Button huy = dialog.findViewById(R.id.btnthoat);
                    Button xoa = dialog.findViewById(R.id.btnxoa);
                    huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    xoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.viDao.xoaVi(viList.get(position).getId());
                            viList.remove(position);
                            notifyDataSetChanged();
                            MainActivity mainActivity = (MainActivity) context;
                            FragmentWallet fragmentWallet = (FragmentWallet) mainActivity.wallet;
                            fragmentWallet.setTongTien();
                            dialog.dismiss();
                        }
                    });
                }
            });


        }

        return convertView;
    }
}
