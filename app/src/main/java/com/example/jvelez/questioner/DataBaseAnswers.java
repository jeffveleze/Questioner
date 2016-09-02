package com.example.jvelez.questioner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jvelez on 31/08/16.
 */
public class DataBaseAnswers extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "answers.db";

    public DataBaseAnswers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static class DatosTabla implements BaseColumns {
        public static final String TABLE_NAME = "respuestas";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_RESPUESTA = "respuesta";
        public static final String COLUMNA_RELACION = "relacion";
        public static final String COLUMNA_FECHA = "fecha";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        private static final String CREAR_TABLA =
                "CREATE TABLE " + DatosTabla.TABLE_NAME + " (" +
                        DatosTabla.COLUMNA_ID + " AUTO_INCREMENT INTEGER PRIMARY KEY," +
                        DatosTabla.COLUMNA_NOMBRE + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_RESPUESTA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_RELACION + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_FECHA + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatosTabla.CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DatosTabla.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}


