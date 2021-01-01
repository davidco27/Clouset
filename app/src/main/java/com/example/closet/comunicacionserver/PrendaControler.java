package com.example.closet.comunicacionserver;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.closet.dominio.Prenda;

import java.util.ArrayList;

public class PrendaControler {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getPrenda(ArrayList<Prenda> lista,String usuario) {
        PrendaDAO.getPrendas(lista,usuario);
    }
    public void deletePrendaId(String id) {
        PrendaDAO.deletePrendaId(id);
    }
    public void insertPrenda(Prenda prenda,String usuario) {
        PrendaDAO.insertPrendas(prenda,usuario);
    }
    public float setValoracion(String id,float valoracion) {
        return PrendaDAO.setValoracion(id,valoracion);
    }
}