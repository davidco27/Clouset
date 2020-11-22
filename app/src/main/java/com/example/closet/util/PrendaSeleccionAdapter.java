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
    private ArrayList<Prenda> prendas;
    private Prenda prenda;

    public PrendaSeleccionAdapter(Activity ac, int resource, ArrayList<Prenda> prendas){
        super(ac.getApplicationContext(),resource,prendas);
        this.ac=ac;
        this.prendas=prendas;
    }


    private class ViewHolder{
        TextView marca;
        TextView tipo;
        TextView valoracion;
        View color;
        ImageView foto;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        prenda=getItem(position);
        LayoutInflater inflater = (LayoutInflater)ac.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_prenda,null);
            holder=new ViewHolder();
            holder.marca = convertView.findViewById(R.id.marca);
            holder.tipo = convertView.findViewById(R.id.tipoPrenda);
            holder.color = convertView.findViewById(R.id.color);
            holder.valoracion= convertView.findViewById(R.id.valoracion);
            holder.foto= convertView.findViewById(R.id.foto);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        holder.marca.setText(prenda.getMarca());
        float val = Math.round(prenda.getValoracion() * 10) / 10.0f;
        holder.valoracion.setText(String.valueOf(val));
        if(val<5.0f)
            holder.valoracion.setTextColor(Color.RED);
        holder.color.setBackgroundColor (Color.parseColor(prenda.getColor()));
        holder.tipo.setText(prenda.getTipo());
        ByteArrayInputStream is = new ByteArrayInputStream(prenda.getFoto());
        Drawable drw = Drawable.createFromStream(is, "foto");
        holder.foto.setImageDrawable(drw);


        return convertView;
    }


}
