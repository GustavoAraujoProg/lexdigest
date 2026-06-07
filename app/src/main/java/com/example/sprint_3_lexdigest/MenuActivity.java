package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        View btnFecharMenu = findViewById(R.id.btn_fechar_menu);
        TextView menuHome = findViewById(R.id.menu_home);
        TextView menuHistorico = findViewById(R.id.menu_historico);
        TextView menuPerfil = findViewById(R.id.menu_perfil);
        TextView txtNomeUsuarioMenu = findViewById(R.id.txt_nome_usuario_menu);
        ImageView imgFotoUsuarioMenu = findViewById(R.id.img_usuario_menu);

        SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
        String nomeSalvo = prefs.getString("nome_usuario", "Usuário");
        String fotoSalvaStr = prefs.getString("foto_usuario", "");

        if (txtNomeUsuarioMenu != null) {
            txtNomeUsuarioMenu.setText(nomeSalvo);
        }

        if (imgFotoUsuarioMenu != null && !fotoSalvaStr.isEmpty()) {
            try {
                imgFotoUsuarioMenu.setImageURI(Uri.parse(fotoSalvaStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (btnFecharMenu != null) {
            btnFecharMenu.setOnClickListener(v -> finish());
        }

        if (menuHome != null) {
            menuHome.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        if (menuHistorico != null) {
            menuHistorico.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, HistoricoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        }

        if (menuPerfil != null) {
            menuPerfil.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        }
    }
}