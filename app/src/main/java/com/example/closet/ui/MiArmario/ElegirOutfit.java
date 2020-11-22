package com.example.closet.ui.MiArmario;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Outfit;
import com.example.closet.util.PrendaSeleccionAdapter;
import com.example.closet.util.Util;

import java.io.ByteArrayInputStream;
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
    private ImageButton btnActivo;
    private ListView lista;
    private ArrayList<Prenda> prendasDeCampo;
    private String estilo;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View view2 = inflater.inflate(R.layout.elegir_seleccionar, container, false);
        Util.setCampos();
        final View popupView = inflater.inflate(R.layout.elegir_estilo,null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(view2, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        //Parte elegir estilo
        ImageButton btnCasual = popupView.findViewById(R.id.btnCasual);
        ImageButton btnFormal = popupView.findViewById(R.id.btnFormal);
        ImageButton btnSporty = popupView.findViewById(R.id.btnSporty);
        btnCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estilo = "Casual";
                popupWindow.dismiss();
                pasarASeleccion(view2);
            }
        });

        btnFormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estilo = "Formal";
                popupWindow.dismiss();
                pasarASeleccion(view2);
            }
        });

        btnSporty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estilo = "Sporty";
                popupWindow.dismiss();
                pasarASeleccion(view2);
            }
        });

        //Parte Seleccionar Prendas

        //Almacenamiento de la seleccion del usuario


        //Algoritmo de selección de las mejores prendas

             //seleccion = buscar(seleccion, estilo);

        //Mostrar los resultados


        return view2;
    }
    private void pasarASeleccion(View view){
        final ArrayList<Prenda> seleccion= new ArrayList<>();
        lista = view.findViewById(R.id.listaSeleccion);
        refrescarLista("Abrigos");
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seleccion.add(prendasDeCampo.get(i));
                ByteArrayInputStream is = new ByteArrayInputStream(prendasDeCampo.get(i).getFoto());
                Drawable drw = Drawable.createFromStream(is, "foto");
                btnActivo.setImageDrawable(drw);}
        });

        final ImageButton btnAbrigo = view.findViewById(R.id.btnAbrigo);
        final ImageButton btnConjunto = view.findViewById(R.id.btnConjunto);
        final ImageButton btnParteSup = view.findViewById(R.id.btnParteArriba);
        final ImageButton btnParteInf = view.findViewById(R.id.btnParteAbajo);
        final ImageButton btnCalzado = view.findViewById(R.id.btnCalzado);
        final ImageButton btnComp = view.findViewById(R.id.btnComplementos);
        Button btnGenerar = view.findViewById(R.id.btnGenerarOutfit);
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
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                mostrarResultados(buscar(seleccion,estilo),view);
            }
        });
    }
    private void mostrarResultados(ArrayList<Prenda> prendas,View view){
        final View popupView = getLayoutInflater().inflate(R.layout.elegir_resultado,null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        for(Prenda seleccionada: prendas) {
            ByteArrayInputStream is = new ByteArrayInputStream(seleccionada.getFoto());
            Drawable drw = Drawable.createFromStream(is, "foto");
            switch (Util.getCampos(seleccionada.getTipo())) {
                case "Abrigo":
                    ImageView imgAbrigo = popupView.findViewById(R.id.imgAbrigo);
                    imgAbrigo.setImageDrawable(drw);
                    break;

                case "ParteSuperior":
                    ImageView imgSuperior = popupView.findViewById(R.id.imgSuperior);
                    imgSuperior.setImageDrawable(drw);
                    break;

                case "ParteInferior":
                    ImageView imgInferior = popupView.findViewById(R.id.imgInferior);
                    imgInferior.setImageDrawable(drw);
                    break;

                case "Completo":
                    ImageView imgCompleto = popupView.findViewById(R.id.imgCompleto);
                    imgCompleto.setImageDrawable(drw);
                    break;

                case "Calzado":
                    ImageView imgCalzado = popupView.findViewById(R.id.imgCalzado);
                    imgCalzado.setImageDrawable(drw);
                    break;

                case "Accesorios":
                    ImageView imgAccesorios = popupView.findViewById(R.id.imgAccesorios);
                    imgAccesorios.setImageDrawable(drw);
                    break;

                default:
                    break;
            }
        }
        getActivity().getSupportFragmentManager().popBackStackImmediate();

    }

    private void refrescarLista(String campoSelect){
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        ArrayList<String> tipos =Util.getMap().get(campoSelect);
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
    private ArrayList<Prenda> buscar(ArrayList<Prenda> seleccion, String estilo) {
        Util.setCampos();

        HashMap<String, ArrayList<String>> mapa = Util.getMap();
        Set<String> campos = mapa.keySet();
        ArrayList<Outfit> outfits = MiArmarioHome.getOutfitsMiArmario();
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();

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
    private float calculaP(Prenda p, Prenda s, ArrayList<Outfit> outfits, String estilo) {

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
                case "Shorts":
                case "Chancletas":
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
                case "Mocasines":
                case "Náuticos":
                case "Plataformas":
                case "Tacones":
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
        else if(estilo.equals("Sporty")){
            switch(tipo) {
                case "Zapatillas":
                case "Sudadera":
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
    private float calculaO(ArrayList<Outfit> outfits, Prenda p) {
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
