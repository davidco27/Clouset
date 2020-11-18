package com.example.closet.ui.MiArmario;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.closet.R;
import com.example.closet.comunicacionserver.Client;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.PrendaAdapter;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

public class MiArmarioHome extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.miarmario_main, container, false);
        try {
            ArrayList<Prenda> prendas = Client.conectarseBD("/getPrenda", null, "",0);
            ListView lista = view.findViewById(R.id.listaPrendas);
            lista.setAdapter(new PrendaAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, prendas));



            }
        catch (Exception e){
            new AlertDialog.Builder(getContext())
                    .setTitle("Error de Conexion con el servidor")
                    .setMessage("Compruebe su conexion a internet y vuelva a intentarlo").setCancelable(true).show();

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

}
