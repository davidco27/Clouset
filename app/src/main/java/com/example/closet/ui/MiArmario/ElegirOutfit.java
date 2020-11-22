package com.example.closet.ui.MiArmario;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Outfit;
import com.example.closet.util.PrendaSeleccionAdapter;
import com.example.closet.util.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ElegirOutfit extends Fragment {

    private static final float PERC_STILO = 0.45f;
    private static final float PERC_COLOR = 0.05f;
    private static final float PERC_VALORACION = 0.2f;
    private static final float PERC_OUTFIT = 0.3f;
    private ListView lista;
    private ArrayList<Prenda> prendasDeCampo;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.elegir_seleccionar, container, false);
        Util.setCampos();
        lista = view.findViewById(R.id.listaSeleccion);
        refrescarLista("Abrigos");
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prenda seleccionada = prendasDeCampo.get(i);
            }
        });
        ImageButton btnAbrigo = view.findViewById(R.id.btnAbrigo);
        ImageButton btnConjunto = view.findViewById(R.id.btnConjunto);
        ImageButton btnParteSup = view.findViewById(R.id.btnParteArriba);
        ImageButton btnParteInf = view.findViewById(R.id.btnParteAbajo);
        ImageButton btnCalzado = view.findViewById(R.id.btnCalzado);
        ImageButton btnComp = view.findViewById(R.id.btnComplementos);

        btnAbrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Abrigos");
            }
        });
        btnConjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Conjunto");
            }
        });
        btnParteSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("ParteSuperior");
            }
        });
        btnParteInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("ParteInferior");
            }
        });
        btnCalzado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Calzado");
            }
        });
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescarLista("Complementos");
            }
        });

        //Almacenamiento de la seleccion del usuario

        //Algoritmo de selección de las mejores prendas
        //ArrayList<Outfit> outfits = MiArmarioHome.getOutfitsMiArmario();

        return view;
    }
    private void refrescarLista(String campoSelect){
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        ArrayList<String> tipos=Util.getMap().get(campoSelect);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Prenda> buscar(ArrayList<Prenda> prendas, Outfit[] outfits, ArrayList<Prenda> seleccion, String estilo) {
        Util.setCampos();

        HashMap<String, ArrayList<String>> mapa = Util.getMap();
        Set<String> campos = mapa.keySet();

        float P = 0f;
        float S = 0f;
        Prenda eleccion = null;

        //si el usuario ha seleccionado al menos una prenda(seleccion)
        if(seleccion!=null) {
            // campo de prenda
            for (String campo:campos) {
                // tipos de prenda de cada campo
                Collection<String> tipos = mapa.get(campo);
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
                seleccion.add(eleccion);
            }
        }
        return seleccion;
    }

    //metodo para calcular la probabilidad de éxito
    @RequiresApi(api = Build.VERSION_CODES.O)
    private float calculaP(Prenda p, Prenda s, Outfit[] outfits, String estilo) {

        return (calculaS(estilo, p.getTipo()) * PERC_STILO) + (calculaC(p.getRGBByteArray(), s.getRGBByteArray()) * PERC_COLOR) + (calculaV(p.getValoracion()) * PERC_VALORACION) + (calculaO(outfits, p) * PERC_OUTFIT);
    }

    //Algoritmo de estilo
    private float calculaS(String estilo, String tipo) {
        float a = 0.3f;

        if(estilo.equals("Casual")) {
            switch(tipo) {
                case "Camiseta":
                case "Jeans":
                case "Zapatillas":
                case "Botas":
                case "Overol":
                case "Abrigo":
                case "Chaqueta":
                case "Cazadora":
                case "Sudadera":
                case "Shorts":
                    a = 1f;
                    break;
                case "Sandalias":
                case "Top":
                    a = 0.8f;
                    break;
                case "Zapato":
                    a = 0.7f;
                    break;
                default:
                    break;
            }
        }
        else if(estilo.equals("Formal")) {
            switch(tipo) {
                case "Camisa":
                case "Blusa":
                case "Pantalón vestir":
                case "Abrigo":
                case "Chaleco":
                case "":
                    a = 1f;
                    break;
                case "Jeans":
                    a = 0.5f;
                    break;
                default:
                    break;
            }
        }
        else {
            switch(tipo) {
                case "":
                    a = 1f;
                    break;
                default:
                    break;
            }
        }
        return a;
    }

    //Algoritmo de color
    @RequiresApi(api = Build.VERSION_CODES.O)
    private float calculaC(byte[] pb, byte[] sb) {
        int suma = 0;
        byte b;

        for(int i = 0; i < pb.length - 1; i++) {
            b = (byte) (pb[i] | sb[i]);
            suma =+ Byte.toUnsignedInt(b);
        }

        return (suma) / 765f;
    }

    //Algoritmo de Valoración
    private float calculaV(float valoracion) { return valoracion / 10f; }

    //Algoritmo de Outfit
    private float calculaO(Outfit[] outfits, Prenda p) {
        float o = 0f;
        for (Outfit outfit : outfits) {
            float f = 0f;

            if (outfit.containsPrenda(p))
                f = 1f;

            f += 0.2f * outfit.getValoracion();

            if (f > o)
                o = f;
        }

        return o;
    }


}
