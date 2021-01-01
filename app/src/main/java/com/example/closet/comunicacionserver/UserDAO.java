package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public static byte[] getPassword(String user) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT password FROM \"Usuarios\"  where nombre_usuario = '"+user +"'");
             ResultSet rs = pst.executeQuery()) {
            if(rs.next())
            return rs.getBytes(1);
            else
                return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public static void insertUser(Usuario user) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try{
        PreparedStatement pst = con.prepareStatement("insert into \"Usuarios\" values ('" + user.getNombreUsuario() +
                "',?,?)");
        pst.setBytes(1, user.getFotoPerfil());
        pst.setBytes(2, user.getPassword());
        pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
}
