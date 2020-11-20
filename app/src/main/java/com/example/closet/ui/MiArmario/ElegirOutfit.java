package com.example.closet.ui.MiArmario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Outfit;

import java.util.ArrayList;

public class ElegirOutfit extends Fragment {

    private static final float PS = 0.45f;
    private static final float PC = 0.05f;
    private static final float PV = 0.2f;
    private static final float PO = 0.3f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.elegir_outfit, container, false);

        //Almacenamiento de la selccion del usuario

        //Algoritmo de selección de las mejores prendas
        //inicializacion
        Prenda eleccion;
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        float P = 0f;
        float S;
        //si el usuario ha seleccionado al menos una prenda(seleccion)
      /*  if(seleccion!=null) {
            // campo de prenda
            for (campo:campos) {
                // tipos de prenda de cada campo
                for (tipo:tipos) {
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

        return (calculaS(estilo) * PS) + (calculaC(p.getByteColorArray(), s.getByteColorArray()) * PC) + (calculaV(p.getValoracion()) * PV) + (calculaO(outfits, p) * PO);
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
    private float calculaC(byte[] pb, byte[] sb) {
        byte[] bt;
        int i;
        for(i = 0;:pb.length())
            bt = pb[i] * pb [i];

        return (() + () + ()) / 765f;
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
