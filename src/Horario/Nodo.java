package Horario;

import java.io.Serializable;

public class Nodo implements Serializable {

    public Horario hor;
    public Nodo sig;

    public Nodo(Horario hor) {
        this.hor = hor;
        this.sig = null;
    }
}
