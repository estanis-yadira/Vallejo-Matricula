
package Controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.*;
import Estudiantes.*;
import Vistas.Paneles.*;

/**
 *
 * @author milag
 */
public class ControladorReportes implements ActionListener {
    private final Reportes vista;
    private ArregloMatriculas arregloMatriculas;
    private ArregloEstudiantes arregloEstudiantes;

    public ControladorReportes(Reportes vista) {
        this.vista = vista;
        recargarDatos();

        this.vista.btnConsulta.addActionListener(this);
        cargarReporte(false);
    }

    public void recargarDatos() {
        arregloMatriculas = AlmacenarMatriculas.Recuperar();
        arregloEstudiantes = AlmacenarEstudiantes.Recuperar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnConsulta) {
            cargarReporte(true);
        }
    }

    public void cargarReporte(boolean mostrarAvisoSiVacio) {
        recargarDatos();

        String anio = vista.cbAñoLectivo.getSelectedItem() != null
                ? vista.cbAñoLectivo.getSelectedItem().toString().trim() : "Todos";
        String grado = vista.cbGrado.getSelectedItem() != null
                ? vista.cbGrado.getSelectedItem().toString().trim() : "Todos";
        String salon = vista.txtCodigoSalon.getText().trim();

        ProcesoMatriculas.mostrarReporte(
                vista.tblReportes, arregloMatriculas, arregloEstudiantes, anio, grado, salon);

        if (mostrarAvisoSiVacio && vista.tblReportes.getRowCount() == 0) {
            MensajeMat.Mostrar("No se encontraron matrículas con los filtros seleccionados");
        }
    }
}
