package devandroid.bruno.jogodavelha.infra.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "jogodavelha.db";
    public static final int DB_VERSION = 1;

    public SQLiteDatabase db;
    public Cursor cursor;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTabelaPartidas = "create table Partidas (" +
                "id integer primary key autoincrement, " +
                "vencedor text)";

        db.execSQL(sqlTabelaPartidas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
