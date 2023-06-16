package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int opcion = 0;

        System.out.println("Bienvenido a la sucursal xxx");
        Taquilla taquilla1 = new Taquilla("Asesorias");
        Taquilla taquilla2 = new Taquilla("Recaudos");
        Taquilla taquilla3 = new Taquilla("Divisas");
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        String nombre;

        while (opcion!= 7){
            System.out.println("Seleccione una opción");
            System.out.println("1. Pedir ficho de Asesorías");
            System.out.println("2. Pedir ficho de Recaudos");
            System.out.println("3. Pedir ficho de Divisas");
            System.out.println("4. Siguiente ficho de Asesorías");
            System.out.println("5. Siguiente ficho de Recaudos");
            System.out.println("6. Siguiente ficho de Divisas");
            System.out.println("7. Salir");
            opcion = sc2.nextInt();
            switch (opcion){
                case 1:
                    System.out.println("Ingrese nombre del cliente: ");
                    nombre = sc.nextLine();
                    taquilla1.agregarClienteEnCola(new Cliente(nombre, "Asesorias", taquilla1.getSiguienteFicho()));
                    break;
                case 2:
                    System.out.println("Ingrese nombre del cliente: ");
                    nombre = sc.nextLine();
                    taquilla2.agregarClienteEnCola(new Cliente(nombre, "Recaudos", taquilla2.getSiguienteFicho()));
                    break;
                case 3:
                    System.out.println("Ingrese nombre del cliente: ");
                    nombre = sc.nextLine();
                    taquilla3.agregarClienteEnCola(new Cliente(nombre, "Divisas", taquilla3.getSiguienteFicho()));
                    break;
                case 4:
                    taquilla1.siguienteCliente();
                    break;
                case 5:
                    taquilla2.siguienteCliente();
                    break;
                case 6:
                    taquilla3.siguienteCliente();
                    break;
                case 7:
                    System.out.println("Gracias por usar nuestros servicios");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        sc.close();
        sc2.close();
    }
}