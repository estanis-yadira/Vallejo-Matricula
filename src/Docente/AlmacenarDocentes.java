/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class AlmacenarDocentes {
    public static String archivo = "DatosdelDocente.bin";

    public static void Guardar(ListaDocente lista) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(archivo));
            os.writeObject(lista);
            os.close();
        } catch (Exception ex) {
            Mensaje.Mostrar("ERROR no se puede guardar " + ex);
        }
    }

    public static ListaDocente Recuperar() {
        ListaDocente lista = new ListaDocente();
        File f = new File(archivo);
        if (!f.exists()) {
            return lista;   // primera ejecución: lista vacía, sin error
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            lista = (ListaDocente) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Mensaje.Mostrar("ERROR no se puede recuperar " + ex);
        }
        return lista;
    }
}
