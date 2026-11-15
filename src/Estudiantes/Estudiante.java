package Estudiantes;

import java.io.Serializable;

public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dni;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String fechaNacimiento;
    private String sexo;
    private String direccion;

    private String dniApoderado;
    private String telefonoApoderado;
    private String nombreApoderado;
    private String apellidoPApoderado;
    private String apellidoMApoderado;
    private String parentesco;

    public Estudiante() {
    }

    public Object[] Registro(int num) {
        Object[] fila = {
            num,
            dni,
            nombre,
            apellidoP,
            apellidoM,
            fechaNacimiento,
            sexo,
            direccion,
            dniApoderado,
            telefonoApoderado,
            nombreApoderado,
            apellidoPApoderado,
            apellidoMApoderado,
            parentesco
        };

        return fila;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDniApoderado() {
        return dniApoderado;
    }

    public void setDniApoderado(String dniApoderado) {
        this.dniApoderado = dniApoderado;
    }

    public String getTelefonoApoderado() {
        return telefonoApoderado;
    }

    public void setTelefonoApoderado(String telefonoApoderado) {
        this.telefonoApoderado = telefonoApoderado;
    }

    public String getNombreApoderado() {
        return nombreApoderado;
    }

    public void setNombreApoderado(String nombreApoderado) {
        this.nombreApoderado = nombreApoderado;
    }

    public String getApellidoPApoderado() {
        return apellidoPApoderado;
    }

    public void setApellidoPApoderado(String apellidoPApoderado) {
        this.apellidoPApoderado = apellidoPApoderado;
    }

    public String getApellidoMApoderado() {
        return apellidoMApoderado;
    }

    public void setApellidoMApoderado(String apellidoMApoderado) {
        this.apellidoMApoderado = apellidoMApoderado;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
