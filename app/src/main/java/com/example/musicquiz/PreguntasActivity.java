package com.example.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.Range;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class PreguntasActivity extends AppCompatActivity {

    final static String DIFICULTAD = "dificultad";
    final static String EDAD = "edad";
    final static String IDIOMA = "idioma";

    private int preguntaCorrecta, totalPreguntas, resultado, totalRespuestas;

    private int totalPop = 0, totalRespuestasPop = 0, totalRock = 0, totalRespuestasRock = 0, totalTrap = 0, totalRespuestasTrap = 0, totalHeavy = 0, totalRespuestasHeavy = 0;

    private CountDownTimer cdt;

    private ArrayList<Pregunta> listaPreguntas = new ArrayList<>();

    private TextView lblPregunta;

    private int justOneTime;
    private boolean textoColor;

    Pregunta preguntaActual;

    private String dificultad, idioma;
    private int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        lblPregunta = findViewById(R.id.lblPregunta);

        Intent intent = getIntent();

        SimpleDateFormat Format = new SimpleDateFormat("mm");
        int minutos = Integer.parseInt(Format.format(Calendar.getInstance().getTime()));

        LinearLayout fondo = findViewById(R.id.fondo);

        if (minutos >= 30)
        {
            // Animaciones (Fondo 2)
            fondo.setBackgroundResource(R.drawable.fondo_preguntas);
            AnimationDrawable fondoAnimated = (AnimationDrawable) fondo.getBackground();
            fondoAnimated.start();
            textoColor = false;
        }else {
            // Animaciones (Fondo 1)
            fondo.setBackgroundResource(R.drawable.fondo2_animated);
            AnimationDrawable fondoAnimated = (AnimationDrawable) fondo.getBackground();
            fondoAnimated.start();
            textoColor = true;
        }



        //if (Integer.parseInt(dtf.format(min)) >= 30){

        //}else{

        //}

        // Animaciones (Contador)
        ImageView contador = findViewById(R.id.contadorTiempo);
        contador.setBackgroundResource(R.drawable.contador);
        AnimationDrawable contadorTiempo = (AnimationDrawable) contador.getBackground();
        contadorTiempo.start();

        dificultad = intent.getStringExtra(DIFICULTAD);
        edad = intent.getIntExtra(EDAD, 16);
        idioma = intent.getStringExtra(IDIOMA);

        totalPreguntas = comprobarDificultad(dificultad, edad);

        Button opcion1 = findViewById(R.id.opcion1);
        Button opcion2 = findViewById(R.id.opcion2);
        Button opcion3 = findViewById(R.id.opcion3);
        Button opcion4 = findViewById(R.id.opcion4);
        Button botonSiguiente = findViewById(R.id.botonSiguiente);
        ImageView imgSiguiente = findViewById(R.id.imgSiguiente);

        botonSiguiente.setEnabled(false);

        if (justOneTime != 1) {

            JSON json = new JSON(getFilesDir().getAbsolutePath());
            switch (idioma) {
                case "Esp":
                    listaPreguntas = castearPreguntas(json.getPreguntasESP());
                    botonSiguiente.setText(R.string.btnPregunta_esp);
                    break;
                case "Eng":
                    listaPreguntas = castearPreguntas(json.getPreguntasING());
                    botonSiguiente.setText(R.string.btnPregunta_eng);
                    break;
                case "Cat":
                    listaPreguntas = castearPreguntas(json.getPreguntasCAT());
                    botonSiguiente.setText(R.string.btnPregunta_cat);
                    break;
            }

            iniciarContador(botonSiguiente, imgSiguiente, opcion1, opcion2, opcion3, opcion4);

            int posicion = randomizer(listaPreguntas.size());
            preguntaActual = listaPreguntas.get(posicion);

            // Cuenta cuantas preguntas de cada genero aparecen
            calcularTotalGenero(preguntaActual);

            listaPreguntas = eliminarPregunta(listaPreguntas, posicion);

            lblPregunta.setText(preguntaActual.getPregunta());
            opcion1.setText(preguntaActual.getListaRespuestas()[0]);
            opcion2.setText(preguntaActual.getListaRespuestas()[1]);
            opcion3.setText(preguntaActual.getListaRespuestas()[2]);
            opcion4.setText(preguntaActual.getListaRespuestas()[3]);

            preguntaCorrecta = preguntaActual.getRespuestaCorrecta();

            justOneTime = 1;
        }
        final Button[] opciones = {opcion1, opcion2, opcion3, opcion4};
        final Button[] botones = {opcion1, opcion2, opcion3, opcion4, botonSiguiente};

        opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarRespuesta(preguntaCorrecta, 0, opciones);
                enableButtons(false, botones);

                cdt.cancel();

            }
        });

        opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarRespuesta(preguntaCorrecta, 1, opciones);
                enableButtons(false, botones);

                cdt.cancel();

            }
        });

        opcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarRespuesta(preguntaCorrecta, 2, opciones);
                enableButtons(false, botones);

                cdt.cancel();

            }
        });

        opcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarRespuesta(preguntaCorrecta, 3, opciones);
                enableButtons(false, botones);

                cdt.cancel();

            }
        });

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableButtons(true, botones);

                imgSiguiente.setImageResource(0);

                resultado += 1;

                if (totalPreguntas == resultado) {
                    Intent intentResultado = new Intent(PreguntasActivity.this, ResultadoActivity.class);
                    intentResultado.putExtra(ResultadoActivity.RESULTADO, totalRespuestas);
                    intentResultado.putExtra(ResultadoActivity.TOTAL, totalPreguntas);

                    int[] totalGeneros = {
                            totalPop,
                            totalRock,
                            totalTrap,
                            totalHeavy
                    };
                    int[] totalRespuestasGeneros = {
                            totalRespuestasPop,
                            totalRespuestasRock,
                            totalRespuestasTrap,
                            totalRespuestasHeavy
                    };

                    intentResultado.putExtra(ResultadoActivity.TOTAL_GENEROS, totalGeneros);
                    intentResultado.putExtra(ResultadoActivity.TOTAL_RESPUESTAS, totalRespuestasGeneros);
                    intentResultado.putExtra(IDIOMA, idioma);

                    finish();
                    startActivity(intentResultado);
                }
                // ===============================================================================================================================================

                if (listaPreguntas.size() == 0) {
                    errorPreguntas(opciones);
                } else {
                    int posicion = randomizer(listaPreguntas.size());
                    preguntaActual = listaPreguntas.get(posicion);
                    listaPreguntas = eliminarPregunta(listaPreguntas, posicion);
                    lblPregunta.setText(preguntaActual.getPregunta());
                    opcion1.setText(preguntaActual.getListaRespuestas()[0]);
                    opcion2.setText(preguntaActual.getListaRespuestas()[1]);
                    opcion3.setText(preguntaActual.getListaRespuestas()[2]);
                    opcion4.setText(preguntaActual.getListaRespuestas()[3]);
                    preguntaCorrecta = preguntaActual.getRespuestaCorrecta();
                    calcularTotalGenero(preguntaActual);
                }
                restablecerBotones(opcion1, opcion2, opcion3, opcion4);
                iniciarContador(botonSiguiente, imgSiguiente, opcion1, opcion2, opcion3, opcion4);
            }
        });
    }

    /**
     * Mètode que canvia el color dels botons
     *
     * @param correcta     Enter amb la posició de la resposta correcta
     * @param seleccionada Enter amb la posició de la resposta sel·leccionada
     * @param opciones     Array amb els quatre botons
     */
    private void marcarRespuesta(int correcta, int seleccionada, Button[] opciones) {
        opciones[correcta].setBackgroundColor(getResources().getColor(R.color.verdeButton));
        if (seleccionada != correcta) {
            opciones[seleccionada].setBackgroundColor(getResources().getColor(R.color.rojoButton));
        }else{
            totalRespuestas++;
            sumarResultadoGenero(preguntaActual);
        }
    }

    /**
     * Metode que canvia el valor 'enabled' dels botons
     *
     * @param modo    Mode en que es posaran els botons
     * @param botones <p>
     *                Array que conté els botons. Els botons 0 a 3 són les opcions, i el botó 4 es el 'botonSiguiente'
     *                </p>
     */
    private void enableButtons(boolean modo, Button[] botones) {
        botones[0].setEnabled(modo);
        botones[1].setEnabled(modo);
        botones[2].setEnabled(modo);
        botones[3].setEnabled(modo);

        botones[4].setEnabled(!modo);
    }

    /**
     * Metode que, si per alguna rao no es poden llegir les pregunte4s, mostrara un missatge d'error en els botons
     *
     * @param opciones Array estatic amb els 'Buttons' de les respostes
     */
    private void errorPreguntas(Button[] opciones){
        opciones[0].setText("No se ha podido cargar la pregunta");
        opciones[1].setText("No se ha podido cargar la pregunta");
        opciones[2].setText("No se ha podido cargar la pregunta");
        opciones[3].setText("No se ha podido cargar la pregunta");
    }

    /*public void botonesCorrecto(Button opcionErroneo, Button opcionCorrecto) {
        opcionErroneo.setBackgroundColor(getResources().getColor(R.color.rojoButton));
        opcionCorrecto.setBackgroundColor(getResources().getColor(R.color.verdeButton));
    }*/

    public void restablecerBotones(Button opcion1, Button opcion2, Button opcion3, Button opcion4) {
        opcion1.setBackgroundColor(getResources().getColor(R.color.grisDesactivado));
        opcion2.setBackgroundColor(getResources().getColor(R.color.grisDesactivado));
        opcion3.setBackgroundColor(getResources().getColor(R.color.grisDesactivado));
        opcion4.setBackgroundColor(getResources().getColor(R.color.grisDesactivado));
    }

    public void calcularTotalGenero(Pregunta preguntaActual) {
        if (preguntaActual.getGenero().equalsIgnoreCase("Pop")) { // Pop
            totalPop += 1;
        } else if (preguntaActual.getGenero().equalsIgnoreCase("Rock")) { // Rock
            totalRock += 1;
        } else if (preguntaActual.getGenero().equalsIgnoreCase("Trap")) { // Trap
            totalTrap += 1;
        } else { // Heavy
            totalHeavy += 1;
        }
    }

    public void sumarResultadoGenero(Pregunta preguntaActual) {
        if (preguntaActual.getGenero().equalsIgnoreCase("Pop")) { // Pop
            totalRespuestasPop += 1;
        } else if (preguntaActual.getGenero().equalsIgnoreCase("Rock")) { // Rock
            totalRespuestasRock += 1;
        } else if (preguntaActual.getGenero().equalsIgnoreCase("Trap")) { // Trap
            totalRespuestasTrap += 1;
        } else { // Heavy
            totalRespuestasHeavy += 1;
        }
    }

    public void iniciarContador(Button botonSiguiente, ImageView imgSiguiente, Button opcion1, Button opcion2, Button opcion3, Button opcion4) {

        final Button[] opciones = {opcion1, opcion2, opcion3, opcion4};

        final TextView textoCuenta = findViewById(R.id.txtTiempo);
        //int textoCuentaSegundos = Integer.valueOf(textoCuenta.getText().toString());

        cdt = new CountDownTimer(25000, 1000) {

            @Override
            public void onTick(long l) {

                if(textoColor == true)
                {
                    textoCuenta.setTextColor(getResources().getColor(R.color.black));
                }else{
                    textoCuenta.setTextColor(getResources().getColor(R.color.grisDesactivado));
                }

                long segundosPendientes = l / 1000;
                textoCuenta.setText(segundosPendientes + "s");

                if (segundosPendientes <= 7) {
                    int rojo = Color.parseColor("#ff0000");
                    textoCuenta.setTextColor(rojo);
                }
            }

            @Override
            public void onFinish() {
                textoCuenta.setText("25");

                if(textoColor == true)
                {
                    textoCuenta.setTextColor(getResources().getColor(R.color.black));
                }else{
                    textoCuenta.setTextColor(getResources().getColor(R.color.grisDesactivado));
                }

                botonSiguiente.setEnabled(false);
                imgSiguiente.setImageResource(0);

                resultado += 1;

                if (totalPreguntas == resultado) {

                    Intent intentResultado = new Intent(PreguntasActivity.this, ResultadoActivity.class);
                    intentResultado.putExtra(ResultadoActivity.RESULTADO, totalRespuestas);
                    intentResultado.putExtra(ResultadoActivity.TOTAL, totalPreguntas);

                    int[] totalGeneros = {totalPop, totalRock, totalTrap, totalHeavy};
                    int[] totalRespuestasGeneros = {totalRespuestasPop, totalRespuestasRock, totalRespuestasTrap, totalRespuestasHeavy};

                    intentResultado.putExtra("totalGeneros", totalGeneros);
                    intentResultado.putExtra("totalRespuestasGeneros", totalRespuestasGeneros);
                    intentResultado.putExtra(IDIOMA, idioma);

                    finish();
                    startActivity(intentResultado);
                }

                if (listaPreguntas.size() == 0) {
                    errorPreguntas(opciones);
                } else {
                    int posicion = randomizer(listaPreguntas.size());
                    preguntaActual = listaPreguntas.get(posicion);
                    listaPreguntas = eliminarPregunta(listaPreguntas, posicion);
                    lblPregunta.setText(preguntaActual.getPregunta());
                    opcion1.setText(preguntaActual.getListaRespuestas()[0]);
                    opcion2.setText(preguntaActual.getListaRespuestas()[1]);
                    opcion3.setText(preguntaActual.getListaRespuestas()[2]);
                    opcion4.setText(preguntaActual.getListaRespuestas()[3]);
                    preguntaCorrecta = preguntaActual.getRespuestaCorrecta();
                    calcularTotalGenero(preguntaActual);
                }
                restablecerBotones(opcion1, opcion2, opcion3, opcion4);
                cdt.cancel();
                iniciarContador(botonSiguiente, imgSiguiente, opcion1, opcion2, opcion3, opcion4);

            }
        };
        cdt.cancel();
        cdt.start();
    }

    // Transforma el array statico de preguntas en un arraylist;
    private ArrayList<Pregunta> castearPreguntas(Pregunta[] preguntas) {
        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        Collections.addAll(listaPreguntas, preguntas);
        return listaPreguntas;
    }

    private ArrayList<Pregunta> eliminarPregunta(ArrayList<Pregunta> listaPreguntas, int posicion) {
        ArrayList<Pregunta> lista = listaPreguntas;
        lista.remove(posicion);
        return lista;
    }

    private int randomizer(int size) {
        return (int) (Math.random() * size);
    }

    public int comprobarDificultad(String dificultad, int edad) {
        int preguntasTotal;
        Range<Integer> ninio, joven;

        // Fàcil (Rangos de edad [1-14],[15-28],[29-*])
        if (dificultad.equalsIgnoreCase("Facil") || dificultad.equalsIgnoreCase("Fàcil") || dificultad.equalsIgnoreCase("Easy")) {
            ninio = Range.between(1, 11);
            joven = Range.between(12, 28);
            if (ninio.contains(edad)) {
                preguntasTotal = 5;
            } else if (joven.contains(edad)) {
                preguntasTotal = 6;
            } else {
                preguntasTotal = 7;
            }
        }// Normal (Rangos de edad [1-10],[11-22],[23-*])
        else if (dificultad.equalsIgnoreCase("Normal") || dificultad.equalsIgnoreCase("Medium")) {
            ninio = Range.between(1, 10);
            joven = Range.between(11, 22);
            if (ninio.contains(edad)) {
                preguntasTotal = 8;
            } else if (joven.contains(edad)) {
                preguntasTotal = 9;
            } else {
                preguntasTotal = 10;
            }
        }// Dificil (Rangos de edad [1-8],[9-18],[19-*])
        else {
            ninio = Range.between(1, 8);
            joven = Range.between(9, 18);
            if (ninio.contains(edad)) {
                preguntasTotal = 11;
            } else if (joven.contains(edad)) {
                preguntasTotal = 12;
            } else {
                preguntasTotal = 13;
            }
        }
        return preguntasTotal;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}