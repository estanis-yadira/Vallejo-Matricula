package Horario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AlmacenarHorarios {

    public static String archivo = "DatosdelHorario.bin";

    public static void Guardar(ListaHorario lista) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(archivo));
            os.writeObject(lista);
            os.close();
        } catch (Exception ex) {
            Mensaje.Mostrar("ERROR no se puede guardar " + ex);
        }
    }

    public static ListaHorario Recuperar() {
        ListaHorario lista = new ListaHorario();
        File f = new File(archivo);
        if (!f.exists()) {
            return lista;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            lista = (ListaHorario) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            Mensaje.Mostrar("ERROR no se puede recuperar " + ex);
        }
        return lista;
    }
}
