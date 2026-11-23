package Horario;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;

public class ArregloHorarios implements Serializable {

    private static final long serialVersionUID = 1L;

    private Horario[] horarios;
    private int cantidad;

    public ArregloHorarios() {
        horarios = new Horario[10];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    public Horario obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("La posición indicada no existe.");
        }
        return horarios[indice];
    }

    public Horario[] buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return new Horario[0];
        }
        Horario[] temp = new Horario[cantidad];
        int n = 0;
        for (int i = 0; i < cantidad; i++) {
            if (dni.trim().equals(horarios[i].getDniDocente())) {
                temp[n++] = horarios[i];
            }
        }
        return Arrays.copyOf(temp, n);
    }

    public boolean agregar(Horario horario) {
        if (horario == null) {
            return false;
        }
        asegurarCapacidad();
        horarios[cantidad] = horario;
        cantidad++;
        return true;
    }

    public boolean eliminar(Horario horario) {
        for (int i = 0; i < cantidad; i++) {
            if (horarios[i] == horario) {
                int elementosDesplazar = cantidad - i - 1;
                if (elementosDesplazar > 0) {
                    System.arraycopy(horarios, i + 1, horarios, i, elementosDesplazar);
                }
                cantidad--;
                horarios[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    public Horario[] obtenerTodos() {
        return Arrays.copyOf(horarios, cantidad);
    }

    public String motivoCruce(Horario nuevo, Horario ignorar) {
        LocalTime ini = LocalTime.parse(nuevo.getHoraInicio());
        LocalTime fin = LocalTime.parse(nuevo.getHoraFin());

        for (int i = 0; i < cantidad; i++) {
            Horario h = horarios[i];
            if (h == ignorar) {
                continue;
            }
            if (!h.getAnio().equals(nuevo.getAnio()) || !h.getDia().equals(nuevo.getDia())) {
                continue;
            }

            LocalTime hIni = LocalTime.parse(h.getHoraInicio());
            LocalTime hFin = LocalTime.parse(h.getHoraFin());
            boolean seSolapan = ini.isBefore(hFin) && hIni.isBefore(fin);
            if (!seSolapan) {
                continue;
            }

            if (h.getCodigoSalon().equalsIgnoreCase(nuevo.getCodigoSalon())) {
                return "El salón " + nuevo.getCodigoSalon() + " ya está ocupado el " + h.getDia()
                        + " de " + h.getHoraInicio() + " a " + h.getHoraFin() + ".";
            }
            if (h.getDniDocente().equals(nuevo.getDniDocente())) {
                return "El docente con DNI " + nuevo.getDniDocente() + " ya tiene clase el " + h.getDia()
                        + " de " + h.getHoraInicio() + " a " + h.getHoraFin() + ".";
            }
        }
        return null;
    }

    private void asegurarCapacidad() {
        if (cantidad == horarios.length) {
            int nuevaCapacidad = horarios.length * 2;
            if (nuevaCapacidad == 0) {
                nuevaCapacidad = 10;
            }
            horarios = Arrays.copyOf(horarios, nuevaCapacidad);
        }
    }
}
