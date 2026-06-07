package com.example.sprint_3_lexdigest;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;

public class HistoricoActivity extends AppCompatActivity {

    private LinearLayout containerHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        ImageView btnVoltar = findViewById(R.id.btnVoltar);
        ImageView btnDelete = findViewById(R.id.btn_delete);
        containerHistorico = findViewById(R.id.containerHistorico);

        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> finish());
        }

        carregarHistorico();

        if (btnDelete != null) {
            btnDelete.setOnClickListener(v -> {
                SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
                prefs.edit().remove("lista_historico").apply();
                containerHistorico.removeAllViews();
                Toast.makeText(HistoricoActivity.this, "Histórico limpo!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void carregarHistorico() {
        SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
        String historicoJson = prefs.getString("lista_historico", "[]");

        try {
            JSONArray jsonArray = new JSONArray(historicoJson);

            if (jsonArray.length() == 0) {
                mostrarMensagemVazia();
                return;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String nome = item.getString("nome_original");
                String data = item.getString("data_hora");
                String pdf = item.getString("arquivo_gerado");

                adicionarBlocoNaTela(nome, data, pdf);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagemVazia();
        }
    }

    private void mostrarMensagemVazia() {
        TextView tvVazio = new TextView(this);
        tvVazio.setText("Nenhum processo analisado ainda.");
        tvVazio.setGravity(Gravity.CENTER);
        tvVazio.setPadding(0, 50, 0, 0);
        tvVazio.setTextSize(16f);
        tvVazio.setTextColor(Color.parseColor("#999999"));
        containerHistorico.addView(tvVazio);
    }

    private void adicionarBlocoNaTela(String nome, String data, String pdf) {
        LinearLayout bloco = new LinearLayout(this);
        bloco.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 30);
        bloco.setLayoutParams(params);
        bloco.setPadding(40, 40, 40, 40);
        bloco.setBackgroundColor(Color.parseColor("#F5F5F5"));

        TextView tvNome = new TextView(this);
        tvNome.setText("📂 " + nome);
        tvNome.setTextSize(16f);
        tvNome.setTypeface(null, Typeface.BOLD);
        tvNome.setTextColor(Color.parseColor("#333333"));

        TextView tvData = new TextView(this);
        tvData.setText("⏱ Processado em: " + data);
        tvData.setTextSize(14f);
        tvData.setTextColor(Color.parseColor("#666666"));
        tvData.setPadding(0, 10, 0, 10);

        TextView tvPdf = new TextView(this);
        tvPdf.setText("📄 " + pdf + "\n(Toque para localizar o PDF)");
        tvPdf.setTextSize(14f);
        tvPdf.setTextColor(Color.parseColor("#0066CC"));

        bloco.addView(tvNome);
        bloco.addView(tvData);
        bloco.addView(tvPdf);

        bloco.setOnClickListener(v -> {
            Toast.makeText(this, "O arquivo foi salvo na pasta 'Downloads' do celular!", Toast.LENGTH_LONG).show();
        });

        containerHistorico.addView(bloco);
    }
}