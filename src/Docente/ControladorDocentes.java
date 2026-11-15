package Docente;

import java.awt.event.ActionListener;
import Vistas.Paneles.Docentes;
import java.awt.event.ActionEvent;


public class ControladorDocentes implements ActionListener {

    Docentes vista;
    Docente doc ;
    ListaDocente lista;
    Nodo encontrado=null;
    ArregloDocentes arreglo;
    
    public ControladorDocentes(Docentes vista){
    this.vista = vista;
    lista = AlmacenarDocentes.Recuperar();
    arreglo = new ArregloDocentes();

Nodo aux = lista.ini;
while (aux != null) {
    arreglo.agregar(aux.doc);
    aux = aux.sig;
}
    vista.btnRegistrar.addActionListener(this);
    vista.btnGuardar.addActionListener(this);
    vista.btnConsultar.addActionListener(this);   
    vista.btnEliminar.addActionListener(this);   
    vista.btnCancelar.addActionListener(this);
    ProcesosparaDocentes.LimpiarEntradas(vista);
    vista.btnEditar.addActionListener(this);
    lista.MostrarLista(vista.tblDocente, lista);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrar) {
            Docente doc= ProcesosparaDocentes.LeerDocente(vista);
            lista.InsertarAlFinal(new Nodo(doc));
            sincronizarArreglo();
            Mensaje.Mostrar("Docente registrado");
            AlmacenarDocentes.Guardar(lista);
            lista.MostrarLista(vista.tblDocente, lista);
            ProcesosparaDocentes.LimpiarEntradas(vista);
        }
        
if (e.getSource() == vista.btnConsultar) {
    String dniBuscado = vista.txtDNI.getText().trim();

    // Buscar el docente utilizando el arreglo de objetos
    Docente docenteEncontrado = arreglo.buscarPorDni(dniBuscado);

    if (docenteEncontrado == null) {
        encontrado = null;
        Mensaje.Mostrar("El DNI " + dniBuscado + " no existe en el arreglo");
    } else {
        // Mantener la referencia al nodo para editar o eliminar
        encontrado = lista.BuscarDocente(dniBuscado);

        ProcesosparaDocentes.MostrarDocentes(
            vista,
            docenteEncontrado
        );
    }
}

        if (e.getSource() == vista.btnEditar) {
              int fila = vista.tblDocente.getSelectedRow();

              if (fila == -1) {
            Mensaje.Mostrar("Seleccione un docente de la tabla");
               return;
               }

              String dni = vista.tblDocente.getValueAt(fila, 1).toString();   // columna 1 = DNI
              encontrado = lista.BuscarDocente(dni);

               if (encontrado != null) {
                 ProcesosparaDocentes.MostrarDocentes(vista, encontrado.doc);
                   } else {
                   Mensaje.Mostrar("No se encontró el docente seleccionado");
                   }
}
        
        
        
        if(e.getSource() == vista.btnEliminar){
            if(encontrado!=null){
                int resp = Mensaje.Preguntar("Confirmar!!",
                          "Desea eliminar al docente con DNI: "+encontrado.doc.getDni());
                if(resp==0){
                    lista.EliminarDocente(encontrado);
                    sincronizarArreglo();
                   AlmacenarDocentes.Guardar(lista);
                    lista.MostrarLista(vista.tblDocente, lista);   // <- refrescar
                    ProcesosparaDocentes.LimpiarEntradas(vista);
                     encontrado = null;
                }
            }else{
                Mensaje.Mostrar("Debe de realizar una búsqueda");
            }
        }
        if (e.getSource() == vista.btnGuardar) {
    try {
        Docente doc = ProcesosparaDocentes.LeerDocente(vista);

        if (encontrado == null) {
            // REGISTRO NUEVO
            if (lista.BuscarDocente(doc.getDni()) != null) {
                Mensaje.Mostrar("Ya existe un docente con el DNI " + doc.getDni());
                return;
            }
            lista.InsertarAlFinal(new Nodo(doc));
            Mensaje.Mostrar("Docente registrado");
        } else {
            // ACTUALIZAR EL YA GUARDADO
            Nodo repetido = lista.BuscarDocente(doc.getDni());
            if (repetido != null && repetido != encontrado) {
                Mensaje.Mostrar("Ya existe otro docente con el DNI " + doc.getDni());
                return;
            }
            encontrado.doc = doc;
            Mensaje.Mostrar("Docente actualizado");
        }

        sincronizarArreglo();
        AlmacenarDocentes.Guardar(lista);
        lista.MostrarLista(vista.tblDocente, lista);
        ProcesosparaDocentes.LimpiarEntradas(vista);
        vista.txtDNI.setText("");
        encontrado = null;   

    } catch (NumberFormatException ex) {
        Mensaje.Mostrar("El teléfono debe ser numérico");
    }
 
        }
        if (e.getSource() == vista.btnCancelar) {
            ProcesosparaDocentes.LimpiarEntradas(vista);

        }

     
    
    }
private void sincronizarArreglo() {
    arreglo = new ArregloDocentes();

    Nodo aux = lista.ini;

    while (aux != null) {
        arreglo.agregar(aux.doc);
        aux = aux.sig;
    }
}
}
    
    
    

