package com.example.closet.comunicacionserver;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.closet.dominio.Prenda;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PrendaDAO {



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getPrendas(ArrayList<Prenda> lista) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM \"Prendas\"");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                //ByteArrayInputStream is = new ByteArrayInputStream(rs.getBytes(1));
                //Drawable drw = Drawable.createFromStream(is, "articleImage");
                lista.add(new Prenda((float)rs.getDouble(1),null,rs.getString(5),rs.getString(2),rs.getString(3),rs.getString(6),rs.getString(4)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void insertPrendas(Prenda prenda) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("insert into \"Prendas\" values ('" +prenda.getValoracion()+
                "','"+prenda.getTipo()+"','"+prenda.getMarca()+"','"+prenda.getLink()+"','"+prenda.getId()+"','"+prenda.getColor()+"')");
             ResultSet rs = pst.executeQuery()) {


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



}

