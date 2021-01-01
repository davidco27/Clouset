package com.example.closet.comunicacionserver;


import com.example.closet.dominio.Usuario;

public class UserControler {
    public byte[] getPassword(String user){
        return  UserDAO.getPassword(user);
    }
    public void insertUser(Usuario user){
        UserDAO.insertUser(user);
    }
}
