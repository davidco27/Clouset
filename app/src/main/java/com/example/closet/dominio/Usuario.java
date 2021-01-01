package com.example.closet.dominio;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombreUsuario;
    private byte[] fotoPerfil,password;
    private static final long serialVersionUID = 23L;

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
