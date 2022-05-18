package com.example.musicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class DificultadActivity extends AppCompatActivity {

    public String idioma = "Esp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultad);

        TextView textdif = findViewById(R.id.lbldif);
        TextView textage = findViewById(R.id.lbledad);
        textdif.setText(getString(R.string.diftext_esp));
        textage.setText(getString(R.string.agetext_esp));

        Button botonComenzar = findViewById(R.id.botonComenzar);
        botonComenzar.setText(getString(R.string.btntext_esp));

        /* ==================================== CARGA SPINNER ====================================*/

        String[] arraydif = getDificultades("Esp");
        Spinner dificultades = findViewById(R.id.spinnerdif);
        DificultadAdapter adapter = new DificultadAdapter(this, arraydif);
        dificultades.setAdapter(adapter);
        /* ==================================== BOTON IDIOMA ==================================== */
        ImageButton español = findViewById(R.id.btnEsp);
        ImageButton english = findViewById(R.id.btnEng);
        ImageButton catala  = findViewById(R.id.btnCat);

        /* ==================================== BOTON COMENZAR ================================== */


        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textoEdad = findViewById(R.id.editedad);
                String auxEdad =textoEdad.getText().toString();

                if (!auxEdad.equals("")){

                    int edad = Integer.parseInt(auxEdad);

                    if(edad > 0 && edad <= 100)
                    {
                        Intent intent = new Intent(DificultadActivity.this, PreguntasActivity.class);
                        intent.putExtra(PreguntasActivity.DIFICULTAD, dificultades.getSelectedItem().toString());
                        intent.putExtra(PreguntasActivity.EDAD, edad);
                        intent.putExtra(PreguntasActivity.IDIOMA, idioma);

                        finish();
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "La edad no es válida!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Debes introducir una edad!", Toast.LENGTH_LONG).show();
                }

            }
        });

        español.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textdif.setText(getString(R.string.diftext_esp));
                textage.setText(getString(R.string.agetext_esp));
                botonComenzar.setText(getString(R.string.btntext_esp));

                DificultadAdapter adapter = new DificultadAdapter(DificultadActivity.this, getDificultades("Esp"));
                idioma = "Esp";
                dificultades.setAdapter(adapter);
            }
        });
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textdif.setText(getString(R.string.diftext_eng));
                textage.setText(getString(R.string.agetext_eng));
                botonComenzar.setText(getString(R.string.btntext_eng));

                DificultadAdapter adapter = new DificultadAdapter(DificultadActivity.this, getDificultades("Eng"));
                idioma = "Eng";
                dificultades.setAdapter(adapter);
            }
        });
        catala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textdif.setText(getString(R.string.diftext_cat));
                textage.setText(getString(R.string.agetext_cat));
                botonComenzar.setText(getString(R.string.btntext_cat));

                DificultadAdapter adapter = new DificultadAdapter(DificultadActivity.this, getDificultades("Cat"));
                idioma = "Cat";
                dificultades.setAdapter(adapter);
            }
        });

        dificultades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = dificultades.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public String [] getDificultades(String lan){
        String[] dificultad = null;

        switch (lan) {
            case "Esp":
                dificultad = new String[]{"Facil", "Normal", "Dificil"};
                break;
            case "Eng":
                dificultad = new String[]{"Easy", "Medium", "Hard"};
                break;
            case "Cat":
                dificultad = new String[]{"Fàcil", "Normal", "Dificil"};
                break;
        }
        return dificultad;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();

    }

}