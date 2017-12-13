package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.agenda.model.Contato;


public class ContatoDAO {
    private static final String[] PROJECTION = new String[]{SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO, SQLiteHelper.KEY_FONE_2};

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public List<Contato> buscaTodosContatos() {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, PROJECTION, null, null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(new Boolean(cursor.getInt(4) == 0 ? Boolean.FALSE : Boolean.TRUE));
            contato.setFone2(cursor.getString(5));
            contatos.add(contato);


        }
        cursor.close();


        database.close();
        return contatos;
    }

    public List<Contato> buscaTodosContatosFavoritos() {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, PROJECTION, SQLiteHelper.KEY_FAVORITO + " = 1", null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(new Boolean(cursor.getInt(4) == 0 ? Boolean.FALSE : Boolean.TRUE));
            contato.setFone2(cursor.getString(5));
            contatos.add(contato);


        }
        cursor.close();


        database.close();
        return contatos;

    }

    public List<Contato> buscaContato(String nome) {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String where = SQLiteHelper.KEY_NAME + " like ?";
        String[] argWhere = new String[]{nome + "%"};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, PROJECTION, where, argWhere,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(new Boolean(cursor.getInt(4) == 0 ? Boolean.FALSE : Boolean.TRUE));
            contato.setFone2(cursor.getString(5));
            contatos.add(contato);


        }
        cursor.close();

        database.close();
        return contatos;
    }

    public void salvaContato(Contato c) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_FONE_2, c.getFone2());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FAVORITO, c.isFavorito());

        if (c.getId() > 0)
            database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                    + c.getId(), null);
        else
            database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();
    }


    public void apagaContato(Contato c) {
        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        database.close();
    }
}
