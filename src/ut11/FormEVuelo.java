package ut11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FormEVuelo extends JDialog implements ActionListener {

  private JLabel lTitulo = new JLabel("Datos del vuelo");
  private JLabel lId = new JLabel("ID: ");
  private JLabel lAerolinea = new JLabel("Aereolinea: ");
  private JLabel lOrigen = new JLabel("Origen: ");
  private JLabel lDestino = new JLabel("Destino: ");
  private JLabel lSalida = new JLabel("Salida: ");
  private JLabel lLlegada = new JLabel("Llegada: ");
  private JLabel lLunes = new JLabel("L");
  private JLabel lMartes = new JLabel("M");
  private JLabel lMiercoles = new JLabel("X");
  private JLabel lJueves = new JLabel("J");
  private JLabel lViernes = new JLabel("V");
  private JLabel lSabado = new JLabel("S");
  private JLabel lDomingo = new JLabel("D");

  public JTextField tId = new JTextField();
  public JTextField tAerolinea = new JTextField();
  private DefaultListModel<String> listModel = new DefaultListModel<>();
  public JList<String> jlAerolinea = new JList<>(listModel);
  public JLabel imageLabel = new JLabel();

  public JComboBox<String> jcbOrigen = new JComboBox<>();
  public JComboBox<String> jcbDestino = new JComboBox<>();
  public JTextField tSalida = new JTextField();
  public JTextField tLlegada = new JTextField();
  public JCheckBox ckL = new JCheckBox();
  public JCheckBox ckM = new JCheckBox();
  public JCheckBox ckX = new JCheckBox();
  public JCheckBox ckJ = new JCheckBox();
  public JCheckBox ckV = new JCheckBox();
  public JCheckBox ckS = new JCheckBox();
  public JCheckBox ckD = new JCheckBox();

  public JButton btBuscar = new JButton("Buscar");
  public JButton btActualizar = new JButton("Actualizar");
  public JButton btNuevo = new JButton("Guardar");
  private boolean isUpdating = false;

  JPanel pId, pDatos;

  public FormEVuelo(JFrame parent) throws HeadlessException {

    super(parent,"Gestion de Vuelos");
    setLayout(null);//centramos la venta
    setLocation(100,100);//posicionamos la ventana con respeto al menu
    setSize(450, 500); //le damos tamaño
    setResizable(false);//cambiar el tamaño de la ventana
    this.setVisible(true);
    setModal(true);

    // Panel Id
    pId = new JPanel();
    pId.setLayout(null);
    pId.setBounds(10, 20, 450, 60);

    pId.add(lTitulo);
    pId.add(lId);
    pId.add(tId);
    pId.add(btBuscar);
    lTitulo.setBounds(10, 0, 150, 30);
    lId.setBounds(10, 40, 100, 20);
    tId.setBounds(100, 40, 100, 20);
    btBuscar.setBounds(225, 40, 155, 20);

    btBuscar.addActionListener(this);

    // Datos del vuelo
    pDatos = new JPanel();
    pDatos.setLayout(null);
    pDatos.setBounds(10, 95, 450, 400);

    pDatos.add(lAerolinea);
    pDatos.add(lOrigen);
    pDatos.add(lDestino);
    pDatos.add(lSalida);
    pDatos.add(lLlegada);
    pDatos.add(lLunes);
    pDatos.add(lMartes);
    pDatos.add(lMiercoles);
    pDatos.add(lJueves);
    pDatos.add(lViernes);
    pDatos.add(lSabado);
    pDatos.add(lDomingo);


    pDatos.add(tAerolinea);
    pDatos.add(new JScrollPane(jlAerolinea));
    pDatos.add(imageLabel);
    pDatos.add(imageLabel);
    pDatos.add(jcbOrigen);
    pDatos.add(jcbDestino);
    pDatos.add(tSalida);
    pDatos.add(tLlegada);
    pDatos.add(ckL);
    pDatos.add(ckM);
    pDatos.add(ckX);
    pDatos.add(ckJ);
    pDatos.add(ckV);
    pDatos.add(ckS);
    pDatos.add(ckD);

    pDatos.add(btActualizar);
    pDatos.add(btNuevo);

    lAerolinea.setBounds(10, 0, 100, 20);
    tAerolinea.setBounds(100, 0, 280, 20);
    JScrollPane scrollPane = new JScrollPane(jlAerolinea);

    // scrollPane.setBounds(100, 25, 250, 75);
    // Configura el imageLabel
    imageLabel.setBounds(100, 25, 280, 75);
    imageLabel.setOpaque(true);

    //pDatos.add(scrollPane);

    lOrigen.setBounds(20, 110, 100, 20);
    jcbOrigen.setBounds(20, 140, 150, 20);
    lDestino.setBounds(230, 110, 100, 20);
    jcbDestino.setBounds(230, 140, 150, 20);
    lSalida.setBounds(20, 170, 75, 20);
    tSalida.setBounds(20, 200, 150, 20);
    lLlegada.setBounds(230, 170, 100, 20);
    tLlegada.setBounds(230, 200, 150, 20);

    lLunes.setBounds(30, 230, 100, 20);
    ckL.setBounds(25, 250, 20, 20);
    lMartes.setBounds(80, 230, 100, 20);
    ckM.setBounds(75, 250, 20, 20);
    lMiercoles.setBounds(130, 230, 100, 20);
    ckX.setBounds(125, 250, 20, 20);
    lJueves.setBounds(180, 230, 100, 20);
    ckJ.setBounds(175, 250, 20, 20);
    lViernes.setBounds(230, 230, 100, 20);
    ckV.setBounds(225, 250, 20, 20);
    lSabado.setBounds(280, 230, 100, 20);
    ckS.setBounds(275, 250, 20, 20);
    lDomingo.setBounds(330, 230, 100, 20);
    ckD.setBounds(325, 250, 20, 20);

    btActualizar.setBounds(85, 280, 200, 25);
    btNuevo.setBounds(85, 310, 200, 25);

    btActualizar.setVisible(false);
    btActualizar.addActionListener(new Actualizar());
    btNuevo.setVisible(false);
    btNuevo.addActionListener(new Nuevo());

    getContentPane().add(pId);
    getContentPane().add(pDatos);

    activarPanel(pDatos, false);


    // Añadir ActionListeners para sincronizar jcbOrigen y jcbDestino.
    jcbOrigen.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateDestinoComboBox();
      }
    });

    jcbDestino.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateOrigenComboBox();
      }
    });
  }


  private void iniciarPantalla() {
    activarPanel(pId, true);
    activarPanel(pDatos, false);
    limpiarPanel(pDatos);
    limpiarPanel(pId);
    btBuscar.setText("Buscar");
    btActualizar.setVisible(false);
    btNuevo.setVisible(false);
  }

  public void activarPanel(JPanel panel, boolean estado) {
    for (Component control : panel.getComponents()) {
      if (control instanceof JTextField) {
        ((JTextField) control).setEditable(estado);
      } else if (control instanceof JCheckBox) {
        control.setEnabled(estado);}
        else if (control instanceof JList) {
          control.setEnabled(estado);}

    }
  }

