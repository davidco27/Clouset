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
        abrigos.add("");

        ArrayList<String> conjuntos = new ArrayList<String>();
        conjuntos.add("Vestido");
        conjuntos.add("Peto");
        conjuntos.add("Overol");
        conjuntos.add("Mono");
        conjuntos.add("");

        ArrayList<String> superiores = new ArrayList<String>();
        superiores.add("Camiseta");
        superiores.add("Camisa");
        superiores.add("Blusa");
        superiores.add("Top");
        superiores.add("");

        ArrayList<String> inferiores = new ArrayList<String>();
        inferiores.add("Jeans");
        inferiores.add("Pantalón vestir");
        inferiores.add("Falda");
        inferiores.add("Shorts");
        inferiores.add("");

        ArrayList<String> calzados = new ArrayList<String>();
        calzados.add("Deportivas");
        calzados.add("Tenis");
        calzados.add("Sandalias");
        calzados.add("Botas");
        calzados.add("Náuticos");
        calzados.add("");

        ArrayList<String> complementos = new ArrayList<String>();
        complementos.add("Bolso");
        complementos.add("Bufanda");
        complementos.add("Pañuelo");
        complementos.add("");


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
