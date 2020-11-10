package com.example.closet.ui.MiArmario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.closet.R;

public class AddPrenda extends Fragment {
    private Button btnGuardar,btnFoto;
    private ImageView img;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_prenda, container, false);
       btnGuardar=view.findViewById(R.id.btn2);
       btnFoto=view.findViewById(R.id.btnFoto);
       img=view.findViewById(R.id.foto);
       btnFoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       startActivityForResult(takePictureIntent,0);

               }

       });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm =(Bitmap) data.getExtras().get("data");
        img.setImageBitmap(bm);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
