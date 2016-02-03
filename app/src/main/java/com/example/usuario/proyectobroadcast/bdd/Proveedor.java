package com.example.usuario.proyectobroadcast.bdd;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


/**
 * Created by usuario on 12/01/2016.
 */
public class Proveedor extends ContentProvider {
    public static final UriMatcher convierteUri2Int;
    public static final int PERDIDA=0, ENTRANTE = 1,  SALIDA = 2;
    private static SQLiteDatabase bd;
    static {
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        /********- ENTRANTE -*/
        convierteUri2Int.addURI(Contrato.TablaEntrante.AUTHORITY,  Contrato.TablaEntrante.TABLA, ENTRANTE);

        convierteUri2Int.addURI(Contrato.TablaPerdida.AUTHORITY,  Contrato.TablaPerdida.TABLA, PERDIDA);
        /********- SALIDA -*/
        convierteUri2Int.addURI(Contrato.TablaSalida.AUTHORITY, Contrato.TablaSalida.TABLA, SALIDA);
        /*********/
    }
    private Ayudante abd;
    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        bd=abd.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {//Devuelve el tipo mime que corresponde a la uri con que se ha llamado
        switch (convierteUri2Int.match(uri)) {

            case PERDIDA:
                return Contrato.TablaPerdida.MJLTIPLE_MIME;

            case ENTRANTE:
                return Contrato.TablaEntrante.MJLTIPLE_MIME;

            case SALIDA:
                return Contrato.TablaSalida.MJLTIPLE_MIME;
            /*********/
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        String tipo=getType(uri);
//        System.out.println("TIPO: "+tipo);
        long rowId=0;
        //Si el ContentValues es nulo, crea un ContentValues
        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException(" null ");
        }
        int conver=convierteUri2Int.match(uri);
        if (conver!= PERDIDA && conver != ENTRANTE && conver != SALIDA) {
            throw new IllegalArgumentException("URI desconocida : " + uri);//SI no es correcta la Uri
        }
        // Insercion de nueva fila
        SQLiteDatabase db = abd.getWritableDatabase();//Conectamos a la base de datos en modo escritura
        /******/
        if(uri.toString().contains("perdida")){
            rowId = db.insert(Contrato.TablaPerdida.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaPerdida.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        } else if(uri.toString().contains("entrante")){
            rowId = db.insert(Contrato.TablaEntrante.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaEntrante.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }else if(uri.toString().contains("salida")){
            rowId = db.insert(Contrato.TablaSalida.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaSalida.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }
        throw new SQLException("Error al insertar fila en : " + uri);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();// Vuelve a abrir la base de datos para conectar con ella en modo escritura
        int match = convierteUri2Int.match(uri);//Obtengo la uri
        int affected;
        long idActividad=0;
        switch (match) {
            case ENTRANTE: //Muchas canciones
                affected = db.delete(Contrato.TablaEntrante.TABLA, selection, selectionArgs);
                break;

            case SALIDA:
                affected = db.delete(Contrato.TablaSalida.TABLA, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        // Notificar cambio asociado a la urigetContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;//Devuelve el numero de filas borradas
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri2Int.match(uri)) {
            case ENTRANTE:
                affected = db.update(Contrato.TablaEntrante.TABLA, values, selection, selectionArgs);
                break;
            case SALIDA:
                affected = db.update(Contrato.TablaSalida.TABLA, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = abd.getReadableDatabase();

        int match = convierteUri2Int.match(uri);
        Cursor c;

        switch (match) {
            case PERDIDA:
                c = db.query(Contrato.TablaPerdida.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ENTRANTE:
                c = db.query(Contrato.TablaEntrante.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SALIDA:
                c = db.query(Contrato.TablaSalida.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaEntrante.CONTENT_URI);
        return c;
    }

}