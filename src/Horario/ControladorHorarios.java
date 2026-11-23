package Horario;

import Docente.AlmacenarDocentes;
import Vistas.Paneles.Horarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorHorarios implements ActionListener {

    Horarios vista;
    ListaHorario lista;
    Nodo encontrado = null;
    ArregloHorarios arreglo;

    public ControladorHorarios(Horarios vista) {
        this.vista = vista;
        lista = AlmacenarHorarios.Recuperar();
        sincronizarArreglo();

        vista.btnRegistrar.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.btnConsultar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        ProcesosparaHorarios.LimpiarEntradas(vista);
        lista.MostrarLista(vista.tblHorario, lista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnRegistrar) {
            try {
                Horario hor = ProcesosparaHorarios.LeerHorario(vista);
                if (!validar(hor, null)) {
                    return;
                }
                lista.InsertarAlFinal(new Nodo(hor));
                sincronizarArreglo();
                AlmacenarHorarios.Guardar(lista);
                Mensaje.Mostrar("Horario registrado");
                terminarOperacion();
            } catch (IllegalArgumentException ex) {
                Mensaje.Mostrar(ex.getMessage());
            }
        }

        if (e.getSource() == vista.btnConsultar) {
            String dniBuscado = vista.txtDNI.getText().trim();

            if (dniBuscado.isEmpty()) {
                encontrado = null;
                lista.MostrarLista(vista.tblHorario, lista);
                return;
            }

            Horario[] resultado = arreglo.buscarPorDni(dniBuscado);

            if (resultado.length == 0) {
                encontrado = null;
                Mensaje.Mostrar("El DNI " + dniBuscado + " no tiene horarios registrados");
            } else {
                lista.MostrarHorarios(vista.tblHorario, resultado);
                if (resultado.length == 1) {
                    encontrado = nodoDe(resultado[0]);
                    ProcesosparaHorarios.MostrarHorario(vista, resultado[0]);
                } else {
                    encontrado = null;  
                }
            }
        }

        if (e.getSource() == vista.btnEditar) {
            Nodo seleccionado = nodoSeleccionado();
            if (seleccionado == null) {
                Mensaje.Mostrar("Seleccione un horario de la tabla");
                return;
            }
            encontrado = seleccionado;
            ProcesosparaHorarios.MostrarHorario(vista, encontrado.hor);
        }

        if (e.getSource() == vista.btnEliminar) {
            Nodo aEliminar = nodoSeleccionado();
            if (aEliminar == null) {
                aEliminar = encontrado;
            }
            if (aEliminar != null) {
                int resp = Mensaje.Preguntar("Confirmar!!",
                        "Desea eliminar el horario del " + aEliminar.hor.getDia()
                        + " " + aEliminar.hor.getHoraInicio() + "-" + aEliminar.hor.getHoraFin()
                        + " (salón " + aEliminar.hor.getCodigoSalon() + ")?");
                if (resp == 0) {
                    lista.EliminarHorario(aEliminar);
                    sincronizarArreglo();
                    AlmacenarHorarios.Guardar(lista);
                    terminarOperacion();
                }
            } else {
                Mensaje.Mostrar("Seleccione un horario de la tabla o realice una búsqueda");
            }
        }

        if (e.getSource() == vista.btnGuardar) {
            try {
                Horario hor = ProcesosparaHorarios.LeerHorario(vista);

                if (!validar(hor, encontrado == null ? null : encontrado.hor)) {
                    return;
                }

                if (encontrado == null) {
                    lista.InsertarAlFinal(new Nodo(hor));
                    Mensaje.Mostrar("Horario registrado");
                } else {
                    encontrado.hor = hor;
                    Mensaje.Mostrar("Horario actualizado");
                }

                sincronizarArreglo();
                AlmacenarHorarios.Guardar(lista);
                terminarOperacion();
            } catch (IllegalArgumentException ex) {
                Mensaje.Mostrar(ex.getMessage());
            }
        }

        if (e.getSource() == vista.btnCancelar) {
            encontrado = null;
            ProcesosparaHorarios.LimpiarEntradas(vista);
            vista.tblHorario.clearSelection();
        }
    }

    private boolean validar(Horario hor, Horario ignorar) {
        if (AlmacenarDocentes.Recuperar().BuscarDocente(hor.getDniDocente()) == null) {
            Mensaje.Mostrar("No existe un docente registrado con el DNI " + hor.getDniDocente());
            return false;
        }
        String cruce = arreglo.motivoCruce(hor, ignorar);
        if (cruce != null) {
            Mensaje.Mostrar(cruce);
            return false;
        }
        return true;
    }

    private void terminarOperacion() {
        lista.MostrarLista(vista.tblHorario, lista);
        ProcesosparaHorarios.LimpiarEntradas(vista);
        vista.txtDNI.setText("");
        encontrado = null;
    }

    private Nodo nodoSeleccionado() {
        int fila = vista.tblHorario.getSelectedRow();
        if (fila == -1) {
            return null;
        }
        String anio = vista.tblHorario.getValueAt(fila, 1).toString();
        String salon = vista.tblHorario.getValueAt(fila, 3).toString();
        String dia = vista.tblHorario.getValueAt(fila, 6).toString();
        String inicio = vista.tblHorario.getValueAt(fila, 7).toString();
        return lista.BuscarHorario(anio, salon, dia, inicio);
    }

    private Nodo nodoDe(Horario h) {
        Nodo aux = lista.ini;
        while (aux != null && aux.hor != h) {
            aux = aux.sig;
        }
        return aux;
    }

    private void sincronizarArreglo() {
        arreglo = new ArregloHorarios();
        Nodo aux = lista.ini;
        while (aux != null) {
            arreglo.agregar(aux.hor);
            aux = aux.sig;
        }
    }
}
