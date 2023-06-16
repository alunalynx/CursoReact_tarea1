package org.example;

public class Cliente implements Observador{
    private String nombre;
    private String servicio;
    private int ficho;

    public Cliente(String nombre, String servicio, int ficho){
        this.nombre = nombre;
        this.servicio = servicio;
        this.ficho = ficho;
    }

    public String getNombre(){
        return nombre;
    }

    public String getServicio(){
        return servicio;
    }

    public int getFicho(){
        return ficho;
    }

    @Override
    public void actualizar(int siguienteFicho) {
        System.out.println(nombre + ", el siguiente cliente en ser atendido es el número: " + siguienteFicho + " tu ficho es: " + ficho);
        if (siguienteFicho == ficho){
            System.out.println("Bienvenido " + nombre + " es su turno");
        }
    }

    public String toString(){
        return "Cliente: " + nombre + " Servicio: " + servicio + " Ficho: " + ficho;
    }
}
