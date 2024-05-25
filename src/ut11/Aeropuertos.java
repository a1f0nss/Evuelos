package ut11;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Aeropuertos extends JDialog implements ActionListener  {

    JTable jtbAeropuerto = new JTable();
    JScrollPane scroll = new JScrollPane(jtbAeropuerto);
   // JButton jbtAccion = new JButton();

    public Aeropuertos(JFrame parent) throws HeadlessException {

        super(parent,"Gestion de Aerpuertos");
        setLayout(null);
        setSize(750, 525);
        setResizable(false);
        setLocationRelativeTo(null);
        this.setVisible(true);
        setModal(true);

        iniciar();
    }
    public void iniciar() {
        jtbAeropuerto.setBounds(0, 0, 650, 290);
        jtbAeropuerto.setShowVerticalLines(false);
        jtbAeropuerto.setRowHeight(30);
        jtbAeropuerto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //jtbAeropuerto.setDefaultEditor(Object.class, null);

        loadTabla();
//        jbtAccion.setBounds(575, 50, 125, 30);
//        jbtAccion.setText("Aeropuertos");
//        jbtAccion.setVisible(false);
//        jbtAccion.addActionListener(this);
//        this.add(jbtAccion);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Aeropuertos")) {
            loadTabla();
        }
    }
    private void loadTabla() {
        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("ID");
        dtm.addColumn("Nombre");
        String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
        try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789");
             Statement stmt = cnx.createStatement()){
            ResultSet rs=stmt.executeQuery("select * from aeropuerto;");
            while(rs.next())
                dtm.addRow(new Object[]{rs.getString("idAeropuerto"), rs.getString("nombre")});
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        jtbAeropuerto.setModel(dtm);

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        jtbAeropuerto.getColumnModel().getColumn(0).setCellRenderer(centro);
        jtbAeropuerto.getColumnModel().getColumn(1).setCellRenderer(centro);
       // jtbAeropuerto.getColumnModel().getColumn(3).setCellRenderer(derecha);

        jtbAeropuerto.getColumnModel().getColumn(0).setPreferredWidth(15);
        jtbAeropuerto.getColumnModel().getColumn(1).setPreferredWidth(175);
       // jtbAeropuerto.getColumnModel().getColumn(2).setPreferredWidth(25);
        //jtbAeropuerto.getColumnModel().getColumn(3).setPreferredWidth(35);

        scroll.setBounds(50, 100, 650, 290);
        this.add(scroll, BorderLayout.CENTER);

        //jbtAccion.setVisible(dtm.getRowCount() > 0);
    }
}