package ut11;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Aeropuertos1 extends JDialog {
    JTable jtbAeropuerto = new JTable();
    JScrollPane scroll = new JScrollPane(jtbAeropuerto);

    public Aeropuertos1(JFrame parent) {
        super(parent, "Gestion de Aeropuertos");
        setLayout(null);
        setSize(750, 525);
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        iniciar();
        this.setVisible(true);
    }

    public void iniciar() {
        jtbAeropuerto.setBounds(0, 0, 650, 290);
        jtbAeropuerto.setShowVerticalLines(false);
        jtbAeropuerto.setRowHeight(30);
        jtbAeropuerto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTabla();

        scroll.setBounds(50, 100, 650, 290);
        this.add(scroll, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void loadTabla() {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 2;
            }
        };

        dtm.addColumn("ID");
        dtm.addColumn("Nombre");
        dtm.addColumn("Acciones");

        dtm.addRow(new Object[]{"", "", "Añadir"});

        String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
        try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789");
             Statement stmt = cnx.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM aeropuerto;");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("idAeropuerto"), rs.getString("nombre"), "Actualizar/Eliminar"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        jtbAeropuerto.setModel(dtm);

        jtbAeropuerto.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        jtbAeropuerto.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor());

        scroll.setBounds(50, 100, 650, 290);
        this.add(scroll, BorderLayout.CENTER);
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton updateButton = new JButton("Actualizar");
            JButton deleteButton = new JButton("Eliminar");

            if (row == 0) {
                updateButton.setText("Añadir");
                deleteButton.setVisible(false);
            }

            panel.add(updateButton);
            if (row != 0) {
                panel.add(deleteButton);
            }

            return panel;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        private final JButton updateButton = new JButton("Actualizar");
        private final JButton deleteButton = new JButton("Eliminar");
        private int currentRow;

        public ButtonEditor() {
            updateButton.addActionListener(this);
            deleteButton.addActionListener(this);
            panel.add(updateButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            if (row == 0) {
                updateButton.setText("Añadir");
                deleteButton.setVisible(false);
            } else {
                updateButton.setText("Actualizar");
                deleteButton.setVisible(true);
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == updateButton) {
                if (currentRow == 0) {
                    addNewRecord();
                } else {
                    updateRecord(currentRow);
                }
            } else if (e.getSource() == deleteButton) {
                deleteRecord(currentRow);
            }
            fireEditingStopped();
        }

        private void addNewRecord() {
            String id = (String) jtbAeropuerto.getValueAt(0, 0);
            String nombre = (String) jtbAeropuerto.getValueAt(0, 1);
            if (!id.isEmpty() && !nombre.isEmpty()) {
                String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
                try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789");
                     PreparedStatement stmt = cnx.prepareStatement("INSERT INTO aeropuerto (idAeropuerto, nombre) VALUES (?, ?)")) {
                    stmt.setString(1, id);
                    stmt.setString(2, nombre);
                    stmt.executeUpdate();
                    ((DefaultTableModel) jtbAeropuerto.getModel()).addRow(new Object[]{id, nombre, "Actualizar/Eliminar"});
                    ((DefaultTableModel) jtbAeropuerto.getModel()).setValueAt("", 0, 0);
                    ((DefaultTableModel) jtbAeropuerto.getModel()).setValueAt("", 0, 1);
                    JOptionPane.showMessageDialog(null, "Registro añadido correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al añadir el registro: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
        }

        private void updateRecord(int row) {
            String id = (String) jtbAeropuerto.getValueAt(row, 0);
            String nombre = (String) jtbAeropuerto.getValueAt(row, 1);
            if (!id.isEmpty() && !nombre.isEmpty()) {
                String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
                try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789");
                     PreparedStatement stmt = cnx.prepareStatement("UPDATE aeropuerto SET nombre = ? WHERE idAeropuerto = ?")) {
                    stmt.setString(1, nombre);
                    stmt.setString(2, id);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el registro: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
            }
        }

        private void deleteRecord(int row) {
            String id = (String) jtbAeropuerto.getValueAt(row, 0);
            if (!id.isEmpty()) {
                String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
                try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789");
                     PreparedStatement stmt = cnx.prepareStatement("DELETE FROM aeropuerto WHERE idAeropuerto = ?")) {
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                    ((DefaultTableModel) jtbAeropuerto.getModel()).removeRow(row);
                    JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + e.getMessage());
                }
            }
        }
    }
}


