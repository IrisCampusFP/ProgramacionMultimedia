package Juego21palitos;

import java.util.Random;
import java.util.Scanner;

public class Partida {
    private final Scanner sc;
    Random rand = new Random();
    private final char palito;
    private final boolean saltoLinea;
    private int numPalos;
    private int numPalosInicial;
    private final boolean ganaIA;
    private final int numJugadores;
    private final boolean hayIA;
    private final Juego j;

    public Partida(char palito, boolean saltoLinea, int numPalos, boolean ganaIA, int numJugadores, boolean hayIA) {
        this.sc = new Scanner(System.in);
        this.palito = palito;
        this.saltoLinea = saltoLinea;
        this.numPalos = numPalos;
        this.ganaIA = ganaIA;
        this.numJugadores = numJugadores;
        this.hayIA = hayIA;
        this.j = new Juego(sc, palito, saltoLinea, numPalos, ganaIA);
    }

    public void introduccion() {
        System.out.println("\nINTRODUCCIÓN:");
        System.out.println("""
                El juego consiste en tachar palitos (de 1 a 4) por turnos
                teniendo 21 palitos en total al inicio de la partida.
                El jugador que tache el último palito, pierde.
                """);
        System.out.println("[Escribe '0' en cualquier momento para volver al menú principal]\n");
    }

