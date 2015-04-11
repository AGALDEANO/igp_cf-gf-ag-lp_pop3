package controleur.pop3;

import base.client.impl.Pop3Client;
import vue.pop3.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//dans cette classe le controleur va contenir le modele et la vue
//afin d'avoir un acces a ces 2 classe en direct
//ainsi qu'un simulateur qui va nous permettre d'avoir le temps d'execution ainsi que les fontions pause et play
public class ControleurPop3 {
	Pop3Client pop3Client;
	FenetrePrincipale fp;

	//-------------
	//constructeur
	//-------------
	public ControleurPop3() {
		pop3Client = new Pop3Client();
		fp = new FenetrePrincipale(pop3Client);
		//evenement pour l'evenement du bouton connexion
		fp.getConnexion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String nomServeur = fp.getNomServeur().getText();
				String port = fp.getPort().getText();
				if (fp.connexionServeur(nomServeur, port)) {
					fp.connecterServeur();
				}
			}
		});
		fp.getConnexionUser().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String username = fp.getIdentifiant().getText();
				String password = fp.getPassword().getText();
				if (fp.connexionClient(username, password)) {
					fp.connecter();
				}

			}
		});
		fp.getRecuperer().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String number_message = fp.getNumberMessage().getText();
				fp.recupereMessage(number_message);

			}
		});
		fp.getDeconnexion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				fp.disconnect();
			}
		});
		fp.getDeconnexionUser().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				fp.disconnect();
			}
		});
	}

	//programme principale qui instancie juste le controleur qui va generer tout le reste
	public static void main(String[] args) {
		new ControleurPop3();
	}
}
