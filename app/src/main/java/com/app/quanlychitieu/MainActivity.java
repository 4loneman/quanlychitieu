package com.app.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.app.quanlychitieu.Data.DanhMucThuChiDAO;
import com.app.quanlychitieu.Data.KhoanThuChiDao;
import com.app.quanlychitieu.Data.ViDao;
import com.app.quanlychitieu.fragment.FragmentAdd;
import com.app.quanlychitieu.fragment.FragmentHome;
import com.app.quanlychitieu.fragment.FragmentReport;
import com.app.quanlychitieu.fragment.FragmentReportBdtc;
import com.app.quanlychitieu.fragment.FragmentReportHm;
import com.app.quanlychitieu.fragment.FragmentReportLstc;
import com.app.quanlychitieu.fragment.FragmentUser;
import com.app.quanlychitieu.fragment.FragmentWallet;
import com.app.quanlychitieu.model.DanhMucThuChi;
import com.app
.quanlychitieu.model.KhoanThuChi;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static CallbackManager callbackManager;
    public Fragment home, wallet, user, add, report, fragmentlstc, fragmenthm, fragmentBdtc;
    public static DanhMucThuChiDAO danhMucThuChiDAO;
    public static ViDao viDao;
    public static KhoanThuChiDao khoanThuChiDao;
    public String tagback;
    public String tagPresent;
    Fragment fragment;
    public static List<DanhMucThuChi> danhMucThuChis, danhMucChi, danhMucThu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        danhMucThuChiDAO = new DanhMucThuChiDAO(this);
        danhMucThuChiDAO.getWritableDatabase();
        danhMucThuChis = danhMucThuChiDAO.loadAll();
        danhMucThu = danhMucThuChiDAO.loadAllthu();
        danhMucChi = danhMucThuChiDAO.loadAllChi();
        viDao = new ViDao(this);
        khoanThuChiDao = new KhoanThuChiDao(this);

        callbackManager = CallbackManager.Factory.create();

        fragmentlstc = new FragmentReportLstc();
        fragmentBdtc = new FragmentReportBdtc();
        fragmenthm = new FragmentReportHm();
        home = new FragmentHome();
        add = new FragmentAdd();
        user = new FragmentUser();
        wallet = new FragmentWallet();
        report = new FragmentReport();
        fragment = home;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = home;
                        tagPresent = FragmentHome.class.getName();
                        break;
                    case R.id.wallet:
                        fragment = wallet;
                        tagPresent = FragmentWallet.class.getName();
                        break;
                    case R.id.user:
                        fragment = user;
                        break;
                    case R.id.add:
                        fragment = add;
                        break;
                    case R.id.report:
                        fragment = report;
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName()).show(fragment).commit();
                return true;
            }
        });
    }

    public void addFragmentAdd() {
        add = new FragmentAdd();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, add).commit();
    }

    public void addFragmentLsthuchi() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentReportLstc(), fragmentlstc.getClass().getSimpleName()).hide(fragment).show(fragmentlstc).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    public void addFragmentbcHangmuc() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmenthm, fragmenthm.getClass().getSimpleName()).hide(fragment).show(fragmenthm).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    public void addFragmentbdtc() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentBdtc, fragmentBdtc.getClass().getSimpleName()).hide(fragment).show(fragmentBdtc).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
