package com.example.alberwills.bioapp_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Registros extends AppCompatActivity {


    Button DatosBoton;
    Utilidades util = new Utilidades();
    int ID = 0;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        DatosBoton = findViewById(R.id.button2);
        final EditText IdUsuario = findViewById(R.id.IdEmpleado);
        lv = findViewById(R.id.RegistrosList);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id)
            {

                Toast.makeText(Registros.this, id + "", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(MainActivity.this, SendMessage.class);
                //String message = "abc";
                //intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(intent);
            }
        });

        //Toast.makeText(Registros.this,
          //      UsuarioJSON.toString(), Toast.LENGTH_LONG).show();


        DatosBoton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                JSONObject UsuarioJSON = util.GetUsuario( IdUsuario.getText().toString());


                if(UsuarioJSON != null )
                {

                    try
                    {
                        ID = UsuarioJSON.getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray Registros = util.RegistrosPorID(ID);



                    ArrayList<Category> category = new ArrayList<Category>();
                    JSONObject json_data;

                    if(Registros.length() > 0)
                    {
                        for(int i=0; i < Registros.length() ; i++)
                        {
                            try {
                                json_data = Registros.getJSONObject(i);
                                String Fecha =json_data.getString("FechaMarca");
                                String Dispositivo=json_data.getString("NomDispositivo");
                                String TipoMarca=json_data.getString("TipoMarca");


                                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                Date date = new Date();

                                try {
                                    date = format.parse(Fecha);
                                    System.out.println("Date ->" + date);
                                } catch (ParseException e) {
                                    e.printStackTrace();

                                }


                                category.add(new Category(Dispositivo, "Marca de " + TipoMarca, date.toString()));
                                Log.d(Dispositivo,"Output");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    else
                    {
                        Toast.makeText(Registros.this,
                                "No se encontrarón registros para este usuario", Toast.LENGTH_LONG).show();
                    }




                    AdapterItem adapter = new AdapterItem(Registros.this, category);

                    lv.setAdapter(adapter);




                }

                else
                {
                    Toast.makeText(Registros.this,
                            "Usuario no existe", Toast.LENGTH_LONG).show();


                    lv.setAdapter(null);

                }



            }
        });

    }
}
