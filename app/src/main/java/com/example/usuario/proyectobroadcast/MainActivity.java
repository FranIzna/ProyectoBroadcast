package com.example.usuario.proyectobroadcast;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.proyectobroadcast.bdd.Contrato;
import com.example.usuario.proyectobroadcast.grafico.Grafico;
import com.example.usuario.proyectobroadcast.grafico.PulsaFecha;

import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity{
//    private Cursor cur;
    private Spinner sp;
    private Context c;
    private static final int SEMANA=0, FECHINI=1,FECHAFIN=2;
    private GregorianCalendar semana,fechaIn,fechaFin;
    private TextView tvIni,tvFin;
    private Uri uri;
    private String nomCampo="";
    private GridLayout rango;
    private GridLayout sem;
    //    Contrato.TablaPerdida.CONTENT_URI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rango=(GridLayout) findViewById(R.id.rango);
        sem=(GridLayout) findViewById(R.id.semana);
        sem.setVisibility(View.GONE);
        rango.setVisibility(View.VISIBLE);
        inicio();
    }

    public void box(){
        Spinner spinner= (Spinner) findViewById(R.id.spinner);
        String[] queryCols=new String[]{"_id", "nombre"};
        String[] adapterCols=new String[]{"nombre"};
        int[] adapterRowViews=new int[]{android.R.id.text1};

        String[] valores = {getString(R.string.perdidas),
                            getString(R.string.entrantes),
                            getString(R.string.salientes)};
        spinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        cur = getContentResolver().query(Contrato.TablaPerdida.CONTENT_URI, null, null, null, null);
                        uri = Contrato.TablaPerdida.CONTENT_URI;
                        nomCampo = "fechaPerdida";
                        break;
                    case 1:
//                        cur = getContentResolver().query(Contrato.TablaEntrante.CONTENT_URI, null, null, null, null);
                        uri = Contrato.TablaEntrante.CONTENT_URI;
                        nomCampo = "fechaEntrante";
                        break;
                    case 2:
//                        cur = getContentResolver().query(Contrato.TablaSalida.CONTENT_URI, null, null, null, null);
                        uri = Contrato.TablaSalida.CONTENT_URI;
                        nomCampo = "fechaSalida";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                uri = Contrato.TablaPerdida.CONTENT_URI;
                nomCampo = "fechaPerdida";
//                cur = getContentResolver().query(Contrato.TablaPerdida.CONTENT_URI, null, null, null, null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
   /*     if (id == R.id.perdidas) {
            cur=getContentResolver().query(Contrato.TablaPerdida.CONTENT_URI,null,null,null,null);
            return true;
        }
        if (id == R.id.entrantes) {
            cur=getContentResolver().query(Contrato.TablaEntrante.CONTENT_URI,null,null,null,null);
            return true;
        }
        if (id == R.id.salientes) {
            cur=getContentResolver().query(Contrato.TablaSalida.CONTENT_URI,null,null,null,null);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
/******************/
private Switch sw;
    public void inicio(){
        sw=(Switch) findViewById(R.id.switch1);
        c=getApplicationContext();
        tvIni=(TextView) findViewById(R.id.inicial);
        tvFin=(TextView) findViewById(R.id.fina);
        box();

        fechaIn=new GregorianCalendar();
        fechaFin=new GregorianCalendar();
        semana=new GregorianCalendar();
//        cur=getContentResolver().query(Contrato.TablaPerdida.CONTENT_URI,null,null,null,null);
    }
    public void switch1(View v){
        if(sw.isChecked()){
            rango.setVisibility(View.GONE);
            sem.setVisibility(View.VISIBLE);
        }else{
            sem.setVisibility(View.GONE);
            rango.setVisibility(View.VISIBLE);
        }
    }
    public void semana(View v){
        Intent i=new Intent(this,PulsaFecha.class);
        startActivityForResult(i, SEMANA);
    }
    public void in(View v){
        Intent i=new Intent(this,PulsaFecha.class);
        startActivityForResult(i, FECHINI);
    }
    public void fin(View v){
        Intent i=new Intent(this,PulsaFecha.class);
        startActivityForResult(i, FECHAFIN);
    }

    public void todo(View v){
        int[]a=Funciones.devDatosFinal(this,0,0,uri,nomCampo);

        Intent intent=new Intent(this,Grafico.class);
        intent.putExtra("aux",a);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case SEMANA:
                    semana.setTimeInMillis(data.getExtras().getLong("aux"));
//                    d=semana.getTime();
//                    tvIni.setText(dateString(d));
                    /***/
                        Date d=semana.getTime();
                        int primerDia=Funciones.getPrimerDia(d);
                        int ultDia=primerDia+6;
            /**/
                        d.setDate(primerDia);
                        fechaIn.setTime(d);
                        d.setDate(ultDia);
                        fechaFin.setTime(d);
            /**/
                        long p=(fechaIn.getTimeInMillis());
                        long u=(fechaFin.getTimeInMillis());
                        /***/
                        int[] a = Funciones.devDatosFinal(this,p,u,uri,nomCampo);
                        Intent intent=new Intent(this,Grafico.class);
                        intent.putExtra("aux",a);
                        startActivity(intent);
                    break;
                case FECHINI:
                    fechaIn.setTimeInMillis(data.getExtras().getLong("aux"));
                        d=fechaIn.getTime();
                        tvIni.setText(dateString(d));
                    break;
                case FECHAFIN:
                    fechaFin.setTimeInMillis(data.getExtras().getLong("aux"));

                    if(fechaIn.getTimeInMillis() > fechaFin.getTimeInMillis()){
                        Toast.makeText(this,
                                this.getString(R.string.fechaInMayor),
                                Toast.LENGTH_SHORT).show();
                        tvFin.setText("");
                    }else{
                        d=fechaFin.getTime();

                        tvFin.setText(dateString(d));
                        a = Funciones.devDatosFinal(this,fechaIn.getTimeInMillis(), fechaFin.getTimeInMillis(),uri,nomCampo);

                        intent=new Intent(this,Grafico.class);
                        intent.putExtra("aux", a);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }


    public String dateString(Date d){
        return d.getDate()+"-"+(d.getMonth()+1)+"-"+(d.getYear()+(1900));
    }
}
