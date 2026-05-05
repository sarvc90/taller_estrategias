package com.taller.taller_estrategias;

// Ejercicio8Knapsack01.java
// Estrategia: Programación Dinámica (0/1 Knapsack)
// Fuente: https://www.geeksforgeeks.org/dsa/0-1-knapsack-problem-dp-10/
// Complejidad Tiempo: O(n×W) | Espacio: O(n×W), reducible a O(W)

public class Ejercicio8Knapsack01 {

    public static void knapsack(int[] pesos, int[] valores, int[] nombres, int W) {
        int n = pesos.length;

        // Tabla dp[i][w] = máximo valor usando los primeros i objetos con capacidad w
        int[][] dp = new int[n + 1][W + 1];

        // Llenar la tabla fila por fila (objeto por objeto)
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                // Opción 1: No tomar el objeto i
                dp[i][w] = dp[i-1][w];

                // Opción 2: Tomar el objeto i (solo si cabe)
                if (pesos[i-1] <= w) {
                    int conObjeto = valores[i-1] + dp[i-1][w - pesos[i-1]];
                    dp[i][w] = Math.max(dp[i][w], conObjeto);
                }
            }
        }

        // Mostrar la tabla dp
        System.out.println("Tabla DP (filas=objetos, columnas=capacidad 0 a " + W + "):");
        System.out.print("     ");
        for (int w = 0; w <= W; w++) System.out.printf("%3d", w);
        System.out.println();
        String[] etiquetas = {"A","B","C","D","E"};
        for (int i = 0; i <= n; i++) {
            System.out.printf("%-5s", i == 0 ? "base" : etiquetas[i-1]);
            for (int w = 0; w <= W; w++) System.out.printf("%3d", dp[i][w]);
            System.out.println();
        }

        System.out.println("\nValor máximo: " + dp[n][W]);

        // Reconstruir qué objetos se tomaron
        System.out.print("Objetos seleccionados: ");
        int w = W;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                System.out.print(etiquetas[i-1] + "(v=" + valores[i-1] + ",w=" + pesos[i-1] + ") ");
                w -= pesos[i-1];
            }
        }
        System.out.println();

        // Mostrar por qué falla lo voraz
        System.out.println("\n¿Por qué falla el enfoque voraz?");
        System.out.println("El voraz elegiría por mayor ratio v/w:");
        for (int i = 0; i < n; i++) {
            System.out.printf("  %s: ratio=%.2f%n", etiquetas[i], (double)valores[i]/pesos[i]);
        }
        System.out.println("Elegiría C(ratio=2.5) luego A(ratio=2.0) luego B(ratio=1.67)");
        System.out.println("Resultado voraz: C+A+B = w=4+1+3=8, v=10+2+5=17");
        System.out.println("Sin embargo, la solución óptima es B+D:");
        System.out.println("B+D = w=3+5=8, valor=5+14=19");
        System.out.println("DP encuentra esta combinación porque evalúa todas las posibilidades.");
    }

    public static void main(String[] args) {
        //        A   B   C   D   E
        int[] pesos  = {1,  3,  4,  5,  7};
        int[] valores= {2,  5, 10, 14, 15};
        int[] nombres= {0,  1,  2,  3,  4};
        int W = 8;

        System.out.println("Objetos: A(w=1,v=2) B(w=3,v=5) C(w=4,v=10) D(w=5,v=14) E(w=7,v=15)");
        System.out.println("Capacidad máxima: " + W);
        System.out.println();

        knapsack(pesos, valores, nombres, W);
    }
}