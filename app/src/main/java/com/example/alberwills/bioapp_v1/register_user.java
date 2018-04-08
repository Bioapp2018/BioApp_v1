package com.example.alberwills.bioapp_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class register_user extends AppCompatActivity {

    EditText Nombre;
    EditText Usuario;
    EditText Contrasenia;
    EditText ConfirmarContrasenia;
    Button RegistrarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Nombre = findViewById(R.id.RegistrarNombre);
        Usuario = findViewById(R.id.RegistrarUsuario);
        Contrasenia = findViewById(R.id.RegistrarPass);
        ConfirmarContrasenia = findViewById(R.id.RegistrarConfPass);



        RegistrarButton = findViewById(R.id.buttonRegistrar);



        RegistrarButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if( !Nombre.getText().toString().isEmpty()
                        && !Usuario.getText().toString().isEmpty()
                        && !Contrasenia.getText().toString().isEmpty()
                        && !ConfirmarContrasenia.getText().toString().isEmpty())
                {
                    if(Contrasenia.getText().toString().equals(ConfirmarContrasenia.getText().toString()))
                    {
                        try {
                            URL url = new URL("http://72.18.147.160:3000/api/usuario");
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            OutputStreamWriter printout;
                            DataInputStream input;

                            urlConnection.setDoOutput(true);
                            urlConnection.setDoInput(true);
                            urlConnection.setUseCaches (false);
                            urlConnection.setRequestProperty("Content-Type", "application/json");
                            urlConnection.setRequestProperty("Accept", "application/json");
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");

                            /*JSONObject cred = new JSONObject();
                            JSONObject auth=new JSONObject();*/
                            JSONObject parent=new JSONObject();
                            /*cred.put("username","adm");
                            cred.put("password", "pwd");
                            auth.put("tenantName", "adm");
                            auth.put("passwordCredentials", cred);*/
                            parent.put("Nombre", Nombre.getText().toString());
                            parent.put("NumDoc", Usuario.getText().toString());
                            parent.put("Sexo", "M");
                            parent.put("Foraneo", "0");
                            parent.put("KeyCode", Contrasenia.getText().toString());
                            parent.put("Status", "0");


                            printout = new OutputStreamWriter(urlConnection.getOutputStream ());
                            printout.write(parent.toString());
                            printout.flush ();
                            printout.close ();

                            //OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream());
                            //wr.write(parent.toString());

                            //Toast.makeText(register_user.this,
                              //      parent.toString(), Toast.LENGTH_LONG).show();

                            try
                            {
                                StringBuilder stringBuilder = new StringBuilder();
                                int HttpResult = urlConnection.getResponseCode();
                                if (HttpResult == HttpURLConnection.HTTP_OK)
                                {
                                    BufferedReader br = new BufferedReader(
                                            new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                                    String line = null;
                                    while ((line = br.readLine()) != null)
                                    {
                                        stringBuilder.append(line + "\n");
                                    }
                                    br.close();
                                    //System.out.println("" + stringBuilder.toString());

                                    Toast.makeText(register_user.this,
                                            "Usuario Creado exitosamente", Toast.LENGTH_LONG).show();

                                    Usuario.setText("");
                                    Nombre.setText("");
                                    Contrasenia.setText("");
                                    ConfirmarContrasenia.setText("");
                                }
                                else{
                                    Toast.makeText(register_user.this,
                                            urlConnection.getResponseMessage() + " - ErrorCode: "+ urlConnection.getResponseCode(), Toast.LENGTH_LONG).show();
                                }



                            }
                            finally{
                                urlConnection.disconnect();
                            }
                        }
                        catch(Exception e) {
                            Log.e("ERROR", e.getMessage(), e);
                            Toast.makeText(register_user.this,
                                    e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                     }
                    else
                    {
                        Toast.makeText(register_user.this,
                                "Contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(register_user.this,
                            "Complete los campos solicitados", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}
