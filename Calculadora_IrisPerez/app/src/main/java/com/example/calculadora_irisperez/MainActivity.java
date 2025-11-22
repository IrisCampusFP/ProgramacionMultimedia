package com.example.calculadora_irisperez;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView operacion;
    private TextView resultado;

    private String expresion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operacion = findViewById(R.id.textViewOperacion);
        resultado = findViewById(R.id.textViewResultado);

        // Números
        configurarBoton(R.id.btn0, "0");
        configurarBoton(R.id.btn1, "1");
        configurarBoton(R.id.btn2, "2");
        configurarBoton(R.id.btn3, "3");
        configurarBoton(R.id.btn4, "4");
        configurarBoton(R.id.btn5, "5");
        configurarBoton(R.id.btn6, "6");
        configurarBoton(R.id.btn7, "7");
        configurarBoton(R.id.btn8, "8");
        configurarBoton(R.id.btn9, "9");

        // Operadores
        configurarBoton(R.id.btnSumar, "+");
        configurarBoton(R.id.btnRestar, "-");
        configurarBoton(R.id.btnMultiplicar, "*");
        configurarBoton(R.id.btnDividir, "/");
        configurarBoton(R.id.btnParentesisIzq, "(");
        configurarBoton(R.id.btnParentesisDer, ")");
        configurarBoton(R.id.btnComa, ".");

        // Botón borrar último carácter
        Button btnBorrar = findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expresion.length() > 0) {
                    expresion = expresion.substring(0, expresion.length() - 1);
                    operacion.setText(expresion);
                }
            }
        });

        // Botón limpiar todo
        Button btnLimpiar = findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expresion = "";
                operacion.setText("");
                resultado.setText("");
            }
        });

        // Botón igual (=)
        Button btnIgual = findViewById(R.id.btnIgual);
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double res = evaluarExpresion(expresion);
                    resultado.setText(String.valueOf(res));
                } catch (Exception e) {
                    resultado.setText("Error");
                }
            }
        });
    }

    // Metodo que asigna texto al pulsar los botones
    private void configurarBoton(int id, final String valor) {
        Button btn = findViewById(id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expresion += valor;
                operacion.setText(expresion);
            }
        });
    }

    // Metodo que evalúa la expresión
    private double evaluarExpresion(String expr) {
        // Eliminamos espacios
        expr = expr.replaceAll("\\s", "");

        Stack<Double> numeros = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            // Si es número o punto decimal
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                i--; // retroceder 1 posición
                numeros.push(Double.parseDouble(sb.toString()));
            }

            // Si es un paréntesis izquierdo
            else if (c == '(') {
                operadores.push(c);
            }

            // Si es un paréntesis derecho
            else if (c == ')') {
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    numeros.push(aplicarOperacion(operadores.pop(), numeros.pop(), numeros.pop()));
                }
                operadores.pop(); // quitar el '('
            }

            // Si es operador
            else if (esOperador(c)) {
                while (!operadores.isEmpty() && prioridad(operadores.peek()) >= prioridad(c)) {
                    numeros.push(aplicarOperacion(operadores.pop(), numeros.pop(), numeros.pop()));
                }
                operadores.push(c);
            }
        }

        // Aplicar operaciones restantes
        while (!operadores.isEmpty()) {
            numeros.push(aplicarOperacion(operadores.pop(), numeros.pop(), numeros.pop()));
        }

        return numeros.pop();
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int prioridad(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    private double aplicarOperacion(char operador, double b, double a) {
        switch (operador) {
            case '+': return suma(a, b);
            case '-': return resta(a, b);
            case '*': return multiplicacion(a, b);
            case '/': return division(a, b);
        }
        return 0;
    }

    // Métodos del autor original
    public double suma(double num1, double num2) {
        return num1 + num2;
    }

    public double resta(double num1, double num2) {
        return num1 - num2;
    }

    public double multiplicacion(double num1, double num2) {
        return num1 * num2;
    }

    public double division(double num1, double num2) {
        if (num2 != 0) return num1 / num2;
        else return 0;
    }
}
