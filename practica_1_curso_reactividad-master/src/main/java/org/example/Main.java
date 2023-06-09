package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.numeros;



public class Main {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        System.out.println("Primera practica curso reactividad.");
        System.out.println("ejercicio 1:");

        nums.add(10);
        nums.add(8);
        nums.add(12);

        numeros n = new numeros2();

        System.out.println("Mayor: " + n.mayor(nums));
        System.out.println("Menor: " + n.menor(nums));

        System.out.println("ejercicio 2: ");
        tienda t = (precio, descuento) -> precio - (precio * descuento / 100);
        System.out.println("Precio con descuento: " + t.precio_con_descuento(100, 10));

        System.out.println("ejercicio 3: ");
        venta v = (precio, iva) -> precio * (iva / 100.00);
        System.out.println("El iva variable  de la venta es: " + v.calcular_iva(2000, 15));

        System.out.println("ejercicio 3: ");
        System.out.println("El iva fijo de la venta es: " + venta.calcular_iva(2000));
    }

}