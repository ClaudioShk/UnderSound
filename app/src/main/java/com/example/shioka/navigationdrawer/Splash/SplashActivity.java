package com.example.shioka.navigationdrawer.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.shioka.navigationdrawer.Activities.LoginActivity;
import com.example.shioka.navigationdrawer.Activities.MainActivity;
import com.example.shioka.navigationdrawer.Utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Intent iLogin = new Intent(this,LoginActivity.class);
        Intent iMain = new Intent(this,MainActivity.class);
        if(!TextUtils.isEmpty(Util.getUserMailPrefs(prefs)) &&
                !TextUtils.isEmpty(Util.getUserPassPrefs(prefs))){
            startActivity(iMain);
        }else {
            startActivity(iLogin);
        }
        finish();
    }
}
