package devandroid.bruno.jogodavelha.controller;

import android.content.ContentValues;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.infra.database.Database;
import devandroid.bruno.jogodavelha.model.Partida;
import devandroid.bruno.jogodavelha.view.MainActivity;

public class PartidaController extends Database {

    public PartidaController(MainActivity mainActivity) {
        super(mainActivity);
    }

    public Partida salvar(Partida partida) {
        ContentValues dtoPartida = new ContentValues();

        dtoPartida.put("vencedor", partida.getVencedor());

        db.insert("Partidas", null, dtoPartida);

        return partida;
    }

    public ArrayList<Partida> listar() {
        ArrayList<Partida> listaPartidas = new ArrayList<Partida>();

        cursor = db.rawQuery("select * from Partidas", null);

        if (cursor.moveToFirst()) {
            do {
                Partida partida = new Partida(cursor.getString(1));

                partida.setId(cursor.getInt(0));

                listaPartidas.add(partida);
            } while (cursor.moveToNext());
        }

        return listaPartidas;
    }
}
