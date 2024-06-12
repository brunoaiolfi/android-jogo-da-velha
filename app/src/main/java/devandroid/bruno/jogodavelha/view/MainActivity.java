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
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import devandroid.bruno.jogodavelha.R;
import devandroid.bruno.jogodavelha.controller.PartidaController;
import devandroid.bruno.jogodavelha.model.Jogador;
import devandroid.bruno.jogodavelha.model.Partida;

// TODO - criar um novo fragmento (HistoricoFragment) para listar as partidas
// TODO - criar um novo layout para o fragmento

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context me = this;
        drawerLayout = findViewById(R.id.draw_view);
        navigationView = findViewById(R.id.nav_view);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment, new JogoFragment()).commit();

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_settings_jogar) {
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new JogoFragment()).commit();
                }
                else if (id == R.id.action_settings_historico) {
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new HistoricoFragment()).commit();
                }

                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });
    }


}