package devandroid.bruno.jogodavelha.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.bruno.jogodavelha.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    public static final int TIME_OUT_SPLASH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cumutarTelaSplash();
    }

    private void cumutarTelaSplash() {
        new Handler().postDelayed(() -> {
            Intent telaPrincipal = new Intent(getBaseContext(), MainActivity.class);

            startActivity(telaPrincipal);

            finish();
        }, TIME_OUT_SPLASH);
    }
}