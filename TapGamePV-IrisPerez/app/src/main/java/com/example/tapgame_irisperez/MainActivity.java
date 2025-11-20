package com.example.tapgame_irisperez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText nombreUsuario;
    EditText claveUsuario;
    Button btnIniciarSesion;
    Button btnRegistrarse;

    EditText nuevoEditText;
    ImageView nuevoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conexion con la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tapgame-f4082-default-rtdb.europe-west1.firebasedatabase.app/");

//        // Registro un nombre de usuario de prueba
//        DatabaseReference nombreUsuarioPrueba = database.getReference("usuarios/usuarioPrueba/nombre");
//        nombreUsuarioPrueba.setValue("Iris");
//        // Registro una clave para ese usuario
//        DatabaseReference claveUsuarioPrueba = database.getReference("usuarios/usuarioPrueba/clave");
//        claveUsuarioPrueba.setValue("1234");


        nombreUsuario = findViewById(R.id.nombreUsuario);
        claveUsuario = findViewById(R.id.claveUsuario);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        nuevoEditText = findViewById(R.id.nuevoEditText);
        nuevoImageView = findViewById(R.id.nuevoImageView);

        // INICIO DE SESIÓN
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Leer los datos introducidos por el usuario
                String entradaNombreUsuario = nombreUsuario.getText().toString();
                String entradaClaveUsuario = claveUsuario.getText().toString();

                // 2. Buscar el nombre de usuario en la base de datos
                DatabaseReference usuarios = database.getReference("usuarios");

                // 3. Comprobar si el usuario existe
                usuarios.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        boolean usuarioEncontrado = false;

                        // Se recorren todos los ids de usuario para buscar que la clave nombre coincida con el nombre introducido
                        for (DataSnapshot refUsuario : task.getResult().getChildren()) {

                            String nombreUsuarioBD = refUsuario.child("nombre").getValue(String.class);

                            // Si el nombre de usuario introducido coincide con el de la base de datos
                            if (entradaNombreUsuario.equals(nombreUsuarioBD)) {
                                usuarioEncontrado = true;

                                // 4. Se obtiene la clave del usuario
                                String claveUsuarioBD = refUsuario.child("clave").getValue(String.class);

                                // 5. Si la clave introducida por el usuario corresponde con la de la base de datos
                                if (entradaClaveUsuario.equals(claveUsuarioBD)) {

                                    // Obtengo los puntos y el nivel del usuario
                                    int puntos = refUsuario.child("puntos").getValue(Integer.class);
                                    int nivel = refUsuario.child("nivel").getValue(Integer.class);

                                    // Informo al usuario de que ha iniciado sesión correctamente
                                    Toast.makeText(MainActivity.this, "Sesión iniciada con éxito", Toast.LENGTH_SHORT).show();

                                    // Al iniciar sesión, se redirige al usuario al juego
                                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                    // Paso los datos del usuario al siguiente activity
                                    i.putExtra("idUsuario", refUsuario.getKey());
                                    i.putExtra("nombreUsuario", nombreUsuarioBD);
                                    i.putExtra("puntos", puntos);
                                    i.putExtra("nivel", nivel);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            }
                        }

                        // Si no se encuentra el usuario en la base de datos
                        if (!usuarioEncontrado) {
                            Toast.makeText(MainActivity.this, "No hay ningún usuario registrado con ese nombre", Toast.LENGTH_SHORT).show();
                        }

                    // Si no logra conectarse a la base de datos
                    } else {
                        Toast.makeText(MainActivity.this, "Error al conectar con la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // REDIRIGIR A REGISTRARSE
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(i);
            }
        });

        nuevoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entradaNuevoEditText = nuevoEditText.getText().toString();

                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                i.putExtra("infoEditText", entradaNuevoEditText);
                startActivity(i);
            }
        });

    }
}