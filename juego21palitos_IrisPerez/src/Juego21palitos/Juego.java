package Juego21palitos;

import java.util.Scanner;

class Juego {
    private final Scanner sc;
    private final VistaMenus m = new VistaMenus();
    private char palito;
    private boolean saltoLinea;
    private int numPalos;
    private boolean ganaIA;


    public Juego() {
        this.sc = new Scanner(System.in);
        this.palito = '|';
        this.saltoLinea = false;
        this.numPalos = 21;
        this.ganaIA = true;
    }

    public Juego(Scanner sc, char palito, boolean saltoLinea, int numPalos, boolean ganaIA) {
        this.sc = sc;
        this.palito = palito;
        this.saltoLinea = saltoLinea;
        this.numPalos = numPalos;
        this.ganaIA = ganaIA;
    }

    public void menuPrincipal() {
        int opcion = m.mostrarMenuPrincipal();

        switch (opcion) {
            case 1:
                submenuJugar();
                break;
            case 2:
                mostrarControles();
                break;
            case 3:
                submenuOpciones();
                break;
            case 4:
                if (m.confirmarCerrarPrograma()) {
                    System.out.println("\nCerrando programa...");
                    System.out.println("¡Hasta pronto :)!");
                    sc.close();
                    System.exit(0);
                } else {
                    System.out.println("Volviendo al menú...");
                    menuPrincipal();
                }
                break;
            default:
                menuPrincipal();
                break;
        }
    }

    public void submenuJugar() {
        int opcion = m.mostrarSubmenuJugar();
        int numJugadores;
        boolean hayIA;

        switch (opcion) {
            case 1:
                numJugadores = 2;
                hayIA = false;
                break;
            case 2:
                numJugadores = 2;
                hayIA = true;
                break;
            case 3:
                submenuElegirJugadores();
                return;
            case 4:
                menuPrincipal();
                return;
            default:
                m.mensajeOpcionInvalida();
                submenuJugar();
                return;
        }
        Partida partida = new Partida(palito, saltoLinea, numPalos, ganaIA, numJugadores, hayIA);
        partida.empezarPartida();
    }

    public void submenuElegirJugadores() {
        int opcion = m.mostrarSubmenuJugadores();
        int numJugadores;
        boolean hayIA;

        switch (opcion) {
            case 1:
                numJugadores = 3;
                hayIA = false;
                break;
            case 2:
                numJugadores = 3;
                hayIA = true;
                break;
            case 3:
                numJugadores = 4;
                hayIA = false;
                break;
            case 4:
                numJugadores = 4;
                hayIA = true;
                break;
            case 5:
                submenuJugar();
                return;
            default:
                m.mensajeOpcionInvalida();
                submenuElegirJugadores();
                return;
        }
        Partida partida = new Partida(palito, saltoLinea, numPalos, ganaIA, numJugadores, hayIA);
        partida.empezarPartida();
    }

    public void mostrarControles() {
        System.out.println("\nNORMAS:");
        System.out.println("- Puedes retirar entre 1 y 4 palos por turno.");
        System.out.println("- El jugador que retire el último palo pierde.");
        System.out.println("CONTROLES:");
        System.out.println("En tu turno, pulsa el número correspondiente al número de palos que desees retirar.");
        System.out.println("Intenta que tu rival retire el último palo para hacerte con la victoria. ¡SUERTE!");

        System.out.print("\n(Pulsa ENTER para volver al menú principal)");
        sc.nextLine(); // Se espera a que el usuario pulse enter
        menuPrincipal();
    }

    public void submenuOpciones() {
        int opcion = m.mostrarSubmenuOpciones();

        switch (opcion) {
            case 1:
                submenuEstiloPalos();
                break;
            case 2:
                submenuNumPalos();
                break;
            case 3:
                submenuIA();
                break;
            case 4:
                menuPrincipal();
                break;
            default:
                m.mensajeOpcionInvalida();
                submenuElegirJugadores();
                break;
        }
    }

