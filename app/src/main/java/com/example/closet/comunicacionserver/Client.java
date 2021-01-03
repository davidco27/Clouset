package com.example.closet.comunicacionserver;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.StrictMode;

import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.dominio.Usuario;
import com.example.closet.signupScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Client {
    private String host;
    private int port;


    public static ArrayList<Prenda> conectarseBD(String peticion, Prenda prenda, String idPrenda, float valoracion,String usuario,Activity ac) {
        //Configure connections
        String host="";
        try {
            BufferedReader br= new BufferedReader( new InputStreamReader(ac.getAssets().open("properties.txt")));
           host=br.readLine().trim();

        }
        catch (IOException ioe){

        }
        int port=1000;
        //Create a cliente class
        Client cliente = new Client(host, port);
        HashMap<String, Object> session = new HashMap<String, Object>();
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(peticion);
        mensajeEnvio.setSession(session);
        mensajeEnvio.setPrenda(prenda);
        mensajeEnvio.setUser(usuario);
        mensajeEnvio.setValoracion(valoracion);
        mensajeEnvio.setIdPrenda(idPrenda);
        cliente.sent(mensajeEnvio, mensajeVuelta,ac);

        ArrayList<Prenda> listaPrendas = null;

        switch (mensajeVuelta.getContext()) {
            case "/getPrendaResponse":
                listaPrendas = (ArrayList<Prenda>) (mensajeVuelta.getSession().get("Prenda"));
                for (Prenda prenda2 : listaPrendas) {
                    System.out.println(prenda2.getId() + " " + prenda2.getMarca() + " " + prenda2.getValoracion());
                }
                break;
            case "/getPrendaIdResponse":
                listaPrendas = (ArrayList<Prenda>) (mensajeVuelta.getSession().get("Prenda"));
                break;
            case "/insertPrendaResponse":
                System.out.println("PRENDA GUARDADA CORRECTAMENTE");
                break;
            case "/changeValoracionResponse":
                System.out.println("VALORACION CAMBIADA CORRECTAMENTE");
                listaPrendas = new ArrayList<>();
                listaPrendas.add(new Prenda(mensajeVuelta.getValoracion(), null, "", "", "", ""));
                break;
            default:
                System.out.println("\nError a la vuelta");
                break;

        }
        return listaPrendas;
    }
    public static void registerUser(Usuario user,Activity ac){
        String host="";
        try {
            BufferedReader br= new BufferedReader( new InputStreamReader(ac.getAssets().open("properties.txt")));
            host=br.readLine().trim();

        }
        catch (IOException ioe){

        }
        int port=1000;
        Client cliente = new Client(host, port);
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext("/insertUser");
        mensajeEnvio.setUsuario(user);
        cliente.sent(mensajeEnvio, mensajeVuelta,ac);

    }
    public static boolean checkPassword(String usuario,byte [] password,Activity ac){
        String host="";
        try {
            BufferedReader br= new BufferedReader( new InputStreamReader(ac.getAssets().open("properties.txt")));
            host=br.readLine().trim();

        }
        catch (IOException ioe){

        }
        int port=1000;
        Client cliente = new Client(host, port);
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext("/checkPassword");
        mensajeEnvio.setUser(usuario);
        cliente.sent(mensajeEnvio, mensajeVuelta,ac);
        byte[] passwordSaved = mensajeVuelta.getPassword();
        return Arrays.equals(passwordSaved,password);

    }
    public static ArrayList<Outfit> conectarseBDOutfits(String peticion,float valoracion,Outfit outfit, Activity ac,String usuario) {
        //Configure connections
        String host="";
        try {
            BufferedReader br= new BufferedReader( new InputStreamReader(ac.getAssets().open("properties.txt")));
            host=br.readLine().trim();

        }
        catch (IOException ioe){

        }
        int port=1000;
        //Create a cliente class
        Client cliente = new Client(host, port);
        HashMap<String, Object> session = new HashMap<String, Object>();
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(peticion);
        mensajeEnvio.setSession(session);
        mensajeEnvio.setOutfit(outfit);
        mensajeEnvio.setUser(usuario);
        mensajeEnvio.setValoracion(valoracion);
        cliente.sent(mensajeEnvio, mensajeVuelta,ac);

        ArrayList<Outfit> listaOutfits = null;

        switch (mensajeVuelta.getContext()) {
            case "/getOutfitResponse":
                listaOutfits = (ArrayList<Outfit>) (mensajeVuelta.getSession().get("Outfit"));

                break;
            case "/insertOutfitResponse":
                System.out.println("PRENDA GUARDADA CORRECTAMENTE");
                break;
            case "/changeValoracionResponse":
                System.out.println("VALORACION CAMBIADA CORRECTAMENTE");
                break;
            default:
                System.out.println("\nError a la vuelta");
                break;

        }
        return listaOutfits;
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void sent(final Message messageOut, final Message messageIn,Activity ac) {

        Socket echoSocket = null;
        OutputStream out = null;
        InputStream in = null;

            StrictMode.ThreadPolicy tp = StrictMode.ThreadPolicy.LAX;
            StrictMode.setThreadPolicy(tp);
            try{
            echoSocket = new Socket(host, port);
            in = echoSocket.getInputStream();
            out = echoSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            //Create the objetct to send
            objectOutputStream.writeObject(messageOut);
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Message msg = (Message) objectInputStream.readObject();
            messageIn.setContext(msg.getContext());
            messageIn.setSession(msg.getSession());
            messageIn.setPassword(msg.getPassword());
            /** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        } catch (UnknownHostException uhe) {
            new AlertDialog.Builder(ac)
                    .setTitle("Error de Conexion con el servidor")
                    .setMessage("Compruebe su conexion a internet y vuelva a intentarlo").setCancelable(true).show();
        }
    catch (IOException ioe) {
        new AlertDialog.Builder(ac)
                .setTitle("Error de Conexion con el servidor")
                .setMessage("Compruebe su conexion  y vuelva a intentarlo").setCancelable(true).show();
    }
            catch (Exception e) {
                new AlertDialog.Builder(ac)
                        .setTitle("Error de Conexion")
                        .setMessage("Compruebe su conexion a internet y vuelva a intentarlo").setCancelable(true).show();
            }


    }
}
