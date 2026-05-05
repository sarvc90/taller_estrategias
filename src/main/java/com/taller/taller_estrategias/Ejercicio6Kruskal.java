package com.taller.taller_estrategias;

// Ejercicio6Kruskal.java
// Estrategia: Algoritmo Voraz (Kruskal - MST)
// Fuente: https://www.geeksforgeeks.org/dsa/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
// Complejidad Tiempo: O(E log E) | Espacio: O(V)

import java.util.*;

public class Ejercicio6Kruskal {

    static class Arista implements Comparable<Arista> {
        int origen, destino, peso;

        Arista(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        // Ordenar aristas de menor a mayor peso (decisión voraz)
        @Override
        public int compareTo(Arista otra) {
            return this.peso - otra.peso;
        }
    }

    // Union-Find: estructura para detectar ciclos eficientemente
    static int[] padre, rango;

    static int find(int x) {
        // Compresión de caminos: hace el árbol más plano
        if (padre[x] != x) padre[x] = find(padre[x]);
        return padre[x];
    }

    static boolean union(int x, int y) {
        int raizX = find(x), raizY = find(y);
        if (raizX == raizY) return false; // Ya están conectados → ciclo

        // Une por rango (el árbol más alto absorbe al más bajo)
        if (rango[raizX] < rango[raizY]) padre[raizX] = raizY;
        else if (rango[raizX] > rango[raizY]) padre[raizY] = raizX;
        else { padre[raizY] = raizX; rango[raizX]++; }
        return true;
    }

    public static void kruskal(int numMunicipios, List<Arista> aristas) {
        // Inicializar Union-Find
        padre = new int[numMunicipios];
        rango  = new int[numMunicipios];
        for (int i = 0; i < numMunicipios; i++) padre[i] = i;

        // PASO 1: Ordenar todas las aristas por costo (decisión voraz)
        Collections.sort(aristas);

        List<Arista> mst = new ArrayList<>();
        long costoTotal = 0;

        // PASO 2: Agregar aristas que no formen ciclo
        for (Arista a : aristas) {
            if (union(a.origen, a.destino)) {
                mst.add(a);
                costoTotal += a.peso;
                if (mst.size() == numMunicipios - 1) break; // Ya conectamos todos
            }
        }

        // Mostrar resultado
        System.out.println("Árbol de Expansión Mínima (Red de Fibra Óptica):");
        System.out.println("─────────────────────────────────────────────────");
        String[] nombres = {"Armenia","Calarcá","Montenegro","Quimbaya","La Tebaida"};
        for (Arista a : mst) {
            System.out.printf("  %s ─── %s : $%,d pesos%n",
                    nombres[a.origen], nombres[a.destino], a.peso);
        }
        System.out.printf("─────────────────────────────────────────────────%n");
        System.out.printf("Costo total mínimo: $%,d pesos%n", costoTotal);
    }

    public static void main(String[] args) {
        // Ejemplo: 5 municipios del Quindío
        // 0=Armenia, 1=Calarcá, 2=Montenegro, 3=Quimbaya, 4=La Tebaida
        int municipios = 5;

        List<Arista> aristas = new ArrayList<>(Arrays.asList(
                new Arista(0, 1, 1_000_000),   // Armenia-Calarcá
                new Arista(1, 2, 2_000_000),   // Calarcá-Montenegro
                new Arista(3, 4, 2_000_000),   // Quimbaya-La Tebaida
                new Arista(0, 2, 3_000_000),   // Armenia-Montenegro
                new Arista(1, 3, 4_000_000),   // Calarcá-Quimbaya
                new Arista(2, 3, 5_000_000),   // Montenegro-Quimbaya
                new Arista(2, 4, 6_000_000)    // Montenegro-La Tebaida
        ));

        kruskal(municipios, aristas);
    }
}