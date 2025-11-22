package com.example.calculadora_irisperez;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity implements ToolbarFragment.Toolbar_listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConstraintLayout c = findViewById(R.id.main);
    }

    @Override
    public void onButtonClick(int size, String text) {
        TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.TextFContainer);
        ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.ImageContainer);

        if (textFragment != null) {
            textFragment.ChangeTextProperties(size, text);
        }

        if (imageFragment != null) {
            imageFragment.ChangeImageAlpha(size);
        }
    }

    @Override
    public void onButtonClick2(int value) {
        ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.ImageContainer);

        if (imageFragment != null) {
            imageFragment.ChangeImageAlpha(value);
        }
    }
}