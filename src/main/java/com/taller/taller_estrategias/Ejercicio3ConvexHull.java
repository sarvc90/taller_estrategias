package com.taller.taller_estrategias;

// Ejercicio3ConvexHull.java
// Estrategia: Algoritmo Voraz (Graham Scan)
// Fuente: https://www.geeksforgeeks.org/dsa/convex-hull-using-graham-scan/
// Complejidad Tiempo: O(n log n) | Espacio: O(n)

import java.util.*;

public class Ejercicio3ConvexHull {

    static class Punto {
        int x, y;
        Punto(int x, int y) { this.x = x; this.y = y; }

        @Override
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    // Calcula la orientación de tres puntos:
    // 0 = colineales, 1 = sentido horario, 2 = antihorario
    static int orientacion(Punto p, Punto q, Punto r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    // Distancia al cuadrado entre dos puntos (evita raíz cuadrada)
    static int distancia(Punto p, Punto q) {
        return (q.x - p.x) * (q.x - p.x) + (q.y - p.y) * (q.y - p.y);
    }

    static Punto pivote; // Punto más bajo (global para el comparador)

    public static List<Punto> grahamScan(Punto[] puntos) {
        int n = puntos.length;
        if (n < 3) return Arrays.asList(puntos);

        // PASO 1: Encontrar el punto más bajo (y si hay empate, el más a la izquierda)
        int minIdx = 0;
        for (int i = 1; i < n; i++) {
            if (puntos[i].y < puntos[minIdx].y ||
                    (puntos[i].y == puntos[minIdx].y && puntos[i].x < puntos[minIdx].x)) {
                minIdx = i;
            }
        }

        // Poner el pivote al inicio
        Punto temp = puntos[0]; puntos[0] = puntos[minIdx]; puntos[minIdx] = temp;
        pivote = puntos[0];

        // PASO 2: Ordenar los demás puntos por ángulo polar respecto al pivote
        // (decisión voraz: procesar de menor a mayor ángulo)
        Arrays.sort(puntos, 1, n, (a, b) -> {
            int ori = orientacion(pivote, a, b);
            if (ori == 0) return distancia(pivote, a) - distancia(pivote, b);
            return (ori == 2) ? -1 : 1;
        });

        // PASO 3: Construir el casco con una pila
        // Decisión voraz en cada paso: solo mantener giros a la izquierda
        Stack<Punto> pila = new Stack<>();
        pila.push(puntos[0]);
        pila.push(puntos[1]);
        pila.push(puntos[2]);

        for (int i = 3; i < n; i++) {
            // Mientras el giro sea horario (no antihorario), sacar de la pila
            while (pila.size() > 1 &&
                    orientacion(pila.get(pila.size()-2), pila.peek(), puntos[i]) != 2) {
                pila.pop();
            }
            pila.push(puntos[i]);
        }

        return new ArrayList<>(pila);
    }

    public static void main(String[] args) {
        Punto[] puntos = {
                new Punto(0,3), new Punto(1,1), new Punto(2,2),
                new Punto(4,4), new Punto(0,0), new Punto(1,2),
                new Punto(3,1), new Punto(3,3)
        };

        System.out.println("Puntos del casco convexo:");
        List<Punto> casco = grahamScan(puntos);
        for (Punto p : casco) System.out.print(p + " ");
        System.out.println();

        System.out.println("\nAplicaciones reales del Convex Hull:");
        System.out.println("1. GPS/Cartografía: calcular el área de regiones geográficas");
        System.out.println("2. Videojuegos: detección de colisiones entre objetos 2D");
    }
}