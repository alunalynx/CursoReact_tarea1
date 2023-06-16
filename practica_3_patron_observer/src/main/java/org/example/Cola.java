package org.example;

import java.util.ArrayList;

public abstract class Cola {

    protected ArrayList<Observador> observadores;

    public void agregarClienteEnCola(Cliente cliente){
        observadores.add(cliente);
    }

    public void borrarClienteEnCola(Observador observador){
        observadores.remove(observador);
    }

    public abstract void notificar();

    public ArrayList<Observador> getObservadores() {
        return observadores;
    }
}
