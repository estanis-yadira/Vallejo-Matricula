
package Salon;

import java.io.Serializable;

public class ArregloSalones implements Serializable {

    private static final long serialVersionUID = 1L;

    private Salon[] salones;
    private int cantidad;

    public ArregloSalones() {
        salones = new Salon[10];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public Salon obtener(int indice) {
        if (indice >= 0 && indice < cantidad) {
            return salones[indice];
        }
        return null;
    }

    public Salon buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < cantidad; i++) {
            if (salones[i].getCodigo().equalsIgnoreCase(codigo.trim())) {
                return salones[i];
            }
        }

        return null;
    }

    public boolean agregar(Salon salon) {
        if (salon == null || salon.getCodigo() == null
                || salon.getCodigo().trim().isEmpty()) {
            return false;
        }

        if (buscarPorCodigo(salon.getCodigo()) != null) {
            return false;
        }

        asegurarCapacidad();
        salones[cantidad] = salon;
        cantidad++;

        return true;
    }

    public boolean actualizar(String codigo, Salon actualizado) {
        if (codigo == null || actualizado == null) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (salones[i].getCodigo().equalsIgnoreCase(codigo.trim())) {

                Salon existente = buscarPorCodigo(actualizado.getCodigo());

                if (existente != null && existente != salones[i]) {
                    return false;
                }

                salones[i] = actualizado;
                return true;
            }
        }

        return false;
    }

    public boolean eliminar(String codigo) {
        if (codigo == null) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            if (salones[i].getCodigo().equalsIgnoreCase(codigo.trim())) {

                for (int j = i; j < cantidad - 1; j++) {
                    salones[j] = salones[j + 1];
                }

                cantidad--;
                salones[cantidad] = null;

                return true;
            }
        }

        return false;
    }

    public Salon[] obtenerTodos() {
        Salon[] copia = new Salon[cantidad];

        for (int i = 0; i < cantidad; i++) {
            copia[i] = salones[i];
        }

        return copia;
    }

    private void asegurarCapacidad() {
        if (cantidad == salones.length) {
            Salon[] nuevo = new Salon[salones.length * 2];

            for (int i = 0; i < cantidad; i++) {
                nuevo[i] = salones[i];
            }

            salones = nuevo;
        }
    }
}
