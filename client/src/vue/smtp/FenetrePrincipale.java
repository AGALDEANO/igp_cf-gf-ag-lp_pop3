package vue.smtp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import base.client.impl.SmtpClient;
import base.email.EmailHeader;
import base.email.Header;
import exception.ErrorResponseServerException;
import exception.UnrespondingServerException;

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
		lblBienvenue.setForeground(new Color(255, 153, 0));
		
		envoie = new JButton();
		envoie.setText("Envoyer");
		envoie.setFocusPainted(false);
		envoie.setBackground(Color.CYAN);
		
		txtA = new JTextField();
		txtA.setText("Destinataire");
		txtA.setColumns(10);
		
		txtObjet = new JTextField();
		txtObjet.setText("Objet");
		txtObjet.setColumns(10);
		
		txtCc = new JTextField();
		txtCc.setText("CC");
		txtCc.setColumns(10);
		
		txtCci = new JTextField();
		txtCci.setText("CCi");
		txtCci.setColumns(10);
		
		txtFrom = new JTextField();
		txtFrom.setText("From");
		txtFrom.setColumns(10);
		
		message = new JTextArea();
		message.setText("Message:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(522)
					.addComponent(envoie, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(564, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(327)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(message, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(txtA, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(txtObjet, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(txtFrom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtCc, Alignment.LEADING)
							.addComponent(txtCci, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(105)
							.addComponent(lblBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(140)))
					.addGap(361))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(53)
					.addComponent(lblBienvenue)
					.addGap(18)
					.addComponent(txtFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(txtCc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtCci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtObjet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(message, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(envoie, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		panel.setLayout(gl_panel);
		initComponents();
		fenetreConnexion();
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
	        	if(!this.txtObjet.getText().equals("")&&!this.txtObjet.getText().equals("Objet"))
	        	{
	        		headers.add(new EmailHeader(Header.SUBJECT, this.txtObjet.getText()));
	        	}
	        	if(!this.txtCc.getText().equals("")&&!this.txtCc.getText().equals("CC"))
	        	{
	        		headers.add(new EmailHeader(Header.CC, this.txtCc.getText()));
	        	}
	        	if(!this.txtCci.getText().equals("")&&!this.txtCci.getText().equals("CCi"))
	        	{
	        		headers.add(new EmailHeader(Header.BCC, this.txtCci.getText()));
	        	}
				smtpClient.sendEmail(body,headers);
			} catch (ErrorResponseServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnrespondingServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        waitForAnswer(smtpClient);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrespondingServerException e) {
			// TODO Auto-generated catch block
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

	
}

