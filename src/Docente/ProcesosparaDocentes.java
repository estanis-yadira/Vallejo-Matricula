/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;
import Vistas.Paneles.Docentes;
public class ProcesosparaDocentes {
      public static void LimpiarEntradas(Docentes d){
        d.txtDniDocente.setText("");
        d.txtNombreDocente.setText("");
        d.txtApellidoDocente.setText("");
        d.txtApellido2Docente.setText("");
        d.txtEspecialidad.setText("");
        d.txtCorreo.setText("");
        d.txtTelefono.setText("");
        d.txtDireccion.setText("");
    }
      
      
    public static Docente LeerDocente(Docentes d){
        Docente doc= new Docente();
        doc.setDni(d.txtDniDocente.getText());
        doc.setNombre(d.txtNombreDocente.getText());
        doc.setApellidoP(d.txtApellidoDocente.getText());
        doc.setApellidoM(d.txtApellido2Docente.getText());
        doc.setEspecialidad(d.txtEspecialidad.getText());
        doc.setCorreo(d.txtCorreo.getText());
        doc.setTelefono(Integer.parseInt(d.txtTelefono.getText()));
        doc.setDireccion(d.txtDireccion.getText());
        return doc;
    }
    
    
    public static void MostrarDocentes(Docentes d,Docente doc){
        d.txtDniDocente.setText(doc.getDni());
        d.txtNombreDocente.setText(doc.getNombre());
        d.txtApellidoDocente.setText(doc.getApellidoP());
        d.txtApellido2Docente.setText(doc.getApellidoM());
        d.txtEspecialidad.setText(doc.getEspecialidad());
        d.txtCorreo.setText(doc.getCorreo());
        d.txtTelefono.setText(String.valueOf(doc.getTelefono()));
        d.txtDireccion.setText(doc.getDireccion());
    }//fin metodo    
}
