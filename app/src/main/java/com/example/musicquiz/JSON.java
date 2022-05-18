package com.example.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class JSON extends AppCompatActivity {
    private String directorio;

    public JSON(String directorio) {
        this.directorio = directorio + File.separator;
    }

    public String getDirectorio() {
        return directorio;
    }

    public Pregunta[] getPreguntasCAT() {
        String rutaJSON = this.directorio + "PreguntasCAT.json";
        Pregunta[] preguntas = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(rutaJSON);
            br = new BufferedReader(fr);

            Gson gson = new Gson();
            preguntas = gson.fromJson(br, Pregunta[].class);

            fr.close();
            br.close();
        } catch (IOException ex) {
            Toast.makeText(this, "ERROR when loading the questions", Toast.LENGTH_LONG);
            preguntas = null;
        }
        return preguntas;
    }

    public Pregunta[] getPreguntasESP() {
        String rutaJSON = this.directorio + "PreguntasESP.json";
        Pregunta[] preguntas = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(rutaJSON);
            br = new BufferedReader(fr);

            Gson gson = new Gson();
            preguntas = gson.fromJson(br, Pregunta[].class);

            fr.close();
            br.close();
        } catch (IOException ex) {
            Toast.makeText(this, "ERROR when loading the questions", Toast.LENGTH_LONG);
            preguntas = null;
        }
        return preguntas;
    }

    public Pregunta[] getPreguntasING() {
        String rutaJSON = this.directorio + "PreguntasING.json";
        Pregunta[] preguntas = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(rutaJSON);
            br = new BufferedReader(fr);

            Gson gson = new Gson();
            preguntas = gson.fromJson(br, Pregunta[].class);

            fr.close();
            br.close();
        } catch (IOException ex) {
            Toast.makeText(this, "ERROR when loading the questions", Toast.LENGTH_LONG);
            preguntas = null;
        }
        return preguntas;
    }

    public Personaje[] getPersonajes() {
        String rutaJSON = this.directorio + "Personajes.json";
        Personaje[] personajes = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(rutaJSON);
            br = new BufferedReader(fr);

            Gson gson = new Gson();
            personajes = gson.fromJson(br, Personaje[].class);

            fr.close();
            br.close();
        } catch (IOException ex) {
            personajes = null;
        }
        return personajes;
    }

    private String arreglarRuta(String ruta) {
        return ruta.replace("\\", "/");
    }
    public Personaje[] adaptarRutaImg(Personaje[] listaPersonajes) {

        Personaje[] personajes = new Personaje[listaPersonajes.length];

        int i = 0;
        for (Personaje person : listaPersonajes) {
            String rutaJSON = person.getRutaImg();
            int llargada = rutaJSON.length();
            String rutaAdaptada = getDirectorio() + rutaJSON.substring(11, llargada).replace("\\", "/");
            person.setRutaImg(rutaAdaptada);
            personajes[i] = person;
            i++;
        }
        return personajes;

        /*
        Personaje[] personajes = new Personaje[listaPersonajes.length];

        int i = 0;
        for (Personaje person: listaPersonajes){
            String rutaJSON = person.getRutaImg();
            String rutaAdaptada = json.getDirectorio() + rutaJSON.substring(11, rutaJSON.length());

            personajes[i] = new Personaje(person.getNombrePersonaje(), rutaAdaptada, person.getUltimaModificacion() );
            i++;
        }
        return personajes;
        */
    }

}
