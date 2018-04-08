package com.example.alberwills.bioapp_v1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    ImageButton EntradaButton;
    ImageButton SalidaButton;
    ImageButton EntradaAlmuerzoButton;
    ImageButton SalidaAlmuerzoButton;
    Button RegistrosBoton;
    Button CrearUsuario;
    Utilidades util = new Utilidades();
    UtilidadesGPS utilidadesGPS = new UtilidadesGPS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        EntradaButton = findViewById(R.id.EntradaButton);
        EntradaButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                MostrarLogin(1);
            }
        });



        SalidaButton = findViewById(R.id.SalidaButton);
        SalidaButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                MostrarLogin(2);
            }
        });


        EntradaAlmuerzoButton = findViewById(R.id.EntradaAlmuerzoButton);
        EntradaAlmuerzoButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                MostrarLogin(3);
            }
        });


        SalidaAlmuerzoButton = findViewById(R.id.SalidaAlmuerzoButton);
        SalidaAlmuerzoButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                MostrarLogin(4);
            }
        });


        RegistrosBoton = findViewById(R.id.btn_registros);
        RegistrosBoton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registros.class));
            }
        });

        CrearUsuario = findViewById(R.id.CrearUsuario);
        CrearUsuario.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, register_user.class));
            }
        });


    }


    public void MostrarLogin(int Funcion)
    {
        DialogFragment login = new login_modal();
        login.show(getFragmentManager(), "Login");
        Bundle bundle = new Bundle();
        bundle.putInt("Funcion", Funcion);
        login.setArguments(bundle);
    }




}
