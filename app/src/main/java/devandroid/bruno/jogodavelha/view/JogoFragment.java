package devandroid.bruno.jogodavelha.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.R;
import devandroid.bruno.jogodavelha.controller.PartidaController;
import devandroid.bruno.jogodavelha.model.Jogador;
import devandroid.bruno.jogodavelha.model.Partida;

public class JogoFragment extends Fragment {

    View view;
    ArrayList<Button> btnBlocos;
    Button btnFinalizar;
    Jogador jogador1;
    Jogador jogador2;
    Jogador jogadorAtual;
    Boolean isGameOver = false;

    TextView txtPlacarJogador1;
    TextView txtPlacarJogador2;

    PartidaController partidaController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_jogo, container, false);


        btnBlocos = new ArrayList<>();
        btnFinalizar = view.findViewById(R.id.btnFinalizar);

        jogador1 = new Jogador("X");
        jogador2 = new Jogador("O");

        jogadorAtual = jogador1;

        txtPlacarJogador1 = view.findViewById(R.id.player1);
        txtPlacarJogador2 = view.findViewById(R.id.player2);

        txtPlacarJogador1.setText("Jogador 1: 0");
        txtPlacarJogador2.setText("Jogador 2: 0");

        partidaController = new PartidaController(view.getContext());

        for (int i = 1; i <= 9; i++) {
            int resId = getResources().getIdentifier("bloco" + i, "id", getActivity().getPackageName());
            Button btnBloco = view.findViewById(resId);
            btnBlocos.add(btnBloco);
        }

        for (Button btnBloco : btnBlocos) {
            btnBloco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isGameOver) {
                        jogada(btnBloco);
                    }
                }
            });
        }
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button btnBloco : btnBlocos) {
                    btnBloco.setText("");
                }

                jogador1.setJogadas(new ArrayList<Integer>());
                jogador2.setJogadas(new ArrayList<Integer>());

                isGameOver = false;
            }
        });


        return view;
    }





    public void jogada(Button blocoSelecionado) {
        if (blocoSelecionado.getText() != "") {
            return;
        }

        marcarBloco(blocoSelecionado);
        adicionarJogada(blocoSelecionado);

        Boolean tempIsGameOver = verificarVitoria();

        if (tempIsGameOver) {
            Toast.makeText(view.getContext(), "O jogador " + jogadorAtual.getNome() + " venceu!", Toast.LENGTH_SHORT).show();
            isGameOver = tempIsGameOver;

            Partida partida = new Partida(jogadorAtual.getNome());
            partidaController.salvar(partida);
        }

        jogadorAtual = jogadorAtual == jogador1 ? jogador2 : jogador1;

    }

    public Boolean verificarVitoria() {
        ArrayList<Integer> jogadas = jogadorAtual.getJogadas();

        int[][] codicaoDeVitoria = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {1, 5, 9},
                {3, 5, 7}
        };

        for (int[] condicao : codicaoDeVitoria) {
            if (jogadas.contains(condicao[0]) && jogadas.contains(condicao[1]) && jogadas.contains(condicao[2])) {

                jogadorAtual.setPontos(jogadorAtual.getPontos() + 1);

                txtPlacarJogador1.setText("Jogador 1: " + jogador1.getPontos());
                txtPlacarJogador2.setText("Jogador 2: " + jogador2.getPontos());

                return true;
            }
        }

        return false;
    }

    public void marcarBloco(Button blocoSelecionado) {
        blocoSelecionado.setText(jogadorAtual.getNome());
    }

    public void adicionarJogada(Button blocoSelecionado) {
        ArrayList<Integer> tempJogadas = jogadorAtual.getJogadas();
        tempJogadas.add(Integer.parseInt(blocoSelecionado.getTag().toString()));

        jogadorAtual.setJogadas(tempJogadas);
    }
}
