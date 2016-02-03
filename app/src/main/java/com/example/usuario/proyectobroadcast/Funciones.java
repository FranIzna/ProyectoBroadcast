package com.example.usuario.proyectobroadcast;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.example.usuario.proyectobroadcast.bdd.Contrato;
import com.example.usuario.proyectobroadcast.llamada.Llamada;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 2dam on 03/02/2016.
 */
public class Funciones {

    public static int getPrimerDia(Date d){
        if (d.getDay()==0) {
            return d.getDate()-6;
        }
        int dia=d.getDate() - (d.getDay()-1);
        if(dia<0){
            return 1;
        }else return dia;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int[] devDatosFinal(Context context,long fechaIn,long fechaFin,Uri uri,String nomCampo){
        int d,l,m,x,j,v,s;   d=l=m=x=j=v=s=0;
        Cursor c;
        long fecha;
//        String num;
        int[] a=null;
        String where=nomCampo+" >= '"+fechaIn+"'" +
                " and "+ nomCampo+" <= '"+ fechaFin +"'";
            if(fechaIn==0 && fechaFin==0){
               c =context.getContentResolver().query(uri,null,null,null,null,null);
            }else  c=context.getContentResolver().query(uri,null,where,null,null,null);

        if(c!=null){
            while(c.moveToNext()){
//            num=c.getString(c.getColumnIndex(c.getColumnName(1)));
                fecha=c.getLong(c.getColumnIndex(c.getColumnName(2)));

                GregorianCalendar gc3=new GregorianCalendar();
                gc3.setTimeInMillis(fecha);
                Date d3=gc3.getTime();
                switch (d3.getDay()){
                    case 0: d++; break;
                    case 1: l++; break;
                    case 2: m++; break;
                    case 3: x++; break;
                    case 4: j++; break;
                    case 5: v++; break;
                    case 6: s++; break;
                }
            }
            a= new int[]{l,m,x,j,v,s,d};
        }else System.out.println("NULL");
        return a;
    }
    public static void insertaDatos(Context c){
        GregorianCalendar gc=new GregorianCalendar();
        gc.set(2016, 1, 10, 0, 0);
        Llamada l=new Llamada("1234",gc.getTimeInMillis());
        for(int i=0;i<4;i++)
            c.getContentResolver().insert(Contrato.TablaPerdida.CONTENT_URI,l.getContentValues("perdida"));
        /*****/
        gc.set(2016,1,11,0,0);
        l=new Llamada("4567",gc.getTimeInMillis());
        c.getContentResolver().insert(Contrato.TablaPerdida.CONTENT_URI,l.getContentValues("perdida"));
        /*****/
        gc.set(2016, 1, 8, 0, 0);
        l=new Llamada("7984",gc.getTimeInMillis());
        c.getContentResolver().insert(Contrato.TablaPerdida.CONTENT_URI, l.getContentValues("perdida"));
        /*****/
        gc.set(2016, 1, 15, 0, 0);
        l=new Llamada("7984",gc.getTimeInMillis());
        c.getContentResolver().insert(Contrato.TablaPerdida.CONTENT_URI,l.getContentValues("perdida"));
    }
}
