package com.example.usuario.proyectobroadcast.grafico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.usuario.proyectobroadcast.R;

import java.util.GregorianCalendar;

public class PulsaFecha extends AppCompatActivity {
    private DatePicker dp;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        dp=(DatePicker) findViewById(R.id.datePicker);
        i=getIntent();
    }
    public void aceptar(View v){
        int d=dp.getDayOfMonth();
        int m=dp.getMonth();
        int y=dp.getYear();
        GregorianCalendar gc=new GregorianCalendar();
        gc.set(y, m, d, 0, 0, 0);
        long milisecond=gc.getTimeInMillis();
        i.putExtra("aux", milisecond);
        setResult(RESULT_OK,i);

        finish();
    }
}
