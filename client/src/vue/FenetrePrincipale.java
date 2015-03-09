package vue;

import base.Client;
import base.ClientObservable;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class FenetrePrincipale extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    JPanel jpprincipal;
    JPanel jpbord1;
    JMenuBar menubar;
    JMenu menu1;
    //****************************
    ClientObservable clientObs;
    private JPanel jpbord2;
    private String nom_fichier;
    private String chemin_fichier;
    private JLabel nomDuFichier;
    private GridLayout g;
    private JTextField identifiant;
    private JTextField mdp;
    private JTextField nomServeur;
    private JButton connexion;

    //-------------
    //constructeur
    //-------------
    public FenetrePrincipale(ClientObservable clientObs) {
        super();
        this.clientObs = clientObs;
        setResizable(false);
        setBackground(new Color(51, 102, 102));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 153, 153));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblBienvenue = new JLabel("Bienvenue sur le client POP3");

        identifiant = new JTextField();
        identifiant.setColumns(10);

        JLabel lblUserName = new JLabel("Identifiant");

        JLabel lblMotDePasse = new JLabel("Mot de passe");

        mdp = new JTextField();
        mdp.setColumns(10);

        JLabel lblNomDuServeur = new JLabel("Nom du serveur");

        nomServeur = new JTextField();
        nomServeur.setColumns(10);

        connexion = new JButton();
        connexion.setText("Connexion");
        connexion.setFocusPainted(false);
        connexion.setBackground(Color.CYAN);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(419)
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addComponent(lblUserName)
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNomDuServeur)
                                                        .addComponent(lblMotDePasse))
                                                .addGap(43)
                                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(mdp, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(nomServeur, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(570, Short.MAX_VALUE))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(474)
                                .addComponent(connexion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(612, Short.MAX_VALUE))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(459)
                                .addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addGap(589))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(112)
                                .addComponent(lblBienvenue)
                                .addGap(35)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUserName))
                                .addGap(18)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(mdp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMotDePasse))
                                .addGap(18)
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNomDuServeur))
                                .addGap(26)
                                .addComponent(connexion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(267, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        initComponents();
    }

    public static boolean waitForAnswer(Client client) {
        String success, error;
        do {
            success = client.getSucessMessage();
            error = client.getErrorMessage();
            if (success != null) {
                System.out.println(success);
                return true;
            }
            if (error != null) {
                System.out.println(error);
                return false;
            }
        } while (success == null && error == null);
        return false;
    }

    public JButton getConnexion() {
        return this.connexion;
    }

    public JTextField getIdentifiant() {
        return this.identifiant;
    }

    public JTextField getMotDePasse() {
        return this.mdp;
    }

    public JTextField getNomServeur() {
        return this.nomServeur;
    }

    private void initComponents() {
        this.setTitle("Client");
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setJMenuBar(menubar);
        this.setVisible(true);
    }

    //le notify de la boucle qui est lancer a partir du modele afin d'actualiser la vue et ensuite de refaire un tour de boucle vers le modele
    //cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilit� de la stopper ou non et d'agir sur son temps d'execution
    @Override
    public void update(Observable obj, Object arg) {
        if (arg instanceof Exception) {
            erreurGenerique(new JFrame(), ((Exception) arg).getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean connexionServeur(String nomServeur) {
        if (!nomServeur.equals("")) {
            clientObs.openConnexion(nomServeur, clientObs.port);
            return waitForAnswer(clientObs);
        } else {
            erreurGenerique(new JFrame(), "Serveur introuvable", "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public boolean connexionClient(String username, String password) {
        boolean connect = false;
        if (!username.equals("") && !password.equals("")) {
            clientObs.enterLogin(username);
            connect = waitForAnswer(clientObs);
            if (connect) {
                clientObs.enterPassword(password);
                return waitForAnswer(clientObs);
            } else {
                return connect;
            }
        } else if (!username.equals("")) {
            clientObs.signIn(username);
            return waitForAnswer(clientObs);
        } else {
            erreurGenerique(new JFrame(), "Veuillez renseignez votre nom", "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return connect;
        }
    }

    public void erreurGenerique(JFrame frame, String message, String titre_fenetre, int type_message) {
        //custom title, warning icon
        JOptionPane.showMessageDialog(frame, message, titre_fenetre, type_message);
    }

    public void connecter() {
        this.setVisible(false);
        FenetreConnecter fenetre = new FenetreConnecter();
        fenetre.setVisible(true);

    }

    public void deconnecter() {
        this.setVisible(true);
    }
}
