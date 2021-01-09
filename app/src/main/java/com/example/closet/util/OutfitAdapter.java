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
import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.ui.MiArmario.MiArmarioHome;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class OutfitAdapter extends ArrayAdapter<Outfit> {
    private Activity ac;
    private ArrayList<Outfit> outfits;
    private Outfit outfit;

    public OutfitAdapter(Activity ac, int resource, ArrayList<Outfit> outfits){
        super(ac.getApplicationContext(),resource,outfits);
        this.ac=ac;
        this.outfits=outfits;
        Util.setCampos();
    }


    @Nullable
    @Override
    public Outfit getItem(int position) {
        return outfits.get(position);
    }

    @Override
    public int getCount() {
        return outfits.size();
    }

    private class ViewHolder{
        TextView nombre;
        TextView valoracion;
        ImageView abrigo,completo,parteSup,parteInf,calzado,accesorios;
        ImageButton valorar,borrar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        outfit=getItem(position);
        LayoutInflater inflater = (LayoutInflater)ac.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_outfit,null);
            holder=new ViewHolder();
            holder.nombre = convertView.findViewById(R.id.nombreOutfit);
            holder.valoracion= convertView.findViewById(R.id.valoracion);
            holder.valorar=convertView.findViewById(R.id.valorar_prenda);
            holder.borrar=convertView.findViewById(R.id.borrar_prenda);
            holder.abrigo=convertView.findViewById(R.id.imgAbrigo);
            holder.completo=convertView.findViewById(R.id.imgCompleto);
            holder.parteSup=convertView.findViewById(R.id.imgSuperior);
            holder.parteInf=convertView.findViewById(R.id.imgInferior);
            holder.calzado=convertView.findViewById(R.id.imgCalzado);
            holder.accesorios=convertView.findViewById(R.id.imgAccesorios);
            holder.valorar.setOnClickListener(myClickListener);
            holder.borrar.setOnClickListener(myClickListener2);
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder) convertView.getTag();
        holder.nombre.setText(outfit.getId());
        float val = Math.round(outfit.getValoracion() * 10) / 10.0f;
        holder.valoracion.setText(String.valueOf(val));
        if(val<5.0f)
            holder.valoracion.setTextColor(Color.RED);
        ArrayList<Prenda> prendas = outfit.getPrendas();

        for(Prenda  prenda : prendas) {
            String campo = Util.getCampos(prenda.getTipo());
            ByteArrayInputStream is = new ByteArrayInputStream(prenda.getFoto());
            Drawable drw = Drawable.createFromStream(is, "foto");
            switch(campo){
                case "Abrigos":
                    holder.abrigo.setImageDrawable(drw);
                    break;

                case "ParteSuperior":
                    holder.parteSup.setImageDrawable(drw);
                    break;

                case "ParteInferior":
                    holder.parteInf.setImageDrawable(drw);
                    break;

                case "Conjunto":
                    holder.completo.setImageDrawable(drw);
                    break;

                case "Calzado":
                    holder.calzado.setImageDrawable(drw);
                    break;

                case "Accesorios":
                    holder.accesorios.setImageDrawable(drw);
                    break;

            }
        }
        holder.valorar.setTag(position);
        holder.borrar.setTag(position);


        return convertView;
    }
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            final Outfit outfit = getItem(position);
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
                    Client.conectarseBDOutfits("/changeValoracionOutfit", valoracion*2,outfit , ac,MainActivity.getUsuario());
                    MiArmarioHome.actualizarLista(false);
                    popupWindow.dismiss();
                }});
        }
    };
    private View.OnClickListener myClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            final Outfit outfit = getItem(position);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Client.conectarseBDOutfits("/deleteOutfit",  0,outfit,ac, MainActivity.getUsuario());
                            MiArmarioHome.actualizarLista(false);
                            dialog.dismiss();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(ac);
            builder.setTitle("¿Está seguro de que quiere borrar este outfit?").setPositiveButton("Si", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    };

}