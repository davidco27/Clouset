package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Prenda;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    /**
     *
     */
    private Prenda prenda;
    private static final long serialVersionUID = 1L;
    private String context;
    private Map<String, Object> session;


    public Message () {
        context=new String();
        session=new HashMap<String, Object>();

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
