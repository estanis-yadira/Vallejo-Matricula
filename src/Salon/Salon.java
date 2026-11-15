
package Salon;

import java.io.Serializable;

public class Salon implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String grado;
    private String seccion;
    private int capacidad;
    private String aulaPiso;
    private String turno;

    public Salon() {
    }

    public Salon(String codigo, String grado, String seccion,
            int capacidad, String aulaPiso, String turno) {
        this.codigo = codigo;
        this.grado = grado;
        this.seccion = seccion;
        this.capacidad = capacidad;
        this.aulaPiso = aulaPiso;
        this.turno = turno;
    }

    public Object[] Registro(int num) {
        return new Object[]{
            num,
            codigo,
            grado,
            seccion,
            capacidad,
            aulaPiso,
            turno
        };
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getAulaPiso() {
        return aulaPiso;
    }

    public void setAulaPiso(String aulaPiso) {
        this.aulaPiso = aulaPiso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
