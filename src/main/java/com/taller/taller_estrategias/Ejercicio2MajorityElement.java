package com.taller.taller_estrategias;

// Ejercicio2MajorityElement.java
// Estrategia: Divide y Vencerás
// Fuente: https://www.geeksforgeeks.org/dsa/majority-element/
// Complejidad Tiempo: O(n log n) | Espacio: O(log n)

public class Ejercicio2MajorityElement {

    // Cuenta cuántas veces aparece 'candidate' en arr entre left y right
    static int countOccurrences(int[] arr, int left, int right, int candidate) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (arr[i] == candidate) count++;
        }
        return count;
    }

    // Encuentra el candidato a mayoritario en el subarreglo [left, right]
    static int findMajority(int[] arr, int left, int right) {
        // Caso base: un solo elemento es su propio candidato
        if (left == right) return arr[left];

        int mid = (left + right) / 2;

        // Busca candidatos en cada mitad
        int leftCandidate  = findMajority(arr, left, mid);
        int rightCandidate = findMajority(arr, mid + 1, right);

        // Si ambas mitades sugieren el mismo candidato → ese es el resultado
        if (leftCandidate == rightCandidate) return leftCandidate;

        // Si difieren → cuenta cuál aparece más en el subarreglo completo
        int leftCount  = countOccurrences(arr, left, right, leftCandidate);
        int rightCount = countOccurrences(arr, left, right, rightCandidate);

        return (leftCount > rightCount) ? leftCandidate : rightCandidate;
    }

    public static int majorityElement(int[] arr) {
        int n = arr.length;
        int candidate = findMajority(arr, 0, n - 1);

        // Verificación final: el candidato debe superar n/2
        int count = 0;
        for (int x : arr) if (x == candidate) count++;

        return (count > n / 2) ? candidate : -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Entrada: [2, 2, 1, 1, 1, 2, 2]");
        System.out.println("Salida:  " + majorityElement(arr));
        // Esperado: 2

        int[] arr2 = {1, 2, 3, 4};
        System.out.println("\nEntrada: [1, 2, 3, 4]");
        System.out.println("Salida:  " + majorityElement(arr2));
        // Esperado: -1 (ninguno supera n/2)
    }
}