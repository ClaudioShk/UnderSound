package com.example.shioka.navigationdrawer.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shioka.navigationdrawer.R;
import com.example.shioka.navigationdrawer.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText editTxtEmail;
    private EditText editTxtPass;
    private Button btnLogIn;
    private Button btnGoToRegister;
    private Switch remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        bindUI();
        setCredentialsIfExists();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTxtEmail.getText().toString();
                String pass = editTxtPass.getText().toString();
                if(login(email,pass)){
                    goToMain();
                    saveOnPreferences(email,pass);
                }
            }
        });
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCredentialsIfExists(){
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){
            editTxtEmail.setText(email);
            editTxtPass.setText(password);
        }
    }

    private boolean login(String email,String pass){
        if(!validEmail(email)){
            Toast.makeText(this,"Email is not valid!",Toast.LENGTH_LONG).show();
            return false;
        }else if(!validPass(pass)) {
            Toast.makeText(this, "Password is not valid!, It must have at least 4 characters", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private void saveOnPreferences(String email,String pass){
        if(remember.isChecked()){
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("email",email);
            editor.putString("pass",pass);
            editor.apply();
        }
    }

    private void bindUI(){
        editTxtEmail=(EditText)findViewById(R.id.txtEmail);
        editTxtPass=(EditText)findViewById(R.id.txtPassword);
        remember=(Switch)findViewById(R.id.switchRemember);
        btnLogIn=(Button)findViewById(R.id.btnLogIn);
        btnGoToRegister =(Button)findViewById(R.id.btnRegisterNow);
    }

    private boolean validEmail(String email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validPass(String pass){
        return pass.length()>=4;
    }

    private void goToMain(){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
