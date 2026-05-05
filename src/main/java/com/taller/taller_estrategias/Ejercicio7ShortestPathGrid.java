package com.taller.taller_estrategias;

// Ejercicio7ShortestPathGrid.java
// Estrategia: Programación Dinámica
// Fuente: https://www.geeksforgeeks.org/dsa/min-cost-path-dp-6/
// Complejidad Tiempo: O(m×n) | Espacio: O(n) con optimización

public class Ejercicio7ShortestPathGrid {

    public static int minCostPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Usamos solo una fila (optimización de espacio O(n))
        // 'dp[j]' = costo mínimo para llegar a la columna j de la fila actual
        int[] dp = new int[n];

        // Inicializar primera fila: solo se puede llegar desde la izquierda
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j-1] + grid[0][j];
        }

        // Llenar fila por fila
        for (int i = 1; i < m; i++) {
            int[] nuevaFila = new int[n];

            // Primera columna: solo se puede llegar desde arriba
            nuevaFila[0] = dp[0] + grid[i][0];

            for (int j = 1; j < n; j++) {
                // Relación de recurrencia:
                // dp[i][j] = grid[i][j] + min(desde arriba, desde izquierda, diagonal)
                int desdeArriba    = dp[j];           // dp anterior en misma columna
                int desdeIzquierda = nuevaFila[j-1];  // columna anterior fila actual
                int desdeDiagonal  = dp[j-1];         // dp anterior columna anterior

                nuevaFila[j] = grid[i][j] + Math.min(desdeArriba,
                        Math.min(desdeIzquierda, desdeDiagonal));
            }
            dp = nuevaFila;
        }

        return dp[n-1];
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        System.out.println("Grid:");
        for (int[] fila : grid) {
            for (int v : fila) System.out.printf("%3d", v);
            System.out.println();
        }

        int resultado = minCostPath(grid);
        System.out.println("\nCosto mínimo desde (0,0) hasta (m-1,n-1): " + resultado);
        // Esperado: 7 → camino: (0,0)→(0,1)→(0,2)→(1,2)→(2,2) = 1+3+1+1+1 = 7

        System.out.println("\nRelación de recurrencia:");
        System.out.println("dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])");
        System.out.println("Optimización de espacio: solo se guarda la fila anterior → O(n)");
    }
}