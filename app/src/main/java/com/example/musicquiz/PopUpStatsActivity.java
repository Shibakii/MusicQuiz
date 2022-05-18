package com.example.musicquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class PopUpStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_stats_layout);

        // ========================================================================
        // Sacamos todos los datos del intent que necesitamos.
        // ========================================================================

        Intent intent = getIntent();
        int[] maximoStats = intent.getIntArrayExtra("1");
        int[] respuestasStats = intent.getIntArrayExtra("2");
        double[] porcentajes = intent.getDoubleArrayExtra("3");
        String idioma = intent.getStringExtra("4");
        int totalPreguntas = maximoStats[0] + maximoStats[1] + maximoStats[2] + maximoStats[3];

        // ========================================================================
        // Cambiamos el idioma.
        // ========================================================================

        TextView totalPreguntas_1 = findViewById(R.id.totalPreg_1);
        TextView totalPreguntas_2 = findViewById(R.id.totalPreg_2);
        TextView totalPreguntas_3 = findViewById(R.id.totalPreg_3);
        TextView totalPreguntas_4 = findViewById(R.id.totalPreg_4);

        TextView totalRespuestas_1 = findViewById(R.id.totalResp_1);
        TextView totalRespuestas_2 = findViewById(R.id.totalResp_2);
        TextView totalRespuestas_3 = findViewById(R.id.totalResp_3);
        TextView totalRespuestas_4 = findViewById(R.id.totalResp_4);

        TextView porcentajes_1 = findViewById(R.id.porcentaje_1);
        TextView porcentajes_2 = findViewById(R.id.porcentaje_2);
        TextView porcentajes_3 = findViewById(R.id.porcentaje_3);
        TextView porcentajes_4 = findViewById(R.id.porcentaje_4);

        if(idioma.equals("Cat")){

            totalPreguntas_1.setText(R.string.totalPreguntas_cat);
            totalPreguntas_2.setText(R.string.totalPreguntas_cat);
            totalPreguntas_3.setText(R.string.totalPreguntas_cat);
            totalPreguntas_4.setText(R.string.totalPreguntas_cat);

            totalRespuestas_1.setText(R.string.totalCorrectas_cat);
            totalRespuestas_2.setText(R.string.totalCorrectas_cat);
            totalRespuestas_3.setText(R.string.totalCorrectas_cat);
            totalRespuestas_4.setText(R.string.totalCorrectas_cat);

            porcentajes_1.setText(R.string.porcentajes_cat);
            porcentajes_2.setText(R.string.porcentajes_cat);
            porcentajes_3.setText(R.string.porcentajes_cat);
            porcentajes_4.setText(R.string.porcentajes_cat);

        }else if(idioma.equals("Eng")){

            totalPreguntas_1.setText(R.string.totalPreguntas_eng);
            totalPreguntas_2.setText(R.string.totalPreguntas_eng);
            totalPreguntas_3.setText(R.string.totalPreguntas_eng);
            totalPreguntas_4.setText(R.string.totalPreguntas_eng);

            totalRespuestas_1.setText(R.string.totalCorrectas_eng);
            totalRespuestas_2.setText(R.string.totalCorrectas_eng);
            totalRespuestas_3.setText(R.string.totalCorrectas_eng);
            totalRespuestas_4.setText(R.string.totalCorrectas_eng);

            porcentajes_1.setText(R.string.porcentajes_eng);
            porcentajes_2.setText(R.string.porcentajes_eng);
            porcentajes_3.setText(R.string.porcentajes_eng);
            porcentajes_4.setText(R.string.porcentajes_eng);

        }

        // ========================================================================
        // Mostramos el pop up con las medidas que deseamos.
        // ========================================================================

        int ancho, alto;

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(medidasVentana);

        ancho = medidasVentana.widthPixels;
        alto = medidasVentana.heightPixels;

        getWindow().setLayout( (int) (ancho * 0.35), (int) (alto * 0.75));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // ========================================================================
        // Instanciamos todos los TextView correspondientes
        // ========================================================================

        TextView PopMax = findViewById(R.id.resultadoPopMax);
        TextView PopRespuestas = findViewById(R.id.resultadoPopRespuestas);
        TextView PopPorcentaje = findViewById(R.id.resultadoPopPorcentaje);

        TextView RockMax = findViewById(R.id.resultadoRockMax);
        TextView RockRespuestas = findViewById(R.id.resultadoRockRespuestas);
        TextView RockPorcentaje = findViewById(R.id.resultadoRockPorcentaje);

        TextView TrapMax = findViewById(R.id.resultadoTrapMax);
        TextView TrapRespuestas = findViewById(R.id.resultadoTrapRespuestas);
        TextView TrapPorcentaje = findViewById(R.id.resultadoTrapPorcentaje);

        TextView HeavyMax = findViewById(R.id.resultadoHeavyMax);
        TextView HeavyRespuestas = findViewById(R.id.resultadoHeavyRespuestas);
        TextView HeavyPorcentaje = findViewById(R.id.resultadoHeavyPorcentaje);

        // ========================================================================
        // Añadir el texto al Pop Up
        // ========================================================================

        PopMax.setText(" " + maximoStats[0] + " / " + totalPreguntas);
        PopRespuestas.setText(" " + respuestasStats[0] + " / " + maximoStats[0]);
        PopPorcentaje.setText(" " + porcentajes[0] + "%");

        RockMax.setText(" " + maximoStats[1] + " / " + totalPreguntas);
        RockRespuestas.setText(" " + respuestasStats[1] + " / " + maximoStats[1]);
        RockPorcentaje.setText(" " + porcentajes[1] + "%");

        TrapMax.setText(" " + maximoStats[2] + " / " + totalPreguntas);
        TrapRespuestas.setText(" " + respuestasStats[2] + " / " + maximoStats[2]);
        TrapPorcentaje.setText(" " + porcentajes[2] + "%");

        HeavyMax.setText(" " + maximoStats[3] + " / " + totalPreguntas);
        HeavyRespuestas.setText(" " + respuestasStats[3] + " / " + maximoStats[3]);
        HeavyPorcentaje.setText(" " + porcentajes[3] + "%");
    }
}
