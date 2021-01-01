package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OutfitDAO {
    public static void getOutfits(ArrayList<Outfit> lista,String usuario) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM \"Outfits\" where nombre_usuario='"+usuario+"'");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String[] prendas = rs.getString(2).split(",");
                ArrayList<Prenda> prendasOutfit = new ArrayList<>();
                for (String prenda : prendas) {
                    prendasOutfit.add(PrendaDAO.getPrendaById(prenda));
                }
                lista.add(new Outfit(rs.getString(1),prendasOutfit,(float)rs.getDouble(3)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void insertOutfit(Outfit outfit,String usuario){
            Connection con = ConnectionDAO.getInstance().getConnection();
            try {
                StringBuilder builder = new StringBuilder();
                for (Prenda p : outfit.getPrendas()){
                    builder.append(p.getId());
                    builder.append(",");
                }
                String prendas = builder.toString();
                PreparedStatement pst = con.prepareStatement("insert into \"Outfits\" values ('" +outfit.getId() +
                        "','" + prendas + "','"+usuario+"','" + 0+"')");

                pst.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

}
