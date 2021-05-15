package com.app.quanlychitieu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentReport extends Fragment {
    View lsthuchi, hangmuc, bdtc, view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.fragment_report, container, false);
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {

        lsthuchi = view.findViewById(R.id.bclstc);
        lsthuchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.addFragmentLsthuchi();
            }
        });
        bdtc = view.findViewById(R.id.bdtc);
        bdtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.addFragmentbdtc();
            }
        });

        hangmuc = view.findViewById(R.id.reporthangmuc);
        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.addFragmentbcHangmuc();
            }
        });
    }
}
