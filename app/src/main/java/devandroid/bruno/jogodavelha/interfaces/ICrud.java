package devandroid.bruno.jogodavelha.interfaces;

import java.util.ArrayList;

public interface ICrud<T> {
    public T salvar(T dto);
    public ArrayList<T> listar();
}
