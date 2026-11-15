package Estudiantes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class AlmacenarEstudiantes {

    private static final String ARCHIVO = "DatosdelEstudiante.bin";

    public static void Guardar(ArregloEstudiantes arreglo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {

            salida.writeObject(arreglo);

        } catch (IOException e) {
            System.out.println("Error al guardar estudiantes: "
                    + e.getMessage());
        }
    }

    public static ArregloEstudiantes Recuperar() {
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(ARCHIVO))) {

            return (ArregloEstudiantes) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArregloEstudiantes();
        }
    }
}

