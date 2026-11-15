package Docente;

import java.io.Serializable;
import java.util.Arrays;

public class ArregloDocentes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Docente[] docentes;
    private int cantidad;

    public ArregloDocentes() {
        docentes = new Docente[10];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public Docente obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                "La posición indicada no existe."
            );
        }

        return docentes[indice];
    }

    public Docente buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.trim().equals(docentes[i].getDni())) {
                return docentes[i];
            }
        }

        return null;
    }

    public boolean agregar(Docente docente) {
        if (docente == null
                || docente.getDni() == null
                || docente.getDni().trim().isEmpty()) {
            return false;
        }

        if (buscarPorDni(docente.getDni()) != null) {
            return false;
        }

        asegurarCapacidad();
        docentes[cantidad] = docente;
        cantidad++;

        return true;
    }

    /**
     * Actualiza los datos de un docente identificado por su DNI.
     * El DNI del objeto recibido debe ser el mismo.
     */
    public boolean actualizar(String dni, Docente actualizado) {
        if (dni == null || actualizado == null
                || actualizado.getDni() == null
                || !dni.equals(actualizado.getDni())) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.equals(docentes[i].getDni())) {
                docentes[i] = actualizado;
                return true;
            }
        }

        return false;
    }

    /**
     * Elimina un docente y desplaza los elementos restantes.
     */
    public boolean eliminar(String dni) {
        if (dni == null) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.equals(docentes[i].getDni())) {

                int elementosDesplazar = cantidad - i - 1;

                if (elementosDesplazar > 0) {
                    System.arraycopy(
                        docentes,
                        i + 1,
                        docentes,
                        i,
                        elementosDesplazar
                    );
                }

                cantidad--;
                docentes[cantidad] = null;

                return true;
            }
        }

        return false;
    }

    /**
     * Devuelve una copia del arreglo con los docentes registrados.
     */
    public Docente[] obtenerTodos() {
        return Arrays.copyOf(docentes, cantidad);
    }

    /**
     * Amplía el arreglo cuando ya no tiene espacio disponible.
     */
    private void asegurarCapacidad() {
        if (cantidad == docentes.length) {
            int nuevaCapacidad = docentes.length * 2;

            if (nuevaCapacidad == 0) {
                nuevaCapacidad = 10;
            }

            docentes = Arrays.copyOf(docentes, nuevaCapacidad);
        }
    }
}
