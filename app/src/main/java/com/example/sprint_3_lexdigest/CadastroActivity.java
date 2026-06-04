package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprint_3_lexdigest.database.DataBaseHelper;

public class CadastroActivity extends AppCompatActivity {

    private EditText etEmail, etPhone, etSenha, etConfirmaSenha;
    private Button btnCriarConta;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dbHelper = new DataBaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSenha = findViewById(R.id.etSenha);
        etConfirmaSenha = findViewById(R.id.etConfirmaSenha);
        btnCriarConta = findViewById(R.id.btnCriarConta);

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String telefone = etPhone.getText().toString();
                String senha = etSenha.getText().toString();
                String confirmaSenha = etConfirmaSenha.getText().toString();

                if (email.isEmpty() || telefone.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
                else if (!senha.equals(confirmaSenha)) {
                    Toast.makeText(CadastroActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean inserido = dbHelper.inserirUsuario("", email, senha, "", telefone);

                    if (inserido) {
                        SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("tem_conta", true);
                        editor.apply();

                        Toast.makeText(CadastroActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao criar a conta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}