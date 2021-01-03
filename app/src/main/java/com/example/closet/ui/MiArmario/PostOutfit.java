package com.example.closet.ui.MiArmario;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.example.closet.MainActivity;
import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.PrendaSeleccionAdapter;
import com.example.closet.util.Util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PostOutfit extends Fragment {
    private ImageButton btnActivo;
    private ListView lista;
    private ArrayList<Prenda> prendasDeCampo;
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view2 = inflater.inflate(R.layout.post_outfit, container, false);
        Util.setCampos();
        final ArrayList<Prenda> seleccion= new ArrayList<>();
        lista = view2.findViewById(R.id.listaSeleccion);
        refrescarLista("Abrigos");
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prenda prendaSeleccionada = prendasDeCampo.get(i);
                for(Prenda  p : seleccion){
                    if (Util.getCampos(p.getTipo()).equals(Util.getCampos(prendaSeleccionada.getTipo())))
                        seleccion.remove(p);
                }
                seleccion.add(prendaSeleccionada);
                ByteArrayInputStream is = new ByteArrayInputStream(prendasDeCampo.get(i).getFoto());
                Drawable drw = Drawable.createFromStream(is, "foto");
                btnActivo.setImageDrawable(drw);}
        });

        final ImageButton btnAbrigo = view2.findViewById(R.id.btnAbrigo);
        final ImageButton btnConjunto = view2.findViewById(R.id.btnConjunto);
        final ImageButton btnParteSup = view2.findViewById(R.id.btnParteArriba);
        final ImageButton btnParteInf = view2.findViewById(R.id.btnParteAbajo);
        final ImageButton btnCalzado = view2.findViewById(R.id.btnCalzado);
        final ImageButton btnComp = view2.findViewById(R.id.btnComplementos);
        Button btnGuardar = view2.findViewById(R.id.btnGuardarOutfit);
        btnActivo =btnAbrigo;

        btnAbrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Abrigos");
                btnActivo=btnAbrigo;
            }
        });
        btnConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Conjunto");
                btnActivo=btnConjunto;
            }
        });
        btnParteSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("ParteSuperior");
                btnActivo=btnParteSup;
            }
        });
        btnParteInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("ParteInferior");
                btnActivo=btnParteInf;
            }
        });
        btnCalzado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Calzado");
                btnActivo=btnCalzado;
            }
        });
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Complementos");
                btnActivo=btnComp;

            }});
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                final View popupView = inflater.inflate(R.layout.poner_nombre_outfit,null);

                final EditText txtNombre = popupView.findViewById(R.id.nombre);
                txtNombre.requestFocus();
                popupView.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String nombre = txtNombre.getText().toString();
                            Client.conectarseBDOutfits("/insertOutfit", 0,new Outfit(nombre,seleccion,0),getActivity(),MainActivity.getUsuario());
                            dialogBuilder.dismiss();
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                        catch (Exception e){
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error de Conexion con el servidor")
                                    .setMessage("Compruebe su conexion a internet y vuelva a intentarlo").setCancelable(true).show();

                        }


                    }
                });
                dialogBuilder.setView(popupView);
                dialogBuilder.show();

            popupView.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            });
        }
        });
        return view2;
    }

    private void refrescarLista(String campoSelect){
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        ArrayList<String> tipos = Util.getMap().get(campoSelect);
        prendasDeCampo =new ArrayList<>();
        for(Prenda p : prendas){
            if(tipos.contains(p.getTipo()))
                prendasDeCampo.add(p);
        }
        PrendaSeleccionAdapter ad = new PrendaSeleccionAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,prendasDeCampo);
        lista.setAdapter(ad);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}

