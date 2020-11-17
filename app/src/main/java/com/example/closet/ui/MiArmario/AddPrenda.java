package com.example.closet.ui.MiArmario;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Prenda;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddPrenda extends Fragment {
    private Button btnGuardar;
    private Bitmap bm;
    private EditText marca,color;
    private ImageView img;
    private String tipoPrenda;
   private Spinner tipo;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getContext(),"android.permission.CAMERA")== PackageManager.PERMISSION_DENIED);
        {
            requestPermissions(new String[] {"android.permission.CAMERA"},1);

        }
       Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,0);
        View view = inflater.inflate(R.layout.add_prenda, container, false);
        img=view.findViewById(R.id.fotoAdd);
        tipo= view.findViewById(R.id.tipo);
        ArrayList<String> tipos=new ArrayList<>();
        tipos.add("Calzado");
        tipos.add("Camiseta");
        tipos.add("Sudadera");
        tipos.add("Pantalon");
        tipos.add("Falda");
        tipos.add("Vestido");
        tipos.add("Polo");
        tipos.add("Sombrero");
        tipos.add("Camisa");
        tipo.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,tipos));
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                           @Override
                                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                               tipoPrenda = tipo.getItemAtPosition(position).toString();
                                           }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        marca=view.findViewById(R.id.marca);
        color=view.findViewById(R.id.color);
       btnGuardar=view.findViewById(R.id.btn2);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         bm =(Bitmap) data.getExtras().get("data");
        img.setImageBitmap(bm);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                 bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                String marc = marca.getText().toString();
                String colo = color.getText().toString();
                try {
                    Client.conectarseBD("/insertPrenda", new Prenda(4.5f, image, marc + Math.round(Math.random() * 1000000), tipoPrenda, marc, colo), "");
                }
                catch (Exception e){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error de Conexion con el servidor")
                            .setMessage("Compruebe su conexion a internet y vuelva a intentarlo").setCancelable(true).show();

                }
                    getActivity().getSupportFragmentManager().popBackStackImmediate();

            }
        });


    }
}
