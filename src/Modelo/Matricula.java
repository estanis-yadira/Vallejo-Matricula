
package Modelo;
import java.io.Serializable;

/**
 *
 * @author milag
 */
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String codigoMatricula;
    private String dniEstudiante;
    private String añoLectivo;
    private String grado;
    private String codigoSalon;
    private String fechaMatricula;
    private String estado;
    
    public Matricula(){
        this.estado = "MATRICULADO";
    }

    public Matricula(String codigoMatricula, String dniEstudiante, String añoLectivo, String grado, String codigoSalon, String fechaMatricula, String estado) {
        this.codigoMatricula = codigoMatricula;
        this.dniEstudiante = dniEstudiante;
        this.añoLectivo = añoLectivo;
        this.grado = grado;
        this.codigoSalon = codigoSalon;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }
    
    public Object[] RegistroMatricula(int num){
        return new Object[]{num, codigoMatricula, dniEstudiante, añoLectivo, grado, codigoSalon, fechaMatricula, estado};
    }

    public String getCodigoMatricula() {return codigoMatricula;}
    public void setCodigoMatricula(String codigoMatricula) {this.codigoMatricula = codigoMatricula;}

    public String getDniEstudiante() {return dniEstudiante;}
    public void setDniEstudiante(String dniEstudiante) { this.dniEstudiante = dniEstudiante; }

    public String getAñoLectivo() {return añoLectivo;}
    public void setAñoLectivo(String añoColectivo) {this.añoLectivo = añoColectivo;}

    public String getGrado() {return grado;}
    public void setGrado(String grado) {this.grado = grado;}

    public String getCodigoSalon() {return codigoSalon;}
    public void setCodigoSalon(String codigoSalon) {this.codigoSalon = codigoSalon; }

    public String getFechaMatricula() {return fechaMatricula;}
    public void setFechaMatricula(String fechaMatricula) {this.fechaMatricula = fechaMatricula;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    
}
