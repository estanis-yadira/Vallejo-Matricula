package Estudiantes;

import java.io.Serializable;
import java.util.Arrays;

public class ArregloEstudiantes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Estudiante[] estudiantes;
    private int cantidad;

    public ArregloEstudiantes() {
        estudiantes = new Estudiante[10];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public Estudiante obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                "La posición indicada no existe."
            );
        }

        return estudiantes[indice];
    }

    public Estudiante buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.trim().equals(estudiantes[i].getDni())) {
                return estudiantes[i];
            }
        }

        return null;
    }

    public boolean agregar(Estudiante estudiante) {
        if (estudiante == null
                || estudiante.getDni() == null
                || estudiante.getDni().trim().isEmpty()) {
            return false;
        }

        if (buscarPorDni(estudiante.getDni()) != null) {
            return false;
        }

        asegurarCapacidad();
        estudiantes[cantidad] = estudiante;
        cantidad++;

        return true;
    }

    public boolean actualizar(String dni, Estudiante actualizado) {
        if (dni == null || actualizado == null
                || actualizado.getDni() == null
                || !dni.equals(actualizado.getDni())) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.equals(estudiantes[i].getDni())) {
                estudiantes[i] = actualizado;
                return true;
            }
        }

        return false;
    }

    public boolean eliminar(String dni) {
        if (dni == null) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (dni.equals(estudiantes[i].getDni())) {

                int elementosDesplazar = cantidad - i - 1;

                if (elementosDesplazar > 0) {
                    System.arraycopy(
                        estudiantes,
                        i + 1,
                        estudiantes,
                        i,
                        elementosDesplazar
                    );
                }

                cantidad--;
                estudiantes[cantidad] = null;

                return true;
            }
        }

        return false;
    }

    public Estudiante[] obtenerTodos() {
        return Arrays.copyOf(estudiantes, cantidad);
    }

    private void asegurarCapacidad() {
        if (cantidad == estudiantes.length) {
            estudiantes = Arrays.copyOf(
                estudiantes,
                estudiantes.length * 2
            );
        }
    }
}
