package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends Pane {

    private final IJeu jeu;

    private final BorderPane vueDuJeu;

    private HBox plateauIHM;
    private VuePlateau plateau;


    private HBox bottom;
    private HBox joueurCourantIHM;
    private VueJoueurCourant joueurCourant;

    private HBox autresJoueursIHM;
    private VueAutresJoueurs autresJoueurs;

    public VueDuJeu(IJeu jeu)
    {
        this.jeu = jeu;

        vueDuJeu = new BorderPane();

        //LEFT PLATEAU
        initialisePlateau();


        // BOTTOM joueurCouran
        initialiseBottom();

        getChildren().add(vueDuJeu);
    }

    private void initialisePlateau()
    {
        plateauIHM = new HBox();
        plateau = new VuePlateau();
        plateauIHM.getChildren().add(plateau);
        vueDuJeu.setLeft(plateauIHM);
    }

    public void initialiseBottom()
    {
        initialiseJoueurCourant();
        initialiseAutresJoueurs();
        bottom = new HBox(joueurCourantIHM, autresJoueursIHM);
        vueDuJeu.setBottom(bottom);
    }

    private void initialiseJoueurCourant()
    {
        joueurCourantIHM = new HBox();
        joueurCourant = new VueJoueurCourant();
        joueurCourantIHM.getChildren().add(joueurCourant);
    }

    private void initialiseAutresJoueurs()
    {
        autresJoueursIHM = new HBox();
        autresJoueurs = new VueAutresJoueurs();
        autresJoueursIHM.getChildren().add(autresJoueurs);
    }


    public void creerBindings()
    {

        plateauIHM.prefWidthProperty().bind(getScene().widthProperty().multiply(0.6));
        plateauIHM.prefHeightProperty().bind(getScene().heightProperty().multiply(0.6));

        bottom.prefHeightProperty().bind(getScene().heightProperty().multiply(0.4));

        ((HBox) bottom.getChildren().get(0)).prefWidthProperty().bind(getScene().widthProperty().multiply(0.6));
        ((HBox) bottom.getChildren().get(1)).prefWidthProperty().bind(((HBox) bottom.getChildren().get(0)).prefWidthProperty().multiply(0.4));


        vueDuJeu.minHeightProperty().bind(getScene().heightProperty());
        vueDuJeu.minWidthProperty().bind(getScene().widthProperty());



        plateau.creerBindings();
        joueurCourant.creerBindings();
        autresJoueurs.creerBindings();

    }

    public IJeu getJeu() {
        return jeu;
    }

}
