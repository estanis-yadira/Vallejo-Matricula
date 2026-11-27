
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Modelo.*;
import Docente.*;
import Estudiantes.*;
import Horario.*;
import Salon.*;
import Vistas.Paneles.*;
/**
 *
 * @author milag
 */
public class ControladorMatriculas implements ActionListener {
    private final Matriculas vista;
    private ArregloMatriculas arregloMatriculas;
    private ArregloEstudiantes arregloEstudiantes;
    private ArregloSalones arregloSalones;
    private Estudiante estudianteActual;
    private String codigoEnEdicion = null;

    public ControladorMatriculas(Matriculas vista) {
        this.vista = vista;
        recargarDatos();

        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);

        limpiarFormulario();
        refrescarTabla();
    }

    public void recargarDatos() {
        arregloMatriculas = AlmacenarMatriculas.Recuperar();
        arregloEstudiantes = AlmacenarEstudiantes.Recuperar();
        arregloSalones = AlmacenarSalones.Recuperar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnConsultar) {
            buscarEstudiante();
        } else if (e.getSource() == vista.btnGuardar) {
            guardarMatricula();
        } else if (e.getSource() == vista.btnEditar) {
            prepararEdicion();
        } else if (e.getSource() == vista.btnEliminar) {
            eliminarMatricula();
        } else if (e.getSource() == vista.btnCancelar) {
            limpiarFormulario();
            vista.tblMatriculas.clearSelection();
        }
    }

    private void buscarEstudiante() {
        recargarDatos();
        String dni = vista.txtBuscarDni.getText().trim();
        if (dni.isEmpty()) {
            estudianteActual = null;
            actualizarTarjetaEstudiante(null, null);
            refrescarTabla();
            return;
        }

        Estudiante est = arregloEstudiantes.buscarPorDni(dni);
        if (est == null) {
            estudianteActual = null;
            actualizarTarjetaEstudiante(null, null);
            MensajeMat.Mostrar("No se encontró un estudiante registrado con el DNI " + dni);
            return;
        }

        estudianteActual = est;
        String anioSeleccionado = vista.cbAñoLectivo.getSelectedItem().toString();
        Matricula matAnio = arregloMatriculas.buscarPorDniAño(dni, anioSeleccionado);
        actualizarTarjetaEstudiante(est, matAnio);

        Matricula[] delAlumno = arregloMatriculas.buscarDni(dni);
        if (delAlumno.length > 0) {
            ProcesoMatriculas.mostrarMatriculas(vista.tblMatriculas, delAlumno);
        } else {
            MensajeMat.Mostrar("Estudiante encontrado. Aún no tiene matrícula registrada para el año " + anioSeleccionado);
        }
    }

    private void guardarMatricula() {
        recargarDatos();

        String dni = (estudianteActual != null)
                ? estudianteActual.getDni()
                : vista.txtBuscarDni.getText().trim();

        if (dni.isEmpty()) {
            MensajeMat.Mostrar("Busque y seleccione un estudiante por DNI antes de matricular");
            return;
        }

        Estudiante est = arregloEstudiantes.buscarPorDni(dni);
        if (est == null) {
            MensajeMat.Mostrar("El estudiante con DNI " + dni + " no existe en el registro de estudiantes");
            return;
        }
        estudianteActual = est;

        String anio = vista.cbAñoLectivo.getSelectedItem().toString().trim();
        String grado = vista.cbGrado.getSelectedItem().toString().trim();
        String codigoSalon = vista.txtCodigoSalon.getText().trim();
        String fecha = vista.txtFechaMatricula.getText().trim();

        if (codigoSalon.isEmpty()) {
            MensajeMat.Mostrar("Ingrese el código del salón");
            return;
        }
        if (fecha.isEmpty()) {
            fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        Salon salon = arregloSalones.buscarPorCodigo(codigoSalon);
        if (salon == null) {
            MensajeMat.Mostrar("No existe un salón registrado con el código " + codigoSalon);
            return;
        }
        if (!salon.getGrado().equalsIgnoreCase(grado)) {
            MensajeMat.Mostrar("El salón " + codigoSalon + " pertenece a " + salon.getGrado()
                    + ", no coincide con el grado seleccionado (" + grado + ")");
            return;
        }

        Matricula matIgnorada = (codigoEnEdicion != null)
                ? arregloMatriculas.buscarPorCodigo(codigoEnEdicion)
                : null;
        int ocupados = arregloMatriculas.contMatriculadosSalonAño(codigoSalon, anio, matIgnorada);
        if (ocupados >= salon.getCapacidad()) {
            MensajeMat.Mostrar("El salón " + codigoSalon + " alcanzó su capacidad máxima ("
                    + salon.getCapacidad() + ") para el año " + anio);
            return;
        }

        if (codigoEnEdicion == null) {
            if (arregloMatriculas.buscarPorDniAño(dni, anio) != null) {
                MensajeMat.Mostrar("El estudiante ya cuenta con matrícula registrada en el año " + anio);
                return;
            }
            String nuevoCodigo = ProcesoMatriculas.generarCodigo(arregloMatriculas, anio);
            Matricula nueva = new Matricula(
                    nuevoCodigo, dni, anio, grado, salon.getCodigo(), fecha, "MATRICULADO");
            if (!arregloMatriculas.agregar(nueva)) {
                MensajeMat.Mostrar("No se pudo registrar la matrícula");
                return;
            }
            MensajeMat.Mostrar("Matrícula registrada con código " + nuevoCodigo);
        } else {
            Matricula actualizada = new Matricula(
                    codigoEnEdicion, dni, anio, grado, salon.getCodigo(), fecha, "MATRICULADO");
            if (!arregloMatriculas.actualizar(codigoEnEdicion, actualizada)) {
                MensajeMat.Mostrar("No se pudo actualizar la matrícula (verifique duplicidad por año lectivo)");
                return;
            }
            MensajeMat.Mostrar("Matrícula " + codigoEnEdicion + " actualizada");
        }

        AlmacenarMatriculas.Guardar(arregloMatriculas);
        actualizarTarjetaEstudiante(est, arregloMatriculas.buscarPorDniAño(dni, anio));
        limpiarFormulario();
        refrescarTabla();
    }

    private void prepararEdicion() {
        int fila = vista.tblMatriculas.getSelectedRow();
        if (fila == -1) {
            MensajeMat.Mostrar("Seleccione una matrícula de la tabla para editar");
            return;
        }
        String codigo = vista.tblMatriculas.getValueAt(fila, 1).toString();
        Matricula mat = arregloMatriculas.buscarPorCodigo(codigo);
        if (mat == null) {
            MensajeMat.Mostrar("No se encontró la matrícula seleccionada");
            return;
        }

        codigoEnEdicion = mat.getCodigoMatricula();
        estudianteActual = arregloEstudiantes.buscarPorDni(mat.getDniEstudiante());
        vista.txtBuscarDni.setText(mat.getDniEstudiante());
        vista.cbAñoLectivo.setSelectedItem(mat.getAñoLectivo());
        vista.cbGrado.setSelectedItem(mat.getGrado());
        vista.txtCodigoSalon.setText(mat.getCodigoSalon());
        vista.txtFechaMatricula.setText(mat.getFechaMatricula());
        actualizarTarjetaEstudiante(estudianteActual, mat);
    }

    private void eliminarMatricula() {
        int fila = vista.tblMatriculas.getSelectedRow();
        String codigo = null;

        if (fila != -1) {
            codigo = vista.tblMatriculas.getValueAt(fila, 1).toString();
        } else if (codigoEnEdicion != null) {
            codigo = codigoEnEdicion;
        }

        if (codigo == null) {
            MensajeMat.Mostrar("Seleccione una matrícula de la tabla para eliminar");
            return;
        }

        int resp = MensajeMat.Preguntar(
                "Confirmar eliminación",
                "Desea eliminar la matrícula " + codigo + "?");
        if (resp == 0) {
            if (arregloMatriculas.eliminar(codigo)) {
                AlmacenarMatriculas.Guardar(arregloMatriculas);
                MensajeMat.Mostrar("Matrícula eliminada");
                limpiarFormulario();
                refrescarTabla();
                if (estudianteActual != null) {
                    Matricula vigente = arregloMatriculas.buscarPorDniAño(
                            estudianteActual.getDni(),
                            vista.cbAñoLectivo.getSelectedItem().toString());
                    actualizarTarjetaEstudiante(estudianteActual, vigente);
                }
            }
        }
    }

    private void actualizarTarjetaEstudiante(Estudiante est, Matricula mat) {
        String anio = vista.cbAñoLectivo.getSelectedItem() != null
                ? vista.cbAñoLectivo.getSelectedItem().toString()
                : "2026";

        vista.lblApoderado.setText("Apoderado:");

        if (est == null) {
            vista.lblEstudiante.setText("Nombre del estudiante");
            vista.lblNombreApod.setText("Nombre del apoderado");
            vista.btnEstado.setText("Sin matrícula " + anio);
            return;
        }

        vista.lblEstudiante.setText(ProcesoMatriculas.nombreCompletoEstudiante(est));
        String apo = ProcesoMatriculas.nombreCompletoApoderado(est);
        vista.lblNombreApod.setText(apo.isEmpty() ? "No registrado" : apo);

        if (mat != null) {
            vista.btnEstado.setText("Matriculado " + anio + " (" + mat.getGrado() + ")");
        } else {
            vista.btnEstado.setText("Sin matrícula " + anio);
        }
    }

    private void limpiarFormulario() {
        codigoEnEdicion = null;
        if (vista.cbAñoLectivo.getItemCount() > 0) {
            vista.cbAñoLectivo.setSelectedIndex(0);
        }
        if (vista.cbGrado.getItemCount() > 0) {
            vista.cbGrado.setSelectedIndex(0);
        }
        vista.txtCodigoSalon.setText("");
        vista.txtFechaMatricula.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void refrescarTabla() {
        ProcesoMatriculas.mostrarMatriculas(
                vista.tblMatriculas, arregloMatriculas.obtenerTodos());
    }
}