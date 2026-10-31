package Vistas.Utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class EstiloTabla {

    public static void aplicar(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor,
                    boolean sel, boolean foco, int fila, int col) {
                super.getTableCellRendererComponent(t, valor, sel, foco, fila, col);
                setBackground(new Color(238, 243, 245));
                setForeground(new Color(68, 84, 94));
                setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
                setHorizontalAlignment(LEFT);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(207, 216, 222)),
                        BorderFactory.createEmptyBorder(0, 8, 0, 8)));
                return this;
            }
        });

        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 32));
        header.setReorderingAllowed(false);

        tabla.setRowHeight(28);
        tabla.setShowVerticalLines(false);
        tabla.setSelectionBackground(new Color(227, 242, 245));
        tabla.setSelectionForeground(new Color(28, 43, 51));
    }
}