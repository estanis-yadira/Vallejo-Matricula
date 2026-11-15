
package Salon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class AlmacenarSalones {

    private static final String ARCHIVO = "DatosdelSalon.bin";

    public static void Guardar(ArregloSalones arreglo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {

            salida.writeObject(arreglo);

        } catch (IOException e) {
            System.out.println("Error al guardar los salones: "
                    + e.getMessage());
        }
    }

    public static ArregloSalones Recuperar() {
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(ARCHIVO))) {

            return (ArregloSalones) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArregloSalones();
        }
    }
}
