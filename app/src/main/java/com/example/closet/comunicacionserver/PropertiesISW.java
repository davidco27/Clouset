package com.example.closet.comunicacionserver;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertiesISW extends Properties{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static PropertiesISW prop;


    private PropertiesISW() {
        try {
            this.loadFromXML(new FileInputStream("properties.xml"));
        } catch (InvalidPropertiesFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
    }
    public static Properties getMyProperties(Context c){
        Properties properties= new Properties();
        try{
                AssetManager assetManager = c.getAssets();
            InputStream inputStream = assetManager.open("properties.xml");

            properties.load(inputStream);
            inputStream.close();

        }catch (IOException e){
            new AlertDialog.Builder(c)
                    .setTitle("Error de Conexion con el servidor")
                    .setMessage(e.getMessage()).setCancelable(true).show();
        }

        return properties;
    }
    public static PropertiesISW getInstance() {
        if (prop==null) {
            prop=new PropertiesISW();
        }
        return prop;

    }

}