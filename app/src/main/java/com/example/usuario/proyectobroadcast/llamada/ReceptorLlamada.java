package com.example.usuario.proyectobroadcast.llamada;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.example.usuario.proyectobroadcast.bdd.Contrato;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by usuario on 26/01/2016.
 */
public class ReceptorLlamada extends BroadcastReceiver {

    private static int ultEstado = TelephonyManager.CALL_STATE_IDLE;
    private static Date fecha;
    private static boolean entrante;
    private static String num;
    private GregorianCalendar gc;

    @Override
public void onReceive(Context context, Intent intent) {

    if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
        num = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
    } else {
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        int state = 0;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            state = TelephonyManager.CALL_STATE_IDLE;
        }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        onCallStateChanged(context, state, number);
    }
}

    //Derived classes should override these to respond to specific events of interest
//    protected void onIncomingCallStarted(Context ctx, String number, Date start){
//        System.out.println("ENTRANTE EMPIEZA "+number);
//    }

    protected void entranteAcaba(Context ctx, String number, Date start, Date end){
//        System.out.println("ENTRANTE ACABA " + number);

        gc=new GregorianCalendar();
        gc.setTime(start);
        long fecha=gc.getTimeInMillis();

        Llamada aux=new Llamada(number,fecha);
        ctx.getContentResolver().insert(Contrato.TablaEntrante.CONTENT_URI,aux.getContentValues("entrante"));
    }

//    protected void onOutgoingCallStarted(Context ctx, String number, Date start){
//        System.out.println("SALIENTE EMPIEZA "+number);
//    }

    protected void salienteAcaba(Context ctx, String number, Date start, Date end){
//        System.out.println("SALIENTE ACABA "+number);

        gc=new GregorianCalendar();
        gc.setTime(start);
        long fecha=gc.getTimeInMillis();

        Llamada aux=new Llamada(number,fecha);
        System.out.println("ASD: "+aux);
        ctx.getContentResolver().insert(Contrato.TablaSalida.CONTENT_URI, aux.getContentValues("salida"));
    }
    /**BORRAR*/
    public String dateString(Date d){
        return d.getDate()+"-"+(d.getMonth()+1)+"-"+(d.getYear());
    }
    /***/
    protected void perdida(Context ctx, String number, Date start){
//        System.out.println("PERDIDA " + number);

        gc=new GregorianCalendar();
        gc.setTime(start);
        System.out.println(dateString(gc.getTime()));

//        gc.set(start.getYear(), start.getMonth(), start.getDate());
//        System.out.println(dateString(gc.getTime()));
        long fecha=gc.getTimeInMillis();

        Llamada aux=new Llamada(number,fecha);
        Date d=gc.getTime();
//        System.out.println("LLAMADA: "+aux.getNumero()+" - "+dateString(d));
        ctx.getContentResolver().insert(Contrato.TablaPerdida.CONTENT_URI, aux.getContentValues("perdida"));
    }

    public void onCallStateChanged(Context context, int state, String number) {
        if(ultEstado == state)
            return;
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                entrante = true;
                fecha = new Date();
                num = number;
//                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if(ultEstado != TelephonyManager.CALL_STATE_RINGING){
                    entrante = false;
                    fecha = new Date();
//                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if(ultEstado == TelephonyManager.CALL_STATE_RINGING){
                    //Ring but no pickup-  a miss
                    perdida(context, num, fecha);
                }else if(entrante){
                    entranteAcaba(context, num, fecha, new Date());
                }else{
                    salienteAcaba(context, num, fecha, new Date());
                }
                break;
        }
        ultEstado = state;
    }
}

