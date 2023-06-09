package org.example;

public interface venta {
    static double calcular_iva(double precio){
        return precio * 0.21;
    };
    double calcular_iva(double precio, double iva);

}
