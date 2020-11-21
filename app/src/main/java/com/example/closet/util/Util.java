package com.example.closet.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Util {

    private static HashMap<String, ArrayList<String>> campos;

    public static void setCampos() {
        campos = new HashMap<>();

        ArrayList<String> abrigos = new ArrayList<String>();
        abrigos.add("Sudadera");
        abrigos.add("Jersey");
        abrigos.add("Abrigo");
        abrigos.add("Chaqueta");
        abrigos.add("Cazadora");
        abrigos.add("Blazer");
        abrigos.add("Chaleco");

        ArrayList<String> conjuntos = new ArrayList<String>();
        conjuntos.add("Vestido");
        conjuntos.add("Peto");
        conjuntos.add("Overol");
        conjuntos.add("Mono");

        ArrayList<String> superiores = new ArrayList<String>();
        superiores.add("Camiseta");
        superiores.add("Camisa");
        superiores.add("Blusa");
        superiores.add("Top");

        ArrayList<String> inferiores = new ArrayList<String>();
        inferiores.add("Jeans");
        inferiores.add("Pantalón vestir");
        inferiores.add("Falda");
        inferiores.add("Shorts");
        inferiores.add("Leggins");

        ArrayList<String> calzados = new ArrayList<String>();
        calzados.add("Deportivas");
        calzados.add("Botas");
        calzados.add("Sandalias");
        calzados.add("Mocasines");
        calzados.add("Náuticos");
        calzados.add("Plataformas");
        calzados.add("Zapato");
        calzados.add("Zapatillas");
        calzados.add("Tacones");
        calzados.add("Chancletas");

        ArrayList<String> complementos = new ArrayList<String>();
        complementos.add("Bolso");
        complementos.add("Bufanda");
        complementos.add("Pañuelo");
        complementos.add("Gorra");
        complementos.add("Sombrero");
        complementos.add("Cinturón");


        campos.put("Abrigos",abrigos);
        campos.put("Conjunto", conjuntos);
        campos.put("ParteSuperior", superiores);
        campos.put("ParteInferior", inferiores);
        campos.put("Calzado", calzados);
        campos.put("Complementos", complementos);
    }

    public static HashMap<String,ArrayList<String>> getMap() { return campos;}

    public static String getCampos(String tipoPrenda){
        Set<String > camposPos =campos.keySet() ;
        for(String campo : camposPos){
            ArrayList<String> tipos = campos.get(campo);
            for(String tipo : tipos){
                if(tipo.equals(tipoPrenda)){
                    return campo;
                }
            }
        }
        return "";
    }
}
