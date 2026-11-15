
package Cursos;

import java.io.Serializable;

public class ArregloCursos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Curso[] cursos;
    private int cantidad;

    public ArregloCursos() {
        cursos = new Curso[10];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public Curso obtener(int indice) {
        if (indice >= 0 && indice < cantidad) {
            return cursos[indice];
        }
        return null;
    }

    public Curso buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidad; i++) {
            if (cursos[i].getCodigo().equalsIgnoreCase(codigo.trim())) {
                return cursos[i];
            }
        }

        return null;
    }

    public boolean agregar(Curso curso) {
        if (curso == null
                || curso.getCodigo() == null
                || curso.getCodigo().trim().isEmpty()
                || curso.getNombre() == null
                || curso.getNombre().trim().isEmpty()) {
            return false;
        }

        if (buscarPorCodigo(curso.getCodigo()) != null) {
            return false;
        }

        asegurarCapacidad();
        cursos[cantidad] = curso;
        cantidad++;

        return true;
    }

    public boolean actualizar(String codigo, Curso actualizado) {
        if (codigo == null || actualizado == null) {
            return false;
        }

        Curso existente = buscarPorCodigo(codigo);

        if (existente == null) {
            return false;
        }

        existente.setNombre(actualizado.getNombre());
        existente.setGrado(actualizado.getGrado());
        existente.setHorasSemanales(actualizado.getHorasSemanales());
        existente.setDniDocente(actualizado.getDniDocente());

        return true;
    }

    public boolean eliminar(String codigo) {
        if (codigo == null) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (cursos[i].getCodigo().equalsIgnoreCase(codigo.trim())) {

                for (int j = i; j < cantidad - 1; j++) {
                    cursos[j] = cursos[j + 1];
                }

                cantidad--;
                cursos[cantidad] = null;

                return true;
            }
        }

        return false;
    }

    public Curso[] obtenerTodos() {
        Curso[] resultado = new Curso[cantidad];

        for (int i = 0; i < cantidad; i++) {
            resultado[i] = cursos[i];
        }

        return resultado;
    }

    private void asegurarCapacidad() {
        if (cantidad == cursos.length) {
            Curso[] nuevoArreglo = new Curso[cursos.length * 2];

            for (int i = 0; i < cantidad; i++) {
                nuevoArreglo[i] = cursos[i];
            }

            cursos = nuevoArreglo;
        }
    }
}
