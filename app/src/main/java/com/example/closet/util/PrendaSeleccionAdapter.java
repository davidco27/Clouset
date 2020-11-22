package com.example.closet.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PrendaSeleccionAdapter extends ArrayAdapter<Prenda> {
    private Activity ac;
    private Prenda prenda;

    public PrendaSeleccionAdapter(Activity ac, int resource, ArrayList<Prenda> prendas){
        super(ac.getApplicationContext(),resource,prendas);
        this.ac=ac;
    }


    private class ViewHolder{
        ImageView foto;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        prenda=getItem(position);
        LayoutInflater inflater = (LayoutInflater)ac.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.lista_select,null);
            holder=new ViewHolder();
            holder.foto= convertView.findViewById(R.id.imagen);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        ByteArrayInputStream is = new ByteArrayInputStream(prenda.getFoto());
        Drawable drw = Drawable.createFromStream(is, "foto");
        holder.foto.setImageDrawable(drw);

        return convertView;
    }


}
