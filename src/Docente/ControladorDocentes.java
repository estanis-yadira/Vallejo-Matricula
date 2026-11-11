/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import java.awt.event.ActionListener;
import Vistas.Paneles.Docentes;
import java.awt.event.ActionEvent;


public class ControladorDocentes implements ActionListener {

    Docentes vista;
    Docente doc ;
    ListaDocente lista;
    Nodo encontrado=null;
    
    public ControladorDocentes(Docentes vista){
    this.vista = vista;
    lista = AlmacenarDocentes.Recuperar();  
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
            Mensaje.Mostrar("Docente registrado");
            AlmacenarDocentes.Guardar(lista);
            lista.MostrarLista(vista.tblDocente, lista);
            ProcesosparaDocentes.LimpiarEntradas(vista);
        }
        if (e.getSource() == vista.btnConsultar) {
             String dniBuscado = vista.txtDNI.getText();
            encontrado =  lista.BuscarDocente(dniBuscado);
            if(encontrado==null){
                Mensaje.Mostrar("El código "+dniBuscado+" no existe en la lista");                
            }else{
                ProcesosparaDocentes.MostrarDocentes(vista, encontrado.doc);
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

     
    
    }}
    
    
    

