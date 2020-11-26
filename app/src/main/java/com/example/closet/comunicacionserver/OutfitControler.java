package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Outfit;

import java.util.ArrayList;

public class OutfitControler {
    public void getOutfits(ArrayList<Outfit> outfits){
        OutfitDAO.getOutfits(outfits);
    }
}
