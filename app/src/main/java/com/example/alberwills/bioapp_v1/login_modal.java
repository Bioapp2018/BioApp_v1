package com.example.alberwills.bioapp_v1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class login_modal extends DialogFragment {


    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 99;
    public int Funcion = 0;
    public String FuncionDescripcion;
    public EditText Usuario;
    public EditText Contrasenia;
    Utilidades util = new Utilidades();
    Location location;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Context context = this.getActivity().getApplicationContext();




        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        criteria.setAccuracy( Criteria.ACCURACY_COARSE );
        String provider = locationManager.getBestProvider( criteria, true );

        if ( provider == null ) {
            //Log.e( TAG, "No location provider found!" );
            //return;

            Toast.makeText(getActivity(),
                    "Esta aplicación necesita autorización a su ubicación para poder marcar." , Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        else
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return "";
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            }
            location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));


        }




        Funcion = getArguments().getInt("Funcion");

        switch (Funcion)
        {
            case 1:
                  FuncionDescripcion = "entrada";
                  break;
            case 2:
                FuncionDescripcion = "salida";
                break;
            case 3:
                FuncionDescripcion = "entrada de almuerzo";
                break;
            case 4:
                FuncionDescripcion = "salida de almuerzo";
                break;

        }




        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.activity_login_modal, null))
                .setTitle("Ingrese sus datos para marcar " + FuncionDescripcion)
                .setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        Dialog f = (Dialog) dialogInterface;
                        EditText Usuario = f.findViewById(R.id.UsuarioLogin);
                        EditText Contrasenia = f.findViewById(R.id.PasswordLogin);
                        //Toast.makeText(getActivity(),
                        //e.getMessage(), Toast.LENGTH_LONG).show();






                        if(!Usuario.getText().toString().isEmpty() && !Contrasenia.getText().toString().isEmpty())
                        {

                            JSONObject UsuarioJSON = util.GetUsuario( Usuario.getText().toString().trim());

                            String ContraseniaCorrecta = "";
                            int IdUsuario = -1;


                            if(UsuarioJSON == null)
                            {
                                Toast.makeText(getActivity(),
                                        "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                                return;
                            }


                            try
                            {
                                 ContraseniaCorrecta = UsuarioJSON.getString("KeyCode");
                                    IdUsuario = UsuarioJSON.getInt("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            if(Contrasenia.getText().toString().equals(ContraseniaCorrecta))
                            {
                                try {


                                    double latitude = location.getLatitude();
                                    double longitud = location.getLongitude();




                                    //Toast.makeText(getActivity(),
                                            //latitude + " - " + longitud , Toast.LENGTH_LONG).show();


                                    URL url = new URL("http://72.18.147.160:3000/api/registros");
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

                                    JSONObject parent=new JSONObject();
                                    parent.put("id", IdUsuario);
                                    parent.put("FechaMarca", util.getDateTime());//Calendar.getInstance().getTime());
                                    parent.put("TipoMarca", FuncionDescripcion);
                                    parent.put("Latitud", latitude);
                                    parent.put("Longitud", longitud);
                                    parent.put("NomDispositivo", "NA");

                                    Toast.makeText(getActivity(),
                                       parent.toString(), Toast.LENGTH_LONG).show();


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

                                            Toast.makeText(getActivity(),
                                                    "Marcaje " + FuncionDescripcion + " realizado correctamente", Toast.LENGTH_LONG).show();


                                        }
                                        else{
                                            Toast.makeText(getActivity(),
                                                    urlConnection.getResponseMessage() + " - ErrorCode: "+ urlConnection.getResponseCode(), Toast.LENGTH_LONG).show();
                                        }



                                    }
                                    finally{
                                        urlConnection.disconnect();
                                    }
                                }
                                catch(Exception e) {
                                    Log.e("ERROR", e.getMessage(), e);
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(),
                                        "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

                            }



                        }
                        else
                        {
                            Toast.makeText(getActivity(),
                                    "Ingrese los datos solicitados", Toast.LENGTH_LONG).show();
                        }

                        //Toast.makeText(getActivity(),
                                //Usuario.getText().toString(), Toast.LENGTH_LONG).show();



                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


        return builder.create();
    }


}
