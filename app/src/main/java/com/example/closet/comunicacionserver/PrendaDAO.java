package com.example.closet.comunicacionserver;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.closet.dominio.Prenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PrendaDAO {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getPrendas(ArrayList<Prenda> lista) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM \"Prendas\" order by id");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                lista.add(new Prenda((float) rs.getDouble(1), rs.getBytes(6), rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(5)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static Prenda getPrendaById(String id){
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM \"Prendas\"  where id = '" + id + "'");
             ResultSet rs = pst.executeQuery()) {

            rs.next();
            return new Prenda((float) rs.getDouble(1), rs.getBytes(6), rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(5));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return  null;
        }

    }

    public static void deletePrendaId(String id) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM \"Prendas\" where id = '" + id + "'");
             pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void insertPrendas(Prenda prenda) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert into \"Prendas\" values ('" + 0 +
                    "','" + prenda.getTipo() + "','" + prenda.getMarca() + "','" + prenda.getId() + "','" + prenda.getColor() + "',?," + 0 + ")");
            pst.setBytes(1, prenda.getFoto());

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static float setValoracion(String id, float valoracion) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT nvaloraciones,valoration FROM \"Prendas\" where id = '" + id + "'");
             ResultSet rs = pst.executeQuery()) {
            rs.next();
            int vecesValorado = rs.getInt(1);
            float valoracionVieja = rs.getFloat(2);
            float valoracionNueva = (vecesValorado * valoracionVieja + valoracion) / (vecesValorado + 1);
            valoracion=valoracionNueva;
                PreparedStatement pst3 = con.prepareStatement("update \"Prendas\" set nvaloraciones = " +(vecesValorado + 1)+" where id='" + id + "'");
                pst3.executeUpdate();
                PreparedStatement pst5 = con.prepareStatement("update \"Prendas\" set valoration = " + valoracionNueva +
                            " where id='" + id + "'");
                pst5.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  valoracion;
}}
