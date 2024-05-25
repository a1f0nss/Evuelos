package ut11;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    public Menu() {
        setJMenuBar(initBarraMenu());
        initPantalla();
    }

    private JMenuBar initBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu0 = new JMenu("Vuelos");
        JMenuItem m00 = new JMenuItem("Datos");
        menu0.add(m00);
        menu0.add(new JSeparator()); // separador
        JMenu menu01 = new JMenu("Vuelos");
        JMenuItem m010 = new JMenuItem("Gestión Vuelos");
        m010.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FormEVuelo dialog = new FormEVuelo(Menu.this);
                dialog.setVisible(true);
            }
        });
        menu01.add(m010);
        JMenuItem m011 = new JMenuItem("Aeropuertos");
        m011.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Aeropuertos1 dialog = new Aeropuertos1(Menu.this);
                dialog.setVisible(true);
            }
        });
        menu01.add(m011);
        menu0.add(menu01);

        JMenuItem m10 = new JMenuItem("Salir");
        m10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(WIDTH);
            }
        });

        menuBar.add(menu0);
        menuBar.add(m10);

        return menuBar;
    }

    private void initPantalla() {
        setLayout(null);
        setTitle("Gestion vuelos");
        setResizable(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
}
