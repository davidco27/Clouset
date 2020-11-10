package com.example.closet.comunicacionserver;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.closet.dominio.Prenda;

import java.util.ArrayList;

public class PrendaControler {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getPrenda(ArrayList<Prenda> lista) {
        PrendaDAO.getPrendas(lista);
    }
    public void insertPrenda(Prenda prenda) {
        PrendaDAO.insertPrendas(prenda);
    }
}