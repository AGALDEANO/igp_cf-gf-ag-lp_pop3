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
    //cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilitï¿½ de la stopper ou non et d'agir sur son temps d'execution
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

package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Window;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;

import base.Client;
import base.ClientObservable;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.File;
import java.net.UnknownHostException;
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
	
	//-------------
	//constructeur
	//-------------
	public FenetrePrincipale(ClientObservable clientObs){
		super();
		this.fenetreUser=new FenetreUser();
		this.fenetreConnecter=new FenetreConnecter();
		this.clientObs=clientObs;
		setResizable(false);
		setBackground(new Color(51, 102, 102));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 102));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblBienvenue = new JLabel("Bienvenue sur le client POP3");
		lblBienvenue.setForeground(new Color(0, 0, 128));
		
		JLabel lblNomDuServeur = new JLabel("Nom du serveur");
		lblNomDuServeur.setForeground(new Color(0, 0, 128));
		
		nomServeur = new JTextField();
		nomServeur.setColumns(10);
		
		connexion = new JButton();
		connexion.setText("Connexion");
		connexion.setFocusPainted(false);
		connexion.setBackground(Color.CYAN);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNomDuServeur)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(22)
									.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(459)
							.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
					.addGap(589))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(112)
					.addComponent(lblBienvenue)
					.addGap(50)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNomDuServeur))
					.addGap(26)
					.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(328, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		initComponents();
	}
	public JButton getConnexion(){return this.connexion;}
	public JButton getConnexionUser(){return this.fenetreUser.getConnexionUser();}
	public JTextField getIdentifiant() {return this.fenetreUser.getIdentifiant();}
	public JTextField getNomServeur() {return this.nomServeur;}
	private void initComponents(){		
		this.setTitle("Client");
		this.setSize(1200,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	
		this.setJMenuBar(menubar);
		this.setVisible(true);
	}
	
	//le notify de la boucle qui est lancer a partir du modele afin d'actualiser la vue et ensuite de refaire un tour de boucle vers le modele
	//cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilité de la stopper ou non et d'agir sur son temps d'execution
	@Override
	public void update(Observable obj, Object arg){	
		if(arg instanceof Exception)
		{
			erreurGenerique(new JFrame(),((Exception)arg).getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
	}
	public boolean connexionServeur(String nomServeur)
	{
		boolean connect=false;
		if(!nomServeur.equals(""))
		{
		clientObs.openConnexion(nomServeur,clientObs.port);
		connect= waitForAnswer(clientObs);
		}
		if(!connect)
		{
			erreurGenerique(new JFrame(),"Serveur introuvable","Warning connexion",JOptionPane.WARNING_MESSAGE);
			return connect;
		}
		else
		{
			connecterServeur();
			return connect;
		}
	}
	public boolean connexionClient(String username)
	{
		boolean connect=false;
		if(!username.equals(""))
		{
		clientObs.enterLogin(username);
		connect=waitForAnswer(clientObs);
		}
		if(!connect)
		{
			erreurGenerique(new JFrame(),"Veuillez renseignez votre nom","Warning connexion",JOptionPane.WARNING_MESSAGE);
			return connect;
		}
		else
		{
			return connect;
		}
	}
	public void erreurGenerique(JFrame frame,String message,String titre_fenetre,int type_message)
	{
		//custom title, warning icon
				JOptionPane.showMessageDialog(frame,message,titre_fenetre,type_message);
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
	public void connecter()
	{
		this.setVisible(false);
		fenetreConnecter.setVisible(true);
		
	}
	public void deconnecter()
	{
		this.setVisible(true);
	}
	public void connecterServeur()
	{
		this.setVisible(false);
		fenetreUser.setVisible(true);
	}
}

