package com.example.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class ResultadoActivity extends AppCompatActivity {

    final static String RESULTADO = "resultado";
    final static String TOTAL = "total";
    final static String TOTAL_GENEROS = "totalGeneros";
    final static String TOTAL_RESPUESTAS = "totalRespuestasGeneros";

    private String idioma;

    private int i = 0;

    private int[] totalGeneros, respuestasCorrectasGeneros;
    private double[] porcentages;

    private Personaje personajeMaster, personajeMinimo;

    ArrayList<Personaje> listaPersonajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Animacion (Star)
        ImageView star = findViewById(R.id.star);
        star.setBackgroundResource(R.drawable.star_animated);
        AnimationDrawable starAnimated = (AnimationDrawable) star.getBackground();
        starAnimated.start();


        Intent intent = getIntent();

        int puntuacion = intent.getIntExtra(RESULTADO, -1);
        int total = intent.getIntExtra(TOTAL, -1);

        totalGeneros = intent.getIntArrayExtra(TOTAL_GENEROS);
        respuestasCorrectasGeneros = intent.getIntArrayExtra(TOTAL_RESPUESTAS);
        idioma = intent.getStringExtra(PreguntasActivity.IDIOMA);

        // =======================================
        porcentages = porcentagesGeneros(totalGeneros, respuestasCorrectasGeneros);
        // =======================================

        JSON json = new JSON(getFilesDir().getAbsolutePath());

        listaPersonajes = castearPersonaje(json.adaptarRutaImg(json.getPersonajes()));

        Personaje KikoMinimoScore = listaPersonajes.get(0);
        Personaje MichaelMaximoScore = listaPersonajes.get(1);

        personajeMaster = MichaelMaximoScore;
        personajeMinimo = KikoMinimoScore;

        TextView puntuacionTotal = findViewById(R.id.puntuacionTotal);

        puntuacionTotal.setText(puntuacion + " / " + total);

        if (puntuacion == 0){

            ImageView imgPersonaje = findViewById(R.id.imgPersonaje);

            Uri uriPersonaje = Uri.fromFile(new File(personajeMinimo.getRutaImg()));
            imgPersonaje.setImageURI(uriPersonaje);

            prefijo(idioma, personajeMinimo);

            TextView pop = findViewById(R.id.Genero1);
            TextView rock = findViewById(R.id.Genero2);
            TextView trap = findViewById(R.id.Genero3);
            TextView heavy = findViewById(R.id.Genero4);

            pop.setText("Pop: 0%");
            rock.setText("Rock: 0%");
            trap.setText("Trap: 0%");
            heavy.setText("Heavy: 0%");

        } else {
            asignarResultado(listaPersonajes);
        }

        Button botonJugarNuevo = findViewById(R.id.botonJugarNuevo);
        Button botonVolverInicio = findViewById(R.id.botonVolverInicio);
        Button popUpStats = findViewById(R.id.mostrarStats);

        botonJugarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentIniciarNuevo = new Intent(ResultadoActivity.this, DificultadActivity.class);

                startActivity(intentIniciarNuevo);

            }
        });

        botonVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentVolverInicio = new Intent(ResultadoActivity.this, MainActivity.class);

                startActivity(intentVolverInicio);

            }
        });

        popUpStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentPopUpStats = new Intent(ResultadoActivity.this, PopUpStatsActivity.class);

                intentPopUpStats.putExtra("1", totalGeneros);
                intentPopUpStats.putExtra("2", respuestasCorrectasGeneros);
                intentPopUpStats.putExtra("3", porcentages);
                intentPopUpStats.putExtra("4", idioma);

                startActivity(intentPopUpStats);

            }
        });
    }
    private void prefijo(String idioma, Personaje personaje) {

        TextView lblEstadisticas = findViewById(R.id.lblEstadisticas);
        TextView lblPuntuacion = findViewById(R.id.lblPuntuacion);
        TextView textoPersonaje = findViewById(R.id.textoPersonaje);
        Button jugarNuevo = findViewById(R.id.botonJugarNuevo);
        Button volverInicio = findViewById(R.id.botonVolverInicio);
        Button botonStats = findViewById(R.id.mostrarStats);

        if (idioma.equals("Esp")) {
            // Español
            lblEstadisticas.setText(" - Estadisticas - ");
            lblPuntuacion.setText("Puntuación: ");
            textoPersonaje.setText("Eres " + personaje.getNombrePersonaje());
            volverInicio.setText("Volver al inicio");
            jugarNuevo.setText("Jugar de nuevo");
            botonStats.setText("Mostrar estadisticas");
        } else if (idioma.equals("Eng")) {
            // English
            lblEstadisticas.setText(" - Stats - ");
            lblPuntuacion.setText("Score: ");
            textoPersonaje.setText("You are " + personaje.getNombrePersonaje());
            volverInicio.setText("Return to start");
            jugarNuevo.setText("Play again");
            botonStats.setText("Show Stats");
        } else {
            // Català
            lblEstadisticas.setText(" - Estadistiques - ");
            lblPuntuacion.setText("Puntuació:");
            textoPersonaje.setText("Ets " + personaje.getNombrePersonaje());
            volverInicio.setText("Tornar a l'inici");
            jugarNuevo.setText("Tornar a Jugar");
            botonStats.setText("Mostrar estadístiques");
        }
    }
    public double[] porcentagesGeneros(int[] totalGeneros, int[] resultadosGeneros) {

        double[] generos = new double[totalGeneros.length];
        double[] resultados = new double[resultadosGeneros.length];

        for (int i = 0; i < totalGeneros.length; i++) {
            generos[i] = (double) totalGeneros[i];
        }
        for (int i = 0; i < resultadosGeneros.length; i++) {
            resultados[i] = (double) resultadosGeneros[i];
        }

        DecimalFormat format = new DecimalFormat("##.##");

        double[] porcentages = new double[totalGeneros.length];
        try {

            for (int i = 0; i < generos.length; i++) {
                if(generos[i] != 0 && resultados[i] != 0){
                    double resultado = (( resultados[i]/ generos[i]) * 100);

                    String aux = format.format(resultado);
                    Number number = format.parse(aux);

                    porcentages[i] = number.doubleValue();
                }else{
                    porcentages[i] = 0;
                }
            }

        } catch (ParseException ignored) {
        }
        return porcentages;
    }


    // generos[0] pop
    // generos[1] rock
    // generos[2] trap
    // generos[4] heavy

    private Personaje obtenerPersonaje(ArrayList<Personaje> personajes, double[] porcentages) {
        // si hay mas correctas de pop
        boolean poprock, poptrap, popheavy, rocktrap, rockheavy, trapheavy;
        poprock = (porcentages[0] == porcentages[1]) && porcentages[0] > porcentages[2] && porcentages[0] > porcentages[3];
        poptrap = (porcentages[0] == porcentages[2]) && porcentages[0] > porcentages[1] && porcentages[0] > porcentages[3];
        popheavy = (porcentages[0] == porcentages[3]) && porcentages[0] > porcentages[1] && porcentages[0] > porcentages[2];
        rocktrap = (porcentages[1] == porcentages[2]) && porcentages[1] > porcentages[0] && porcentages[1] > porcentages[3];
        rockheavy = (porcentages[1] == porcentages[3]) && porcentages[1] > porcentages[0] && porcentages[1] > porcentages[2];
        trapheavy = (porcentages[2] == porcentages[3]) && porcentages[2] > porcentages[1] && porcentages[2] > porcentages[0];

        Personaje personaje;

        if (porcentages[0] > porcentages[1] && porcentages[0] > porcentages[2] && porcentages[0] > porcentages[3]) {
            personaje = personajeAleatorio(personajes, "Pop");
        } else if (porcentages[1] > porcentages[0] && porcentages[1] > porcentages[2] && porcentages[1] > porcentages[3]) {
            personaje = personajeAleatorio(personajes, "Rock");
        } else if (porcentages[2] > porcentages[0] && porcentages[2] > porcentages[1] && porcentages[2] > porcentages[3]) {
            personaje = personajeAleatorio(personajes, "Trap");
        } else if (porcentages[3] > porcentages[0] && porcentages[3] > porcentages[1] && porcentages[3] > porcentages[2]) {
            personaje = personajeAleatorio(personajes, "Heavy");
        } else if (poprock) {
            personaje = personajeAleatorio(personajes, "Pop", "Rock");
        } else if (poptrap) {
            personaje = personajeAleatorio(personajes, "Pock", "Trap");
        } else if (popheavy) {
            personaje = personajeAleatorio(personajes, "Pop", "Heavy");
        } else if (rocktrap) {
            personaje = personajeAleatorio(personajes, "Rock", "Trap");
        } else if (rockheavy) {
            personaje = personajeAleatorio(personajes, "Rock", "Heavy");
        } else if (trapheavy) {
            personaje = personajeAleatorio(personajes, "Trap", "Heavy");
        } else {
            personaje = personajeMaster;
        }

        return personaje;
    }

    private Personaje personajeAleatorio(ArrayList<Personaje> personajes, String... generos) {

        Personaje personaje;
        ArrayList<Personaje> personajesGeneros = new ArrayList<>();

        for (Personaje personajeAux : personajes) {
            for (String genero : generos) {
                if (personajeAux.getGenero().equals(genero)) {
                    personajesGeneros.add(personajeAux);
                }
            }
        }
        int index = randomizer(personajesGeneros.size());
        personaje = personajesGeneros.get(index);
        return personaje;
    }

    public void asignarResultado(ArrayList<Personaje> personajes) {

        Personaje personaje = obtenerPersonaje(personajes, porcentages);

        prefijo(idioma, personaje);

        ImageView imgPersonaje = findViewById(R.id.imgPersonaje);
        //imgPersonaje.setImageResource(personaje.getRutaImg());
        Uri uriPersonaje = Uri.fromFile(new File(personaje.getRutaImg()));
        imgPersonaje.setImageURI(uriPersonaje);

        TextView pop = findViewById(R.id.Genero1);
        TextView rock = findViewById(R.id.Genero2);
        TextView trap = findViewById(R.id.Genero3);
        TextView heavy = findViewById(R.id.Genero4);

//        float porcentajePop = (respuestasCorrectasGeneros[0] * 100) / totalGeneros[0];

        // REVISAR (MULTIPLICA POR 0 Y PETA)

        //float porcentajeRock = (respuestasCorrectasGeneros[1] * 100) / totalGeneros[1];
        //float porcentajeTrap = (respuestasCorrectasGeneros[2] * 100) / totalGeneros[2];
        //float porcentajeHeavy = (respuestasCorrectasGeneros[3] * 100) / totalGeneros[3];
        pop.setText("Pop: " + porcentages[0] + "%");
        rock.setText("Rock: " + porcentages[1] + "%");
        trap.setText("Trap: " + porcentages[2] + "%");
        heavy.setText("Heavy: " + porcentages[3] + "%");
    }

    private ArrayList<Personaje> castearPersonaje(Personaje[] personajes) {

        ArrayList<Personaje> listaPersonajes = new ArrayList<>();
        Collections.addAll(listaPersonajes, personajes);
        return listaPersonajes;

    }

    public int randomizer(int size) {
        return (int) (Math.random() * size);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();

    }

}