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
                SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);

                boolean temConta = prefs.getBoolean("tem_conta", false);

                if (temConta) {
                    Intent intent = new Intent(BemVindoActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(BemVindoActivity.this, CadastroActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}