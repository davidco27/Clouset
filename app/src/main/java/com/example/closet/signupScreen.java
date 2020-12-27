package com.example.closet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.closet.comunicacionserver.Client;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class signupScreen extends Activity {
    private byte[] image=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.signup_screen);
        final EditText txtUser =findViewById(R.id.user);
        final EditText txtPassword = findViewById(R.id.password);
        final EditText txtPassword2 = findViewById(R.id.password2);
        findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtUser.getText().toString();
                String password1 = txtPassword.getText().toString();
                String password2 = txtPassword2.getText().toString();
                if(usuario.equals("") || password1.equals("") || password2.equals(""))
                new AlertDialog.Builder(signupScreen.this)
                        .setTitle("Error")
                        .setMessage("Debe rellenar todos los campos").setCancelable(true).show();
                else if (!password1.equals(password2))
                    new AlertDialog.Builder(signupScreen.this)
                            .setTitle("Error")
                            .setMessage("Las contraseñas deben coincidir").setCancelable(true).show();
                else if (image == null)
                    new AlertDialog.Builder(signupScreen.this)
                            .setTitle("Error")
                            .setMessage("Debe elegir un avatar de su galeria").setCancelable(true).show();
                else{
                    new AlertDialog.Builder(signupScreen.this)
                            .setTitle("EXITO")
                            .setMessage("USUARIO REGISTRADO CORRECTAMENTE").setCancelable(true).show();
                    Intent intent = new Intent(signupScreen.this, loginScreen.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        findViewById(R.id.btn_foto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream is = this.getContentResolver().openInputStream(data.getData());
                Bitmap bm = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
