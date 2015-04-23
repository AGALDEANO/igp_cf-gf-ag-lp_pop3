package vue.smtp;

import base.client.impl.SmtpClient;
import base.email.EmailHeader;
import base.email.Header;
import exception.ErrorResponseServerException;
import exception.UnrespondingServerException;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;
	//****************************
	private SmtpClient smtpClient;
	private JButton envoie;
	private JTextField txtA;
	private JTextField txtObjet;
	private JTextField txtCc;
	private JTextField txtCci;
	private JTextField txtFrom;
	private JTextArea message;
	private FenetreConnexion fenetreConnexion;
	private JLabel lblCc;
	private JLabel lblDestinataire;
	private JLabel lblFrom;

	//-------------
	//constructeur
	//-------------
	public FenetrePrincipale(SmtpClient smtpClient) {
		super();
		this.fenetreConnexion=new FenetreConnexion();
		this.smtpClient = smtpClient;
		setResizable(false);
		setBackground(new Color(51, 102, 102));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 204));
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblBienvenue = new JLabel("Bienvenue sur le client SMTP");
		lblBienvenue.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBienvenue.setForeground(new Color(0, 0, 0));
		
		envoie = new JButton();
		envoie.setText("Envoyer");
		envoie.setFocusPainted(false);
		envoie.setBackground(Color.CYAN);
		
		txtA = new JTextField();
		txtA.setColumns(10);
		
		txtObjet = new JTextField();
		txtObjet.setColumns(10);
		
		txtCc = new JTextField();
		txtCc.setColumns(10);
		
		txtCci = new JTextField();
		txtCci.setColumns(10);
		
		txtFrom = new JTextField();
		txtFrom.setColumns(10);
		
		message = new JTextArea();
		message.setText("Message:");
		
		JLabel lblObjet = new JLabel("Objet:");
		
		JLabel lblCci = new JLabel("CCi:");
		
		lblCc = new JLabel("CC:");
		
		lblDestinataire = new JLabel("Destinataire:");
		
		lblFrom = new JLabel("From:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(522)
					.addComponent(envoie, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(564, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(240)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblObjet)
						.addComponent(lblCci, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCc, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDestinataire)
						.addComponent(lblFrom, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(message, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
						.addComponent(txtA, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
						.addComponent(txtObjet, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
						.addComponent(txtFrom, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtCc, Alignment.LEADING)
							.addComponent(txtCci, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(105)
							.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
							.addGap(140)))
					.addGap(361))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(53)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblObjet)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblBienvenue)
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFrom))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDestinataire))
							.addGap(14)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtCc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCc))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtCci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCci))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtObjet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(message, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(envoie, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		panel.setLayout(gl_panel);
		initComponents();
		connecterServeur();
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
	public JButton getConnexion() {return this.fenetreConnexion.getConnexion();}
	public JTextField getPort(){return this.fenetreConnexion.getPort();}
	public JTextField getNomServeur(){return this.fenetreConnexion.getNomServeur();}
	public JButton getEnvoie(){return this.envoie;}
	public JTextField getFrom(){return this.txtFrom;}
	public JTextField getTo(){return this.txtA;}
	public JTextField getCc(){return this.txtCc;}
	public JTextField getCci(){return this.txtCci;}
	public JTextField getObject(){return this.txtObjet;}
	public JTextArea getMessage(){return this.message;}
	//************************************



	//CONNEXION ENTRE LES FENETRE
	//************************************

	//************************************
	public void sendMail()
	{
		String response=verify();
		if(response.equals(""))
		{
			String body = this.message.getText();
	        body = body
	                .replaceAll("\r\n", "\n")
	                .replaceAll("\n", "\r\n")
	                .replaceAll("\r\n.\r\n", "\r\n.\n");
	        try {
	        	ArrayList<EmailHeader> headers=new ArrayList<EmailHeader>();
	        	headers.add(new EmailHeader(Header.FROM, this.txtFrom.getText()));
	        	headers.add(new EmailHeader(Header.TO,this.txtA.getText()));
	        	if(!this.txtObjet.getText().equals(""))
	        	{
	        		headers.add(new EmailHeader(Header.SUBJECT, this.txtObjet.getText()));
	        	}
	        	if(!this.txtCc.getText().equals(""))
	        	{
	        		headers.add(new EmailHeader(Header.CC, this.txtCc.getText()));
	        	}
	        	if(!this.txtCci.getText().equals(""))
	        	{
	        		headers.add(new EmailHeader(Header.BCC, this.txtCci.getText()));
	        	}
				smtpClient.sendEmail(body,headers);
			} catch (ErrorResponseServerException e) {
				this.erreurGenerique(new JFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (UnrespondingServerException e) {
				this.erreurGenerique(new JFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
	        if(waitForAnswer(smtpClient))
	        	{reset();}
		}
		else
		{
			this.erreurGenerique(new JFrame(), "Veuillez renseignez le champ"+response,"Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	public String verify()
	{
		if(this.txtFrom.getText().equals("") || this.txtFrom.getText().equals("From"))
		{
			return "From";
		}
		if(this.txtA.getText().equals("") || this.txtFrom.getText().equals("Destinataire"))
		{
			return "Destinataire";
		}
		return "";
	}
	public boolean ouvertureConnexion(String host,int port)
	{
		try {
			smtpClient.openConnexion(host,port);
		} catch (ErrorResponseServerException e) {
			this.erreurGenerique(new JFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (UnrespondingServerException e) {
			this.erreurGenerique(new JFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return waitForAnswer(smtpClient);
	}
	public boolean waitForAnswer(SmtpClient smtpClient) {
		String success, error;
		do {
			success = smtpClient.getSucessMessage();
			error = smtpClient.getErrorMessage();
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

	public String waitForAnswerString(SmtpClient smtpClient) {
		String success, error;
		do {
			success = smtpClient.getSucessMessage();
			error = smtpClient.getErrorMessage();
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

	public void connecterServeur() {
		this.setVisible(true);
		this.fenetreConnexion.setVisible(false);
	}
	public void fenetreConnexion()
	{
		this.setVisible(false);
		this.fenetreConnexion.setVisible(true);
	}
	public void reset()
	{
		this.txtA.setText("");
		this.txtCc.setText("");
		this.txtCci.setText("");
		this.txtFrom.setText("");
		this.txtObjet.setText("");
		this.message.setText("");
		connecterServeur();
	}
}

