package com.example.sprint_3_lexdigest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Agora sim, voltamos a carregar o nosso layout!
        setContentView(R.layout.activity_home);
    }
}