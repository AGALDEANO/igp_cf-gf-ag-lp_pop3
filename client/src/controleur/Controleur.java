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
                String nomServeur = fp.getNomServeur().getText();
                String port = fp.getPort().getText();
                if (fp.connexionServeur(nomServeur,port)) {
                    fp.connecterServeur();
                }
            }
        });
        fp.getConnexionUser().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String username = fp.getIdentifiant().getText();
                if (fp.connexionClient(username)) {
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
        new Controleur();
    }
}
