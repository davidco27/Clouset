package com.example.closet.dominio;


import java.io.Serializable;

public class Prenda implements Serializable {
    private byte[] foto;
    private String id,marca,tipo,color;
    private float valoracion;

    public Prenda(float valoracion,byte[] foto,String id,String tipo,String marca,String color) {
        this.valoracion=valoracion;
        this.foto = foto;
        this.id=id;
        this.tipo=tipo;
        this.marca=marca;
        this.color=color;
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

    public  byte[] getRGBByteArray() {
        byte[] data = new byte[3];
        for (int i = 0; i < 6; i += 2) {
            data[i / 2] = (byte) ((Character.digit(color.charAt(i), 16) << 4)
                    + Character.digit(color.charAt(i+1), 16));
        }
        return data;
    }
}
