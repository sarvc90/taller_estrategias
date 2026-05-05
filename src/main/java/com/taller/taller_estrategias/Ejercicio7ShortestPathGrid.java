package com.taller.taller_estrategias;

// Ejercicio7ShortestPathGrid.java
// Estrategia: Programación Dinámica
// Fuente: https://www.geeksforgeeks.org/dsa/min-cost-path-dp-6/
// Complejidad Tiempo: O(m*n) | Espacio: O(n)

public class Ejercicio7ShortestPathGrid {

    public static int minCostPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // dp[j] almacena el costo mínimo para llegar
        // a la columna j de la fila actual
        int[] dp = new int[n];

        // Primera fila:
        // solo se puede llegar desde la izquierda
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        // Procesar fila por fila
        for (int i = 1; i < m; i++) {
            int[] nuevaFila = new int[n];

            // Primera columna:
            // solo se puede llegar desde arriba
            nuevaFila[0] = dp[0] + grid[i][0];

            for (int j = 1; j < n; j++) {

                // Opciones válidas:
                int desdeArriba = dp[j];           // dp[i-1][j]
                int desdeIzquierda = nuevaFila[j-1]; // dp[i][j-1]
                int desdeDiagonal = dp[j-1];       // dp[i-1][j-1]

                // Relación de recurrencia:
                // tomar el menor costo de las tres opciones
                nuevaFila[j] = grid[i][j] + Math.min(
                        desdeArriba,
                        Math.min(desdeIzquierda, desdeDiagonal)
                );
            }

            // Actualizar fila actual
            dp = nuevaFila;
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {

        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        System.out.println("Matriz de costos:");
        for (int[] fila : grid) {
            for (int valor : fila) {
                System.out.printf("%3d", valor);
            }
            System.out.println();
        }

        int resultado = minCostPath(grid);

        System.out.println("\nCosto mínimo desde (0,0) hasta (m-1,n-1): " + resultado);

        // Ruta óptima posible usando diagonal:
        // (0,0) → (1,0) → (2,1) → (2,2)
        // costo = 1 + 1 + 2 + 1 = 5

        System.out.println("\nRelación de recurrencia:");
        System.out.println(
                "dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])"
        );

        System.out.println("\nOptimización:");
        System.out.println("Solo se almacena una fila a la vez -> O(n) espacio");
    }
}