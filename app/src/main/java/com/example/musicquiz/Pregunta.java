package com.example.musicquiz;

import java.io.UnsupportedEncodingException;

public class Pregunta {
    private final String pregunta;
    private final String[] listaRespuestas;
    private final int respuestaCorrecta;
    private final String dataModificacio;
    private final String dificultat;
    private final String genero;

    public Pregunta(String pregunta, String[] listaRespuestas, int respuestaCorrecta,
                    String dataModificacio, String dificultat, String genero) {
        this.pregunta = pregunta;
        this.listaRespuestas = listaRespuestas;
        this.respuestaCorrecta = respuestaCorrecta;
        this.dataModificacio = dataModificacio;
        this.dificultat = dificultat;
        this.genero = genero;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String[] getListaRespuestas() {
        return listaRespuestas;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getDataModificacio() {
        return dataModificacio;
    }

    public String getDificultat() {
        return dificultat;
    }

    public String getGenero() {
        return genero;
    }
}
