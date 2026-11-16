package Vistas.Paneles;

import Horario.AlmacenarHorarios;
import Horario.ArregloHorarios;
import Horario.Horario;
import Horario.ValidadorHorario;
import Vistas.Utilidades.EstiloTabla;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;


public class Horarios extends javax.swing.JPanel {
private ArregloHorarios arregloHorarios;
    private String codigoOriginal = null;
    /**
     * Creates new form Dashboard
     */
    public Horarios() {
        
        initComponents();

        arregloHorarios = AlmacenarHorarios.Recuperar();

        configurarSpinners();
        configurarTabla();
        EstiloTabla.aplicar(tblEstudiantes);
        actualizarTabla();

        jButton1.addActionListener(e -> registrarHorario());
        jButton5.addActionListener(e -> guardarHorario());
        jButton6.addActionListener(e -> cancelarOperacion());
        jButton2.addActionListener(e -> consultarHorarios());
        jButton3.addActionListener(e -> eliminarHorario());
        jButton4.addActionListener(e -> editarHorario());
    }

    private void configurarSpinners() {
        spHoraInicio.setModel(new SpinnerDateModel());
        spHoraInicio.setEditor(new JSpinner.DateEditor(spHoraInicio, "HH:mm"));

        spHoraFin.setModel(new SpinnerDateModel());
        spHoraFin.setEditor(new JSpinner.DateEditor(spHoraFin, "HH:mm"));

        valoresPorDefecto();
    }

    private void valoresPorDefecto() {
        establecerHora(spHoraInicio, LocalTime.of(8, 0));
        establecerHora(spHoraFin, LocalTime.of(9, 0));
    }

