package vue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;

public class FenetreConnecter extends JFrame {
	private JTextField numberMessage;
	private JButton recuperer;
	private JTextArea champMessage;
	private JMenuItem deconnexion;

	public FenetreConnecter() throws HeadlessException {
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("Client");
		this.setSize(1200, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setLocationRelativeTo(null);
		setBackground(new Color(51, 102, 102));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 153));
		getContentPane().add(panel, BorderLayout.WEST);

		JLabel lblClientPop = new JLabel("Client POP3");

		JLabel label_1 = new JLabel("R\u00E9cup\u00E9rer un message :");
		label_1.setForeground(new Color(153, 0, 102));
		label_1.setFont(new Font("Verdana", Font.BOLD, 13));

		numberMessage = new JTextField();
		numberMessage.setColumns(10);

		JLabel lblNumroDuMessage = new JLabel("Num\u00E9ro du message:");

		champMessage = new JTextArea();
		champMessage.setEditable(false);
		champMessage.setRows(2);
		champMessage.setText("Messages:");

		recuperer = new JButton();
		recuperer.setText("r\u00E9cup\u00E9rer");
		recuperer.setFocusPainted(false);
		recuperer.setBackground(Color.CYAN);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panel.createSequentialGroup().addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_panel.createSequentialGroup()
														.addGap(233).addGroup(
														gl_panel.createParallelGroup(
																Alignment.TRAILING)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						label_1,
																						GroupLayout.PREFERRED_SIZE,
																						175,
																						GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addComponent(
																										lblNumroDuMessage)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										numberMessage,
																										GroupLayout.PREFERRED_SIZE,
																										38,
																										GroupLayout.PREFERRED_SIZE)))
																.addGroup(
																		gl_panel.createSequentialGroup()
																				.addComponent(
																						recuperer,
																						GroupLayout.PREFERRED_SIZE,
																						132,
																						GroupLayout.PREFERRED_SIZE)
																				.addGap(23)))
														.addPreferredGap(
																ComponentPlacement.UNRELATED)
														.addComponent(
																champMessage,
																GroupLayout.PREFERRED_SIZE,
																385,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_panel.createSequentialGroup()
														.addGap(538)
														.addComponent(
																lblClientPop)))
								.addContainerGap(646, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
						gl_panel.createSequentialGroup().addGap(56)
								.addComponent(lblClientPop).addGap(100)
								.addGroup(gl_panel.createParallelGroup(
										Alignment.LEADING)
										.addComponent(champMessage,
												GroupLayout.PREFERRED_SIZE, 180,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												gl_panel.createSequentialGroup()
														.addGap(27)
														.addComponent(label_1,
																GroupLayout.PREFERRED_SIZE,
																17,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18).addGroup(
														gl_panel.createParallelGroup(
																Alignment.BASELINE)
																.addComponent(
																		lblNumroDuMessage)
																.addComponent(
																		numberMessage,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(
																ComponentPlacement.UNRELATED)
														.addComponent(
																recuperer)))
								.addContainerGap(201, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);

		deconnexion = new JMenuItem("D\u00E9connexion");
		mnNewMenu.add(deconnexion);
	}

	public JButton getRecuperer() {
		return this.recuperer;
	}

	//public JButton getListMessage(){return this.ListMessage;}
	public JTextField getNumberMessage() {
		return this.numberMessage;
	}

	public JTextArea getChampMessage() {
		return this.champMessage;
	}

	public JMenuItem getDeconnexion() {
		return this.deconnexion;
	}

	public void reset() {
		this.champMessage.setText("Messages:");
		this.numberMessage.setText("");
	}
}