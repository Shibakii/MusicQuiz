package com.example.musicquiz;

public class Personaje {
    private String NombrePersonaje;
    private String RutaImg;
    private String UltimaModificacion;
    private String genero;

    public Personaje(String nombrePersonaje, String rutaImg, String genero, String ultimaModificacion) {
        NombrePersonaje = nombrePersonaje;
        RutaImg = rutaImg;
        this.genero = genero;
        UltimaModificacion = ultimaModificacion;
    }

    public Personaje(String nombrePersonaje, String rutaImg, String ultimaModificacion) {
        NombrePersonaje = nombrePersonaje;
        RutaImg = rutaImg;
        UltimaModificacion = ultimaModificacion;
    }

    public String getNombrePersonaje() {
        return NombrePersonaje;
    }

    public String getRutaImg() {
        return RutaImg;
    }

    public String getUltimaModificacion() {
        return UltimaModificacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setRutaImg(String rutaImg) {
        RutaImg = rutaImg;
    }
}
