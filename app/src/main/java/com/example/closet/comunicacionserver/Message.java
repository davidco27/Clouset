package com.example.closet.comunicacionserver;
import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    /**
     *
     */
    private Prenda prenda;
    private Usuario usuario;
    private Outfit outfit;
    private static final long serialVersionUID = 56L;
    private String context;
    private String idPrenda,idOutfit;
    private String user;
    private byte[] password;
    private float valoracion;
    private Map<String, Object> session;


    public Message () {
        context= "";
        session=new HashMap<String, Object>();

    }

    public void setIdOutfit(String idOutfit) {
        this.idOutfit = idOutfit;
    }

    public String getIdOutfit() {
        return idOutfit;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
    }

    public Outfit getOutfit() {
        return outfit;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public void setIdPrenda(String idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getIdPrenda() {
        return idPrenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }

    public Prenda getPrenda() {
        return prenda;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