    public void submenuEstiloPalos() {
        int opcion = m.mostrarSubmenuEstiloPalos();

        switch (opcion) {
            case 1:
                submenuCaracter();
                break;
            case 2:
                submenuSaltoLinea();
            case 3:
                submenuOpciones();
                break;
            default:
                m.mensajeOpcionInvalida();
                submenuEstiloPalos();
                break;
        }
    }

    public void submenuCaracter() {
        System.out.println("\nELEGIR CARACTER PALITOS:");
        System.out.println("Caracter actual: " + palito);
        System.out.println("1. '|'.");
        System.out.println("2. '&'.");
        System.out.println("3. Personalizado.");
        System.out.println("4. Volver.");
        int opcion = VistaMenus.pedirOpcion();

        switch (opcion) {
            case 1:
                this.palito = '|';
                submenuCaracter();
                break;
            case 2:
                this.palito = '&';
                submenuCaracter();
                break;
            case 3:
                pedirCaracter();
                submenuCaracter();
                break;
            case 4:
                submenuEstiloPalos();
            default:
                m.mensajeOpcionInvalida();
                submenuEstiloPalos();
                break;
        }
    }

    public void pedirCaracter() {
        System.out.println("\nCARACTER PERSONALIZADO");
        System.out.print("Introduce a continuación el caracter que quieras asignar a los palitos: ");
        this.palito = sc.next().charAt(0);
    }

    public void submenuSaltoLinea() {
        System.out.println("\nACTIVAR / DESACTIVAR SALTO DE LÍNEA");
        System.out.println("Estado actual: " + (saltoLinea ? "Activado" : "Desactivado"));
        System.out.println("1. Activar.");
        System.out.println("2. Desactivar.");
        System.out.println("3. Volver");
        int opcion = VistaMenus.pedirOpcion();

        switch (opcion) {
            case 1:
                this.saltoLinea = true;
                submenuSaltoLinea();
                break;
            case 2:
                this.saltoLinea = false;
                submenuSaltoLinea();
                break;
            case 3:
                submenuEstiloPalos();
                break;
            default:
                m.mensajeOpcionInvalida();
                submenuSaltoLinea();
                break;
        }
    }

    public void submenuNumPalos() {
        System.out.println("\nELEGIR NÚMERO DE PALOS:");
        System.out.println("1. 16 palitos.");
        System.out.println("2. 21 palitos.");
        System.out.println("3. 26 palitos.");
        System.out.println("4. 31 palitos.");
        System.out.println("5. 36 palitos.");
        System.out.println("6. 41 palitos.");
        System.out.println("7. Volver.");
        System.out.println("(Número actual: " + numPalos + ")");
        int opcion = VistaMenus.pedirOpcion();

        switch (opcion) {
            case 1:
                numPalos = 16;
                submenuNumPalos();
                break;
            case 2:
                numPalos = 21;
                submenuNumPalos();
                break;
            case 3:
                numPalos = 26;
                submenuNumPalos();
                break;
            case 4:
                numPalos = 31;
                submenuNumPalos();
                break;
            case 5:
                numPalos = 36;
                submenuNumPalos();
                break;
            case 6:
                numPalos = 41;
                submenuNumPalos();
                break;
            case 7:
                submenuOpciones();
                break;
            default:
                m.mensajeOpcionInvalida();
                submenuNumPalos();
                break;
        }
    }

    public void submenuIA() {
        System.out.println("\nELEGIR NIVEL IA:");
        System.out.println("1. Fácil.");
        System.out.println("2. Difícil.");
        System.out.println("3. Volver.");
        System.out.println("Nivel actual: " + (ganaIA ? "Difícil" : "Fácil"));
        int opcion = VistaMenus.pedirOpcion();

        switch (opcion) {
            case 1:
                ganaIA = false;
                submenuIA();
                break;
            case 2:
                ganaIA = true;
                submenuIA();
                break;
            case 3:
                submenuOpciones();
                break;
        }
    }
}
