package com.example.sprint_3_lexdigest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lexdigest.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlContatos = "CREATE TABLE contatos (codigo INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT)";
        db.execSQL(sqlContatos);

        String sqlUsuarios = "CREATE TABLE usuarios (codigo INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, email TEXT, senha TEXT, cpf TEXT, telefone TEXT)";
        db.execSQL(sqlUsuarios);

        String sqlAdmin = "INSERT INTO usuarios (nome, email, senha) VALUES ('Admin', 'admin@teste.com', 'adm123')";
        db.execSQL(sqlAdmin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contatos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public boolean inserirUsuario(String nome, String email, String senha, String cpf, String telefone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("email", email);
        values.put("senha", senha);
        values.put("cpf", cpf);
        values.put("telefone", telefone);
        long result = db.insert("usuarios", null, values);
        return result != -1;
    }

    public boolean verificarLogin(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email=? AND senha=?", new String[]{email, senha});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}