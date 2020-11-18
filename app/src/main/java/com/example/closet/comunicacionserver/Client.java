package com.example.closet.comunicacionserver;
import android.app.AlertDialog;
import android.os.StrictMode;

import com.example.closet.dominio.Prenda;
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
    private int port;


    public static ArrayList<Prenda> conectarseBD(String peticion, Prenda prenda, String idPrenda, float valoracion) throws Exception {
        //Configure connections
        String host = "192.168.43.215";
        //PropertiesISW.getInstance().getProperty("host");
        int port = 1000;
        //Integer.parseInt(PropertiesISW.getInstance().getProperty("port"));
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
        cliente.sent(mensajeEnvio, mensajeVuelta);

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


    public void sent(final Message messageOut, final Message messageIn) throws Exception {

        Socket echoSocket = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy tp = StrictMode.ThreadPolicy.LAX;
            StrictMode.setThreadPolicy(tp);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }


    }
}
