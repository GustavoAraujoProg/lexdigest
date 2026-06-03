package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // 1. Declarar o botão
    private ImageView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 2. Ligar a variável ao botão do XML
        btnMenu = findViewById(R.id.btnMenu);

        // 3. Criar a ação de clique no botão do menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 4. Abre o seu ecrã de menu (MenuActivity) com o fundo transparente!
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        });
    }
}