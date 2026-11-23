package Horario;

import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListaHorario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Nodo ini;
    public Nodo fin;

    public ListaHorario() {
        ini = fin = null;
    }

    public static final String[] TITULOS = {"Num", "AÑO", "GRADO", "SALON", "CURSO", "DNI DOCENTE", "DIA", "INICIO", "FIN"};

    public void MostrarLista(JTable tabla, ListaHorario lista) {
        DefaultTableModel mt = crearModelo();
        tabla.setModel(mt);
        int num = 0;
        Nodo aux = lista.ini;
        while (aux != null) {
            num++;
            mt.addRow(aux.hor.Registro(num));
            aux = aux.sig;
        }
    }

    public void MostrarHorarios(JTable tabla, Horario[] horarios) {
        DefaultTableModel mt = crearModelo();
        tabla.setModel(mt);
        for (int i = 0; i < horarios.length; i++) {
            mt.addRow(horarios[i].Registro(i + 1));
        }
    }

    private DefaultTableModel crearModelo() {
        return new DefaultTableModel(null, TITULOS) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    public void InsertarAlFinal(Nodo nuevo) {
        if (ini == null && fin == null) {
            ini = fin = nuevo;
        } else {
            fin.sig = nuevo;
        }
        fin = nuevo;
        fin.sig = null;
    }

    public Nodo BuscarHorario(String anio, String salon, String dia, String horaInicio) {
        Nodo aux = ini;
        while (aux != null && !(aux.hor.getAnio().equals(anio)
                && aux.hor.getCodigoSalon().equalsIgnoreCase(salon)
                && aux.hor.getDia().equals(dia)
                && aux.hor.getHoraInicio().equals(horaInicio))) {
            aux = aux.sig;
        }
        return aux;
    }

    public void EliminarHorario(Nodo actual) {
        if (actual == null || ini == null) {
            return;
        }
        if (ini == actual) {
            ini = actual.sig;
            if (ini == null) {
                fin = null;
            }
        } else {
            Nodo anterior = ini;
            while (anterior != null && anterior.sig != actual) {
                anterior = anterior.sig;
            }
            if (anterior != null) {
                anterior.sig = actual.sig;
                if (fin == actual) {
                    fin = anterior;
                }
            }
        }
    }
}
