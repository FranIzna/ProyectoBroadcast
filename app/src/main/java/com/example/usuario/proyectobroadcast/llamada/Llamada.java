package com.example.usuario.proyectobroadcast.llamada;

import android.content.ContentValues;

import com.example.usuario.proyectobroadcast.bdd.Contrato;

import java.util.Date;

/**
 * Created by usuario on 26/01/2016.
 */
public class Llamada {
    private String numero;
    private long fecha;

    public Llamada(String numero, long fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Llamada{" +
                "numero=" + numero +
                ", fecha=" + fecha +
                '}';
    }
    public ContentValues getContentValues(String aux){
        ContentValues cv = new ContentValues();
        switch (aux){
            case "entrante":
//                System.out.println("cv entrante");
//                cv.put(Contrato.TablaEntrante._ID,this.id);
                cv.put(Contrato.TablaEntrante.NUMERO,this.numero);
                cv.put(Contrato.TablaEntrante.FECHA,this.fecha);
                break;
            case "salida":
//                System.out.println("cv salida");
//                cv.put(Contrato.TablaSalida._ID,this.id);
                cv.put(Contrato.TablaSalida.NUMERO,this.numero);
                cv.put(Contrato.TablaSalida.FECHA,this.fecha);
                break;
            case "perdida":
//                System.out.println("cv perdida");
                cv.put(Contrato.TablaPerdida.NUMERO,this.numero);
                cv.put(Contrato.TablaPerdida.FECHA,this.fecha);
                break;
        }
        return cv;
    }
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
