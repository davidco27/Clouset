package com.example.closet.ui.MiArmario;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Outfit;
import com.example.closet.util.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ElegirOutfit extends Fragment {

    private static final float PS = 0.45f;
    private static final float PC = 0.05f;
    private static final float PV = 0.2f;
    private static final float PO = 0.3f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.elegir_outfit, container, false);

        //Almacenamiento de la seleccion del usuario

        //Algoritmo de selección de las mejores prendas

        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        ArrayList<Outfit> outfits = MiArmarioHome.getOutfitsMiArmario();

        HashMap<String, ArrayList<String>> mapaTipos = Util.getMap();
        Set<String> campos = mapaTipos.keySet();

        float P = 0f;
        float S;
        Prenda eleccion = null;

        //si el usuario ha seleccionado al menos una prenda(seleccion)
      /*  if(seleccion!=null) {
            // campo de prenda
            for (String campo:campos) {
                // tipos de prenda de cada campo
                Collection<String> tipos = mapaTipos.get(campo);
                for (String tipo:tipos) {
                    // prendas de MiArmario
                    for(Prenda prenda:prendas) {
                        float C = 1f;
                        for (Prenda sel:seleccion)
                            C = C * calculaP(prenda, sel, outfits, estilo);
                        if (C > S) {
                            S = C;
                            eleccion = prenda;
                        }
                    }
                }
                prendas.add(eleccion);
            }
        }



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    //metodo para calcular la probabilidad de éxito
    private float calculaP(Prenda p, Prenda s, Outfit[] outfits, String estilo) {

        return (calculaS(estilo) * PS) + (calculaC(p.getRGBByteArray(), s.getRGBByteArray()) * PC) + (calculaV(p.getValoracion()) * PV) + (calculaO(outfits, p) * PO);
    }

    //Algoritmo de estilo
    private float calculaS(String estilo) {
        float a;

        if(estilo.equals("Casual")) {

        }
        else if () {

        }
        else {

        }

        return a;
    }

    //Algoritmo de color
    @RequiresApi(api = Build.VERSION_CODES.O)
    private float calculaC(byte[] pb, byte[] sb) {
        int suma = 0;
        byte b;

        for(int i = 0; i < pb.length - 1; i++) {
            byte  = (byte) (pb[i] | sb[i]);
            suma =+ Byte.toUnsignedInt(b);
        }

        return (suma) / 765f;
    }

    //Algoritmo de Valoración
    private float calculaV(float valoracion) { return valoracion / 10f; }

    //Algoritmo de Outfit
    private float calculaO(Outfit[] outfits, Prenda p) {
        float o = 0f;
        for(Outfit outfit: outfits) {
            float f = 0f;

            if(outfit.containsPrenda(p))
                f = 1f;

            f += 0.02f * outfit.getValoracion();

            if(f > o)
                o = f;
        }

        return o;*/
      return view;
    }
}
