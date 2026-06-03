package com.example.sprint_3_lexdigest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Apanha o botão de "Sair" (com o ícone de X) do seu layout
        LinearLayout btnSair = findViewById(R.id.menu_sair);

        // Agora ele APENAS FECHA O MENU e volta para onde você estava
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Destrói o menu e volta para a tela de fundo
            }
        });

        // O botão "Home" também pode apenas fechar o menu, se a pessoa já estiver na Home
        findViewById(R.id.menu_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}