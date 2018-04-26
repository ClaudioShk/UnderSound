package com.example.shioka.navigationdrawer.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.shioka.navigationdrawer.Fragments.AvailableSongsFragment;
import com.example.shioka.navigationdrawer.Fragments.ContactUsFragment;
import com.example.shioka.navigationdrawer.Fragments.AboutFragment;
import com.example.shioka.navigationdrawer.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setToolbar();
        getSupportActionBar();
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navView);

        setFragmentByDefault();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fragmentTransaction = false;
                android.support.v4.app.Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.menuContactUs:
                        fragment = new ContactUsFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menuAvailableSongs:
                        fragment = new AvailableSongsFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menuAbout:
                        fragment = new AboutFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menuLogOut:
                        removeSharedPreferences();
                        logOut();
                        break;
                }
                if(fragmentTransaction){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,fragment)
                            .commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    private void setFragmentByDefault(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,new AvailableSongsFragment())
                .commit();
        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void logOut(){
        Intent i = new Intent(this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
