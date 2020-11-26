package com.example.closet.comunicacionserver;


import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class SocketServer extends Thread {
    public static final int PORT_NUMBER = 1000;

    protected Socket socket;

    private SocketServer(Socket socket) {
        this.socket = socket;
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
        start();
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            //first read the object that has been sent
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            Message mensajeIn= (Message)objectInputStream.readObject();
            //Object to return informations
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            Message mensajeOut=new Message();
            switch (mensajeIn.getContext()) {
                case "/getPrenda":
                    PrendaControler controler=new PrendaControler();
                    ArrayList<Prenda> lista=new ArrayList<Prenda>();
                    controler.getPrenda(lista);
                    mensajeOut.setContext("/getPrendaResponse");
                    HashMap<String,Object> session=new HashMap<String, Object>();
                    session.put("Prenda",lista);
                    mensajeOut.setSession(session);
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/getOutfit":
                    OutfitControler controlador=new OutfitControler();
                    ArrayList<Outfit> listaOutfits=new ArrayList<>();
                    controlador.getOutfits(listaOutfits);
                    mensajeOut.setContext("/getOutfitResponse");
                    HashMap<String,Object> session2=new HashMap<String, Object>();
                    session2.put("Outfit",listaOutfits);
                    mensajeOut.setSession(session2);
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/deletePrendaId":
                     controler=new PrendaControler();
                    controler.deletePrendaId(mensajeIn.getIdPrenda());
                    mensajeOut.setContext("/deletePrendaIdResponse");
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/insertPrenda":
                    PrendaControler controler2=new PrendaControler();
                    controler2.insertPrenda(mensajeIn.getPrenda());
                    mensajeOut.setContext("/insertPrendaResponse");
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/insertOutfit":
                     controlador=new OutfitControler();
                    controlador.insertOutfit(mensajeIn.getOutfit());
                    mensajeOut.setContext("/insertOutfitResponse");
                    objectOutputStream.writeObject(mensajeOut);
                    break;
                case "/changeValoracion":
                     controler=new PrendaControler();
                     float valoration=controler.setValoracion(mensajeIn.getIdPrenda(),mensajeIn.getValoracion());
                    mensajeOut.setContext("/changeValoracionResponse");
                    mensajeOut.setValoracion(valoration);
                    objectOutputStream.writeObject(mensajeOut);
                    break;

                default:
                    System.out.println("\nParámetro no encontrado");
                    break;
            }


        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to get streams from client");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("SocketServer Example");
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT_NUMBER);
            while (true) {
                /**
                 * create a new {@link SocketServer} object for each connection
                 * this will allow multiple client connections
                 */
                new SocketServer(server.accept());
            }
        } catch (IOException ex) {
            System.out.println("Unable to start server.");
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}