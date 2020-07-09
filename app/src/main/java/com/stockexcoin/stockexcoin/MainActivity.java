package com.stockexcoin.stockexcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stockexcoin.stockexcoin.ui.coin.CoinFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch(destination.getId()){
                    case R.id.navigation_home:
                        navView.getMenu().getItem(0).setEnabled(false);
                        navView.getMenu().getItem(1).setEnabled(true);
                        navView.getMenu().getItem(2).setEnabled(true);
                        menu.clear();
                        break;
                    case R.id.navigation_coin:
                        navView.getMenu().getItem(0).setEnabled(true);
                        navView.getMenu().getItem(1).setEnabled(false);
                        navView.getMenu().getItem(2).setEnabled(true);
                        menu.clear();
                        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
                        MenuItem searchItem = menu.findItem(R.id.search);
                        SearchView searchView = (SearchView) searchItem.getActionView();
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                CoinFragment.doSearch(newText);
                                return false;
                            }
                        });
                        break;
                    case R.id.navigation_settings:
                        navView.getMenu().getItem(0).setEnabled(true);
                        navView.getMenu().getItem(1).setEnabled(true);
                        navView.getMenu().getItem(2).setEnabled(false);
                        menu.clear();
                        getMenuInflater().inflate(R.menu.toolbar_info, menu);
                        break;
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Log.d("Toolbar","search");
                break;
            case R.id.sort:
                Log.d("Toolbar","sort");
                CoinFragment.doSort();
                break;
            case R.id.toolbar_info:
                Log.d("Toolbar","info");
                AlertDialog adb = new AlertDialog.Builder(this).setTitle(R.string.info_title).setMessage(R.string.info_message).create();
                adb.show();
                ((TextView) Objects.requireNonNull(adb.findViewById(android.R.id.message))).setMovementMethod(LinkMovementMethod.getInstance());

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getBaseContext()));
        setTheme(settings.getString("theme_selected", "dark").equals("dark")?
                R.style.DarkTheme:R.style.LightTheme);
        if (settings.getBoolean("service_selected", false)==true){
            stopService(new Intent(this,StockExCoinService.class));
            startService(new Intent(this,StockExCoinService.class));}
        else stopService(new Intent(this,StockExCoinService.class));


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }



}
