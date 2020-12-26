package com.example.closet.comunicacionserver;

import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;

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

            rs.next();
            return rs.getBytes(1);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
