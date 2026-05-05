package com.taller.taller_estrategias;

// Ejercicio4Devuelta.java
// Estrategia: Algoritmo Voraz
// Fuente: https://www.geeksforgeeks.org/dsa/find-minimum-number-of-coins-that-make-a-change/
// Complejidad Tiempo: O(D) donde D = número de denominaciones | Espacio: O(1)

public class Ejercicio4Devuelta {

    public static void calcularDevuelta(int monto, int[] denominaciones, int[] disponibilidad) {
        System.out.println("Monto a devolver: $" + monto);
        System.out.println("─────────────────────────────");

        int montoRestante = monto;
        int totalBilletes = 0;
        boolean posible = true;

        // PASO 1: Las denominaciones ya vienen de mayor a menor
        // Decisión voraz: siempre usar el billete más grande posible
        for (int i = denominaciones.length - 1; i >= 0; i--) {
            if (montoRestante == 0) break;

            // ¿Cuántos billetes de esta denominación necesito?
            int necesito = montoRestante / denominaciones[i];

            // No puedo usar más de los que hay disponibles
            int usar = Math.min(necesito, disponibilidad[i]);

            if (usar > 0) {
                System.out.println("Billetes de $" + denominaciones[i] + ": usar " + usar);
                montoRestante -= usar * denominaciones[i];
                totalBilletes += usar;
            }
        }

        System.out.println("─────────────────────────────");
        if (montoRestante == 0) {
            System.out.println("✅ Total de billetes usados: " + totalBilletes);
        } else {
            System.out.println("❌ No es posible dar el vuelto exacto.");
            System.out.println("   Falta: $" + montoRestante);
            System.out.println("   (El enfoque voraz falla cuando no hay billetes suficientes");
            System.out.println("    de las denominaciones necesarias. En ese caso se");
            System.out.println("    necesitaría Programación Dinámica para explorar");
            System.out.println("    todas las combinaciones posibles.)");
        }
    }

    public static void main(String[] args) {
        // Ejemplo del taller
        int[] denominaciones  = {10000, 20000, 50000, 100000};
        int[] disponibilidad  = {3, 2, 1, 5};

        System.out.println("=== PRUEBA 1: Monto posible ===");
        calcularDevuelta(180000, denominaciones, disponibilidad);

        System.out.println("\n=== PRUEBA 2: Monto imposible ===");
        calcularDevuelta(90000, new int[]{10000,20000,50000,100000},
                new int[]{0, 0, 0, 5});
        // Solo hay billetes de 100k → no se puede dar 90k exactos
    }
}