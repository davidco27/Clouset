package com.example.closet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.closet.comunicacionserver.Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class loginScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login_screen);
        final EditText txtUser =findViewById(R.id.input_user);
        final EditText txtPassword = findViewById(R.id.input_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobarDatos(txtUser.getText().toString(),txtPassword.getText().toString())){
                    Intent intent = new Intent(loginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        findViewById(R.id.link_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(loginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

    }
    public boolean comprobarDatos(String usuario,String password){
        byte[] bytesOfMessage = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encriptada = md.digest(bytesOfMessage);
        return Client.checkPassword(usuario,encriptada,this);
    }
}
