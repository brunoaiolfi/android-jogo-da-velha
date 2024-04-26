package devandroid.bruno.jogodavelha.model;

import java.util.ArrayList;

public class Jogador {
    private String nome = "";
    private int pontos = 0;
    private ArrayList<Integer> jogadas = new ArrayList<>();

    public Jogador(String nome ) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Integer> getJogadas() {
        return jogadas;
    }

    public void setJogadas(ArrayList<Integer> jogadas) {
        this.jogadas = jogadas;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
