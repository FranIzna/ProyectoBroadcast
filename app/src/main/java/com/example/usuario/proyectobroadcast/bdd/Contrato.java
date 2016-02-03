package com.example.usuario.proyectobroadcast.bdd;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by usuario on 12/01/2016.
 */
public class Contrato {
    private Contrato(){}

    public static abstract class TablaEntrante implements BaseColumns {
        public static final String TABLA = "entrante";
        public static final String NUMERO = "numeroEntrante";
        public static final String FECHA = "fechaEntrante";

        public final static String AUTHORITY = "com.example.usuario.proyectobroadcast";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
    public static abstract class TablaPerdida implements BaseColumns {
        public static final String TABLA = "perdida";
        public static final String NUMERO = "numeroPerdida";
        public static final String FECHA = "fechaPerdida";

        public final static String AUTHORITY = "com.example.usuario.proyectobroadcast";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
    public static abstract class TablaSalida implements BaseColumns {
        public static final String TABLA = "salida";
        public static final String NUMERO = "numeroSalida";
        public static final String FECHA = "fechaSalida";

        public final static String AUTHORITY = "com.example.usuario.proyectobroadcast";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }

}
