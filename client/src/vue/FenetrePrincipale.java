//package vue;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.Window;
//import java.util.Observable;
//import java.util.Observer;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JPanel;
//
//import modele.Cellule;
//import modele.Modele;
//import modele.Point;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.awt.image.BufferedImage;
//import java.awt.Component;
//import javax.swing.JLabel;
//import java.awt.Insets;
//import java.awt.Dimension;
//import javax.swing.JEditorPane;
//import javax.swing.JList;
//import javax.swing.JTextField;
//import java.awt.Font;
//import javax.swing.JComboBox;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import java.io.File;
//public class FenetrePrincipale extends JFrame implements Observer {
//	
//	private static final long serialVersionUID = 1L;
//	JPanel jpprincipal;
//	JPanel jpcases;
//	JPanel jpbord1;
//	JMenuBar menubar;
//	JMenu menu1;
//	JButton initialiser;
//	JButton desactiver;
//	JButton activer;
//	JButton canon;
//	JButton vider;
//	JComboBox listSpeed;
//	//****************************
//	Modele modele;
//	private JLabel nb_vivante;
//	private JPanel jpbord2;
//	private static final String[]list={"5","10","25","50","100","150","200","500"};
//	private JLabel lblMortes;
//	private JLabel nb_morte;
//	private JLabel motifs;
//	private JButton rechercher;
//	private String nom_fichier;
//	private String chemin_fichier;
//	private JLabel nomDuFichier;
//	private GridLayout g;
//	private JTextField textField;
//	private JTextField textField_1;
//	private JTextField textField_2;
//	
//	//-------------
//	//constructeur
//	//-------------
//	public FenetrePrincipale(Modele modele){
//		super();
//		setBackground(new Color(51, 102, 102));
//		
//		JPanel panel = new JPanel();
//		getContentPane().add(panel, BorderLayout.CENTER);
//		
//		JLabel lblBienvenue = new JLabel("Bienvenue sur le client POP3");
//		
//		textField = new JTextField();
//		textField.setColumns(10);
//		
//		JLabel lblUserName = new JLabel("Identifiant");
//		
//		JLabel lblMotDePasse = new JLabel("Mot de passe");
//		
//		textField_1 = new JTextField();
//		textField_1.setColumns(10);
//		
//		JLabel lblNomDuServeur = new JLabel("Nom du serveur");
//		
//		textField_2 = new JTextField();
//		textField_2.setColumns(10);
//		
//		JButton btnConnexion = new JButton("Connexion");
//		GroupLayout gl_panel = new GroupLayout(panel);
//		gl_panel.setHorizontalGroup(
//			gl_panel.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(348)
//					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
//						.addGroup(gl_panel.createSequentialGroup()
//							.addComponent(lblUserName)
//							.addGap(57)
//							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//						.addGroup(gl_panel.createSequentialGroup()
//							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
//								.addComponent(lblNomDuServeur)
//								.addComponent(lblMotDePasse))
//							.addGap(43)
//							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
//								.addComponent(textField_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//								.addComponent(textField_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(440)
//					.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//					.addGap(694))
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(410)
//					.addComponent(btnConnexion)
//					.addContainerGap(777, Short.MAX_VALUE))
//		);
//		gl_panel.setVerticalGroup(
//			gl_panel.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(35)
//					.addComponent(lblBienvenue)
//					.addGap(110)
//					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
//						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//						.addComponent(lblUserName))
//					.addGap(18)
//					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
//						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//						.addComponent(lblMotDePasse))
//					.addGap(18)
//					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
//						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//						.addComponent(lblNomDuServeur))
//					.addGap(26)
//					.addComponent(btnConnexion)
//					.addContainerGap(258, Short.MAX_VALUE))
//		);
//		panel.setLayout(gl_panel);
//		initComponents();
//	}
//	public JButton getRechercher() { return this.rechercher; }
//	public JComboBox getListSpeed(){return this.listSpeed;}
//	public JButton getInitialiser() { return this.initialiser; }
//	public JButton getDesactiver() { return this.desactiver; }
//	public JButton getActiver() { return this.activer; }
//	public JButton getCanon(){return this.canon;}
//	public JButton getVider(){return this.vider;}
//	private void initComponents(){		
//		this.setTitle("Client");
//		this.setSize(1200,600);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setLocationRelativeTo(null);
//	
//		this.setContentPane(jpprincipal);
//		this.setJMenuBar(menubar);
//		this.setVisible(true);
//	}
//	
//	public void rechercher()
//	{
//		
//		FenetreChoix choix=new FenetreChoix();
//		choix.setVisible(isVisible());
//		String str[]=choix.choisir();
//		if(str[0]=="rien")
//		{
//			choix.setVisible(false);
//			rechercher();
//		}
//		else
//		{
//		chemin_fichier=str[0];
//		nom_fichier=str[1];
//		nomDuFichier.setText(nom_fichier);
//		File monfichier=new File(chemin_fichier);
//		modele.traitement_image(monfichier);
//		//--------------------
//		//essai afin d'essayer de pouvoir recuperer une image de n'importe qu'elle taille et de la redimensionner 
//		//ou de l'adapter en fonction des cases disponible
//		/*
//		this.jpcases.removeAll();
//		g.setRows(modele.NB_CASES_L);
//		g.setColumns(modele.NB_CASES_C);
//		System.out.println(modele.NB_CASES_C);
//		for(int i=0; i<Modele.NB_CASES_L; i++){  
//			for(int j=0; j<Modele.NB_CASES_C; j++){
//				Point p = new Point(j, i);
//				Cellule c =  modele.getMap().get(p);
//				jpcases.add(c);
//			}
//		}
//		*/
//		//--------------------
//		}
//	}
//	//on a besoin de cette methode afin que la fenetre gere de maniere dynamique la modification de de la fenetre en pause
//	public void update_nombre()
//	{
//		nb_vivante.setText(modele.getNb_vivantes());
//		String morte=String.valueOf((modele.NB_CASES_L*modele.NB_CASES_C)-Integer.valueOf(modele.getNb_vivantes()));
//		nb_morte.setText(morte);
//		
//	}
//	//le notify de la boucle qui est lancer a partir du modele afin d'actualiser la vue et ensuite de refaire un tour de boucle vers le modele
//	//cette boucle n'est gerer que grace a la classe simulateur qui nous donne la possibilité de la stopper ou non et d'agir sur son temps d'execution
//	@Override
//	public void update(Observable obj, Object arg){	
//		System.out.println("next");
//		update_nombre();
//		for(Point p : modele.getMap().keySet()){
//			Cellule c = modele.getMap().get(p);
//			c.setBackground((c.getEtat())?Color.orange:Color.BLACK);
//		}
//	}
//}
