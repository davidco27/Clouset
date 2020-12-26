package com.example.closet.dominio;

public class Usuario {
    private String nombreUsuario;
    private byte[] fotoPerfil;

    public Usuario(byte[] fotoPerfil,String nombreUsuario) {
        this.fotoPerfil = fotoPerfil;
        this.nombreUsuario=nombreUsuario;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
