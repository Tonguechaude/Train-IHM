package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends FlowPane {

    @FXML
    private Pane autresJoueursBox;

    @FXML
    private VBox autresJoueursList;

    private HBox nomJoueurBox;
    private HBox argentBox;

    private HBox victoireBox;

    private HBox railsBox;

    private HBox piocheBox;


    public VueAutresJoueurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AutresJoueurs.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        getChildren().add(autresJoueursBox);
    }

    public void creerBindings() {
        System.out.println("Création des bindings pour les autres joueurs...");

        // Listener pour mettre à jour la liste des autres joueurs lorsque le joueur courant change
        GestionJeu.getJeu().joueurCourantProperty().addListener(
                (source, oldJoueur, newJoueur) -> {
                    mettreAJourAutresJoueurs(newJoueur);
                }
        );

        // Initialisation de la liste des autres joueurs
        mettreAJourAutresJoueurs(GestionJeu.getJeu().joueurCourantProperty().get());
    }

    public String nomIcone(String couleur)
    {
        return switch (couleur)
        {
            case "ROUGE" ->  "red";
            case "VERT" ->  "green";
            case "JAUNE" ->  "yellow";
            case "BLEU" ->  "blue";
            default -> "pas de couleur";
        };
    }



    private void mettreAJourAutresJoueurs(IJoueur joueurCourant) {
        autresJoueursList.getChildren().clear();

        List<? extends IJoueur> joueurs = GestionJeu.getJeu().getJoueurs();
        for (IJoueur joueur : joueurs) {
            if (!joueur.equals(joueurCourant)) {
                Pane joueurPane = creerHboxJoueur(joueur);
                autresJoueursList.getChildren().add(joueurPane);
            }
        }
    }

    private HBox creerHboxJoueur(IJoueur joueur) {
        System.out.println(joueur.getCouleur().name());
        System.out.println(nomIcone(joueur.getCouleur().name()));
        System.out.println("/images/icons/cube_" + nomIcone(joueur.getCouleur().name()) + ".png");

        HBox hBox = new HBox();
        Label nomJoueur = new Label(joueur.getNom());
        Label nbArgent = new Label(String.valueOf(joueur.getArgent()));
        Label pointVictoire = new Label(String.valueOf(joueur.getScoreTotal()));
        Label nbRails = new Label(String.valueOf(joueur.pointsRailsProperty().get()));
        Label pioche = new Label(String.valueOf(joueur.piocheProperty().sizeProperty().get()));

        ImageView joueurCouleur = new ImageView(new Image("images/icons/cube_red.png", (double) 35, (double) 35, true, true ));
        ImageView imageArgent = new ImageView(new Image("images/Perso/JoueurCourant/icone_argent.png", (double) 35, (double) 35, true, true));
        ImageView imageVictoire = new ImageView(new Image("images/Perso/JoueurCourant/star.png", (double) 35, (double) 35, true, true));
        ImageView imageRails = new ImageView(new Image("images/Perso/JoueurCourant/rail.png", (double) 35, (double) 35, true, true));
        ImageView imagePioche = new ImageView(new Image("images/Perso/JoueurCourant/deck.png", (double) 35, (double) 35, true, true));

        nomJoueurBox = new HBox(nomJoueur, joueurCouleur);
        nomJoueurBox.setAlignment(Pos.CENTER);
        argentBox = new HBox(nbArgent, imageArgent);
        argentBox.setAlignment(Pos.CENTER);
        victoireBox = new HBox(pointVictoire, imageVictoire);
        victoireBox.setAlignment(Pos.CENTER);
        railsBox = new HBox(nbRails, imageRails);
        railsBox.setAlignment(Pos.CENTER);
        piocheBox = new HBox(pioche, imagePioche);
        piocheBox.setAlignment(Pos.CENTER);


        hBox.getChildren().addAll(nomJoueurBox, argentBox, victoireBox, railsBox, piocheBox);
        hBox.setSpacing(10);

        return hBox;
    }
}







