package controleur;

import base.client.ClientObservable;
import base.client.ClientThrowable;
import vue.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//dans cette classe le controleur va contenir le modele et la vue
//afin d'avoir un acces a ces 2 classe en direct
//ainsi qu'un simulateur qui va nous permettre d'avoir le temps d'execution ainsi que les fontions pause et play
public class Controleur {
    ClientThrowable client;
    ClientObservable clientObs;
    FenetrePrincipale fp;

    //-------------
    //constructeur
    //-------------
    public Controleur() {
        client = new ClientThrowable();
        clientObs = new ClientObservable(client);
        fp = new FenetrePrincipale(clientObs);
        clientObs.addObserver(fp);
        client.start();
        //evenement pour l'evenement du bouton connexion
        fp.getConnexion().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String username = fp.getIdentifiant().getText();
                String password = fp.getMotDePasse().getText();
                String nomServeur = fp.getNomServeur().getText();
                fp.connecter();
//				if(fp.connexionServeur(nomServeur))
//				{
//					if(fp.connexionClient(username,password))
//					{
//						fp.connecter();
//					}
//				}
            }
        });
    }

    //programme principale qui instancie juste le controleur qui va generer tout le reste
    public static void main(String[] args) {
        new Controleur();
    }
}
