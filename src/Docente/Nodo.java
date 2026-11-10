/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
