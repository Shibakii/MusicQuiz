package com.example.musicquiz;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DificultadAdapter extends ArrayAdapter
{
    private String [] dificultades;

    public DificultadAdapter(Context context, String [] dificultades)
    {
        super(context, android.R.layout.simple_spinner_dropdown_item, dificultades);
        this.dificultades = dificultades;
    }


}
