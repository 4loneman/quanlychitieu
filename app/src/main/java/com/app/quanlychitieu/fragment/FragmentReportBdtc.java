package com.app.quanlychitieu.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentReportBdtc extends Fragment {
    View view;
    BarChart barChart;
    RadioGroup radioGroup;
    List<BarEntry> barEntries, barEntriesThu, barEntriesChi;
    int flag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_baocao_bdtc, container, false);
        barEntriesChi = MainActivity.khoanThuChiDao.getDatasetBarChart(1);
        barEntriesThu = MainActivity.khoanThuChiDao.getDatasetBarChart(0);
        barEntries = barEntriesThu;
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {
        barChart = (BarChart) view.findViewById(R.id.barchart);
        barChart.getDescription().setText(getContext().getString(R.string.baocaothuchitheothang));
        radioGroup = view.findViewById(R.id.ragroup);

        // loc theo muc thu chi
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radthu:
                        barEntries = barEntriesThu;
                        flag = 0;
                        break;
                    case R.id.radchi:
                        barEntries = barEntriesChi;
                        flag = 1;
                        break;
                }
                dataChange();

            }
        });


        dataChange();
    }

    public void dataChange() {
        BarDataSet dataSet = new BarDataSet(barEntries, getContext().getString(R.string.thang));
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.animateY(1400);
    }
}
