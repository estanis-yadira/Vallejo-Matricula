
package Modelo;

import Docente.Mensaje;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author milag
 */
public class AlmacenarMatriculas {
    private static final String ARCHIVO = "DatosMatricula.bin";

    public static void Guardar(ArregloMatriculas arreglo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {
            salida.writeObject(arreglo);
        } catch (IOException e) {
            Mensaje.Mostrar("Error al guardar matrículas: " + e.getMessage());
        }
    }

    public static ArregloMatriculas Recuperar() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            return new ArregloMatriculas();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(f))) {
            return (ArregloMatriculas) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArregloMatriculas();
        }
    }
}
