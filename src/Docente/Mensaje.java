
package Docente;

import javax.swing.JOptionPane;
public class Mensaje {
    
        public static void Mostrar(String msj){
        JOptionPane.showMessageDialog(null,msj);
    }
        
    public static String Leer(String msj){
        return JOptionPane.showInputDialog(msj);
    }
    
    public static int Preguntar(String titulo,String msj){
        return JOptionPane.showConfirmDialog(null,
                  msj,titulo,JOptionPane.OK_CANCEL_OPTION);
    }
}
