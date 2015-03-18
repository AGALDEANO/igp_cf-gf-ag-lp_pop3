package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import base.client.Client;
import base.client.Config;
import base.email.Email;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FenetrePrincipale extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    //****************************
	private Client client;
	private FenetreConnecter fenetreConnecter;
    private FenetreUser fenetreUser;
    private JTextField nomServeur;
    private JButton connexion;
    private JTextField port;
    private JCheckBox checkConnexionSecurise;

    //-------------
    //constructeur
    //-------------
	public FenetrePrincipale(Client client) {
		super();
        this.fenetreUser = new FenetreUser();
        this.fenetreConnecter = new FenetreConnecter();
		this.client = client;
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
        checkConnexionSecurise.setBackground(new Color(51,102,102));
        
        JLabel lblConnexionScuris = new JLabel("Connexion s\u00E9curis\u00E9:");
        lblConnexionScuris.setForeground(new Color(0, 0, 128));
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(459)
        					.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addContainerGap(389, Short.MAX_VALUE)
        					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        						.addGroup(gl_panel.createSequentialGroup()
        							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        								.addGroup(gl_panel.createSequentialGroup()
        									.addComponent(portLbl, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        									.addGap(35))
        								.addGroup(gl_panel.createSequentialGroup()
        									.addComponent(lblConnexionScuris, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.UNRELATED)))
        							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        								.addComponent(checkConnexionSecurise)
        								.addComponent(port, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
        								.addComponent(nomServeur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_panel.createSequentialGroup()
        							.addComponent(lblNomDuServeur)
        							.addGap(18)
        							.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))))
        			.addGap(599))
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
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(checkConnexionSecurise, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblConnexionScuris))
        			.addGap(10)
        			.addComponent(connexion, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(296, Short.MAX_VALUE))
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
    
    public JButton getRecuperer(){
    	return this.fenetreConnecter.getRecuperer();
    }
//    public JButton getListMessage(){
//    	return this.fenetreConnecter.getListMessage();
//    }
    public JTextField getNumberMessage(){
    	return this.fenetreConnecter.getNumberMessage();
    }
    public JMenuItem getDeconnexion(){
    	return this.fenetreConnecter.getDeconnexion();
    }
    public JMenuItem getDeconnexionUser(){
    	return this.fenetreUser.getDeconnexion();
    }
  //************************************

    public boolean connexionServeur(String nomServeur,String port) {
        boolean connect = false;
        if (!nomServeur.equals("")) {
        	if(!port.equals(""))
        	{
        		client.openConnexion(nomServeur, Integer.valueOf(port),this.checkConnexionSecurise.isSelected());
			}
        	else
        	{
				client.openConnexion(nomServeur, Config.getDefaultPort(),this.checkConnexionSecurise.isSelected());
			}
			connect = waitForAnswer(client);
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
        String connect;
        if (!username.equals("")) {
			client.signIn(username);
			connect = waitForAnswerString(client);
		}
        else
        {
        	erreurGenerique(new JFrame(), "Veuillez renseignez votre nom", "Warning connexion", JOptionPane.WARNING_MESSAGE);
        	return false;
        }
        if (connect!=null) {
        	erreurGenerique(new JFrame(), connect, "Warning connexion", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else
        {
        	return true;
        }
    }
    public void recupereMessage(String number_message)
    {
    	try{
    	int number=Integer.valueOf(number_message);

			this.client.getMessage(number);
			String erreur = waitForAnswerString(client);
			if(erreur!=null)
    	{
    		this.erreurGenerique(new JFrame(), erreur, "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
			Email email = this.client.getMessage();
			if(email!=null)
    		{
    			this.fenetreConnecter.getChampMessage().setText(email.headersToString()+"\n"+email.bodyToString());
    		}
    	}
    	}catch(java.lang.NumberFormatException e)
    	{
    		this.erreurGenerique(new JFrame(), "Invalid Parameter","Warning", JOptionPane.WARNING_MESSAGE);
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
        fenetreUser.setVisible(true);
    }
    public void disconnect()
    {
		this.client.closeConnexion();
		this.fenetreConnecter.setVisible(false);
    	this.fenetreUser.setVisible(false);
    	this.setVisible(true);
    }
    //************************************
    
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
                //erreurGenerique(new JFrame(),error,"Erreur",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } while (success == null && error == null);
        return false;
    }
    public String waitForAnswerString(Client client) {
        String success, error;
        do {
            success = client.getSucessMessage();
            error = client.getErrorMessage();
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
    public void erreurGenerique(JFrame frame, String message, String titre_fenetre, int type_message) {
        //custom title, warning icon
        JOptionPane.showMessageDialog(frame, message, titre_fenetre, type_message);
    }
  //le notify de la boucle qui est lancer a partir du modele afin d'actualiser la vue et ensuite de refaire un tour de boucle vers le modele
    //cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilit� de la stopper ou non et d'agir sur son temps d'execution
    @Override
    public void update(Observable obj, Object arg) {
        if (arg instanceof Exception) {
            erreurGenerique(new JFrame(), ((Exception) arg).getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

