package Juego21palitos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VistaMenus {
    private static Scanner sc;

    public VistaMenus() {
        sc = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\nMENÚ PRINCIPAL:");
        System.out.println("1. Jugar");
        System.out.println("2. Controles/ayuda");
        System.out.println("3. Opciones");
        System.out.println("4. Salir");
        return pedirOpcion();
    }

    public int mostrarSubmenuJugar() {
        System.out.println("\nJUGAR:");
        System.out.println("1. Jugador vs Jugador.");
        System.out.println("2. Jugador vs IA.");
        System.out.println("3. 3 o más jugadores");
        System.out.println("4. Volver.");
        return pedirOpcion();
    }

    public int mostrarSubmenuJugadores() {
        System.out.println("\nELEGIR JUGADORES:");
        System.out.println("1. 3 jugadores.");
        System.out.println("2. 2 jugadores y 1 IA.");
        System.out.println("3. 4 jugadores.");
        System.out.println("4. 3 jugadores y 1 IA.");
        System.out.println("5. Volver.");
        return pedirOpcion();
    }

    public int mostrarSubmenuOpciones() {
        System.out.println("\nOPCIONES:");
        System.out.println("1. Cambiar estilo palitos");
        System.out.println("2. Cambiar número de palos");
        System.out.println("3. Cambiar nivel IA");
        System.out.println("4. Volver.");
        return pedirOpcion();
    }

    public int mostrarSubmenuEstiloPalos() {
        System.out.println("\nCAMBIAR ESTILO PALITOS:");
        System.out.println("1. Elegir caracter.");
        System.out.println("2. Activar/Desactivar salto de línea.");
        System.out.println("3. Volver.");
        return pedirOpcion();
    }

    public boolean confirmarCerrarPrograma() {
        System.out.println("\n¿Estás segur@ de que quieres cerrar el programa?");
        System.out.println("1. Cerrar");
        System.out.println("2. Cancelar");
        int respuesta = pedirOpcion();
        while (respuesta != 1 && respuesta != 2) {
            System.out.print("Entrada no válida (introduce 1 para salir, 2 para cancelar): ");
            respuesta = pedirOpcion();
        }
        return respuesta == 1; // Devuelve true si respuesta es 1
    }

    public static int pedirOpcion() {
        System.out.print("Selecciona una opción: ");
        return pedirNumero();
    }

    public static int pedirNumero() {
        try {
            int num = sc.nextInt();
            sc.nextLine(); // (Limpiar buffer)
            return num;
        } catch (InputMismatchException e) {
            sc.nextLine(); // (Limpiar buffer)
            System.out.print("Entrada no válida. Debes introducir un número entero: ");
            return pedirNumero();
        }
    }

    public void mensajeOpcionInvalida() {
        System.out.println("Opción no válida. Escribe un número correspondiente a la opción que quieras seleccionar.");
    }
}
