package vue.smtp;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;

public class FenetreConnexion extends JFrame {

    private static final long serialVersionUID = 1L;
    //****************************
    private JTextField nomServeur;
    private JButton connexion;
    private JTextField port;

    //-------------
    //constructeur
    //-------------
    public FenetreConnexion() {
        setResizable(false);
        setBackground(new Color(51, 102, 102));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 102, 102));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblBienvenue = new JLabel("Bienvenue sur le client SMTP");
        lblBienvenue.setForeground(new Color(0, 0, 128));

        JLabel lblNomDuServeur = new JLabel("Nom du serveur:");
        lblNomDuServeur.setForeground(new Color(0, 0, 128));

        nomServeur = new JTextField();
        nomServeur.setColumns(10);

        connexion = new JButton();
        connexion.setText("Connexion");
        connexion.setFocusPainted(false);
        connexion.setBackground(Color.CYAN);

        JLabel portLbl = new JLabel("Port:");
        portLbl.setForeground(new Color(0, 0, 128));

        port = new JTextField();
        port.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(459)
                                                .addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addContainerGap(375, Short.MAX_VALUE)
                                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                                .addComponent(portLbl, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(35)
                                                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(port, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_panel.createSequentialGroup()
                                                                .addComponent(lblNomDuServeur)
                                                                .addGap(126)))))
                                .addGap(599))
                        .addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
                                .addGap(466)
                                .addComponent(connexion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(620, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(112)
                                .addComponent(lblBienvenue)
                                .addGap(28)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNomDuServeur))
                                .addGap(14)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portLbl))
                                .addGap(18)
                                .addComponent(connexion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(323, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Client");
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JButton getConnexion() {
        return this.connexion;
    }

    public JTextField getPort() {
        return this.port;
    }

    public JTextField getNomServeur() {
        return this.nomServeur;
    }

}
