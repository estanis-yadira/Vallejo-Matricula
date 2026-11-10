/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import java.io.Serializable;

public class Docente implements Serializable{
    private String dni;
    private String nombre,apellidoP, apellidoM, especialidad, correo;
    private int telefono;
    private String direccion;

    public Docente() {
    }

 public Object[] Registro(int num){
       Object[] fila={num,dni,nombre,apellidoP,apellidoM, especialidad, correo, telefono,direccion};
       return fila;
   }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {this.dni = dni;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) { this.nombre = nombre;}
    public String getApellidoP() {return apellidoP;}
    public void setApellidoP(String apellidoP) {this.apellidoP = apellidoP;}
    public String getApellidoM() {return apellidoM;}
    public void setApellidoM(String apellidoM) {this.apellidoM = apellidoM;}
    public String getEspecialidad() {return especialidad;}
    public void setEspecialidad(String especialidad) {this.especialidad = especialidad;}
    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}
    public int getTelefono() {return telefono;}
    public void setTelefono(int telefono) {this.telefono = telefono;}
    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}  
}
