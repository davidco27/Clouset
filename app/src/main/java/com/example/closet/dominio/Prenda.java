package com.example.closet.dominio;


import java.io.Serializable;

public class Prenda implements Serializable {
    private byte[] foto;
    private String id,marca,tipo,color,link;
    private float valoracion;

    public Prenda(float valoracion,byte[] foto,String id,String tipo,String marca,String color,String link) {
        this.valoracion=valoracion;
        this.foto = foto;
        this.id=id;
        this.tipo=tipo;
        this.marca=marca;
        this.color=color;
        this.link=link;
    }

    public String getLink() {
        return link;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }
}
