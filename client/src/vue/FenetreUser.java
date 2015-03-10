package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FenetreUser extends JFrame
{
	private JTextField identifiant;
	private JButton connexionUser;

	public FenetreUser() throws HeadlessException
	{
		initComponents();
	}

	private void initComponents()
	{
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 102));
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblUtilisateur = new JLabel("Utilisateur:");
		lblUtilisateur.setForeground(new Color(0, 0, 128));
		
		identifiant = new JTextField();
		identifiant.setColumns(10);
		
		connexionUser= new JButton();
		connexionUser.setText("Connexion");
		connexionUser.setFocusPainted(false);
		connexionUser.setBackground(Color.CYAN);
		
		JLabel label_1 = new JLabel("Bienvenue sur le client POP3");
		label_1.setForeground(new Color(0, 0, 128));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 1194, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblUtilisateur)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(22)
									.addComponent(connexionUser, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(459)
							.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
					.addGap(589))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 572, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(112)
					.addComponent(label_1)
					.addGap(50)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(identifiant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUtilisateur))
					.addGap(26)
					.addComponent(connexionUser, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(328, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		this.setTitle("Client");
		this.setSize(1200,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setLocationRelativeTo(null);
		setBackground(new Color(51, 102, 102));
	}
	public JTextField getIdentifiant(){return this.identifiant;}
	public JButton getConnexionUser(){return this.connexionUser;}

}