    public void imprimirPalos() {
        if (saltoLinea) { System.out.println(); }

        for (int i=0; i<numPalos; i++) {
            System.out.print(palito);
            if (saltoLinea && (i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    public void empezarPartida() {
        this.numPalosInicial = numPalos;
        introduccion();
        boolean esIA;
        int numJugador = 1;
        int palosQuitados = 1;

        while (numPalos >= 1) {
            if (hayIA && numJugador == numJugadores) {
                esIA = true;
            } else {
                esIA = false;
            }

            // 1. Se imprimen los palos
            System.out.print("Palitos restantes: ");
            imprimirPalos();
            System.out.println(" [" + numPalos + "]");

            // Si solo queda 1 palo
            if (numPalos == 1) {
                comprobarGanador(numJugador, esIA);
                return;
            }

            // 2. Se indica el turno
            if (esIA) {
                System.out.println("\nTURNO IA");
                palosQuitados = turnoIA(palosQuitados);
                System.out.println("La IA ha retirado " + palosQuitados + " palos.");
            } else if (hayIA && numJugadores == 2) {
                System.out.println("\nTU TURNO");
                palosQuitados = turnoJugador();
            } else {
                System.out.println("\nTURNO JUGADOR " + numJugador);
                palosQuitados = turnoJugador();
            }

            // Si el jugador elige salir ('0'):
            if (palosQuitados == 0) {
                boolean salir = confirmarSalir();
                if (salir) {
                    System.out.println("\nVolviendo al menú principal...");
                    j.menuPrincipal();
                    return; // Se finaliza este metodo (el flujo continua volviendo al menu principal)
                } else {
                    System.out.println("\nReanudando partida...\n");
                    continue; // Se vuelve a iniciar el metodo
                }
            }

            // 3. Se restan los palos retirados al número de palos
            numPalos -= palosQuitados;

            // Si algun jugador retira el ultimo palo
            if (numPalos == 0) {
                comprobarGanador(numJugador, esIA);
                return;
            }

            // 4. Se cambia el turno al siguiente jugador
            numJugador++;
            if (numJugador > numJugadores) numJugador = 1;
        }
    }

    private int turnoJugador() {
        System.out.print("Escribe el nº de palos a retirar (1-4): ");
        int palosQuitados = VistaMenus.pedirNumero();

        while (palosQuitados < 0 || palosQuitados > 4 || palosQuitados > numPalos) {
            System.out.print("Número no válido. Escribe de nuevo el nº de palos a retirar: ");
            palosQuitados = VistaMenus.pedirNumero();
        }
        return palosQuitados;
    }

    private int turnoIA(int palosQuitadosJugador) {
        int palosQuitadosIA;
        if (numJugadores == 2 && ganaIA) {
            // (La IA siempre gana)
            palosQuitadosIA = (5 - palosQuitadosJugador);
        } else if (numJugadores == 2) {
            // (La IA siempre deja al jugador un número de palos múltiplo de 5 para dejarle ganar)
            palosQuitadosIA = (numPalos % 5);
            palosQuitadosIA = (palosQuitadosIA == 0) ? 1 : palosQuitadosIA;
        } else {
            if (ganaIA && numPalos <= 5) {
                palosQuitadosIA = numPalos - 1;
            } else {
                // (La IA retira un número aleatorio de palos)
                palosQuitadosIA = rand.nextInt(4) + 1;
            }
        }
        return palosQuitadosIA;
    }

    public boolean confirmarSalir() {
        System.out.println("\n¿Estás segur@ de que quieres salir?");
        System.out.println("(Se terminará la partida y volverás al menú principal)");
        System.out.println("1. Salir");
        System.out.println("2. Continuar partida");
        int respuesta = VistaMenus.pedirOpcion();
        while (respuesta != 1 && respuesta != 2) {
            System.out.print("Entrada no válida (introduce 1 para salir, 2 para continuar): ");
            respuesta = VistaMenus.pedirNumero();
        }
        return respuesta == 1; // Devuelve true si respuesta es 1
    }

    public void comprobarGanador(int numJugador, boolean esIA) {
        System.out.print("\nFIN DE LA PARTIDA.");
        if (numPalos == 0) {
            if (numJugadores == 2) {
                if (esIA) {
                    System.out.println("La IA ha retirado el último palo y ha perdido.");
                    System.out.println("🎉 ¡ENHORABUENA, HAS GANADO A LA IA! :) 🎉");
                } else {
                    if (hayIA) {
                        System.out.println("GAME OVER :(. Has retirado el último palo, HAS PERDIDO :(.");
                    } else {
                        System.out.println("\nEl JUGADOR " + numJugador + " ha retirado el último palo y HA PERDIDO.");
                        int contrario = (numJugador == 1) ? 2 : 1;
                        System.out.println("🎉 ¡ENHORABUENA AL JUGADOR " + contrario + "! :) 🎉");
                    }
                }
            } else {
                if (esIA) {
                    System.out.println("La IA ha retirado el último palo y ha perdido.");
                } else {
                    System.out.println("\nEl JUGADOR " + numJugador + " ha retirado el último palo y HA PERDIDO.");
                }
                System.out.println("🎉 ¡ENHORABUENA AL RESTO DE JUGADORES! :) 🎉");

            }
        } else if (numPalos == 1) {
            System.out.print(" [Solo queda 1 palo]\n\n");
            if (numJugadores == 2) {
                if (esIA) {
                    System.out.println("La IA pierde.");
                    System.out.println("🎉 ¡ENHORABUENA, HAS GANADO A LA IA! :) 🎉");
                } else {
                    if (hayIA) {
                        System.out.println("GAME OVER, HAS PERDIDO :(.");
                    } else {
                        int contrario = (numJugador == 1) ? 2 : 1;
                        System.out.println("El jugador " + numJugador + " pierde.");
                        System.out.println("🎉 ¡ENHORABUENA AL JUGADOR " + contrario + "! :) 🎉");
                    }
                }
            } else {
                if (esIA) {
                    System.out.println("La IA pierde. ");
                } else {
                    System.out.println("El JUGADOR " + numJugador + " HA PERDIDO :(.");
                }
                System.out.println("🎉 ¡ENHORABUENA AL RESTO DE JUGADORES! :) 🎉");
            }
        }
        // Se ofrece al usuario la opción de reinicar partida
        if (reiniciarPartida()) {
            empezarPartida(); // Se vuelve a empezar la partida
        } else {
            System.out.println("Volviendo al menú...");
            j.menuPrincipal(); // Se vuelve al menú principal
        }
    }

    public boolean reiniciarPartida() {
        System.out.print("\nEscribe 'R' para reiniciar el juego (cualquier otra tecla para volver al menú):  ");
        String tecla = sc.next();
        if (tecla.equalsIgnoreCase("R")) {
            System.out.println("Reiniciando partida...");
            numPalos = numPalosInicial;
            return true;
        } else {
            return false;
        }
    }
}
