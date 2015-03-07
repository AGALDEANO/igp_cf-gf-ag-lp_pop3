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
public class FenetrePrincipale extends JFrame implements Observer {
	
	private static final long serialVersionUID = 1L;
	JPanel jpprincipal;
	JPanel jpcases;
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
	public FenetrePrincipale(ClientObservable clientObs){
		super();
		this.clientObs=clientObs;
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
		
		connexion = new JButton("Connexion");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(419)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblUserName)
								.addGap(57)
								.addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNomDuServeur)
									.addComponent(lblMotDePasse))
								.addGap(43)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(mdp, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(nomServeur, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(68)
							.addComponent(connexion)
							.addPreferredGap(ComponentPlacement.RELATED, 54, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(646, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(450)
					.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(598))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblBienvenue)
					.addGap(111)
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
					.addComponent(connexion)
					.addContainerGap(256, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		initComponents();
	}
	public JButton getConnexion(){return this.connexion;}
	public JTextField getIdentifiant() {return this.identifiant;}
	public JTextField getMotDePasse() {return this.mdp;}
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
		//System.out.println("next");
	}
	public boolean connexionServeur(String nomServeur)
	{
		if(!nomServeur.equals(""))
		{
		clientObs.openConnexion(nomServeur,clientObs.port);
		return waitForAnswer(clientObs);
		}
		else
		{
			erreurConnexionServeur();
			return false;
		}
	}
	public boolean connexionClient(String username, String password)
	{
		boolean connect=false;
		if(!username.equals("") && !password.equals(""))
		{
		clientObs.enterLogin(username);
		connect=waitForAnswer(clientObs);
		if(connect)
		{
			clientObs.enterPassword(password);
			return waitForAnswer(clientObs);
		}
		else
		{
			return connect;
		}
		}
		else if(!username.equals(""))
		{
			clientObs.signIn(username);
			return waitForAnswer(clientObs);
		}
		else
		{
			erreurConnexionClient();
			return connect;
		}
	}
	public void erreurConnexionClient()
	{
		//custom title, warning icon
		JOptionPane.showMessageDialog(new JFrame(),
		    "Veuillez renseignez votre nom",
		    "Connexion warning",
		    JOptionPane.WARNING_MESSAGE);
	}
	public void erreurConnexionServeur()
	{
		//custom title, warning icon
				JOptionPane.showMessageDialog(new JFrame(),
				    "Serveur introuvable",
				    "Connexion warning",
				    JOptionPane.WARNING_MESSAGE);
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
}
