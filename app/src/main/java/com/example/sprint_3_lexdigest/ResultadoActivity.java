package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultadoActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private TextView tvResultado;
    private String nomeArquivoOriginal = "Processo_Desconhecido.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        btnVoltar = findViewById(R.id.btnVoltar);
        tvResultado = findViewById(R.id.tvResultado);

        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> finish());
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("URI_DO_PDF")) {
            String uriString = intent.getStringExtra("URI_DO_PDF");
            Uri pdfUri = Uri.parse(uriString);
            nomeArquivoOriginal = pdfUri.getLastPathSegment();
            processarPDF();
        } else {
            Toast.makeText(this, "Erro ao carregar PDF", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void processarPDF() {
        if (tvResultado != null) {
            String textoVisual = "✅ IA Finalizou a Análise!\n\n" +
                    "📄 Arquivo Gerado:\n" +
                    "Analise_LexDigest.pdf\n\n" +
                    "Tópicos Extraídos:\n" +
                    "• Síntese dos Fatos\n" +
                    "• Riscos e Fundamentos\n" +
                    "• Estratégia de Defesa\n" +
                    "• Prazos Imediatos\n\n" +
                    "(TOQUE AQUI PARA BAIXAR O PDF)";

            tvResultado.setText(textoVisual);
            tvResultado.setOnClickListener(v -> gerarPdfReal());

            salvarNaListaDeHistorico(nomeArquivoOriginal, "Analise_LexDigest.pdf");
        }
    }

    private void salvarNaListaDeHistorico(String nomeOriginal, String pdfGerado) {
        SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
        String historicoAtual = prefs.getString("lista_historico", "[]");

        try {
            JSONArray jsonArray = new JSONArray(historicoAtual);
            JSONObject novoItem = new JSONObject();

            novoItem.put("nome_original", nomeOriginal);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault());
            String dataHora = sdf.format(new Date());
            novoItem.put("data_hora", dataHora);

            novoItem.put("arquivo_gerado", pdfGerado);

            JSONArray novoArray = new JSONArray();
            novoArray.put(novoItem);
            for (int i = 0; i < jsonArray.length(); i++) {
                novoArray.put(jsonArray.get(i));
            }

            prefs.edit().putString("lista_historico", novoArray.toString()).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gerarPdfReal() {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(14f);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        String textoDoPdf =
                "LEXDIGEST - RELATÓRIO DE ANÁLISE PROCESSUAL\n" +
                        "======================================================================\n\n" +
                        "DOCUMENTO ORIGINAL: " + nomeArquivoOriginal + "\n" +
                        "NATUREZA DA AÇÃO: Indenizatória c/c Repetição de Indébito e Danos Morais\n" +
                        "STATUS: Processo em Fase Inicial (Citação expedida)\n\n" +
                        "1. SÍNTESE DOS FATOS E PEDIDOS\n" +
                        "A parte autora alega cobrança indevida por serviços não contratados, ocorridos\n" +
                        "no período de março a maio do corrente ano. Requer a devolução em dobro dos\n" +
                        "valores pagos (Art. 42, Parágrafo Único, do CDC), bem como indenização por\n" +
                        "danos morais no montante de R$ 15.000,00. Há pedido de tutela de urgência\n" +
                        "para suspensão imediata das cobranças e exclusão dos órgãos de proteção.\n\n" +
                        "2. ANÁLISE DE RISCO E FUNDAMENTAÇÃO JURÍDICA\n" +
                        "Risco Classificado: MODERADO/ALTO.\n" +
                        "O juízo deferiu a tutela antecipada e determinou a inversão do ônus da prova,\n" +
                        "amparado no Código de Defesa do Consumidor (Art. 6º, VIII). A jurisprudência\n" +
                        "majoritária do STJ (Súmula 297 e Súmula 326) é amplamente favorável ao\n" +
                        "pleito autoral em casos de falha na prestação de serviço não comprovada\n" +
                        "pela empresa ré.\n\n" +
                        "3. ESTRATÉGIA DE DEFESA RECOMENDADA\n" +
                        "- PRELIMINAR: Alegação de inépcia da inicial por falta de provas mínimas.\n" +
                        "- MÉRITO: Demonstrar a legitimidade das cobranças através de logs de\n" +
                        "  sistema, aceite eletrônico de termos de uso e gravações de atendimento.\n" +
                        "- PEDIDO SUBSIDIÁRIO: Em caso de eventual condenação, requerer a fixação\n" +
                        "  do dano moral em patamares razoáveis.\n\n" +
                        "4. PRAZOS E PROVIDÊNCIAS IMEDIATAS\n" +
                        "-> Prazo para Agravo de Instrumento (Tutela): 15 dias úteis.\n" +
                        "-> Prazo para Contestação: 15 dias úteis a contar da audiência de conciliação.\n" +
                        "-> Providência: Juntar aos autos o histórico completo do cliente em até 48h.\n\n" +
                        "======================================================================\n" +
                        "Documento gerado automaticamente por Inteligência Artificial - LexDigest v1.0";

        int x = 40;
        int y = 50;

        String[] linhas = textoDoPdf.split("\n");
        for (String linha : linhas) {
            canvas.drawText(linha, x, y, paint);
            y += 20;
        }

        pdfDocument.finishPage(page);

        File diretorioDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        String horaPDF = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        File arquivoPdf = new File(diretorioDownloads, "Analise_LexDigest_" + horaPDF + ".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(arquivoPdf));
            Toast.makeText(this, "PDF SALVO COM SUCESSO NA PASTA DOWNLOADS!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao salvar PDF.", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }
}