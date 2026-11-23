package Horario;

import Vistas.Paneles.Horarios;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;

public class ProcesosparaHorarios {

    public static void LimpiarEntradas(Horarios h) {
        h.cboAnio.setSelectedIndex(0);
        h.cboGrado.setSelectedIndex(0);
        h.cboDia.setSelectedIndex(0);
        h.txtDniDocente.setText("");
        h.txtCodigoSalon.setText("");
        h.txtCodigoCurso.setText("");
        h.spHoraInicio.setValue(horaADate(7, 0));
        h.spHoraFin.setValue(horaADate(8, 0));
    }

    public static Horario LeerHorario(Horarios h) {
        String dni = h.txtDniDocente.getText().trim();
        String salon = h.txtCodigoSalon.getText().trim();
        String curso = h.txtCodigoCurso.getText().trim();

        if (dni.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el DNI del docente a cargo");
        }
        if (salon.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el código del salón");
        }
        if (curso.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el código del curso");
        }

        LocalTime inicio = leerHora(h.spHoraInicio);
        LocalTime fin = leerHora(h.spHoraFin);
        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser menor que la hora de fin");
        }

        Horario hor = new Horario();
        hor.setAnio(h.cboAnio.getSelectedItem().toString());
        hor.setGrado(h.cboGrado.getSelectedItem().toString());
        hor.setDia(h.cboDia.getSelectedItem().toString());
        hor.setDniDocente(dni);
        hor.setCodigoSalon(salon);
        hor.setCodigoCurso(curso);
        hor.setHoraInicio(inicio.toString());
        hor.setHoraFin(fin.toString());
        return hor;
    }

    public static void MostrarHorario(Horarios h, Horario hor) {
        h.cboAnio.setSelectedItem(hor.getAnio());
        h.cboGrado.setSelectedItem(hor.getGrado());
        h.cboDia.setSelectedItem(hor.getDia());
        h.txtDniDocente.setText(hor.getDniDocente());
        h.txtCodigoSalon.setText(hor.getCodigoSalon());
        h.txtCodigoCurso.setText(hor.getCodigoCurso());
        LocalTime i = LocalTime.parse(hor.getHoraInicio());
        LocalTime f = LocalTime.parse(hor.getHoraFin());
        h.spHoraInicio.setValue(horaADate(i.getHour(), i.getMinute()));
        h.spHoraFin.setValue(horaADate(f.getHour(), f.getMinute()));
    }

    private static LocalTime leerHora(JSpinner sp) {
        try {
            sp.commitEdit();
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Hora inválida, use el formato HH:mm");
        }
        Date d = (Date) sp.getValue();
        LocalTime t = d.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return LocalTime.of(t.getHour(), t.getMinute());
    }

    public static Date horaADate(int hora, int minuto) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minuto);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
