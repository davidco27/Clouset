package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Outfit;

import java.util.ArrayList;

public class OutfitControler {
    public void getOutfits(ArrayList<Outfit> outfits,String usuario){
        OutfitDAO.getOutfits(outfits,usuario);
    }
    public void insertOutfit(Outfit outfit,String usuario){
        OutfitDAO.insertOutfit(outfit,usuario);
    }
}
