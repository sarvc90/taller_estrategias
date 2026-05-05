package com.taller.taller_estrategias;

// Ejercicio5MochilaFraccionaria.java
// Estrategia: Algoritmo Voraz
// Fuente: https://www.geeksforgeeks.org/dsa/fractional-knapsack-problem/
// Complejidad Tiempo: O(n log n) | Espacio: O(1)

import java.util.Arrays;

public class Ejercicio5MochilaFraccionaria {

    static class Objeto {
        int cantidad, peso, valor;
        double ratio; // valor / peso

        Objeto(int cantidad, int peso, int valor) {
            this.cantidad = cantidad;
            this.peso = peso;
            this.valor = valor;
            this.ratio = (double) valor / peso;
        }
    }

    // Calcula el valor total usando una heurística (orden ya definido)
    static double calcularValor(Objeto[] objetos, double capacidad) {
        double valorTotal = 0;
        double restante = capacidad;

        for (Objeto obj : objetos) {
            if (restante <= 0) break;

            // Peso total disponible de este objeto (cantidad * peso)
            double pesoDisponible = (double) obj.cantidad * obj.peso;
            double valorDisponible = (double) obj.cantidad * obj.valor;

            if (pesoDisponible <= restante) {
                // Cabe todo el objeto completo
                valorTotal += valorDisponible;
                restante -= pesoDisponible;
            } else {
                // Solo cabe una fracción
                double fraccion = restante / pesoDisponible;
                valorTotal += fraccion * valorDisponible;
                restante = 0;
            }
        }

        return valorTotal;
    }

    public static void main(String[] args) {
        double capacidad = 520;

        // Datos del taller:
        // Objeto: 1(q=3,w=210,v=15), 2(q=2,w=230,v=50),
        //         3(q=4,w=150,v=20), 4(q=5,w=40,v=55), 5(q=1,w=500,v=300)
        Objeto[] objetos = {
                new Objeto(3, 210, 15),
                new Objeto(2, 230, 50),
                new Objeto(4, 150, 20),
                new Objeto(5,  40, 55),
                new Objeto(1, 500, 300)
        };

        // ── HEURÍSTICA 1: Mayor valor primero ──
        Objeto[] h1 = objetos.clone();
        Arrays.sort(h1, (a, b) -> b.valor - a.valor);
        double v1 = calcularValor(h1, capacidad);
        System.out.printf("Heurística 1 (Mayor valor):       %.2f%n", v1);

        // ── HEURÍSTICA 2: Menor peso primero ──
        Objeto[] h2 = objetos.clone();
        Arrays.sort(h2, (a, b) -> a.peso - b.peso);
        double v2 = calcularValor(h2, capacidad);
        System.out.printf("Heurística 2 (Menor peso):        %.2f%n", v2);

        // ── HEURÍSTICA 3: Mayor relación valor/peso ──
        Objeto[] h3 = objetos.clone();
        Arrays.sort(h3, (a, b) -> Double.compare(b.ratio, a.ratio));
        double v3 = calcularValor(h3, capacidad);
        System.out.printf("Heurística 3 (Mayor ratio v/w):   %.2f%n", v3);

        System.out.println("\n✅ La heurística óptima es: Mayor relación valor/peso");
        System.out.println("   Porque maximiza el valor obtenido por cada unidad de peso.");
        System.out.println("   Las otras pueden desperdiciar capacidad con objetos pesados");
        System.out.println("   o de poco valor.");
    }
}