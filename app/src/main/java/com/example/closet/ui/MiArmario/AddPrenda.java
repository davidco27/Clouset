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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.closet.MainActivity;
import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.Util;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class AddPrenda extends Fragment {
    private Button btnGuardar,btnColor;
    private int selectedColorR,selectedColorG,selectedColorB;
    private Bitmap bm;
    private EditText marca;
    private ImageView img;
    private String tipoPrenda;
    private Spinner tipo;
    private String campoSelecionado;
    private Spinner campo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getContext(),"android.permission.CAMERA")== PackageManager.PERMISSION_DENIED);
        {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);

        }
        Util.setCampos();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,0);
        View view = inflater.inflate(R.layout.add_prenda, container, false);
        img =view.findViewById(R.id.fotoAdd);
        tipo = view.findViewById(R.id.tipoSpinner);
        HashMap<String, ArrayList<String>> mapa = Util.getMap();

        //Parte campos
        ArrayList<String> campos = new ArrayList<>(mapa.keySet());
        campo=view.findViewById(R.id.campoSpinner);
        campoSelecionado = "Todos";
        campo.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, campos));
        campo.setSelection(5);
        campo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                campoSelecionado = campo.getItemAtPosition(position).toString();
                ArrayList<String> tipos = Util.getMap().get(campoSelecionado);

                tipo.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, tipos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Parte de tipos
        ArrayList<String> tipos = mapa.get(campoSelecionado);

        tipo.setAdapter(new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, tipos));
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
        btnColor=view.findViewById(R.id.color);
        final ColorPicker cp = new ColorPicker(getActivity(), 128, 128, 128);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.show();
                Button okColor = cp.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectedColorR = cp.getRed();
                        selectedColorG = cp.getGreen();
                        selectedColorB = cp.getBlue();

                        cp.dismiss();
                    }
                });
            }
        });



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
                try {
                    Client.conectarseBD("/insertPrenda", new Prenda(0, image, marc + Math.round(Math.random() * 1000000), tipoPrenda, marc,
                            Integer.toHexString(selectedColorR)+Integer.toHexString(selectedColorG)+Integer.toHexString(selectedColorB)), "",0,getActivity());
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
