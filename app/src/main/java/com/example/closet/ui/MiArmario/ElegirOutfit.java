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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.closet.MainActivity;
import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
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
    private boolean repetir = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View view2 = inflater.inflate(R.layout.elegir_seleccionar, container, false);
        Util.setCampos();
        final View popupView = inflater.inflate(R.layout.elegir_estilo, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        do {
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
        }
        while(repetir);

        getActivity().getSupportFragmentManager().popBackStackImmediate();

        return view2;
    }
    private void pasarASeleccion(View view){
        final ArrayList<Prenda> seleccion= new ArrayList<>();
        lista = view.findViewById(R.id.listaSeleccion);
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
                case "Abrigos":
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

                case "Conjunto":
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
        final ArrayList<Prenda> seleccion = prendas;

        final Button btnRepetir = popupView.findViewById(R.id.btnRepetir);
        final Button btnGuardar = popupView.findViewById(R.id.btnGuardar);
        final Button btnOk = popupView.findViewById(R.id.btnOk);

        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //manda al usuario a elegir_eleccion
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //manda al ususario de vuelta a MiArmario
                repetir = false;
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            //  manda al usuario a nombrar el outfit obtenido
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                final View popupView =  getLayoutInflater().inflate(R.layout.poner_nombre_outfit,null);

                final EditText txtNombre = popupView.findViewById(R.id.nombre);
                txtNombre.requestFocus();
                popupView.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String nombre = txtNombre.getText().toString();
                            repetir = false;
                            Client.conectarseBDOutfits("/insertOutfit", 0,new Outfit(nombre, seleccion,0),getActivity(), MainActivity.getUsuario());
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
        ArrayList<Prenda> prendas = MiArmarioHome.getPrendasMiArmario();
        ArrayList<Outfit> outfits = MiArmarioHome.getOutfitsMiArmario();

        Prenda eleccion;

        //si el usuario ha seleccionado al menos una prenda(seleccion)
        if(seleccion!=null) {
            // campo de prenda
            for (String campo:campos) {
                // tipos de prenda de cada campo
                eleccion = null;
                float S = 0f;
                Collection<String> tipos = mapa.get(campo);
                for (String tipo:tipos) {
                    // prendas de MiArmario
                    ArrayList<Prenda> prendasTipo = getPrendasXtipo(prendas, tipo);

                    for(Prenda prenda:prendasTipo) {
                        float C = 1f;
                        for (Prenda sel:seleccion)
                            C = C * calculaP(prenda, sel, estilo, outfits);
                        if (C > S) {
                            S = C;
                            eleccion = prenda;
                        }
                    }
                }
                if(eleccion != null)
                    seleccion.add(eleccion);
            }
        }
        return seleccion;
    }

    //metodo para calcular la probabilidad de éxito
    @RequiresApi(api = Build.VERSION_CODES.O)
    private float calculaP(Prenda p, Prenda s, String estilo, ArrayList<Outfit> outfits) {

        return (calculaS(estilo, p.getTipo()) * PERC_STILO) + (calculaC(p.getRGBByteArray(), s.getRGBByteArray()) * PERC_COLOR) + (calculaV(p.getValoracion()) * PERC_VALORACION) +(calculaO(outfits, p) * PERC_OUTFIT);
    }

    //Algoritmo de estilo
    private float calculaS(String estilo, String tipo) {
        float a = 0.3f;

        switch (estilo) {
            case "Casual":
                switch (tipo) {
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
                break;
            case "Formal":
                switch (tipo) {
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
                break;
            case "Sporty":
                switch (tipo) {
                    case "Zapatillas":
                    case "Sudadera":
                        a = 1f;
                        break;
                    default:
                        break;
                }
                break;
        }
        return a;
    }

    //Algoritmo de color
    @RequiresApi(api = Build.VERSION_CODES.O)
    private float calculaC(byte[] pb, byte[] sb) {
        int suma = 0;
        byte b;

        for(int i = 0; i < pb.length; i++) {
            b = (byte) (pb[i] | sb[i]);
            suma += Byte.toUnsignedInt(b);
        }

        return (suma) / 765f;
    }

    //Algoritmo de Valoración
    private float calculaV(float valoracion) { return valoracion / 10f; }

    //Algoritmo de Outfit
    private float calculaO(ArrayList<Outfit> outfits, Prenda p) {
        float o = 0f;
        if(!outfits.isEmpty()) {
            for (Outfit outfit : outfits) {
                float f = 0f;

                if (outfit.containsPrenda(p))
                    f = 1f;

                f += 0.2f * outfit.getValoracion();

                if (f > o)
                    o = f;
            }
        }
        return o;
    }

    public ArrayList<Prenda> getPrendasXtipo(ArrayList<Prenda> prendas, String tipo) {

        ArrayList<Prenda> prendasXtipo = new ArrayList<>();

        for(Prenda p: prendas) {
            if(p.getTipo().equals(tipo))
                prendasXtipo.add(p);
        }

        return prendasXtipo;
    }
}
