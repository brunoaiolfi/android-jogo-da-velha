package devandroid.bruno.jogodavelha.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.R;
import devandroid.bruno.jogodavelha.controller.PartidaController;
import devandroid.bruno.jogodavelha.model.Partida;

public class HistoricoFragment extends Fragment {
    View view;
    PartidaController partidaController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_historico, container, false);

        partidaController = new PartidaController(view.getContext());

        listarPartidas();
        return view;
    }

    public void listarPartidas() {
        ArrayList<Partida> listaPartidas = partidaController.listar();
        TableLayout tablePartidas = view.findViewById(R.id.tablePartidas);
        tablePartidas.removeAllViews();
        adicionarCabecalho();

        for (Partida partida : listaPartidas) {
            adicionarPartidaNaLista(partida);
        }

    }

    public void adicionarCabecalho() {
        TableLayout tablePartidas = view.findViewById(R.id.tablePartidas);

        TableRow row = new TableRow(view.getContext());

        TextView textViewId = new TextView(view.getContext());
        textViewId.setText("Partida");
        row.addView(textViewId);

        TextView textViewVencedor = new TextView(view.getContext());
        textViewVencedor.setText("Vencedor");
        row.addView(textViewVencedor);

        tablePartidas.addView(row);
    }

    public void adicionarPartidaNaLista(Partida partida) {
        TableLayout tablePartidas = view.findViewById(R.id.tablePartidas);

        TableRow row = new TableRow(view.getContext());

        TextView textViewId = new TextView(view.getContext());
        textViewId.setText(String.valueOf(partida.getId()));
        row.addView(textViewId);

        TextView textViewVencedor = new TextView(view.getContext());
        textViewVencedor.setText(partida.getVencedor());
        row.addView(textViewVencedor);

        tablePartidas.addView(row);
    }
}
