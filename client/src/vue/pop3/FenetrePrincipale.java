package vue.pop3;

import base.client.Config;
import base.client.Port;
import base.client.impl.Pop3Client;
import base.email.Email;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.util.Observable;

public class FenetrePrincipale extends JFrame {

    private static final long serialVersionUID = 1L;
    //****************************
    private Pop3Client pop3Client;
    private FenetreConnecter fenetreConnecter;
    private FenetreUser fenetreUser;
    private JTextField nomServeur;
    private JButton connexion;
    private JTextField port;
    private JCheckBox checkConnexionSecurise;

    //-------------
    //constructeur
    //-------------
    public FenetrePrincipale(Pop3Client pop3Client) {
        super();
        this.fenetreUser = new FenetreUser();
        this.fenetreConnecter = new FenetreConnecter();
        this.pop3Client = pop3Client;
        setResizable(false);
        setBackground(new Color(51, 102, 102));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 102, 102));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblBienvenue = new JLabel("Bienvenue sur le client POP3");
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

        checkConnexionSecurise = new JCheckBox("");
        checkConnexionSecurise.setBackground(new Color(51, 102, 102));

        JLabel lblConnexionScuris = new JLabel("Connexion s\u00E9curis\u00E9:");
        lblConnexionScuris.setForeground(new Color(0, 0, 128));
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(
                        gl_panel.createSequentialGroup().addGroup(
                                gl_panel.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(
                                                gl_panel.createSequentialGroup()
                                                        .addGap(459)
                                                        .addComponent(
                                                                lblBienvenue,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))
                                        .addGroup(
                                                gl_panel.createSequentialGroup()
                                                        .addContainerGap(389,
                                                                Short.MAX_VALUE)
                                                        .addGroup(
                                                                gl_panel.createParallelGroup(
                                                                        Alignment.TRAILING)
                                                                        .addGroup(
                                                                                gl_panel.createSequentialGroup()
                                                                                        .addGroup(
                                                                                                gl_panel.createParallelGroup(
                                                                                                        Alignment.TRAILING)
                                                                                                        .addGroup(
                                                                                                                gl_panel.createSequentialGroup()
                                                                                                                        .addComponent(
                                                                                                                                portLbl,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                45,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addGap(35))
                                                                                                        .addGroup(
                                                                                                                gl_panel.createSequentialGroup()
                                                                                                                        .addComponent(
                                                                                                                                lblConnexionScuris,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                124,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addPreferredGap(
                                                                                                                                ComponentPlacement.UNRELATED)))
                                                                                        .addGroup(
                                                                                                gl_panel.createParallelGroup(
                                                                                                        Alignment.LEADING)
                                                                                                        .addComponent(
                                                                                                                checkConnexionSecurise)
                                                                                                        .addComponent(
                                                                                                                port,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                36,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(
                                                                                                                nomServeur,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(
                                                                                gl_panel.createSequentialGroup()
                                                                                        .addComponent(
                                                                                                lblNomDuServeur)
                                                                                        .addGap(18)
                                                                                        .addComponent(
                                                                                                connexion,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                108,
                                                                                                GroupLayout.PREFERRED_SIZE)))))
                                .addGap(599)));
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
                        gl_panel.createSequentialGroup().addGap(112)
                                .addComponent(lblBienvenue).addGap(28).addGroup(
                                gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nomServeur,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNomDuServeur))
                                .addGap(14).addGroup(
                                gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(port,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(portLbl)).addGap(18)
                                .addGroup(gl_panel.createParallelGroup(
                                        Alignment.TRAILING)
                                        .addComponent(checkConnexionSecurise,
                                                GroupLayout.PREFERRED_SIZE, 18,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblConnexionScuris))
                                .addGap(10).addComponent(connexion,
                                GroupLayout.PREFERRED_SIZE, 22,
                                GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(296, Short.MAX_VALUE)));
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

    //GETTER
    //************************************
    public JButton getConnexion() {
        return this.connexion;
    }

    public JButton getConnexionUser() {
        return this.fenetreUser.getConnexionUser();
    }

    public JTextField getIdentifiant() {
        return this.fenetreUser.getIdentifiant();
    }

    public JTextField getNomServeur() {
        return this.nomServeur;
    }

    public JTextField getPort() {
        return this.port;
    }

    public JButton getRecuperer() {
        return this.fenetreConnecter.getRecuperer();
    }

    //    public JButton getListMessage(){
    //    	return this.fenetreConnecter.getListMessage();
    //    }
    public JTextField getNumberMessage() {
        return this.fenetreConnecter.getNumberMessage();
    }

    public JMenuItem getDeconnexion() {
        return this.fenetreConnecter.getDeconnexion();
    }

    public JMenuItem getDeconnexionUser() {
        return this.fenetreUser.getDeconnexion();
    }

    public JTextField getPassword() {
        return this.fenetreUser.getPassword();
    }
    //************************************

    public boolean connexionServeur(String nomServeur, String port) {
        boolean connect = false;
        Config.setSsl(checkConnexionSecurise.isSelected());
        if (!nomServeur.equals("")) {
            if (!port.equals("")) {
                pop3Client.openConnexion(nomServeur, Integer.parseInt(port));
            } else {
                pop3Client.openConnexion(nomServeur, Config.getSsl() ?
                        Port.POP3_SSL.getValue() :
                        Port.POP3.getValue());
            }
            connect = waitForAnswer(pop3Client);
        }
        if (!connect) {
            erreurGenerique(new JFrame(), "Serveur introuvable",
                    "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return connect;
        } else {
            connecterServeur();
            return connect;
        }
    }

    public boolean connexionClient(String username, String password) {
        String connect;
        if (!username.equals("")) {
            if (!password.equals("")) {
                pop3Client.signIn(username, password);
            } else {
                pop3Client.signIn(username);
            }
            connect = waitForAnswerString(pop3Client);
        } else {
            erreurGenerique(new JFrame(), "Veuillez renseignez votre nom",
                    "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (connect != null) {
            erreurGenerique(new JFrame(), connect, "Warning connexion",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public void recupereMessage(String number_message) {
        try {
            int number = Integer.parseInt(number_message);

            this.pop3Client.getMessage(number);
            String erreur = waitForAnswerString(pop3Client);
            if (erreur != null) {
                this.erreurGenerique(new JFrame(), erreur, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Email email = this.pop3Client.getMessage();
                if (email != null) {
                    this.fenetreConnecter.getChampMessage().setText(email.toString());
                }
            }
        } catch (java.lang.NumberFormatException e) {
            this.erreurGenerique(new JFrame(), "Invalid Parameter", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //CONNEXION ENTRE LES FENETRE
    //************************************
    public void connecter() {
        this.fenetreUser.setVisible(false);
        this.fenetreConnecter.reset();
        fenetreConnecter.setVisible(true);

    }

    public void deconnecter() {
        this.setVisible(true);
    }

    public void connecterServeur() {
        this.setVisible(false);
        this.fenetreUser.resetPassword();
        fenetreUser.setVisible(true);
    }

    public void disconnect() {
        this.pop3Client.closeConnexion();
        this.fenetreConnecter.setVisible(false);
        this.fenetreUser.setVisible(false);
        this.setVisible(true);
    }
    //************************************

    public boolean waitForAnswer(Pop3Client pop3Client) {
        String success, error;
        do {
            success = pop3Client.getSucessMessage();
            error = pop3Client.getErrorMessage();
            if (success != null) {
                System.out.println(success);
                return true;
            }
            if (error != null) {
                System.out.println(error);
                //erreurGenerique(new JFrame(),error,"Erreur",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } while (success == null && error == null);
        return false;
    }

    public String waitForAnswerString(Pop3Client pop3Client) {
        String success, error;
        do {
            success = pop3Client.getSucessMessage();
            error = pop3Client.getErrorMessage();
            if (success != null) {
                System.out.println(success);
                return null;
            }
            if (error != null) {
                System.out.println(error);
                //erreurGenerique(new JFrame(),error,"Erreur",JOptionPane.ERROR_MESSAGE);
                return error;
            }
        } while (success == null && error == null);
        return error;
    }

    public void erreurGenerique(JFrame frame, String message,
                                String titre_fenetre, int type_message) {
        //custom title, warning icon
        JOptionPane
                .showMessageDialog(frame, message, titre_fenetre, type_message);
    }

    //le notify de la boucle qui est lancer a partir du modele afin d'actualiser la vue et ensuite de refaire un tour de boucle vers le modele
    //cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilit� de la stopper ou non et d'agir sur son temps d'execution
    public void update(Observable obj, Object arg) {
        if (arg instanceof Exception) {
            erreurGenerique(new JFrame(), ((Exception) arg).getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

