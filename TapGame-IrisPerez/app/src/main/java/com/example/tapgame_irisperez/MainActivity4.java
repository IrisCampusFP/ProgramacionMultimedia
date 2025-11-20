package com.example.tapgame_irisperez;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tapgame-f4082-default-rtdb.europe-west1.firebasedatabase.app/");

    Button btnVolver;
    TextView tvPrimero;
    TextView tvSegundo;
    TextView tvTercero;

    public static class Usuario {
        String nombre;
        int puntos;
        Usuario(String nombre, int puntos) {
            this.nombre = nombre;
            this.puntos = puntos;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolver = findViewById(R.id.btnVolver);
        tvPrimero = findViewById(R.id.tvPrimero);
        tvSegundo = findViewById(R.id.tvSegundo);
        tvTercero = findViewById(R.id.tvTercero);

        DatabaseReference usuarios = database.getReference("usuarios");

        // Se ordenan los usuarios por puntos y se obtienen los 3 con más puntuación
        // Users are sorted by points and the top 3 with the highest score are obtained
        usuarios.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<Usuario> listaUsuarios = new ArrayList<>();

                // Se recorren todos los usuarios
                // All users are iterated over
                for (DataSnapshot usuario : task.getResult().getChildren()) {
                    String nombreUsuario = usuario.child("nombre").getValue(String.class);
                    int puntos = usuario.child("puntos").getValue(Integer.class);

                    if (nombreUsuario != null) {
                        listaUsuarios.add(new Usuario(nombreUsuario, puntos));
                    }
                }

                // Si no se encuentra el usuario en la base de datos
                // If no users are found in the database
                if (listaUsuarios.isEmpty()) {
                    tvPrimero.setText("No hay usuarios registrados");
                    tvSegundo.setText("");
                    tvTercero.setText("");
                    return;
                }

                // Se ordena la lista por puntos
                // The list is sorted by points
                Collections.sort(listaUsuarios, (a, b) -> Integer.compare(b.puntos, a.puntos));

                Usuario u1 = listaUsuarios.get(0);
                tvPrimero.setText("1º " + u1.nombre + " (" + u1.puntos + " puntos) 🥇");

                if (listaUsuarios.size() > 1) {
                    Usuario u2 = listaUsuarios.get(1);
                    tvSegundo.setText("2º " + u2.nombre + " (" + u2.puntos + " puntos) 🥈");
                } else {
                    tvSegundo.setText("");
                }

                if (listaUsuarios.size() > 2) {
                    Usuario u3 = listaUsuarios.get(2);
                    tvTercero.setText("3º " + u3.nombre + " (" + u3.puntos + " puntos) 🥉");
                } else {
                    tvTercero.setText("");
                }
            } else {
                Toast.makeText(MainActivity4.this, "Error al cargar los datos de los usuarios de la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        // Implementación del botón para volver al juego
        // Implementation of the button to return to the game
        // (Vuelve al activity anterior sin crear otro MainActivity2)
        // (Returns to the previous activity without creating another MainActivity2)
        btnVolver.setOnClickListener(v -> finish());
    }
}