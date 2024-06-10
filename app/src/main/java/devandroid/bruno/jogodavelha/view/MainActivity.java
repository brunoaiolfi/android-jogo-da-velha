package devandroid.bruno.jogodavelha.view;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.R;
import devandroid.bruno.jogodavelha.controller.PartidaController;
import devandroid.bruno.jogodavelha.model.Jogador;
import devandroid.bruno.jogodavelha.model.Partida;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context me = this;
        drawerLayout = findViewById(R.id.draw_view);
        navigationView = findViewById(R.id.nav_view);

        btnBlocos = new ArrayList<>();
        btnFinalizar = findViewById(R.id.btnFinalizar);

        jogador1 = new Jogador("X");
        jogador2 = new Jogador("O");

        jogadorAtual = jogador1;

        txtPlacarJogador1 = findViewById(R.id.player1);
        txtPlacarJogador2 = findViewById(R.id.player2);

        txtPlacarJogador1.setText("Jogador 1: 0");
        txtPlacarJogador2.setText("Jogador 2: 0");

        partidaController = new PartidaController(MainActivity.this);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_settings_jogar) {
                    Toast.makeText(me, "Jogar", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (item.getItemId() == R.id.action_settings_historico) {
                    Toast.makeText(me, "Histórico", Toast.LENGTH_SHORT).show();
                    return true;
                }

                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

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

        listarPartidas();
    }

    public void listarPartidas() {
        ArrayList<Partida> listaPartidas = partidaController.listar();
        TableLayout tablePartidas = findViewById(R.id.tablePartidas);
        tablePartidas.removeAllViews();

        for (Partida partida : listaPartidas) {
            adicionarPartidaNaLista(partida);
        }

    }

    public void adicionarPartidaNaLista(Partida partida) {
        TableLayout tablePartidas = findViewById(R.id.tablePartidas);

        TableRow row = new TableRow(this);

        TextView textViewId = new TextView(this);
        textViewId.setText(String.valueOf(partida.getId()));
        row.addView(textViewId);

        TextView textViewVencedor = new TextView(this);
        textViewVencedor.setText(partida.getVencedor());
        row.addView(textViewVencedor);

        tablePartidas.addView(row);
    }

    public void jogada(Button blocoSelecionado) {
        if (blocoSelecionado.getText() != "") {
            return;
        }

        marcarBloco(blocoSelecionado);
        adicionarJogada(blocoSelecionado);

        Boolean tempIsGameOver = verificarVitoria();

        if (tempIsGameOver) {
            Toast.makeText(this, "O jogador " + jogadorAtual.getNome() + " venceu!", Toast.LENGTH_SHORT).show();
            isGameOver = tempIsGameOver;

            Partida partida = new Partida(jogadorAtual.getNome());
            partidaController.salvar(partida);
            listarPartidas();
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