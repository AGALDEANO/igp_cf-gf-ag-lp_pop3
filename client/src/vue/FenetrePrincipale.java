package vue;

import base.client.Client;
import base.client.ClientObservable;

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
    private FenetreConnecter fenetreConnecter;
    private FenetreUser fenetreUser;
    private JPanel jpbord2;
    private String nom_fichier;
    private String chemin_fichier;
    private JLabel nomDuFichier;
    private GridLayout g;
    private JTextField nomServeur;
    private JButton connexion;
    private JTextField port;

    //-------------
    //constructeur
    //-------------
    public FenetrePrincipale(ClientObservable clientObs) {
        super();
        this.fenetreUser = new FenetreUser();
        this.fenetreConnecter = new FenetreConnecter();
        this.clientObs = clientObs;
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
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        					.addGroup(gl_panel.createSequentialGroup()
        						.addGap(455)
        						.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
        					.addGroup(gl_panel.createSequentialGroup()
        						.addGap(459)
        						.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(portLbl, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNomDuServeur, Alignment.TRAILING))
        					.addGap(18)
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(port, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        						.addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addGap(589))
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
        				.addComponent(portLbl)
        				.addComponent(port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(20)
        			.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(322, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        initComponents();
    }

    public boolean waitForAnswer(Client client) {
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
                erreurGenerique(new JFrame(),error,"Erreur",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } while (success == null && error == null);
        return false;
    }

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

    public boolean connexionServeur(String nomServeur,String port) {
        boolean connect = false;
        if (!nomServeur.equals("")) {
        	if(!port.equals(""))
        	{
        		clientObs.openConnexion(nomServeur, Integer.valueOf(port));
        	}
        	else
        	{
        		clientObs.openConnexion(nomServeur, clientObs.port);
        	}
            connect = waitForAnswer(clientObs);
        }
        if (!connect) {
            erreurGenerique(new JFrame(), "Serveur introuvable", "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return connect;
        } else {
            connecterServeur();
            return connect;
        }
    }

    public boolean connexionClient(String username) {
        boolean connect = false;
        if (!username.equals("")) {
            clientObs.signIn(username);
            connect = waitForAnswer(clientObs);
        }
        if (!connect) {
            erreurGenerique(new JFrame(), "Veuillez renseignez votre nom", "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return connect;
        } else {
            return connect;
        }
    }

    public void erreurGenerique(JFrame frame, String message, String titre_fenetre, int type_message) {
        //custom title, warning icon
        JOptionPane.showMessageDialog(frame, message, titre_fenetre, type_message);
    }

    public void connecter() {
        this.fenetreUser.setVisible(false);
        fenetreConnecter.setVisible(true);

    }

    public void deconnecter() {
        this.setVisible(true);
    }

    public void connecterServeur() {
        this.setVisible(false);
        fenetreUser.setVisible(true);
    }
}

