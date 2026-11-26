
package Docente;

import java.io.Serializable;

public class Nodo implements Serializable{
        public Docente doc;
    public Nodo sig;
    public Nodo(Docente doc){
        this.doc = doc;
        this.sig=null;
    }  
}
