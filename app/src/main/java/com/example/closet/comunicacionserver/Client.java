package com.example.closet.comunicacionserver;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.StrictMode;

import com.example.closet.dominio.Prenda;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class Client {
    private String host;
    private int port;


    public static ArrayList<Prenda> conectarseBD(String peticion, Prenda prenda, String idPrenda, float valoracion,Activity ac) {
        //Configure connections
        String host="192.168.43.215";
        int port=1000;
        //Create a cliente class
        Client cliente = new Client(host, port);
        HashMap<String, Object> session = new HashMap<String, Object>();
        Message mensajeEnvio = new Message();
        Message mensajeVuelta = new Message();
        mensajeEnvio.setContext(peticion);
        mensajeEnvio.setSession(session);
        mensajeEnvio.setPrenda(prenda);
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
