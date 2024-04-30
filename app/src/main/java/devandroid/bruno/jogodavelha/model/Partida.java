package devandroid.bruno.jogodavelha.model;

public class Partida {
    private int id;
    private String vencedor;

    public Partida( String vencedor) {
        setVencedor(vencedor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
}
