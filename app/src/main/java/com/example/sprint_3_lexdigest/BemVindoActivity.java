package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class BemVindoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bemvindo);

        LinearLayout btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acessa a "memória" do aplicativo
                SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);

                // Verifica se a chave "tem_conta" é true (o padrão é false se a pessoa nunca abriu o app)
                boolean temConta = prefs.getBoolean("tem_conta", false);

                if (temConta) {
                    // Se já tem conta registrada, vai direto para o Login
                    Intent intent = new Intent(BemVindoActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // Se é o primeiro contato (não tem conta), vai para o Cadastro
                    Intent intent = new Intent(BemVindoActivity.this, CadastroActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}