public void limpiarPanel(JPanel panel) {
  boolean transferidoFoco = false;
  for (Component control : panel.getComponents()) {
    if (control instanceof JTextField) {
      ((JTextField) control).setText("");
      // Se transfiere el foco al primer JTextField
      if (!transferidoFoco) {
        ((JTextField) control).grabFocus();
        transferidoFoco = true;
      }
    } else if (control instanceof JCheckBox) {
      ((JCheckBox) control).setSelected(false);

    } else if (control instanceof JList) {
      ((JList<JScrollPane>) control).clearSelection();

    }else if (control instanceof JComboBox) {
      ((JComboBox) control).setSelectedIndex(-1);
    }
  }
}

  ArrayList origenes = new ArrayList<>();
  ArrayList destinos = new ArrayList<>();
  public boolean cargarDatos() {
    boolean ok = false;

    String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
    try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789")) {
      String sentencias = " select concat(idAeropuerto,' (',nombre,')') as aereo from aeropuerto; SELECT nombre FROM aerolinea;";
      PreparedStatement pstm = cnx.prepareStatement(sentencias);
      boolean resultsCar = pstm.execute();

      int rsCount = 0;
      Set<String> aerolineas = new HashSet<>();

      if (resultsCar) {
        do {
          ResultSet rs = pstm.getResultSet();
          rsCount++;

          if (rsCount == 1) {
            while (rs.next()) {
              origenes.add(rs.getString("aereo"));
              destinos.add(rs.getString("aereo"));
              jcbOrigen.addItem(rs.getString("aereo"));
              jcbDestino.addItem(rs.getString("aereo"));
            }
          }

          if (rsCount == 2) {
            while (rs.next()) {
              aerolineas.add(rs.getString("nombre"));
            }
          }

          resultsCar = pstm.getMoreResults();
        } while (resultsCar);


        for (String aerolinea : aerolineas) {
          listModel.addElement(aerolinea);
        }
        ok = true;
      }
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
    }
    return ok;
  }

  private void updateDestinoComboBox() {
    String selectedOrigen = (String) jcbOrigen.getSelectedItem();
    ArrayList<String> newDestinos = new ArrayList<>(destinos);
    newDestinos.remove(selectedOrigen);
    jcbDestino.setModel(new DefaultComboBoxModel<>(newDestinos.toArray(new String[0])));
  }

  private void updateOrigenComboBox() {
    String selectedDestino = (String) jcbDestino.getSelectedItem();
    ArrayList<String> newOrigenes = new ArrayList<>(origenes);
    newOrigenes.remove(selectedDestino);
    jcbOrigen.setModel(new DefaultComboBoxModel<>(newOrigenes.toArray(new String[0])));
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if (listModel.isEmpty() && jcbOrigen.getItemCount() == 0 && jcbDestino.getItemCount() == 0) {
      cargarDatos();
    }

    if (!tId.getText().isBlank() && e.getActionCommand().equals("Buscar")) {

      String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
      try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789")) {
        String sentencias = "SELECT vuelo.idVuelo, aerolinea.nombre, CONCAT(vuelo.oIdAeropuerto, ' (', origen.nombre, ')') AS oAereo, CONCAT(vuelo.dIdAeropuerto, ' (', destino.nombre, ')') AS dAereo, vuelo.hSalida, vuelo.hLlegada, vuelo.frecuencia, aerolinea.logo FROM vuelo JOIN aerolinea ON aerolinea.idAerolinea = vuelo.idAerolinea JOIN aeropuerto AS origen ON vuelo.oIdAeropuerto = origen.idAeropuerto JOIN aeropuerto AS destino ON vuelo.dIdAeropuerto = destino.idAeropuerto WHERE vuelo.idVuelo = ?;";
        PreparedStatement pstm = cnx.prepareStatement(sentencias);
        pstm.setString(1, tId.getText());

        boolean hasResults = pstm.execute();
        boolean hasData = false;

        if (hasResults) {
          activarPanel(pDatos, true);
          btActualizar.setVisible(true);
          btBuscar.setText("Nueva Búsqueda");
          int rsCount = 0;

          do {
            ResultSet rs = pstm.getResultSet();
            if (rs.next()) {
              hasData = true;
              rsCount++;
              activarPanel(pId, false);
              if (rsCount == 1) {
                tId.setText(rs.getString("idVuelo"));
                tAerolinea.setText(rs.getString("nombre"));
                tAerolinea.setEnabled(false);
                tAerolinea.addMouseListener(new MouseAdapter() {
                  @Override
                  public void mouseClicked(MouseEvent e) {
                    mostrarDialogo();
                  }
                });

                displayImage(rs.getString("logo"));
                jcbOrigen.setSelectedItem(rs.getString("oAereo"));
                jcbDestino.setSelectedItem(rs.getString("dAereo"));
                tSalida.setText(rs.getString("hSalida"));
                tLlegada.setText(rs.getString("hLlegada"));

                String frecu = rs.getString("frecuencia");
                ckL.setSelected(frecu.charAt(0) == '1');
                ckM.setSelected(frecu.charAt(1) == '1');
                ckX.setSelected(frecu.charAt(2) == '1');
                ckJ.setSelected(frecu.charAt(3) == '1');
                ckV.setSelected(frecu.charAt(4) == '1');
                ckS.setSelected(frecu.charAt(5) == '1');
                ckD.setSelected(frecu.charAt(6) == '1');

              }

            }
          } while (pstm.getMoreResults());

          if (!hasData) {
            noResultsFound();
          }
        } else {
          noResultsFound();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }


    }
    if(e.getActionCommand().equals("Nueva Búsqueda")){
      iniciarPantalla();
      limpiarPanel(pId);
      limpiarPanel(pDatos);
    }
  }

  private void mostrarDialogo() {
    JDialog dialogo = new JDialog(this, "Seleccione Aerolinea", true);
    dialogo.setSize(200, 175);
    dialogo.setLocationRelativeTo(this);

    for (int i = 0; i < listModel.size(); i++) {
      if (listModel.getElementAt(i).equals(tAerolinea.getText())) {
        jlAerolinea.setSelectedIndex(i);
        jlAerolinea.ensureIndexIsVisible(i);
        break;
      }
    }

    JButton boton = new JButton("Seleccionar");
    boton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String seleccion = jlAerolinea.getSelectedValue();
        if (seleccion != null) {
          tAerolinea.setText(seleccion);
          dialogo.dispose();
        } else {
          JOptionPane.showMessageDialog(dialogo, "No se ha seleccionado ningún elemento.");
        }
      }
    });

    dialogo.setLayout(new BorderLayout());
    dialogo.add(new JScrollPane(jlAerolinea), BorderLayout.CENTER);
    dialogo.add(boton, BorderLayout.SOUTH);
    dialogo.setVisible(true);
  }

  private void noResultsFound() {
    int respuesta = JOptionPane.showConfirmDialog(this, "El vuelo no existe ¿Desea darlo de alta?", "AVISO", JOptionPane.YES_NO_OPTION);
    if (respuesta == JOptionPane.YES_OPTION) {
      activarPanel(pId, true);
      tAerolinea.setEnabled(false);
      activarPanel(pDatos, true);
      limpiarPanel(pDatos);
      btBuscar.setText("Nueva Búsqueda");
      btActualizar.setVisible(false);
      btNuevo.setVisible(true);
    } else {
      limpiarPanel(pDatos);
      limpiarPanel(pId);
    }
  }

  class Actualizar implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent a) {

      String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
      try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789")) {

        tAerolinea.setText(jlAerolinea.getSelectedValue());


        if (!tId.getText().isBlank() && !tAerolinea.getText().isBlank() && jcbOrigen.getSelectedItem() != null && jcbDestino.getSelectedItem() != null && fHoraValido(tSalida.getText()) && fHoraValido(tLlegada.getText())) {

          String updateSQL = "UPDATE vuelo SET idAerolinea = ?, oIdAeropuerto = ?, dIdAeropuerto = ?, hSalida = ?, hLlegada = ?, frecuencia = ? WHERE idVuelo = ?";
          PreparedStatement pstm = cnx.prepareStatement(updateSQL);

          pstm.setString(1, idAerolinRetur(tId.getText()));
          pstm.setString(2, jcbOrigen.getSelectedItem().toString().substring(0,3));
          pstm.setString(3, jcbDestino.getSelectedItem().toString().substring(0,3));
          pstm.setString(4, tSalida.getText());
          pstm.setString(5, tLlegada.getText());
          pstm.setString(6, getFrecuenciaString());
          pstm.setString(7, tId.getText());

          int rowsUpdated = pstm.executeUpdate();

          if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "El vuelo ha sido actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            iniciarPantalla();
            limpiarPanel(pDatos);
          } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el vuelo. Verifique los datos e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos y cumplir el formato de hora.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  }
  private String idAerolinRetur(String nAerolinea) {

    String Ids = "";
    String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
    try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789")) {
      String sentencia = "select idAerolinea from  aerolinea where aerolinea.nombre=?;";
      PreparedStatement pstm = cnx.prepareStatement(sentencia);

      pstm.setString(1, tAerolinea.getText());
      ResultSet rs = pstm.executeQuery();
      if (rs.next()) {
        Ids = rs.getString("idAerolinea");

      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return Ids;
  }

    public boolean fHoraValido(String hora) {

      String regex = "^([01]\\d|2[0-3]):([0-5]\\d)$";

      return hora != null && hora.matches(regex);
    }
    private String getFrecuenciaString( ) {

      StringBuilder frecu = new StringBuilder();
      frecu.append(ckL.isSelected() ? '1' : '0');
      frecu.append(ckM.isSelected() ? '1' : '0');
      frecu.append(ckX.isSelected() ? '1' : '0');
      frecu.append(ckJ.isSelected() ? '1' : '0');
      frecu.append(ckV.isSelected() ? '1' : '0');
      frecu.append(ckS.isSelected() ? '1' : '0');
      frecu.append(ckD.isSelected() ? '1' : '0');
      return frecu.toString();
    }


  class Nuevo implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent a) {

      if (a.getActionCommand().equals("Nueva Búsqueda")) {
        iniciarPantalla();
      }
      tAerolinea.setEnabled(false);
      tAerolinea.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent a) {
          mostrarDialogo();
        }
      });
      tAerolinea.setText(jlAerolinea.getSelectedValue());
      String url = "jdbc:mysql://localhost:3306/evuelos?allowMultiQueries=true";
      try (Connection cnx = DriverManager.getConnection(url, "estudiante", "123456789")) {

        if (!tId.getText().isBlank() && !tAerolinea.getText().isBlank() && jcbOrigen.getSelectedItem() != null && jcbDestino.getSelectedItem() != null && fHoraValido(tSalida.getText()) && fHoraValido(tLlegada.getText())) {

          String updateSQL = "INSERT INTO vuelo (idVuelo, idAerolinea, oIdAeropuerto, dIdAeropuerto, hSalida, hLlegada, frecuencia) values(?, ?, ?, ?, ?, ?, ?)";
          PreparedStatement pstm = cnx.prepareStatement(updateSQL);

          pstm.setString(1, tId.getText());
          pstm.setString(2, idAerolinRetur(tId.getText()));
          pstm.setString(3, jcbOrigen.getSelectedItem().toString().substring(0, 3));
          pstm.setString(4, jcbDestino.getSelectedItem().toString().substring(0, 3));
          pstm.setString(5, tSalida.getText());
          pstm.setString(6, tLlegada.getText());
          pstm.setString(7, getFrecuenciaString());


          int rowsUpdated = pstm.executeUpdate();

          if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "El vuelo ha sido dado de alta exitosamente.", "Alta correcta", JOptionPane.INFORMATION_MESSAGE);
            iniciarPantalla();
            limpiarPanel(pDatos);
          } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el vuelo. Verifique los datos e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos y cumplir el formato de hora.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  private void displayImage(String text) {

      ImageIcon icon = getImageForValue(text); // Pasa directamente el texto ingresado
      if (icon != null) {
        imageLabel.setIcon(icon);
      } else {
        // No se encontró una imagen para el valor ingresado
        imageLabel.setIcon(getImageForValue("blanco.jpg"));
      }
  }

  private ImageIcon getImageForValue(String value) {

    String filename = "./src/images/" + value;
    java.io.File file = new java.io.File(filename);
    if (file.exists()) {
      return new ImageIcon(filename);
    } else {
      System.out.println("Imagen no encontrada: " + filename);
      return null;
    }
  }
}
