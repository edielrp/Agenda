package br.edu.ifspsaocarlos.agenda.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteHelper";

    private static final String DATABASE_NAME = "agenda.db";
    static final String DATABASE_TABLE = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_FONE_2 = "fone2";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_DATA_ANIVERSARIO = "dataAniversario";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, " +
            KEY_FONE_2 + " TEXT, " +
            KEY_EMAIL + " TEXT, " +
            KEY_DATA_ANIVERSARIO + " TEXT, " +
            KEY_FAVORITO + ");";
    private static final String DATABASE_UPGRADE_TO_VERSION_2 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + KEY_FAVORITO + ";";
    private static final String DATABASE_UPGRADE_TO_VERSION_3 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + KEY_FONE_2 + " TEXT;";
    private static final String DATABASE_UPGRADE_TO_VERSION_4 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + KEY_DATA_ANIVERSARIO + " TEXT;";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading DB: " + DATABASE_NAME + "...");
        Log.i(TAG, "Version: " + oldVersion + " To: " + newVersion);

        switch (oldVersion) {
            case 1:
                database.execSQL(DATABASE_UPGRADE_TO_VERSION_2);
            case 2:
                database.execSQL(DATABASE_UPGRADE_TO_VERSION_3);
            case 3:
                database.execSQL(DATABASE_UPGRADE_TO_VERSION_4);
                break;
            default:
                Log.i(TAG, "Versão não existente: " + oldVersion);
                break;

        }
    }
}

