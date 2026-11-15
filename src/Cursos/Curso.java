package Cursos;

import java.io.Serializable;

public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private String grado;
    private int horasSemanales;
    private String dniDocente;

    public Curso() {
    }

    public Curso(String codigo, String nombre, String grado,
                 int horasSemanales, String dniDocente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.grado = grado;
        this.horasSemanales = horasSemanales;
        this.dniDocente = dniDocente;
    }

    public Object[] Registro(int num) {
        return new Object[]{
            num,
            codigo,
            nombre,
            grado,
            horasSemanales,
            dniDocente
        };
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getDniDocente() {
        return dniDocente;
    }

    public void setDniDocente(String dniDocente) {
        this.dniDocente = dniDocente;
    }
}
