package com.example.sprint_3_lexdigest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private ImageView imgFotoPerfil;
    private ImageView btnEditarFoto;
    private EditText edtNomePerfil;
    private Button btnSalvarPerfil;
    private Uri fotoSelecionadaUri = null;

    private final ActivityResultLauncher<Intent> galeriaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fotoSelecionadaUri = result.getData().getData();
                    if (fotoSelecionadaUri != null) {
                        try {
                            getContentResolver().takePersistableUriPermission(
                                    fotoSelecionadaUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        imgFotoPerfil.setImageURI(fotoSelecionadaUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnVoltar = findViewById(R.id.btnVoltar);
        imgFotoPerfil = findViewById(R.id.imgPerfil);
        btnEditarFoto = findViewById(R.id.btnEditarFoto);
        edtNomePerfil = findViewById(R.id.etNome);
        btnSalvarPerfil = findViewById(R.id.btnSalvar);

        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> finish());
        }

        SharedPreferences prefs = getSharedPreferences("LexDigestPrefs", MODE_PRIVATE);
        String nomeSalvo = prefs.getString("nome_usuario", "");
        String fotoSalvaStr = prefs.getString("foto_usuario", "");

        if (edtNomePerfil != null && !nomeSalvo.isEmpty()) {
            edtNomePerfil.setText(nomeSalvo);
        }

        if (imgFotoPerfil != null && !fotoSalvaStr.isEmpty()) {
            try {
                imgFotoPerfil.setImageURI(Uri.parse(fotoSalvaStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (imgFotoPerfil != null) {
            imgFotoPerfil.setOnClickListener(v -> abrirGaleria());
        }

        if (btnEditarFoto != null) {
            btnEditarFoto.setOnClickListener(v -> abrirGaleria());
        }

        if (btnSalvarPerfil != null) {
            btnSalvarPerfil.setOnClickListener(v -> {
                String novoNome = edtNomePerfil != null ? edtNomePerfil.getText().toString().trim() : "";

                if (novoNome.isEmpty()) {
                    Toast.makeText(this, "Por favor, digite seu nome", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("nome_usuario", novoNome);

                if (fotoSelecionadaUri != null) {
                    editor.putString("foto_usuario", fotoSelecionadaUri.toString());
                }

                editor.apply();

                Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        galeriaLauncher.launch(intent);
    }
}