package com.example.alberwills.bioapp_v1;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alberwills on 27/3/18.
 */

public class Utilidades
{

    public void MostrarMensaje(Context context, String Texto)
    {
        Toast.makeText( context,
                Texto, Toast.LENGTH_SHORT).show();
    }


    public JSONObject GetUsuario(String pUsuario)
    {
        try {
            URL url = new URL("http://72.18.147.160:3000/api/usuario");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                JSONArray UsuariosArray = new JSONArray(stringBuilder.toString());
                JSONObject UsuarioObject;





                for(int i=0; i < UsuariosArray.length() ; i++)
                {
                    UsuarioObject = UsuariosArray.getJSONObject(i);
                    String Usuario =UsuarioObject.getString("NumDoc");

                    if(Usuario.equals(pUsuario.toString()))
                    {
                        return UsuarioObject;
                    }
                }

                return null;


            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


    public JSONArray RegistrosPorID(int ID)
    {
        try {
            URL url = new URL("http://72.18.147.160:3000/api/registros/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                JSONArray RegistrosArray = new JSONArray(stringBuilder.toString());
                JSONObject json_data;


                JSONArray RegistrosDeUsuario = new JSONArray();



                for(int i=0; i < RegistrosArray.length() ; i++)
                {
                    json_data = RegistrosArray.getJSONObject(i);
                    int ID_usuario =json_data.getInt("id");

                    if(ID == ID_usuario)
                    {
                        RegistrosDeUsuario.put(json_data);
                    }

                }

                return RegistrosDeUsuario;

            }
            finally{
                urlConnection.disconnect();

            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }



    public String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = new Date();



        return dateFormat.format(date);
    }



}
