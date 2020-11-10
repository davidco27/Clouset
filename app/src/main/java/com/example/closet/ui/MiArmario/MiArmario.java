package com.example.closet.ui.MiArmario;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.example.closet.R;
import com.example.closet.dominio.Prenda;
import com.example.closet.util.PrendaAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MiArmario extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton btnMenu;
    private String prueba;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_miarmario, container, false);
        final DrawerLayout drawer = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view2);
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        btnMenu= view.findViewById(R.id.btn1);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        ArrayList<Prenda> prendas =new ArrayList<>();
        //prendas.add(new Prenda(getActivity().getResources().getDrawable(R.drawable.pantalon),"Vaqueros informales","Pantalon Vaquero","Zara","Azul marino"));
        //prendas.add(new Prenda(getActivity().getResources().getDrawable(R.drawable.cami),"Camiseta del Madrid","Camiseta","Adidas","Blanco"));
        PrendaAdapter prendaAdapter =new PrendaAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,prendas);
        ListView lista = view.findViewById(R.id.listaPrendas);
        lista.setAdapter(prendaAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}