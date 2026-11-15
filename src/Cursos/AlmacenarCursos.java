package Cursos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class AlmacenarCursos {

    private static final String ARCHIVO = "DatosdelCurso.bin";

    public static void Guardar(ArregloCursos arreglo) {
        try (ObjectOutputStream salida =
                new ObjectOutputStream(
                        new FileOutputStream(ARCHIVO))) {

            salida.writeObject(arreglo);

        } catch (IOException e) {
            System.out.println("Error al guardar los cursos: "
                    + e.getMessage());
        }
    }

    public static ArregloCursos Recuperar() {
        try (ObjectInputStream entrada =
                new ObjectInputStream(
                        new FileInputStream(ARCHIVO))) {

            return (ArregloCursos) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArregloCursos();
        }
    }
}
