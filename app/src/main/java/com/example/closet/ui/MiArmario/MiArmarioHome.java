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
import com.example.closet.dominio.Outfit;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.PrendaAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MiArmarioHome extends Fragment {

    private static ArrayList<Prenda> prendas;

    private static ArrayList<Outfit> outfits;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.miarmario_main, container, false);
            prendas = Client.conectarseBD("/getPrenda", null, "",0,getActivity());
            ListView lista = view.findViewById(R.id.listaPrendas);
            lista.setAdapter(new PrendaAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, prendas));

            /*
            outfits = Client.conectarseBD("/getOutfit", null, "", 0);
             */

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static ArrayList<Prenda> getPrendasMiArmario() { return prendas;}
    public static ArrayList<Outfit> getOutfitsMiArmario() { return outfits;}

}
