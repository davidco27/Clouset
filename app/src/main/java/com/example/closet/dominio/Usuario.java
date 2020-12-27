package com.example.closet.dominio;

public class Usuario {
    private String nombreUsuario;
    private byte[] fotoPerfil,password;

    public Usuario(byte[] fotoPerfil,byte[] password,String nombreUsuario) {
        this.fotoPerfil = fotoPerfil;
        this.nombreUsuario=nombreUsuario;
        this.password=password;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
