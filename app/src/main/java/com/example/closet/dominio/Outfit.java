package com.example.closet.dominio;

import java.io.Serializable;
import java.util.ArrayList;

public class Outfit implements Serializable {
    private String id;
    private static final long serialVersionUID = 40L;
    private ArrayList<Prenda> prendas;
    private float valoracion;
    public Outfit(String id,ArrayList<Prenda> prendas,float valoracion){
        this.id =id;
        this.prendas=prendas;
        this.valoracion=valoracion;
    }

    public float getValoracion() {
        return valoracion;
    }

    public ArrayList<Prenda> getPrendas() {
        return prendas;
    }

    public String getId() {
        return id;
    }

    public boolean containsPrenda(Prenda p) {
        if(this.getPrendas().contains(p))
            return true;
        else
            return false;
    }
}
