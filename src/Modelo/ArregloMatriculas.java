
package Modelo;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author milag
 */
public class ArregloMatriculas implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Matricula[] matriculas;
    private int cantidad;
    
    public ArregloMatriculas(){
        matriculas = new Matricula[10];
        cantidad = 0;
    }
    
    public int getCantidad(){return cantidad;}
    public boolean estadoVacio(){return cantidad == 0;}
    public Matricula obtener(int indice){
        if(indice < 0 || indice >= cantidad){
            return null;
        }
        return matriculas[indice];
    }
    
    public Matricula buscarPorCodigo(String codigoMatricula){
        if(codigoMatricula == null || codigoMatricula.trim().isEmpty()){
            return null;
        }
        for(int i = 0; i < cantidad; i++){
            if(codigoMatricula.trim().equalsIgnoreCase(matriculas[i].getCodigoMatricula())){
                return matriculas[i];
            }
        }
        return null;
    }
    
    public Matricula buscarPorDniAño(String dniEstudiante, String añoColectivo){
        if(dniEstudiante == null || añoColectivo == null){
            return null;
        }
        for (int i = 0; i < cantidad; i++){
            if(dniEstudiante.trim().equals(matriculas[i].getDniEstudiante()) && añoColectivo.trim().equalsIgnoreCase(matriculas[i].getAñoLectivo())){
                return matriculas[i];  
            }
        }
        return null;
    }
    
    public Matricula[] buscarDni(String dniEstudiante){
        if(dniEstudiante == null || dniEstudiante.trim().isEmpty()){
            return new Matricula[0];
        }
        Matricula[] temp = new Matricula[cantidad];
        int n = 0;
        for(int i = 0; i < cantidad; i++){
            if(dniEstudiante.trim().equals(matriculas[i].getDniEstudiante())){
                temp[n++] = matriculas[i];
            }
        }
        return Arrays.copyOf(temp, n);
    }
    
    public Matricula[] filtrar(String añoLectivo, String grado, String codigoSalon) {
        Matricula[] temp = new Matricula[cantidad];
        int n = 0;
        String salonFiltro = (codigoSalon == null) ? "" : codigoSalon.trim();

        for (int i = 0; i < cantidad; i++) {
            Matricula m = matriculas[i];
            boolean coincideAnio = añoLectivo == null || añoLectivo.equals("Todos")
                    || m.getAñoLectivo().equalsIgnoreCase(añoLectivo.trim());
            boolean coincideGrado = grado == null || grado.equals("Todos")
                    || m.getGrado().equalsIgnoreCase(grado.trim());
            boolean coincideSalon = salonFiltro.isEmpty()
                    || m.getCodigoSalon().equalsIgnoreCase(salonFiltro);

            if (coincideAnio && coincideGrado && coincideSalon) {
                temp[n++] = m;
            }
        }
        return Arrays.copyOf(temp, n);
    }
    
    public int contMatriculadosSalonAño(String codigoSalon, String añoLectivo, Matricula ignorar){
        if (codigoSalon == null || añoLectivo == null) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            Matricula m = matriculas[i];
            if (m == ignorar) {
                continue;
            }
            if (codigoSalon.trim().equalsIgnoreCase(m.getCodigoSalon())
                    && añoLectivo.trim().equalsIgnoreCase(m.getAñoLectivo())) {
                total++;
            }
        }
        return total;
    }

public boolean agregar(Matricula matricula) {
        if (matricula == null
                || matricula.getDniEstudiante() == null
                || matricula.getDniEstudiante().trim().isEmpty()) {
            return false;
        }
        if (buscarPorDniAño(matricula.getDniEstudiante(), matricula.getAñoLectivo()) != null) {
            return false;
        }
        asegurarCapacidad();
        matriculas[cantidad] = matricula;
        cantidad++;
        return true;
    }
public boolean actualizar(String codigoMatricula, Matricula actualizada) {
        if (codigoMatricula == null || actualizada == null) {
            return false;
        }
        for (int i = 0; i < cantidad; i++) {
            if (codigoMatricula.equalsIgnoreCase(matriculas[i].getCodigoMatricula())) {
                Matricula duplicada = buscarPorDniAño(
                        actualizada.getDniEstudiante(), actualizada.getAñoLectivo());
                if (duplicada != null && duplicada != matriculas[i]) {
                    return false;
                }
                matriculas[i] = actualizada;
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String codigoMatricula) {
        if (codigoMatricula == null) {
            return false;
        }
        for (int i = 0; i < cantidad; i++) {
            if (codigoMatricula.equalsIgnoreCase(matriculas[i].getCodigoMatricula())) {
                int desplazar = cantidad - i - 1;
                if (desplazar > 0) {
                    System.arraycopy(matriculas, i + 1, matriculas, i, desplazar);
                }
                cantidad--;
                matriculas[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    public Matricula[] obtenerTodos() {
        return Arrays.copyOf(matriculas, cantidad);
    }

    private void asegurarCapacidad() {
        if (cantidad == matriculas.length) {
            matriculas = Arrays.copyOf(matriculas, matriculas.length * 2);
        }
    }
}

