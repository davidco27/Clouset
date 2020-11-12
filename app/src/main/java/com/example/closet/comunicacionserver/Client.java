package com.example.closet.comunicacionserver;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.closet.dominio.Prenda;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;


public class Client {
    private String host;
    private static Context activity;
    private int port;

    public static void main(String args[]) {
     //   ByteArrayOutputStream stream = new ByteArrayOutputStream();
      //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
       // byte[] imageInByte = stream.toByteArray();
       // conectarseBD("/insertPrenda",new Prenda(5.4f,null,"Id distinto","Vaqueros","Zara","Azul marino",
         //       "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstleyVEVO"));
        //conectarseBD("/getPrenda",null);
    }

    public static void conectarseBD(String peticion, Prenda prenda, Context ac) {
        activity=ac;
        //Configure connections
        String host ="172.24.144.215";
                //PropertiesISW.getInstance().getProperty("host");
        int port = 8081;
                //Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
        //Create a cliente class
        Client cliente = new Client(host, port);
        HashMap<String, Object> session = new HashMap<String, Object>();
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(peticion);
        mensajeEnvio.setSession(session);
        mensajeEnvio.setPrenda(prenda);
        cliente.sent(mensajeEnvio, mensajeVuelta);


        switch (mensajeVuelta.getContext()) {
            case "/getPrendaResponse":
                ArrayList<Prenda> listaPrendas = (ArrayList<Prenda>) (mensajeVuelta.getSession().get("Prenda"));
                for (Prenda prenda2 : listaPrendas) {
                    System.out.println(prenda2.getId() + " " + prenda2.getMarca() + " " + prenda2.getValoracion());
                }
                break;
            case "/insertPrendaResponse":
                System.out.println("PRENDA GUARDADA CORRECTAMENTE");
                break;
            default:
                System.out.println("\nError a la vuelta");
                break;

        }
    }


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void sent(Message messageOut, Message messageIn) {
        try {

            Socket echoSocket = null;
            OutputStream out = null;
            InputStream in = null;

            try {
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


            } catch (UnknownHostException e) {
                new AlertDialog.Builder(activity).setTitle("ERROR").setMessage("PENE")
                        .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
                System.err.println("Unknown host: " + host);
                System.exit(1);
            } catch (IOException e) {
                new AlertDialog.Builder(activity).setTitle("CONECTAR").setMessage("ZION")
                        .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
                System.err.println("Unable to get streams from servers");
                System.exit(1);
            }

            /** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
