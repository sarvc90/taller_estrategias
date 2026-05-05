package com.taller.taller_estrategias;

// Ejercicio1SmallerElements.java
// Estrategia: Divide y Vencerás (Merge Sort modificado)
// Fuente: https://www.geeksforgeeks.org/dsa/count-smaller-elements-on-right-side/
// Complejidad Tiempo: O(n log n) | Espacio: O(n)

import java.util.Arrays;

public class Ejercicio1SmallerElements {

    // Este arreglo guarda el conteo de elementos menores para cada posición
    static int[] count;

    // Función principal que inicia el proceso
    public static int[] countSmaller(int[] arr) {
        int n = arr.length;
        count = new int[n];

        // Creamos un arreglo de índices para rastrear la posición original
        // de cada elemento mientras los ordenamos
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        mergeSort(arr, indices, 0, n - 1);
        return count;
    }

    // Divide el arreglo en dos mitades y las ordena recursivamente
    static void mergeSort(int[] arr, Integer[] indices, int left, int right) {
        if (left >= right) return; // Caso base: un solo elemento

        int mid = (left + right) / 2;
        mergeSort(arr, indices, left, mid);      // Ordena mitad izquierda
        mergeSort(arr, indices, mid + 1, right); // Ordena mitad derecha
        merge(arr, indices, left, mid, right);   // Combina y cuenta
    }

    // Mezcla las dos mitades y cuenta los elementos menores
    static void merge(int[] arr, Integer[] indices, int left, int mid, int right) {
        Integer[] temp = new Integer[right - left + 1];
        int i = left;       // Puntero mitad izquierda
        int j = mid + 1;    // Puntero mitad derecha
        int k = 0;          // Puntero del arreglo temporal
        int rightCount = 0; // Cuántos de la derecha ya pasaron al frente

        while (i <= mid && j <= right) {
            if (arr[indices[j]] < arr[indices[i]]) {
                // El elemento de la derecha es menor → cuenta para todos
                // los elementos restantes de la izquierda
                rightCount++;
                temp[k++] = indices[j++];
            } else {
                // El elemento de la izquierda va primero →
                // rightCount dice cuántos de la derecha ya lo superaron
                count[indices[i]] += rightCount;
                temp[k++] = indices[i++];
            }
        }

        // Procesa los elementos restantes de la izquierda
        while (i <= mid) {
            count[indices[i]] += rightCount;
            temp[k++] = indices[i++];
        }

        // Copia los restantes de la derecha
        while (j <= right) {
            temp[k++] = indices[j++];
        }

        // Copia el resultado temporal al arreglo de índices
        for (int x = 0; x < temp.length; x++) {
            indices[left + x] = temp[x];
        }
    }

    public static void main(String[] args) {
        // Prueba con el ejemplo del taller
        int[] arr = {5, 2, 6, 1, 3};
        System.out.println("Entrada: " + Arrays.toString(arr));

        int[] resultado = countSmaller(arr);
        System.out.println("Salida:  " + Arrays.toString(resultado));
        // Esperado: [3, 1, 2, 0, 0]
    }
}