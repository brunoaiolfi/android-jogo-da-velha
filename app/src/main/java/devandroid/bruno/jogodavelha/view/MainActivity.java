package devandroid.bruno.jogodavelha.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.R;
import devandroid.bruno.jogodavelha.model.Jogador;

public class MainActivity extends AppCompatActivity {

    ArrayList<Button> btnBlocos;
    Button btnFinalizar;
    Jogador jogador1;
    Jogador jogador2;
    Jogador jogadorAtual;
    Boolean isGameOver = false;

    TextView txtPlacarJogador1;
    TextView txtPlacarJogador2;

    public void jogada(Button blocoSelecionado) {
        if (blocoSelecionado.getText() != "") {
            return;
        }

        marcarBloco(blocoSelecionado);
        adicionarJogada(blocoSelecionado);

        Boolean tempIsGameOver = verificarVitoria();

        if (tempIsGameOver) {
            Toast.makeText(this, "O jogador " + jogadorAtual.getNome() + " venceu!", Toast.LENGTH_SHORT).show();
        }

        jogadorAtual = jogadorAtual == jogador1 ? jogador2 : jogador1;
        isGameOver = tempIsGameOver;

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

                int tempPontos = jogadorAtual.getPontos();
                jogadorAtual.setPontos(tempPontos + 1);

                if (jogadorAtual == jogador1) {
                    txtPlacarJogador1.setText("Jogador 1: " + jogadorAtual.getPontos());
                } else {
                    txtPlacarJogador2.setText("Jogador 2: " + jogadorAtual.getPontos());
                }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBlocos = new ArrayList<>();
        btnFinalizar = findViewById(R.id.btnFinalizar);

        jogador1 = new Jogador("X");
        jogador2 = new Jogador("O");
        jogadorAtual = jogador1;


        for (int i = 1; i <= 9; i++) {
            int resId = getResources().getIdentifier("bloco" + i, "id", getPackageName());
            Button btnBloco = findViewById(resId);
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

        txtPlacarJogador1 = findViewById(R.id.player1);
        txtPlacarJogador2 = findViewById(R.id.player2);

        txtPlacarJogador1.setText("Jogador 1: 0");
        txtPlacarJogador2.setText("Jogador 2: 0");
    }
}