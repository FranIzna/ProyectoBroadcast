package com.example.usuario.proyectobroadcast.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by usuario on 12/01/2016.
 */
public class Ayudante extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="llamadas.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+ Contrato.TablaEntrante.TABLA+ " ("+
                Contrato.TablaEntrante._ID+ " integer primary key, "+
                Contrato.TablaEntrante.NUMERO+ " integer, "+
                Contrato.TablaEntrante.FECHA+ " int)";
        db.execSQL(sql);

        sql="create table "+ Contrato.TablaPerdida.TABLA+ " ("+
                Contrato.TablaPerdida._ID+ " integer primary key, "+
                Contrato.TablaPerdida.NUMERO+ " integer, "+
                Contrato.TablaPerdida.FECHA+ " int)";
        db.execSQL(sql);

        sql="create table "+ Contrato.TablaSalida.TABLA+ " ("+
                Contrato.TablaSalida._ID+ " integer primary key, "+
                Contrato.TablaSalida.NUMERO+ " integer, "+
                Contrato.TablaSalida.FECHA+ " int)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "
                + Contrato.TablaEntrante.TABLA;
        db.execSQL(sql);
        sql="drop table if exists "
                + Contrato.TablaPerdida.TABLA;
        db.execSQL(sql);

        sql="drop table if exists "
                + Contrato.TablaSalida.TABLA;
        db.execSQL(sql);

        onCreate(db);
    }
}
