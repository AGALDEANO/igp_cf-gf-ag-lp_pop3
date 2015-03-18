package vue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;

public class FenetreUser extends JFrame {
	private JTextField identifiant;
	private JButton connexionUser;
	private JMenuItem deconnexion;
	private JPasswordField password;

	public FenetreUser() throws HeadlessException {
		initComponents();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 102));
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblUtilisateur = new JLabel("Utilisateur:");
		lblUtilisateur.setForeground(new Color(0, 0, 128));

		identifiant = new JTextField();
		identifiant.setColumns(10);

		connexionUser = new JButton();
		connexionUser.setText("Connexion");
		connexionUser.setFocusPainted(false);
		connexionUser.setBackground(Color.CYAN);

		JLabel label_1 = new JLabel("Bienvenue sur le client POP3");
		label_1.setForeground(new Color(0, 0, 128));

		JLabel lblMotDePasse = new JLabel("Mot de passe:");
		lblMotDePasse.setForeground(new Color(0, 0, 128));

		password = new JPasswordField();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panel.createSequentialGroup().addGroup(
								gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(
												gl_panel.createSequentialGroup()
														.addGap(459)
														.addComponent(label_1,
																GroupLayout.DEFAULT_SIZE,
																146,
																Short.MAX_VALUE))
										.addGroup(
												gl_panel.createSequentialGroup()
														.addGap(433).addGroup(
														gl_panel.createParallelGroup(
																Alignment.LEADING)
																.addComponent(
																		lblUtilisateur,
																		Alignment.TRAILING)
																.addComponent(
																		lblMotDePasse,
																		Alignment.TRAILING))
														.addPreferredGap(
																ComponentPlacement.UNRELATED)
														.addGroup(
																gl_panel.createParallelGroup(
																		Alignment.LEADING,
																		false)
																		.addComponent(
																				password)
																		.addComponent(
																				identifiant))
														.addGap(14)))
								.addGap(589)).addGroup(
						gl_panel.createSequentialGroup().addGap(479)
								.addComponent(connexionUser,
										GroupLayout.PREFERRED_SIZE, 108,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(607, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panel.createSequentialGroup().addGap(112)
								.addComponent(label_1).addGap(50).addGroup(
								gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUtilisateur)
										.addComponent(identifiant,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(13).addGroup(
								gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblMotDePasse)
										.addComponent(password,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(18).addComponent(connexionUser,
								GroupLayout.PREFERRED_SIZE, 22,
								GroupLayout.PREFERRED_SIZE)
								.addContainerGap(309, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		this.setTitle("Client");
		this.setSize(1200, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setLocationRelativeTo(null);
		setBackground(new Color(51, 102, 102));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		deconnexion = new JMenuItem("D\u00E9connexion");
		menu.add(deconnexion);
	}

	public JTextField getIdentifiant() {
		return this.identifiant;
	}

	public JTextField getPassword() {
		return this.password;
	}

	public JButton getConnexionUser() {
		return this.connexionUser;
	}

	public JMenuItem getDeconnexion() {
		return this.deconnexion;
	}

	public void resetPassword() {
		this.password.setText("");
	}
}
