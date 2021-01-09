package com.example.closet.ui.MiArmario;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.closet.MainActivity;
import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.OutfitAdapter;
import com.example.closet.util.PrendaAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MiArmarioHome extends Fragment {
   private static String usuario;
    private static ArrayList<Prenda> prendas;
    private static ArrayList<Outfit> outfits;
    private  static ListView lista;
    private  static Activity activity;
    private  static Button btnPrendas,btnOutfits;
    private static View rayaPrendas,rayaOutfits;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.miarmario_main, container, false);
         btnPrendas = view.findViewById(R.id.btnPrendas);
         btnOutfits = view.findViewById(R.id.btnOutfits);
         rayaOutfits= view.findViewById(R.id.outfits);
        rayaPrendas = view.findViewById(R.id.prendas);
        return view;
    }
    public static void actualizarLista(boolean listaPrendas){
        usuario = MainActivity.getUsuario();
        prendas = Client.conectarseBD("/getPrenda", null, "",0,usuario,activity);
        outfits = Client.conectarseBDOutfits("/getOutfit", 0, null, activity,usuario);
        if (listaPrendas){
        lista.setAdapter(new PrendaAdapter(activity, R.layout.support_simple_spinner_dropdown_item, prendas));
        rayaOutfits.setVisibility(View.INVISIBLE);
        rayaPrendas.setVisibility(View.VISIBLE);}
        else{
            lista.setAdapter(new OutfitAdapter(activity, R.layout.support_simple_spinner_dropdown_item, outfits));
            rayaOutfits.setVisibility(View.VISIBLE);
            rayaPrendas.setVisibility(View.INVISIBLE);}

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity=getActivity();
        lista = view.findViewById(R.id.listaPrendas);
        actualizarLista(true);
        btnOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rayaOutfits.setVisibility(View.VISIBLE);
                rayaPrendas.setVisibility(View.INVISIBLE);
                lista.setAdapter(new OutfitAdapter(activity, R.layout.support_simple_spinner_dropdown_item, outfits));
            }
        });
        btnPrendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rayaOutfits.setVisibility(View.INVISIBLE);
                rayaPrendas.setVisibility(View.VISIBLE);
                lista.setAdapter(new PrendaAdapter(activity, R.layout.support_simple_spinner_dropdown_item, prendas));
            }
        });
    }

    public static ArrayList<Prenda> getPrendasMiArmario() { return prendas;}
    public static ArrayList<Outfit> getOutfitsMiArmario() { return outfits;}

}
