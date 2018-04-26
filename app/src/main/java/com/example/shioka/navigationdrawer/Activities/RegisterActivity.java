package com.example.shioka.navigationdrawer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shioka.navigationdrawer.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText txtUsername,txtEmail,txtPassword,txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindUI();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindUI(){
        txtUsername=(EditText)findViewById(R.id.txtRegisterUser);
        txtEmail=(EditText)findViewById(R.id.txtRegisterEmail);
        txtPassword=(EditText)findViewById(R.id.txtRegisterPassword);
        txtConfirmPassword=(EditText)findViewById(R.id.txtConfirmPassword);
        btnRegister=(Button)findViewById(R.id.btnRegister);
    }

}
