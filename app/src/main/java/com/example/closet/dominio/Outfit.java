package com.example.closet.dominio;

import java.util.ArrayList;

public class Outfit  {
    private String nombre;
    private ArrayList<Prenda> prendas;
    private float valoracion;
    public Outfit(String nombre,ArrayList<Prenda> prendas,float valoracion){
        this.nombre =nombre;
        this.prendas=prendas;
        this.valoracion=valoracion;
    }

    public float getValoracion() {
        return valoracion;
    }

    public ArrayList<Prenda> getPrendas() {
        return prendas;
    }

    public String getNombre() {
        return nombre;
    }
}
