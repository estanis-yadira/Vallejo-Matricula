package Horario;

import java.io.Serializable;

public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String anio;
    private String grado;
    private String dniDocente;
    private String codigoSalon;
    private String codigoCurso;
    private String dia;
    private String horaInicio;
    private String horaFin;
    public Horario() {
    }

    public Object[] Registro(int num) {
        Object[] fila = {num, anio, grado, codigoSalon, codigoCurso, dniDocente, dia, horaInicio, horaFin};
        return fila;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getDniDocente() {
        return dniDocente;
    }

    public void setDniDocente(String dniDocente) {
        this.dniDocente = dniDocente;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