    private void establecerHora(JSpinner spinner, LocalTime hora) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora.getHour());
        c.set(Calendar.MINUTE, hora.getMinute());
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        spinner.setValue(c.getTime());
    }

    private LocalTime leerHora(JSpinner spinner) {
        Date fecha = (Date) spinner.getValue();
        return fecha.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .withSecond(0)
                .withNano(0);
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"N°", "Código", "Año", "Grado", "Salón", "Curso",
                    "DNI docente", "Día", "Inicio", "Fin"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tblEstudiantes.setModel(modelo);
    }

    private void actualizarTabla() {
        mostrarEnTabla(arregloHorarios.obtenerOrdenados());
    }

    private void mostrarEnTabla(Horario[] lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantes.getModel();
        modelo.setRowCount(0);

        for (int i = 0; i < lista.length; i++) {
            modelo.addRow(lista[i].Registro(i + 1));
        }
    }

    private void registrarHorario() {
        limpiarFormulario();
        codigoOriginal = null;
        jTextField3.requestFocus();
    }

    private void guardarHorario() {
        String anio = jComboBox3.getSelectedItem().toString();
        String grado = jComboBox4.getSelectedItem().toString();
        String codigoSalon = jTextField3.getText().trim();
        String codigoCurso = jTextField4.getText().trim();
        String dni = jTextField11.getText().trim();
        String dia = jComboBox1.getSelectedItem().toString();
        LocalTime inicio = leerHora(spHoraInicio);
        LocalTime fin = leerHora(spHoraFin);

        String codigo = codigoOriginal == null ? generarCodigo() : codigoOriginal;

        Horario horario = new Horario(codigo, anio, grado, codigoSalon,
                codigoCurso, dni, dia, inicio, fin);

        String error = ValidadorHorario.validar(horario, codigoOriginal, arregloHorarios);

        if (error != null) {
            JOptionPane.showMessageDialog(this, error);
            return;
        }

        boolean estabaEditando = codigoOriginal != null;
        boolean resultado = estabaEditando
                ? arregloHorarios.actualizar(codigoOriginal, horario)
                : arregloHorarios.agregar(horario);

        if (!resultado) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el horario. Verifique sus datos.");
            return;
        }

        AlmacenarHorarios.Guardar(arregloHorarios);
        actualizarTabla();
        limpiarFormulario();
        codigoOriginal = null;

        JOptionPane.showMessageDialog(this, estabaEditando
                ? "Horario actualizado correctamente."
                : "Horario registrado correctamente.");
    }

    private String generarCodigo() {
        int siguiente = arregloHorarios.getCantidad() + 1;
        String codigo;

        do {
            codigo = String.format("HOR%03d", siguiente);
            siguiente++;
        } while (arregloHorarios.buscarPorCodigo(codigo) != null);

        return codigo;
    }

    private void consultarHorarios() {
        String dni = jTextField12.getText().trim();

        if (dni.isEmpty()) {
            actualizarTabla();
            return;
        }

        Horario[] encontrados = arregloHorarios.buscarPorDocente(dni);

        if (encontrados.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron horarios para ese docente.");
            return;
        }

        mostrarEnTabla(ArregloHorarios.ordenar(encontrados));
    }

    private Horario horarioSeleccionado() {
        int fila = tblEstudiantes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un horario de la tabla.");
            return null;
        }

        int filaModelo = tblEstudiantes.convertRowIndexToModel(fila);
        String codigo = tblEstudiantes.getModel()
                .getValueAt(filaModelo, 1).toString();

        return arregloHorarios.buscarPorCodigo(codigo);
    }

    private void editarHorario() {
        Horario horario = horarioSeleccionado();

        if (horario != null) {
            cargarFormulario(horario);
            codigoOriginal = horario.getCodigo();
        }
    }

    private void eliminarHorario() {
        Horario horario = horarioSeleccionado();

        if (horario == null) {
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este horario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION
                && arregloHorarios.eliminar(horario.getCodigo())) {
            AlmacenarHorarios.Guardar(arregloHorarios);
            actualizarTabla();
            limpiarFormulario();
            codigoOriginal = null;

            JOptionPane.showMessageDialog(this,
                    "Horario eliminado correctamente.");
        }
    }

    private void cargarFormulario(Horario h) {
        jComboBox3.setSelectedItem(h.getAnio());
        jComboBox4.setSelectedItem(h.getGrado());
        jTextField3.setText(h.getCodigoSalon());
        jTextField4.setText(h.getCodigoCurso());
        jTextField11.setText(h.getDniDocente());
        jComboBox1.setSelectedItem(h.getDia());
        establecerHora(spHoraInicio, h.getHoraInicio());
        establecerHora(spHoraFin, h.getHoraFin());
    }

    private void limpiarFormulario() {
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        valoresPorDefecto();
    }

    private void cancelarOperacion() {
        limpiarFormulario();
        codigoOriginal = null;
        actualizarTabla();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        spHoraInicio = new javax.swing.JSpinner();
        spHoraFin = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 247, 249));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(50, 52, 62));
        jLabel1.setText("Horarios");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(50, 52, 62));
        jLabel2.setText("Horario de clases por grado y salón");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 231, 235), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(103, 115, 124));
        jLabel3.setText("Año lectivo:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 17, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(103, 115, 124));
        jLabel5.setText("Código del salón:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 17, -1, -1));

        jTextField3.setForeground(new java.awt.Color(138, 149, 156));
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 216, 222)));
        jTextField3.addActionListener(this::jTextField3ActionPerformed);
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 37, 176, 29));

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(103, 115, 124));
        jLabel6.setText("Código del curso:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 17, -1, -1));

        jTextField4.setForeground(new java.awt.Color(138, 149, 156));
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 216, 222)));
        jTextField4.addActionListener(this::jTextField4ActionPerformed);
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 37, 176, 29));

        jComboBox1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(138, 149, 156));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 98, 192, 29));

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(103, 115, 124));
        jLabel8.setText("Hora de inicio:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 78, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(103, 115, 124));
        jLabel12.setText("Día de la semana:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 78, -1, -1));

        jButton5.setBackground(new java.awt.Color(58, 139, 155));
        jButton5.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vistas/Imagenes/guardar.png"))); // NOI18N
        jButton5.setText("Guardar");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, -1, 35));

        jButton6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(50, 52, 62));
        jButton6.setText("Cancelar");
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 84, 35));

        jComboBox3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(138, 149, 156));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2026", "2025" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 37, 176, 29));

        jLabel4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(103, 115, 124));
        jLabel4.setText("Grado:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 17, -1, -1));

        jComboBox4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(138, 149, 156));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto" }));
        jComboBox4.addActionListener(this::jComboBox4ActionPerformed);
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 37, 192, 29));

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(103, 115, 124));
        jLabel18.setText("DNI del docente a cargo:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 78, -1, -1));

        jTextField11.setForeground(new java.awt.Color(138, 149, 156));
        jTextField11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 216, 222)));
        jTextField11.addActionListener(this::jTextField11ActionPerformed);
        jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 98, 176, 29));

        spHoraInicio.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jPanel1.add(spHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 98, 176, 30));

        spHoraFin.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jPanel1.add(spHoraFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 98, 176, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(103, 115, 124));
        jLabel19.setText("Hora de fin:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 78, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 810, 200));

        jButton1.setBackground(new java.awt.Color(58, 139, 155));
        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Registrar horario");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 150, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 231, 235), 2));

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(103, 115, 124));
        jLabel17.setText("Buscar por DNI:");

        jTextField12.setForeground(new java.awt.Color(138, 149, 156));
        jTextField12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(207, 216, 222)));

        jButton2.setBackground(new java.awt.Color(58, 139, 155));
        jButton2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vistas/Imagenes/lupa-consultar.png"))); // NOI18N
        jButton2.setText("Consultar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        tblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEstudiantes.setGridColor(new java.awt.Color(238, 243, 245));
        jScrollPane2.setViewportView(tblEstudiantes);

        jButton3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(50, 52, 62));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vistas/Imagenes/tacho-eliminar.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setBackground(new java.awt.Color(58, 139, 155));
        jButton4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vistas/Imagenes/lapiz-editar.png"))); // NOI18N
        jButton4.setText("Editar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 810, 290));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JSpinner spHoraFin;
    private javax.swing.JSpinner spHoraInicio;
    private javax.swing.JTable tblEstudiantes;
    // End of variables declaration//GEN-END:variables
}
