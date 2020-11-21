package com.example.closet.dominio;

import java.util.ArrayList;

public class Outfit  {
    private String nombre;
    private ArrayList<Prenda> prendas;
    private String id;
    private float valoracion;
    public Outfit(String id,String nombre,ArrayList<Prenda> prendas,float valoracion){
        this.nombre =nombre;
        this.id=id;
        this.prendas=prendas;
        this.valoracion=valoracion;
    }

    public String getId() {
        return id;
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

    public boolean containsPrenda(Prenda p) {
        if(this.getPrendas().contains(p))
            return true;
        else
            return false;
    }
}
