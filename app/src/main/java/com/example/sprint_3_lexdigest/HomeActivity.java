package com.example.sprint_3_lexdigest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageView btnMenu;
    private View btnUpload;

    private final ActivityResultLauncher<Intent> seletorDePDF = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri pdfUri = result.getData().getData();
                    abrirTelaDeResultado(pdfUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnMenu = findViewById(R.id.btnMenu);
        btnUpload = findViewById(R.id.btnEncontrarPdf);

        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
            });
        }

        if (btnUpload != null) {
            btnUpload.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                seletorDePDF.launch(intent);
            });
        }
    }

    private void abrirTelaDeResultado(Uri pdfUri) {
        Intent intent = new Intent(HomeActivity.this, ResultadoActivity.class);
        if (pdfUri != null) {
            intent.putExtra("URI_DO_PDF", pdfUri.toString());
        }
        startActivity(intent);
    }
}