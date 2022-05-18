package com.example.musicquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        Button continuar = findViewById(R.id.siguienteActividad);

        int ancho, alto;

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(medidasVentana);

        ancho = medidasVentana.widthPixels;
        alto = medidasVentana.heightPixels;

        getWindow().setLayout( (int) (ancho * 0.33), (int) (alto * 0.46));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpActivity.this, DificultadActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

}
