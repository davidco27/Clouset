package com.example.closet.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class PrendaAdapter extends ArrayAdapter<Prenda> {
    private Activity ac;
    public PrendaAdapter(Activity ac, int resource, ArrayList<Prenda> prendas){
        super(ac.getApplicationContext(),resource,prendas);
        this.ac=ac;
    }
    private class ViewHolder{
        TextView marca;
        TextView tipo;
        TextView valoracion;
        TextView color;
        ImageView foto;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Prenda prenda=getItem(position);
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
        holder.valoracion.setText(String.valueOf(prenda.getValoracion()));
        if(prenda.getValoracion()<5.0f)
            holder.valoracion.setTextColor(Color.RED);
        holder.color.setText(prenda.getColor());
        holder.tipo.setText(prenda.getTipo());
        ByteArrayInputStream is = new ByteArrayInputStream(prenda.getFoto());
        Drawable drw = Drawable.createFromStream(is, "foto");
        holder.foto.setImageDrawable(drw);
        return convertView;
    }
}
