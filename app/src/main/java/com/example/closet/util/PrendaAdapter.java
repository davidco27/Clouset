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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.closet.MainActivity;
import com.example.closet.R;

import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Prenda;
import com.example.closet.ui.MiArmario.MiArmarioHome;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PrendaAdapter extends ArrayAdapter<Prenda> {
    private Activity ac;
    private ArrayList<Prenda> prendas;
    private Prenda prenda;

    public PrendaAdapter(Activity ac, int resource, ArrayList<Prenda> prendas){
        super(ac.getApplicationContext(),resource,prendas);
        this.ac=ac;
        this.prendas=prendas;
    }


    @Nullable
    @Override
    public Prenda getItem(int position) {
        return prendas.get(position);
    }

    @Override
    public int getCount() {
        return prendas.size();
    }

    private class ViewHolder{
        TextView marca;
        TextView tipo;
         TextView valoracion;
        View color;
        ImageView foto;
         ImageButton valorar,borrar;
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
            holder.valorar=convertView.findViewById(R.id.valorar_prenda);
            holder.borrar=convertView.findViewById(R.id.borrar_prenda);
            holder.valorar.setOnClickListener(myClickListener);
            holder.borrar.setOnClickListener(myClickListener2);
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
        holder.valorar.setTag(position);
        holder.borrar.setTag(position);


        return convertView;
    }
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            final Prenda prenda = getItem(position);
            LayoutInflater inflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.popup_valorar,null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);
            final Button getRating = popupView.findViewById(R.id.getRating);
            final RatingBar ratingBar = popupView.findViewById(R.id.rating);
            getRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float valoracion=ratingBar.getRating();
                        Client.conectarseBD("/changeValoracion", null, prenda.getId(),valoracion*2, MainActivity.getUsuario(),ac);
                    MiArmarioHome.actualizarLista();
                         popupWindow.dismiss();
            }});
        }
    };
    private View.OnClickListener myClickListener2 = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        final Prenda prenda = getItem(position);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Client.conectarseBD("/deletePrendaId", null, prenda.getId(), 0, MainActivity.getUsuario(),ac);
                        MiArmarioHome.actualizarLista();
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                         dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ac);
        builder.setTitle("¿Está seguro de que quiere borrar la prenda?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
};

}
