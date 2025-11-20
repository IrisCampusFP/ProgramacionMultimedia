package com.example.tapgame_irisperez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    String idUsuario;
    int puntos;
    int nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nombreUsuario = findViewById(R.id.nombreUsuarioRegistro);
        EditText claveUsuario = findViewById(R.id.claveUsuarioRegistro);
        Button btnRegistrarme = findViewById(R.id.btnRegistrarme);
        Button btnVolverAtras = findViewById(R.id.btnVolverAtras);


        // Conexión con la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tapgame-f4082-default-rtdb.europe-west1.firebasedatabase.app/");

        // REGISTRAR USUARIO
        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Leer los datos introducidos por el usuario
                String entradaNombreUsuario = nombreUsuario.getText().toString();
                String entradaClaveUsuario = claveUsuario.getText().toString();

                // 2. Comprobar que los campos no estén vacíos
                if (entradaNombreUsuario.isEmpty() || entradaClaveUsuario.isEmpty()) {
                    Toast.makeText(MainActivity3.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. Buscar el nombre de usuario en la base de datos
                DatabaseReference usuarios = database.getReference("usuarios");

                usuarios.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                        // 4. Comprobar si el usuario existe
                        for (DataSnapshot usuarioSnap : task.getResult().getChildren()) {
                            String nombreUsuarioBD = usuarioSnap.child("nombre").getValue(String.class);

                            if (entradaNombreUsuario.equals(nombreUsuarioBD)) {
                                // 5. Si existe un usuario con ese nombre, le redirijo a la pantalla de inicio de sesión
                                Toast.makeText(MainActivity3.this, "Ya existe un usuario con ese nombre, prueba a iniciar sesión.\nRedirigiendo a iniciar sesión...", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(MainActivity3.this, MainActivity.class);
                                i.putExtra("nombreUsuario", nombreUsuarioBD); // Paso el nombre de usuario al siguiente activity
                                startActivity(i);
                                finish();
                                return;
                            }
                        }

                        // 5. Si no hay ningún usuario registrado con ese nombre, se ejecuta el registro
                        DatabaseReference refUsuario = usuarios.push(); // (Crea un id aleatorio para guardar los datos del usuario)

                        idUsuario = refUsuario.getKey();

                        refUsuario.child("puntos").setValue(0);
                        refUsuario.child("nivel").setValue(0);
                        refUsuario.child("nombre").setValue(entradaNombreUsuario);
                        refUsuario.child("clave").setValue(entradaClaveUsuario);

                        Toast.makeText(MainActivity3.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                        // 6. Redirigir al usuario al juego
                        Intent i = new Intent(MainActivity3.this, MainActivity2.class);
                        // Paso los datos del usuario al siguiente activity
                        i.putExtra("idUsuario", idUsuario);
                        i.putExtra("nombreUsuario", entradaNombreUsuario);
                        i.putExtra("puntos", 0);
                        i.putExtra("nivel", 0);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(MainActivity3.this, "Error al conectar con la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // REDIRIGIR A INICIAR SESION (Cierra el activity y vuelve al anterior)
        btnVolverAtras.setOnClickListener(v -> finish());


    }
}