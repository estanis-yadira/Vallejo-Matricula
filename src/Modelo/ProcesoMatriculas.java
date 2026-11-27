
package Modelo;

import Estudiantes.Estudiante;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author milag
 */
public class ProcesoMatriculas {
    public static final String[] TITULOS_MATRICULA = {"N.º", "Código", "DNI Estudiante", "Año Lectivo","Grado", "Salón", "Fecha", "Estado"};

    public static final String[] TITULOS_REPORTE = {"N.º", "DNI", "Estudiante", "Apoderado", "Teléfono","Año Lectivo", "Grado", "Salón", "Fecha Matrícula"};

    public static String generarCodigo(ArregloMatriculas arreglo, String anioLectivo) {
        int correlativo = arreglo.getCantidad() + 1;
        String codigo;
        do {
            codigo = String.format("MAT-%s-%03d", anioLectivo.trim(), correlativo);
            correlativo++;
        } while (arreglo.buscarPorCodigo(codigo) != null);
        return codigo;
    }

    public static String nombreCompletoEstudiante(Estudiante est) {
        if (est == null) {
            return "";
        }
        return unirNoVacio(est.getNombre(), est.getApellidoP(), est.getApellidoM());
    }

    public static String nombreCompletoApoderado(Estudiante est) {
        if (est == null) {
            return "";
        }
        return unirNoVacio(
                est.getNombreApoderado(),
                est.getApellidoPApoderado(),
                est.getApellidoMApoderado());
    }

    public static void mostrarMatriculas(JTable tabla, Matricula[] lista) {
        DefaultTableModel modelo = new DefaultTableModel(null, TITULOS_MATRICULA) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modelo);
        for (int i = 0; i < lista.length; i++) {
            modelo.addRow(lista[i].RegistroMatricula(i + 1));
        }
    }

    public static void mostrarReporte(JTable tabla, ArregloMatriculas arreglo,
            Estudiantes.ArregloEstudiantes estudiantes,
            String anioLectivo, String grado, String codigoSalon) {
        DefaultTableModel modelo = new DefaultTableModel(null, TITULOS_REPORTE) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modelo);

        Matricula[] filtradas = arreglo.filtrar(anioLectivo, grado, codigoSalon);
        for (int i = 0; i < filtradas.length; i++) {
            Matricula m = filtradas[i];
            Estudiante est = estudiantes.buscarPorDni(m.getDniEstudiante());
            Object[] fila = {
                i + 1,
                m.getDniEstudiante(),
                est != null ? nombreCompletoEstudiante(est) : "No registrado",
                est != null ? nombreCompletoApoderado(est) : "-",
                est != null ? est.getTelefonoApoderado() : "-",
                m.getAñoLectivo(),
                m.getGrado(),
                m.getCodigoSalon(),
                m.getFechaMatricula()
            };
            modelo.addRow(fila);
        }
    }

    private static String unirNoVacio(String a, String b, String c) {
        StringBuilder sb = new StringBuilder();
        if (a != null && !a.trim().isEmpty()) {
            sb.append(a.trim());
        }
        if (b != null && !b.trim().isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(b.trim());
        }
        if (c != null && !c.trim().isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(c.trim());
        }
        return sb.toString();
    }
}
