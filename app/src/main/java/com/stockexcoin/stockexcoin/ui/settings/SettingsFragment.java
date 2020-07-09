package com.stockexcoin.stockexcoin.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;

import com.stockexcoin.stockexcoin.StockExCoinService;
import com.takisoft.preferencex.PreferenceFragmentCompat;

import com.stockexcoin.stockexcoin.R;

import java.util.Objects;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "theme_selected":
                Objects.requireNonNull(getActivity()).recreate();
                break;
            case "service_selected":
                if(getPreferenceManager().getSharedPreferences().getBoolean("service_selected",false)){
                    Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(),StockExCoinService.class));
                }
                else
                    Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(),StockExCoinService.class));
                break;
        }

    }


}
