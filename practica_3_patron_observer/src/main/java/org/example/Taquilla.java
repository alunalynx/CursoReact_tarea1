package org.example;

import java.util.ArrayList;

public class Taquilla extends Cola {

    private String servicio;
    private int fichoActivo;
    private int siguienteFicho;

    public Taquilla(String servicio){
        this.servicio = servicio;
        System.out.println("Nueva taquilla de " + servicio + " activada");
        observadores = new ArrayList<>();
        fichoActivo = 0;
        siguienteFicho = 0;
    }

    public String getServicio(){
        return servicio;
    }

    public void siguienteCliente(){
        fichoActivo++;
        notificar();
        borrarPorFicho(fichoActivo);
    }

    public int getSiguienteFicho(){
        siguienteFicho++;
        return siguienteFicho;
    }

    private void borrarPorFicho(int ficho){
        this.borrarClienteEnCola(observadores.stream()
                .filter(observador -> ((Cliente)observador).getFicho() == ficho)
                .findFirst()
                .get());
    }

    @Override
    public void notificar() {
        observadores.forEach((observador) -> {
            observador.actualizar(fichoActivo);
        });
    }
}
