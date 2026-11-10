/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import static Docente.AlmacenarDocentes.archivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListaDocente implements Serializable{
    public Nodo ini;
    public Nodo fin;
    public ListaDocente(){
        ini=fin=null;
    }

 public void MostrarLista(JTable tabla, ListaDocente lista){
        String titulos[]={"Num","DNI","NOMBRE","APELLIDO P.","APELLIDO M.", "ESPECIALIDAD", "CORREO", "TELEFONO", "DIRECCION"};
        DefaultTableModel mt = new DefaultTableModel(null,titulos);
        tabla.setModel(mt);
        int num=0;
        Nodo aux=lista.ini;
        while(aux!=null){
            num++;
            mt.addRow(aux.doc.Registro(num));
            aux=aux.sig;
        }
    } 
 
  public void InsertarAlFinal(Nodo nuevo){
        if(ini==null && fin==null){
            ini=fin=nuevo;
        }else{
            fin.sig=nuevo;
        }
        fin=nuevo;
        fin.sig=null;
    }
 
    public Nodo BuscarDocente(String dniBuscado){
        Nodo aux=ini;
        while(aux!=null && !dniBuscado.equals(aux.doc.getDni())){
            aux=aux.sig;
        }
        return aux;
    }//fin buscar
    
   
    public void EliminarDocente(Nodo actual) {
    if (actual == null || ini == null) return;
    if (ini == actual) {
        ini = actual.sig;
        if (ini == null) fin = null;
    } else {
        Nodo anterior = ini;
        while (anterior != null && anterior.sig != actual) {
            anterior = anterior.sig;
        }
        if (anterior != null) {
            anterior.sig = actual.sig;
            if (fin == actual) fin = anterior;
        }
    }
}//fin eliminar
    
    
}

