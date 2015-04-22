package vue.pop3;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//-------------
//constructeur
//-------------
public class FenetreChoix extends JFrame {
    protected String nomFichier;
    protected String[] chemin = new String[2];
    JFileChooser fileChooser;

    public FenetreChoix() {
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("Choix d'image");
        this.setSize(800, 400);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        fileChooser = new JFileChooser();
        getContentPane().add(fileChooser, BorderLayout.CENTER);
    }

    //cette fenetre a pour but de pouvoir recuperer une erreur sans que cela influe sur le modele qui doit etre stable
    public String[] choisir() {
        //le file user et a modifier selon l'utilisateur personnellement sa me permet d'acceder directement a mon dossier image
        //afin de ne pas perturber l'utilisateur cependant ici ne gerant les autre fichier vous etes obliger de choisir un fichier avant de sortir de cette fenetre
        //ce qui serait modifier en gerant le bouton annuler par la suite
        fileChooser.setCurrentDirectory(new File(
                "C:" + File.separator + "Users" + File.separator + "firethorn"
                        + File.separator + "Pictures"));
        int retour = fileChooser.showOpenDialog(null);
        try {
            if (retour == JFileChooser.APPROVE_OPTION) {
                // nom du fichier  choisi
                nomFichier = fileChooser.getSelectedFile().getName();
                // chemin absolu du fichier choisi
                chemin[0] = String.valueOf(
                        fileChooser.getSelectedFile().getAbsolutePath());
                this.setVisible(false);
                chemin[1] = nomFichier;
            } else {
                chemin[0] = "rien";
            }
            return chemin;
        } catch (Error e) {
            System.out
                    .println("Veuillez recommencer il faut choisir un fichier");
            chemin[0] = "rien";
            return chemin;
        }

    }
}
