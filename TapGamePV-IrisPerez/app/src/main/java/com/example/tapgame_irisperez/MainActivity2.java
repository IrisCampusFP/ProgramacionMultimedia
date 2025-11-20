package com.example.tapgame_irisperez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    // Conexion con la base de datos
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tapgame-f4082-default-rtdb.europe-west1.firebasedatabase.app/");

    DatabaseReference refUsuario;
    TextView textoBienvenida;
    TextView tvPuntos;
    TextView tvNivel;
    int incrementoPuntos = 1;
    int clics;
    Button boton;
    int puntos;
    int nivel;
    Button btnRanking;
    long msUltimoClic = 0L;
    Button btnSalir;

    TextView tvInfoEditText;

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


        textoBienvenida = findViewById(R.id.textoBienvenida);
        tvPuntos = findViewById(R.id.tvPuntos);
        tvNivel = findViewById(R.id.tvNivel);
        boton = findViewById(R.id.boton);
        btnRanking = findViewById(R.id.btnRanking);
        btnSalir = findViewById(R.id.btnSalir);

        tvInfoEditText = findViewById(R.id.tvInfoEditText);


        // Accedo al usuario
//        String idUsuario = getIntent().getStringExtra("idUsuario");
//        refUsuario = database.getReference("usuarios").child(idUsuario);

        // Obtengo los datos del usuario
//        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
//        puntos = getIntent().getIntExtra("puntos", 0);
//        nivel = getIntent().getIntExtra("nivel", 0);

        String infoEditText = getIntent().getStringExtra("infoEditText");


//        textoBienvenida.setText("Bienvenid@ " + nombreUsuario + " :)");
//        tvPuntos.setText(String.valueOf(puntos));
//        String textoNivel = "Nivel " + nivel;
//        tvNivel.setText(textoNivel);

        tvInfoEditText.setText(infoEditText);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long ahora = System.currentTimeMillis();

                // Reicio de clics si deja de pulsar para reiniciar los mensajes de ira
                if (ahora - msUltimoClic > 5000) {
                    clics = 0; // Se resetean los clics para que se vuelvan a mostrar los mensajes
                }
                msUltimoClic = ahora;

                clics++;
                puntos += incrementoPuntos;
                tvPuntos.setText(String.valueOf(puntos));

                // Cada 20 clics el puntuador aumenta 1 punto
                if (clics % 20 == 0) {
                    incrementoPuntos += 1;

                    // Muestro un mensaje a los primeros 20 clics
                    if (clics == 20) {
                        Toast.makeText(MainActivity2.this, "🔥 Ira nivel 1 😠 ¡Ahora putúas más! 🔥", Toast.LENGTH_SHORT).show();
                    }
                }

                // Cada 50 clics el puntuador aumenta 3 puntos
                if (clics % 50 == 0) {
                    incrementoPuntos += 3;

                    // Muestro un mensaje en los primeros 50 clics
                    if (clics == 50) {
                        Toast.makeText(MainActivity2.this, "🔥🔥 Ira nivel 2 😤 ¡Ahora putúas más! 🔥🔥", Toast.LENGTH_SHORT).show();
                    }
                }

                // Cada 100 clics el puntuador aumenta 5 puntos y el usuario sube de nivel
                if (clics % 100 == 0) {
                    incrementoPuntos += 5;

                    // Muestro un mensaje en los primeros 100 clics
                    if (clics == 100) {
                        Toast.makeText(MainActivity2.this, "🔥🔥🔥 Ira nivel máximo 😡 ¡Ahora putúas más! 🔥🔥🔥", Toast.LENGTH_SHORT).show();
                    }
                }

                // Cada 1000 puntos el usuario sube de nivel
                int nuevoNivel = Math.max(1, 1 + puntos / 1000);
                if (nuevoNivel > nivel) {
                    nivel = nuevoNivel;
                    String textoNivel = "Nivel " + nivel;
                    tvNivel.setText(textoNivel);
                    Toast.makeText(MainActivity2.this, "🎈🎉¡Enhorabuena, has ascendido al nivel " + nivel + "!🎉🎈", Toast.LENGTH_LONG).show();
                }
                guardarProgreso();
            }
        });

        // Implementación del botón ver ranking
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, MainActivity4.class);
                startActivity(i);
            }
        });

        // Implementación del botón para cerrar sesión
        btnSalir.setOnClickListener(v -> finish());
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        guardarProgreso();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        guardarProgreso();
//    }

    // Metodo para guardar el progreso del usuario
    private void guardarProgreso() {
        if (refUsuario != null) {
            refUsuario.child("puntos").setValue(puntos);
            refUsuario.child("nivel").setValue(nivel);
        }
    }
}
